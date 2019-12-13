package com.knucse.knugra.PD_package.Graduation_Info_package;


import android.util.Log;

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
    //졸업요건정보 검색(졸업트랙)
    //졸업요건정보 검색(졸업트랙)//
    // (전체조건)/(학생이 완수한조건)
    public static void Graduation_Info_compare(){
        int i;
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
            if(student_career.getCareer_track().equals(std_track.info_track))
                break;
        }
        //비교(전체 갯수 count, 완수한 갯수 count)
        for(i=0;i<std_track.size();i++) {
            Iterator<StudentCareer> sc=student_career.iterator();
            while(sc.hasNext()){
                StudentCareer scobject= sc.next();
                if(scobject.getName().equals(std_track.get(i).getName())){
                    try {
                        stddata = Integer.parseInt(std_track.get(i).getContent());//content가 숫자
                        userdata = Integer.parseInt(scobject.getContent());
                        success_rate = (float) userdata/(float) stddata;
                        //name에 따라서 결과 출력하면 됨
                        Log.d("성공률", success_rate.toString());
                    }catch(NumberFormatException e) {
                        //NumberFormatException=숫자형태가 아닌 문자열
                    }
                }
            }
        }

    }

    //졸업요건정보 업데이트()
    //졸업요건정보 추가()
    //남은 졸업자격정보 열람 요청(졸업트랙)

}
