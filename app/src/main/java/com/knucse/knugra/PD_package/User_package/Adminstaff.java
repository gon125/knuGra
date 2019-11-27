package com.knucse.knugra.PD_package.User_package;

public class Adminstaff extends User {
    private String admin_name;
    private long admin_num;

    public void login(String login_id, String login_pw){
        this.user_id=login_id;
        this.user_pw=login_pw;
    }
}
