package com.knucse.knugra.PD_package.User_package;

public class User {
    protected String user_id;
    protected String user_pw;
    protected boolean user_access;
    protected String user_mail;

    public void login(String login_id, String login_pw){
        this.user_id=login_id;
        this.user_pw=login_pw;
    }
}
