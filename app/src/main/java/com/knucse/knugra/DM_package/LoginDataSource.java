package com.knucse.knugra.DM_package;
import com.knucse.knugra.DM_package.model.LoggedInUser;
import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    public Result<LoggedInUser> login(String username, String password) {
        try {
            ServerConnectTask serverConnectTask = new ServerConnectTask();
            return serverConnectTask.execute(username, password, RequestType.LOGIN);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

}
