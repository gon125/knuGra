package com.knucse.knugra.PD_package.Graduation_Info_package;


import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.firestore.FirebaseFirestore;
import com.knucse.knugra.DM_package.DAPATH;
import com.knucse.knugra.DM_package.Database;
import com.knucse.knugra.PD_package.User_package.Student_package.Student;
import com.knucse.knugra.PD_package.User_package.Student_package.StudentCareer;
import com.knucse.knugra.PD_package.User_package.Student_package.StudentCareerList;
import com.knucse.knugra.PD_package.User_package.User;

import java.util.ArrayList;
import java.util.Iterator;

public class Graduation_Info_List extends ArrayList<Graduation_Info>{
    private static volatile Graduation_Info_List instance;

    private Graduation_Info_List() {
    }

    public static Graduation_Info_List getInstance() {
        if (instance == null) {
            instance = new Graduation_Info_List();
            Database.getGraduationInfoList();
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
    //졸업요건정보 업데이트()
    //졸업요건정보 추가()
}
