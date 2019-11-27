package com.knucse.knugra.PD_package.User_package;

public class User {
    protected String user_id;
    protected String user_pw;
    protected boolean user_access;


    protected User(String login_id, String login_pw, boolean login_access) {
        this.user_id=login_id;
        this.user_pw=login_pw;
        this.user_access=login_access;
    }
    /*
    //sigleton pattern
    private static User login_user =null;

    private User(String login_id, String login_pw, boolean login_access){
        this.user_id=login_id;
        this.user_pw=login_pw;
        this.user_access=login_access;
    }

    public static User getInstance(String login_id, String login_pw, boolean login_access) {
        // 객채가 이미 있는경우 새로 생성
        if (login_user==null) {
            login_user = new User(login_id, login_pw, login_access);
        }
        return login_user;
    }
    */
}
