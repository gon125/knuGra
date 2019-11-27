package com.knucse.knugra.PD_package.User_package;

public class Professor extends User {
    private String pro_name;
    private long pro_num;

    public void login(String login_id, String login_pw){
        this.user_id=login_id;
        this.user_pw=login_pw;
    }
}
