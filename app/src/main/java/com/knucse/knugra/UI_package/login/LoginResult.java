package com.knucse.knugra.UI_package.login;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    @Nullable
    private LoggedInUserView success;
    @Nullable
    private Integer error;
    int errorCode;

    LoginResult(@Nullable Integer error, int errorCode) {
        this.error = error;
        this.errorCode = errorCode;
    }

    LoginResult(@Nullable LoggedInUserView success) {
        this.success = success;
    }

    @Nullable
    LoggedInUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }

    public int getErrorCode() {
        return errorCode;
    }

    void resetError() { this.error = null;}

}
