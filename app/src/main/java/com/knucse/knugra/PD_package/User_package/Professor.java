package com.knucse.knugra.PD_package.User_package;

import com.knucse.knugra.PD_package.User_package.Student_package.Student;

public class Professor extends UserData {
    private String pro_name;
    private long pro_num;

    //sigleton pattern
    private static Professor login_pro =null;


    public Professor() {};
    //login할 때 정보 같이 가져옴
    private Professor(String pro_lid, String pro_lpw, boolean pro_laccess, String pro_lname, long pro_lnum){
        //super(pro_lid, pro_lpw, pro_laccess);
        this.pro_name=pro_lname;
        this.pro_num=pro_lnum;
    }

    public static Professor getInstance(String pro_lid, String pro_lpw, boolean pro_laccess, String pro_lname, long pro_lnum) {
        // 객채가 이미 있는경우 새로 생성
        if (login_pro==null) {
            login_pro = new Professor(pro_lid, pro_lpw, pro_laccess, pro_lname, pro_lnum);
        }
        return login_pro;
    }
}
