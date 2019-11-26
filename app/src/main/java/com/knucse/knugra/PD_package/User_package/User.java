package com.knucse.knugra.PD_package.User_package;

public class User {
    private String user_id;
    private String user_pw;
    private boolean user_access;
    private String user_mail;

    public void login(String login_id, String login_pw){
        this.user_id=login_id;
        this.user_pw=login_pw;
    }
}
