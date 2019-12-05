package com.knucse.knugra.DM_package;
import com.knucse.knugra.DM_package.model.LoggedInUser;
import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    public Result<LoggedInUser> login(String username, String password) {
        try {
            ServerConnectTask asyncTask = new ServerConnectTask();
            return asyncTask.execute(username, password, RequestType.LOGIN).get();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

}
