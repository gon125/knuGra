package com.knucse.knugra.DM_package;

import android.os.AsyncTask;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.knucse.knugra.DM_package.model.LoggedInUser;
import com.knucse.knugra.PD_package.User_package.Student_package.Student;
import com.knucse.knugra.PD_package.User_package.Student_package.StudentCareer;
import com.knucse.knugra.PD_package.User_package.Student_package.StudentCareerList;
import com.knucse.knugra.PD_package.User_package.User;
import com.knucse.knugra.PD_package.User_package.UserAccessLevel;
import com.knucse.knugra.PD_package.User_package.UserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class ServerConnectTask {
    public Result<LoggedInUser> execute(String... str) {
        String username = str[0];
        String pwd = str[1];
        String requestType = str[2];
        try {
            Socket socket;
            InputStream is;
            OutputStream os;
            socket = new Socket("54.180.106.239", 3456);

            is = socket.getInputStream();
            os = socket.getOutputStream();

            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("requestType", requestType);
            jsonObject.addProperty("id", username);
            jsonObject.addProperty("pwd", pwd);

            String s = jsonObject.toString();
            bw.write(s);
            bw.flush();

            InputStreamReader isr = new InputStreamReader(is);

            BufferedReader br = new BufferedReader(isr);

            // wait for the server respond
            String result = br.readLine();
            switch (requestType) {
                case RequestType.LOGIN:
                    return login(result, username, pwd);
                case RequestType.UPDATE:
                    update(result);
                    break;
            }

        } catch (IOException e) {
            return new Result.Error(new IOException("Error serverConnect in", e));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new Result.Error(new Exception("Error logging in", e));
        }
        return new Result.Error(new Exception("Error serverConnect"));
    }

    private Result<LoggedInUser> login(String result, String username, String pwd) {
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
    }

    private void update(String result) {
        int i, j;
        StudentCareer update_data;
        Student update_student =(Student)(User.getInstance().getUserData());//현재 로그인 한 student 정보
        StudentCareerList update_list=update_student.getStudentCareerList();//여기에 update_data 추가할 것
        update_data = new StudentCareer();
        update_data.setName("이수합계");
        update_data.setContent("120");
        update_list.add(update_data);

        update_data = new StudentCareer();
        update_data.setName("기본소양");
        update_data.setContent("5");
        update_list.add(update_data);

        update_data = new StudentCareer();
        update_data.setName("공학인증");
        update_data.setContent("3");
        update_list.add(update_data);

        update_list.setCareer_track(DAPATH.COMPUTPER_ABEEK);
        try {
            result = "{\"key\":\"value\"}";
            JSONObject jo = new JSONObject(result);
            Iterator keyi=jo.keys();

            for(i=0;i<jo.length();i++) {//객체갯수만큼 반복해서 StudentCareer객체 만들어서 넣기
                //선언
                String jo_name = keyi.next().toString();//key값
                //
                for(j=0;j<update_list.size();j++) {
                    //list에 있으면 추가하면 안됨, 값 변경
                    if(jo_name.equals(update_list.get(j).getName())){
                        update_list.get(j).setContent(jo.getString(jo_name));
                    }
                    else{//list에 없으면 객체 새로 만들어서 추가
                        update_data=new StudentCareer();
                        update_data.setName(jo_name);//key값
                        update_data.setContent(jo.getString(jo_name));//value값
                        update_list.add(update_data);
                    }
                }
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        /*JsonParser jp = new JsonParser();
        StudentCareerList update_list=update_student.getStudentCareerList();
        JsonObject jo = (JsonObject)jp.parse(result); //key:value, dictionary형태로 변환해서 저장
        */
    }


}