package com.knucse.knugra.DM_package;

import android.os.AsyncTask;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.knucse.knugra.DM_package.model.LoggedInUser;
import com.knucse.knugra.PD_package.User_package.Student_package.Student;
import com.knucse.knugra.PD_package.User_package.User;
import com.knucse.knugra.PD_package.User_package.UserAccessLevel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {
        try {
            ServerConnectTask asyncTask = new ServerConnectTask();

            return asyncTask.execute(username, password).get();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    private class ServerConnectTask extends AsyncTask<String, Void, Result<LoggedInUser>> {
        @Override
        protected Result<LoggedInUser> doInBackground(String... str) {
            if(android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();
            String username = str[0];
            String pwd = str[1];
            System.out.println(username + "\n" + pwd);
            try {
                Socket socket;
                InputStream is;
                OutputStream os;

                //aws server host
                socket = new Socket("54.180.149.57", 3456);
                //SocketAddress addr = new InetSocketAddress(InetAddress.getByName("ec2-54-180-123-105.ap-northeast-2.compute.amazonaws.com"), 3456/*port*/) ;
                //socket.connect(addr);

                is = socket.getInputStream();
                os = socket.getOutputStream();

                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("requestType", "login");
                jsonObject.addProperty("id", username);
                jsonObject.addProperty("pwd", pwd);

                String s = jsonObject.toString();
                bw.write(s);
                bw.flush();

                InputStreamReader isr = new InputStreamReader(is);

                BufferedReader br = new BufferedReader(isr);

                // wait for the server respond
                String result = br.readLine();

                JsonParser jp = new JsonParser();

                JsonObject jo = (JsonObject)jp.parse(result);
                JsonElement je = jo.get("login");
                String respond = je.getAsString();
                System.out.println(respond);

                if (respond.equals("success")) {
                    LoggedInUser user =
                            new LoggedInUser(
                                    username,
                                    pwd,
                                    username);

                    // set User data
                  User newUser = User.getInstance(user, UserAccessLevel.STUDENT);


                    return new Result.Success<>(user);
                } else return new Result.Error(new IOException("Error logging in"));
            } catch (IOException e) {
                return new Result.Error(new IOException("Error logging in", e));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return new Result.Error(new Exception("Error logging in", e));
            }

        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Result<LoggedInUser> result) {

        }

    }
}
