package com.knucse.knugra.PD_package.User_package;

public class Manager extends User {
    private String man_name;

    public void login(String login_id, String login_pw){
        this.user_id=login_id;
        this.user_pw=login_pw;
    }
}
