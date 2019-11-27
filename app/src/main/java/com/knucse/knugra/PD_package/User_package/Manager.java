package com.knucse.knugra.PD_package.User_package;

import com.knucse.knugra.PD_package.User_package.Student_package.Student;

public class Manager extends User {
    private String man_name;

    //sigleton pattern
    private static Manager login_man =null;

    private Manager(String man_lid, String man_lpw, boolean man_laccess, String man_lname){
        super(man_lid, man_lpw, man_laccess);
        this.man_name=man_lname;
    }

    public static Manager getInstance(String man_lid, String man_lpw, boolean man_laccess, String man_lname) {
        // 객채가 이미 있는경우 새로 생성
        if (login_man==null) {
            login_man = new Professor(man_lid, man_lpw, man_laccess, man_lname);
        }
        return login_man;
    }
}
