package com.knucse.knugra.PD_package.Graduation_Info_package;


import com.knucse.knugra.DM_package.DAPATH;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import static com.knucse.knugra.DM_package.DAPATH.*;

public class Graduation_Info_List extends ArrayList<Graduation_Info>{
    private static volatile Graduation_Info_List instance;

    private static ArrayList<String[]> resultDesign;

    private static ArrayList<String[]> resultRequired;

    private static ArrayList<String[]> resultCommonMajor;

    private static ArrayList<String[]> resultGeneral;

    public static ArrayList<String[]> getResultDesign() {
        return resultDesign;
    }

    public static ArrayList<String[]> getResultRequired() {
        return resultRequired;
    }

    public static ArrayList<String[]> getResultCommonMajor() {
        return resultCommonMajor;
    }

    public static ArrayList<String[]> getResultGeneral() {
        return resultGeneral;
    }


    private Graduation_Info_List() {
    }

    public static Graduation_Info_List getInstance() {
        if (instance == null) {
            instance = new Graduation_Info_List();
        }

        return instance;
    }

    //남은 졸업자격정보 열람 요청(졸업트랙)
    // (전체조건)/(학생이 완수한조건)
    public static ArrayList<String[]> Graduation_Info_compare(final String selectedTrack){
        int i;
        boolean found;
        float totalSuccess_rate = 0;
        int totalcount = 0, resultStartup=-1;

        String userContentString;
        StudentCareer scobject = null;
        ArrayList<String[]> returnValueList = new ArrayList<>();
        String[] element;
        //list가 전체정보
        //학생정보
        Student current_student =(Student)(User.getInstance().getUserData());//현재 로그인 한 student 정보
        StudentCareerList student_career=current_student.getStudentCareerList();//학생career가져오기
        //
        Graduation_Info_List std_career = getInstance();
        Graduation_Info std_track=new Graduation_Info();
        int userdata, stddata;
        Float success_rate;
        //초기화
        ArrayList<String[]> result = null;
        resultDesign = null;
        resultRequired = null;
        resultCommonMajor = null;
        resultGeneral = null;

        //해당 트랙 정보가져오기
        Iterator<Graduation_Info> std = std_career.iterator();
        while(std.hasNext()){
            std_track=std.next();
            if(selectedTrack.equals(std_track.info_track)) {
                break;
            }
        }

        //학생 career에 결과가 있는 항목
        for(i=0;i<std_track.size();i++) {
            Iterator<StudentCareer> sc=student_career.iterator();
            // reset found
            found = false;
            while(sc.hasNext()){ //해당 요건 찾기
                scobject = sc.next();
                if(scobject.getName().equals(std_track.get(i).getName())) { //학생 career에 같은 항목 있는 경우
                    found = true;
                    break;
                }
            }

            //함수필요(과목이수관련)
            if (found==false && needFunctionCheck(std_track.get(i).getName())==true){
                found = true;
                result = null;
            }

            try {
                if(std_track.get(i).getName().equals(GRAINFO_ENGLISH)){
                    stddata=700;
                }
                else{
                    stddata = Integer.parseInt(std_track.get(i).getContent());//content가 숫자
                }
                //

                if(found == true) {
                    if(std_track.get(i).getName().equals(GRAINFO_ENGLISH)){//영어성적은 pass/fail로 구별됨
                        System.out.println("영어"+scobject.getContent());
                        if(scobject.getContent().equals("pass")){
                            userdata = 700;
                        }
                        else{
                            userdata = 0;
                        }
                        userContentString = scobject.getContent();
                    }
                    else if(needFunctionCheck(std_track.get(i).getName())){//함수로 계산 필요한 경우 (과목 이수여부 check)
                        switch(std_track.get(i).getName()){
                            case GRAINFO_COMMON_MAJOR:
                                resultCommonMajor = std_career.Subject_Required_check(SOFTWARE_COMBINED_COMMON_MAJOR, stddata);
                                result = resultCommonMajor;
                                break;
                            case GRAINFO_GENERAL:
                                resultGeneral = std_career.Subject_Required_check(SOFTWARE_COMBINED_GENERAL, stddata);
                                result = resultGeneral;
                                break;
                            case GRAINFO_DESIGN:
                                resultDesign = std_career.Subject_Design_check(stddata);
                                result = resultDesign;
                                break;
                            case GRAINFO_STARTUP:
                                resultStartup = std_career.Subject_Startup_check();
                                break;
                        }
                        //
                        if(result!=null){
                            String[] Result = result.get(0);
                            String Result_user = Result[2];

                            userdata = Integer.parseInt(Result_user);
                        }
                        else{
                            userdata=resultStartup;
                        }
                        userContentString = new Integer(userdata).toString();
                    }
                    else {
                        userdata = Integer.parseInt(scobject.getContent());
                        userContentString = new Integer(userdata).toString();
                    }
                }
                else { //학생이 아무것도 안 했을 경우
                    userdata = 0;
                    userContentString = "0";
                    if(std_track.get(i).getName().equals(GRAINFO_ENGLISH)){
                        userContentString = "fail";
                    }
                }

                //비율계산
                if (userdata > stddata) {
                    success_rate = new Float(1.0);

                } else {
                    success_rate = (float) userdata/(float) stddata;
                }

                // 영어성적의 경우
                if (std_track.get(i).getName().equals(GRAINFO_ENGLISH)) {
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

        //학생career에 없고 이수 여부로 check하는 경우
        //심컴abeek과 글솦 필수는 표준갯수가 아니라 모두 들어야함
        switch(std_track.info_track){
            case COMPUTPER_ABEEK:
            case GLOBAL_SOFTWARE_DOUBLE_MAJOR:
            case GLOBAL_SOFTWARE_OVERSEAS_UNIV:
            case GLOBAL_SOFTWARE_MASTERS_CHAINING:
                resultRequired = std_career.Subject_Required_check(std_track.info_track, -1);
                break;
            default:
                resultRequired = null;
                break;
        }

        //필수과목 count
        if(resultRequired!=null) {
            returnValueList.add(resultRequired.get(0));
            String[] Required = resultRequired.get(0);
            String Required_std = Required[1], Required_user = Required[2];

            Float user_required_score = Float.parseFloat(Required_user);
            int std_required_score = Integer.parseInt(Required_std);
            if (user_required_score > std_required_score) {
                totalSuccess_rate += std_required_score;
            } else
                totalSuccess_rate += user_required_score;

            totalcount += std_required_score;
        }

        int a = (int)((totalSuccess_rate/totalcount) * 100);
        element = new String[]{"총  합", "", "", new Integer(a).toString() + "%"};
        returnValueList.add(0, element);
        return returnValueList;
    }


    //졸업요건정보 검색(졸업트랙)//
    public static ArrayList<ArrayList<String[]>> Graduation_Info_search(final String selectedTrack) {
        int i;
        Graduation_Info_List std_career = getInstance();
        Graduation_Info std_track = new Graduation_Info();
        ArrayList<String[]> returnList = new ArrayList<>();
        ArrayList<ArrayList<String[]>> returnList2= new ArrayList<>(); //test
        String[] element;
        SubjectList required_subject = null;
        SubjectList designed_subject = null;

        //해당트랙정보 가져오기
        Iterator<Graduation_Info> std = std_career.iterator();
        while (std.hasNext()) {
            std_track = std.next();
            if (selectedTrack.equals(std_track.info_track)) {
                required_subject=Database.getRequiredSubjectList(std_track.info_track); //track에 해당하는 필수과목
                if(std_track.info_track.compareTo(COMPUTPER_ABEEK)==0){
                    designed_subject=Database.getDesignSubjectList();
                }
                break;
            }

        }
        //문자열로 반환해주기
        for(i=0;i<std_track.size();i++) {
            element = new String[]{std_track.get(i).getName(), std_track.get(i).getContent()};
            returnList.add(element);
        }
        returnList2.add(returnList);
        //필수과목 정보 문자열로 반환
        if (required_subject != null) {
            returnList = new ArrayList<>();
            Set<String> required_keys = required_subject.keySet();
            Iterator<String> required_key = required_keys.iterator();
            while (required_key.hasNext()) {
                Subject nowsubject = required_subject.get(required_key.next());
                if (nowsubject.get(SUBJECT_REPLACE) == null || nowsubject.get(SUBJECT_REPLACE).compareTo("") == 0 || nowsubject.get(SUBJECT_REPLACE).compareTo(" ") == 0) {
                    element = new String[]{nowsubject.get(SUBJECT_NAME)};
                    returnList.add(element);
                }
            }
            returnList2.add(returnList);
        }

        //설계과목 정보 문자열로 반환 - 심컴abeek만 해당
        if (designed_subject != null) {
            returnList = new ArrayList<>();
            Set<String> designed_keys = designed_subject.keySet();
            Iterator<String> designed_key = designed_keys.iterator();
            while (designed_key.hasNext()) {
                element = new String[]{designed_subject.get(designed_key.next()).get(SUBJECT_NAME)};
                returnList.add(element);
            }
            returnList2.add(returnList);
        }

        return returnList2;
    }

    //설계과목 이수여부확인
    private static ArrayList<String[]> Subject_Design_check(int sub_data){//대체과목없음, 이수여부 리스트 필요, 학점으로 계산
        String subject_type = "설계과목";
        float success_rate;
        int success;
        //결과 값 저장공간
        ArrayList<String[]> returnValueList = new ArrayList<>();
        String[] element;
        int user_data;
        //학생정보
        Student now_student =(Student)(User.getInstance().getUserData());//현재 로그인 한 student 정보
        SubjectList student_design = now_student.getCompletedSubjectList();//학생 이수현황 가져오기
        //(표준) 설계과목정보
        SubjectList sub_list = Database.getDesignSubjectList();

        //설계과목 각각 가져와서 비교
        Set<String> sub_keys = sub_list.keySet();
        Iterator<String> sub_key = sub_keys.iterator();
        user_data=0;//이수한 설계과목 학점

        while(sub_key.hasNext()){
            //과목코드로 이수확인
            String now_key=sub_key.next();
            Subject now_sub = sub_list.get(now_key);

            if(student_design.containsKey(now_key)){//이수 했을 경우
                user_data+=Integer.parseInt(now_sub.get(SUBJECT_CREDIT));
                element = new String[]{now_sub.get(SUBJECT_NAME), now_key, "O"};
                returnValueList.add(element);
            }
            else{//이수 안했을 경우
                element = new String[]{now_sub.get(SUBJECT_NAME), now_key, "X"};
                returnValueList.add(element);
            }
        }
        success_rate = (float)(user_data) / (float)(sub_data);

        success=(int)(success_rate*100);
        element = new String[]{subject_type, new Integer(sub_data).toString(), new Integer(user_data).toString(), new Integer(success).toString() + "%"};
        returnValueList.add(0, element);

        return returnValueList;
    }
    //필수과목 이수여부확인
    private static ArrayList<String[]> Subject_Required_check(String track, int credit){//대체과목 있음, 이수여부 리스트 필요, 경우에 따라 과목수, 학점으로 계산
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
        SubjectList student_required = now_student.getCompletedSubjectList();//학생 이수현황 가져오기
        //(표준) 필수과목정보
        SubjectList sub_list = Database.getRequiredSubjectList(track);
        //필수과목 각각 가져와서 비교
        Set<String> sub_keys = sub_list.keySet();
        Iterator<String> sub_key = sub_keys.iterator();

        user_data=0;//이수한 필수과목수/학점
        if(credit<0){
            sub_data=0;//과목 수로 계산
        }
        else{
            sub_data=credit;//학점으로 계산
        }

        while(sub_key.hasNext()){
            found = false;//찾기 전 초기화
            //과목코드로 이수확인
            String now_key=sub_key.next();
            if(student_required.containsKey(now_key)){//이수 했을 경우
                Subject now_sub = sub_list.get(now_key);
                //이수한 게 최근에 대체된 과목일 경우
                //이미 else while에서 list에 더해줌
                if(now_sub.get(DAPATH.SUBJECT_REPLACE)!=null && now_sub.get(DAPATH.SUBJECT_REPLACE).compareTo("") != 0 && now_sub.get(DAPATH.SUBJECT_REPLACE).compareTo(" ") != 0){
                    found=true;
                }
                else{
                    if(credit<0){
                          sub_data++;
                          user_data++;
                    }
                    else{
                        user_data+=Integer.parseInt(now_sub.get(SUBJECT_CREDIT));
                    }
                    element = new String[]{now_sub.get(DAPATH.SUBJECT_NAME), now_key, "O"};
                    returnValueList.add(element);
                }

            }
            else{//이수 안했을 경우
                Subject sub_info = sub_list.get(now_key);
                //반복해서 대체할 수 있는 걸 찾기
                if(sub_info.get(DAPATH.SUBJECT_REPLACE)==null || sub_info.get(DAPATH.SUBJECT_REPLACE).compareTo("")==0){
                    Iterator<String> sub_replaces = sub_keys.iterator();
                    while(sub_replaces.hasNext()){//현재 검사중인 과목이 다른 과목의 대체과목일 경우
                        Subject sub_replace = sub_list.get(sub_replaces.next());
                        if(sub_replace.get(DAPATH.SUBJECT_REPLACE)!=null && sub_replace.get(DAPATH.SUBJECT_REPLACE).compareTo("")!=0 && sub_replace.get(DAPATH.SUBJECT_REPLACE).compareTo(" ")!=0){//not space and null
                            if(sub_replace.get(DAPATH.SUBJECT_REPLACE).compareTo(sub_info.get(DAPATH.SUBJECT_CODE))==0){//대체되는 과목이면
                                if(student_required.containsKey(sub_replace.get(DAPATH.SUBJECT_CODE))){//이수했을 경우
                                    found=true;
                                    if(credit<0){
                                        sub_data++;
                                        user_data++;
                                    }
                                    else{
                                        user_data+=Integer.parseInt(sub_replace.get(SUBJECT_CREDIT));
                                    }
                                    element = new String[]{sub_replace.get(DAPATH.SUBJECT_NAME), sub_info.get(DAPATH.SUBJECT_REPLACE), "O"};
                                    returnValueList.add(element);
                                }
                            }
                        }
                    }
                }
                else
                    continue; //현재 다른 과목으로 대체된 과목(현재 과목에서 이미 check해서 그냥 넘어감)

                if(found==false){//모두(원래, 대체) 이수 안했을 경우
                    if(credit<0){
                        sub_data++;
                    }
                    Subject now_sub = sub_list.get(now_key);
                    element = new String[]{now_sub.get(DAPATH.SUBJECT_NAME), now_key, "X"};
                    returnValueList.add(element);
                }

            }
        }
        if(user_data>sub_data){
            success_rate= new Float(1.0);
        }
        else{
            success_rate = (float)(user_data) / (float)(sub_data);
        }
        success=(int)(success_rate*100);
        element = new String[]{subject_type, Integer.toString(sub_data), Integer.toString(user_data), new Integer(success).toString() + "%"};
        returnValueList.add(0, element);


        return returnValueList;
    }

    private static int Subject_Startup_check(){ //대체과목 없음, 이수여부 리스트 X, 총 이수 학점만, 학점으로 계산
        String subject_type = "창업과목";
        //결과 값 저장공간
        int user_data=0;
        //학생정보
        Student now_student =(Student)(User.getInstance().getUserData());//현재 로그인 한 student 정보
        SubjectList student_startup = now_student.getCompletedSubjectList();//학생 이수현황 가져오기
        //(표준) 필수과목정보
        SubjectList sub_list = Database.getStartupSubjectList();

        Set<String> sub_keys = sub_list.keySet();
        Iterator<String> sub_key = sub_keys.iterator();

        while(sub_key.hasNext()){
            String now_key=sub_key.next();
            Subject now_sub = sub_list.get(now_key);

            if(student_startup.containsKey(now_key)){//이수 했을 경우
                user_data+=Integer.parseInt(now_sub.get(SUBJECT_CREDIT));
            }
        }
        return user_data;
    }

    private static Boolean needFunctionCheck(String object){
        switch(object){
            case GRAINFO_DESIGN:
            case GRAINFO_STARTUP:
            case GRAINFO_COMMON_MAJOR:
            case GRAINFO_GENERAL:
                return true;
            default:
               return false;
        }
    }

    //졸업요건정보 업데이트()
    //졸업요건정보 추가()
}
