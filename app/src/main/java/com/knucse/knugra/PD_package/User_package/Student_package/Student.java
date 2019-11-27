package com.knucse.knugra.PD_package.User_package.Student_package;

import com.knucse.knugra.PD_package.User_package.User;

public class Student extends User {
    private String std_name;
    private long std_schoolnum;
    private String std_birth;
    private String std_major;
    private char std_grade;
    private String std_ph;
    private String std_nation;
    //객체 생성

    //login할 때 정보 같이 가져옴
    public void login(String std_lname, long std_lschoolnum, String std_lbirth, String std_lmajor, char std_lgrade, String std_lph, String std_lnation){
    }
}