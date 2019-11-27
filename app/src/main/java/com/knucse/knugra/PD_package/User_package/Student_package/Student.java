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
    //sigleton pattern
    private static Student login_std =null;

    //login할 때 정보 같이 가져옴
    private Student(String std_lid, String std_lpw, boolean std_laccess, String std_lname, long std_lschoolnum, String std_lbirth, String std_lmajor, char std_lgrade, String std_lph, String std_lnation){
        super(std_lid, std_lpw, std_laccess);
        this.std_name=std_lname;
        this.std_schoolnum=std_lschoolnum;
        this.std_birth=std_lbirth;
        this.std_major=std_lmajor;
        this.std_grade=std_lgrade;
        this.std_ph=std_lph;
        this.std_nation=std_lnation;
    }

    public static Student getInstance(String std_lid, String std_lpw, boolean std_laccess, String std_lname, long std_lschoolnum, String std_lbirth, String std_lmajor, char std_lgrade, String std_lph, String std_lnation) {
        // 객채가 이미 있는경우 새로 생성
        if (login_std==null) {
            login_std = new Student(std_lid, std_lpw, std_laccess, std_lname, std_lschoolnum, std_lbirth, std_lmajor, std_lgrade, std_lph, std_lnation);
        }
        return login_std;
    }
}