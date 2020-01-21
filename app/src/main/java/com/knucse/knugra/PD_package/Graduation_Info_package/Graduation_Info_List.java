package com.knucse.knugra.PD_package.Graduation_Info_package;

import com.knucse.knugra.DM_package.Database;
import com.knucse.knugra.PD_package.User_package.Student_package.Student;
import com.knucse.knugra.PD_package.User_package.Student_package.StudentCareer;
import com.knucse.knugra.PD_package.User_package.Student_package.StudentCareerList;
import com.knucse.knugra.PD_package.User_package.User;
//subject test
import com.knucse.knugra.PD_package.Subject_package.Subject;
import com.knucse.knugra.PD_package.Subject_package.SubjectList;
import java.lang.String;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class Graduation_Info_List extends ArrayList<Graduation_Info>{
    private static volatile Graduation_Info_List instance;

    private Graduation_Info_List() {
    }

    public static Graduation_Info_List getInstance() {
        if (instance == null) {
            instance = new Graduation_Info_List();
            //Database.getGraduationInfoList();
            Database.getGraduationInfoList_temp();
        }

        return instance;
    }

    //남은 졸업자격정보 열람 요청(졸업트랙)
    // (전체조건)/(학생이 완수한조건)
    public static ArrayList<String[]> Graduation_Info_compare(final String selectedTrack){
        int i;
        boolean found;
        float totalSuccess_rate = 0;
        int totalcount = 0;
        String userContentString;
        StudentCareer scobject = null;
        ArrayList<String[]> returnValueList = new ArrayList<>();
        String[] element;
        //list가 전체정보
        //학생정보받아와서
        Student current_student =(Student)(User.getInstance().getUserData());//현재 로그인 한 student 정보
        StudentCareerList student_career=current_student.getStudentCareerList();//학생career가져오기
        Graduation_Info_List std_career=getInstance();
        Graduation_Info std_track=new Graduation_Info();
        int userdata, stddata;
        Float success_rate;

        //Design & Required
        //float design_rate=0, design_std=0;
        //float required_rate=0, required_std=0;
        ArrayList<String[]> resultDesign = std_career.Subject_Design_check();
        ArrayList<String[]> resultRequired = std_career.Subject_Required_check();

        //해당 트랙 정보가져오기
        Iterator<Graduation_Info> std = std_career.iterator();
        while(std.hasNext()){
            std_track=std.next();
            if(selectedTrack.equals(std_track.info_track))
                break;
        }
        //비교(전체 갯수 count, 완수한 갯수 count)
        for(i=0;i<std_track.size();i++) {
            Iterator<StudentCareer> sc=student_career.iterator();
            // reset found
            found = false;
            while(sc.hasNext()){
                scobject = sc.next();
                if(scobject.getName().equals(std_track.get(i).getName())){
                        found = true;
                        break;
                }
            }

            try {
                stddata = Integer.parseInt(std_track.get(i).getContent());//content가 숫자
                if(found == true) {
                    userdata = Integer.parseInt(scobject.getContent());
                    userContentString = scobject.getContent();
                } else {
                    userdata = 0;
                    userContentString = "0";
                }

                if (userdata > stddata) {
                    success_rate = new Float(1.0);

                } else {
                    success_rate = (float) userdata/(float) stddata;
                }
                // 영어성적의 경우
                if (Integer.valueOf(std_track.get(i).getContent()) > 500) {
                    totalSuccess_rate += success_rate*15;
                    totalcount += 15;
                } else {
                    totalSuccess_rate += success_rate*Float.valueOf(std_track.get(i).getContent());
                    totalcount += Integer.valueOf(std_track.get(i).getContent());
                }

                int temp = (int)(success_rate*100);


                element = new String[]{std_track.get(i).getName(), std_track.get(i).getContent(), userContentString, new Integer(temp).toString() + "%"};


                returnValueList.add(element);


            }catch(NumberFormatException e) {
                //NumberFormatException=숫자형태가 아닌 문자열
            }
        }

        //설계과목 count design_rate, design_std
        returnValueList.add(resultDesign.get(0));
        String[] Design = resultDesign.get(0);
        String Design_std = Design[1], Design_user = Design[2];
        totalSuccess_rate += Float.valueOf(Design_user);
        totalcount += Integer.getInteger(Design_std);
        //totalSuccess_rate += design_rate*design_std;
        //totalcount += (int)(design_std);

        //필수과목 count
        returnValueList.add(resultRequired.get(0));
        String[] Required = resultRequired.get(0);
        String Required_std = Required[1], Required_user = Required[2];
        totalSuccess_rate += Float.valueOf(Required_user);
        totalcount += Integer.getInteger(Required_std);
        //totalSuccess_rate += required_rate*required_std;
        //totalcount += (int)(required_std);

        int a = (int)((totalSuccess_rate/totalcount) * 100);
        element = new String[]{"총  합", "", "", new Integer(a).toString() + "%"};
        returnValueList.add(0, element);
        return returnValueList;
    }


    //졸업요건정보 검색(졸업트랙)//
    public static ArrayList<String[]> Graduation_Info_search(final String selectedTrack) {
        int i;
        Graduation_Info_List std_career = getInstance();
        Graduation_Info std_track = new Graduation_Info();
        ArrayList<String[]> returnList = new ArrayList<>();
        String[] element;

        //해당트랙정보 가져오기
        Iterator<Graduation_Info> std = std_career.iterator();
        while (std.hasNext()) {
            std_track = std.next();

            if (selectedTrack.equals(std_track.info_track))
                break;

        }
        //문자열로 반환해주기
        for(i=0;i<std_track.size();i++) {
            element = new String[]{std_track.get(i).getName(), std_track.get(i).getContent()};
            returnList.add(element);
        }
        return returnList;
    }

    //설계과목 이수여부확인
    public static ArrayList<String[]> Subject_Design_check(){
        String subject_type = "설계과목";
        float success_rate;
        int success;
        boolean found;
        //결과 값 저장공간
        ArrayList<String[]> returnValueList = new ArrayList<>();
        String[] element;
        int user_data, sub_data;
        //학생정보
        Student now_student =(Student)(User.getInstance().getUserData());//현재 로그인 한 student 정보
        SubjectList student_design = now_student.getDesignSubjectList();//학생 설계과목 이수현황 가져오기
        //(표준) 설계과목정보
        SubjectList sub_list = Database.getDesignSubjectList();

        //설계과목 각각 가져와서 비교
        Set<String> sub_keys = sub_list.keySet();
        Iterator<String> sub_key = sub_keys.iterator();
        user_data=0;//이수한 설계과목수
        sub_data=0;//모든 설계과목 수
        while(sub_key.hasNext()){
            //과목코드로 이수확인
            found = false;//찾기 전 초기화
            sub_data++;
            String now_key=sub_key.next();
            if(student_design.containsKey(now_key)){//이수 했을 경우
                found = true;
                user_data++;
                Subject now_sub = student_design.get(now_key);
                element = new String[]{now_key, now_sub.get("교과목명"), "O"};
                returnValueList.add(element);
            }
            else{//이수 안했을 경우
                //대체 과목 여부 확인
                Subject sub_info = sub_list.get(now_key);
                String replace_key = sub_info.get("대체교과목번호");
                if(student_design.containsKey(replace_key)){
                    found=true;
                    user_data++;
                    element = new String[]{replace_key, sub_info.get("교과목명"), "O(대체)"};
                    returnValueList.add(element);
                }
                else if(found==false) {//모두(원래, 대체) 이수 안했을 경우
                    Subject now_sub = sub_list.get(now_key);
                    element = new String[]{now_key, now_sub.get(now_key) , "X"};
                    returnValueList.add(element);
                }
            }
        }
        success_rate = (float)(user_data) / (float)(sub_data);
        success=(int)(success_rate*100);
        element = new String[]{subject_type, new Integer(sub_data).toString(),new Integer(user_data).toString(), success + "%"};
        returnValueList.add(0, element);

        //design_success=success_rate;
        //design_std=sub_data;

        return returnValueList;
    }
    //필수과목 이수여부확인
    public static ArrayList<String[]> Subject_Required_check(){
        String subject_type = "필수과목";
        float success_rate;
        int success;
        boolean found;
        //결과 값 저장공간
        ArrayList<String[]> returnValueList = new ArrayList<>();
        String[] element;
        int user_data, sub_data;
        //학생정보
        Student now_student =(Student)(User.getInstance().getUserData());//현재 로그인 한 student 정보
        SubjectList student_required = now_student.getRequiredSubjectList();//학생 필수과목 이수현황 가져오기
        //(표준) 필수과목정보
        SubjectList sub_list = Database.getRequiredSubjectList();

        //필수과목 각각 가져와서 비교
        Set<String> sub_keys = sub_list.keySet();
        Iterator<String> sub_key = sub_keys.iterator();
        user_data=0;//이수한 필수과목수
        sub_data=0;//모든 필수과목 수
        while(sub_key.hasNext()){
            found = false;//찾기 전 초기화
            //과목코드로 이수확인
            sub_data++;
            String now_key=sub_key.next();
            if(student_required.containsKey(sub_key.next())){//이수 했을 경우
                user_data++;
                Subject now_sub = student_required.get(now_key);
                element = new String[]{now_key, now_sub.get(now_key) + "O"};
                returnValueList.add(element);
            }
            else{//이수 안했을 경우
                Subject sub_info = sub_list.get(now_key);
                String replace_key = sub_info.get("대체교과목번호");
                if(student_required.containsKey(replace_key)){
                    found=true;
                    user_data++;
                    element = new String[]{replace_key, sub_info.get("교과목명"), "O(대체)"};
                    returnValueList.add(element);
                }
                else if(found==false) {//모두(원래, 대체) 이수 안했을 경우
                    Subject now_sub = sub_list.get(now_key);
                    element = new String[]{now_key, now_sub.get(now_key) + "X"};
                    returnValueList.add(element);
                }
            }
        }

        success_rate = (float)(user_data) / (float)(sub_data);
        success=(int)(success_rate*100);
        element = new String[]{subject_type, new Integer(sub_data).toString(),new Integer(user_data).toString(), success + "%"};
        returnValueList.add(0, element);

        //required_success=success_rate;
        //required_std=sub_data;

        return returnValueList;
    }
    //졸업요건정보 업데이트()
    //졸업요건정보 추가()
}
