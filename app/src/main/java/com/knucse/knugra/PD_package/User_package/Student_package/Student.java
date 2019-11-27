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
    public void login(String login_id, String login_pw){
        this.user_id=login_id;
        this.user_pw=login_pw;
    }
}
