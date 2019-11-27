package com.knucse.knugra.PD_package.User_package;

import com.knucse.knugra.PD_package.User_package.Student_package.Student;

public class Adminstaff extends User {
    private String admin_name;
    private long admin_num;

    //sigleton pattern
    private static Adminstaff login_admin =null;

    //login할 때 정보 같이 가져옴
    private Adminstaff(String admin_lid, String admin_lpw, boolean admin_laccess, String admin_lname, long admin_lnum){
        super(admin_lid, admin_lpw, admin_laccess);
        this.admin_name=admin_lname;
        this.admin_num=admin_lnum;
    }

    public static Adminstaff getInstance(String admin_lid, String admin_lpw, boolean admin_laccess, String admin_lname, long admin_lnum) {
        // 객채가 이미 있는경우 새로 생성
        if (login_admin==null) {
            login_admin = new Adminstaff(admin_lid, admin_lpw, admin_laccess, admin_lname, admin_lnum);
        }
        return login_admin;
    }
}
