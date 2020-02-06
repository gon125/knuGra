package com.knucse.knugra.DM_package.model;

public class ERROR {
    public static final int NO_PROBLEM = 0;
    public static final int ID_PASSWARD_INCORRECT = 1;
    public static final int PASSWORD_CHANGE_DATE_THREE_MONTHS = 2;
    public static final int EXCEPTION = 3;
    public static final int CODE_NULL = 999;
    public static final int UNKNWON = 123;

    private static String ID_PASSWARD_INCORRECT_STRING = "아이디 혹은 비밀번호가 다릅니다.";
    private static String PASSWORD_CHANGE_DATE_THREE_MONTHS_STRING = "비밀번호를 교체한지 3달이 넘었습니다.";
    private static String SERVER_CONNECTION_STRING = "서버와의 연결이 좋지 않습니다.";

    public static String toString(int errorCode) {
        switch (errorCode) {
            case ID_PASSWARD_INCORRECT: return ID_PASSWARD_INCORRECT_STRING;
            case PASSWORD_CHANGE_DATE_THREE_MONTHS: return PASSWORD_CHANGE_DATE_THREE_MONTHS_STRING;
            default: return SERVER_CONNECTION_STRING;
        }
    }

}
