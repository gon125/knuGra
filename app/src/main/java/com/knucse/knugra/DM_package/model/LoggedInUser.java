package com.knucse.knugra.DM_package.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String userPwd;
    private String displayName;

    public LoggedInUser(String userId, String userPwd, String displayName) {
        this.userId = userId;
        this.userPwd = userPwd;
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPwd() { return userPwd; }

    public String getDisplayName() {
        return displayName;
    }
}
