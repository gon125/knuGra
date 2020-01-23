package com.knucse.knugra.DM_package;

import android.os.AsyncTask;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.knucse.knugra.DM_package.model.LoggedInUser;
import com.knucse.knugra.PD_package.Subject_package.Subject;
import com.knucse.knugra.PD_package.Subject_package.SubjectList;
import com.knucse.knugra.PD_package.User_package.Student_package.Student;
import com.knucse.knugra.PD_package.User_package.Student_package.StudentCareer;
import com.knucse.knugra.PD_package.User_package.Student_package.StudentCareerList;
import com.knucse.knugra.PD_package.User_package.User;
import com.knucse.knugra.PD_package.User_package.UserAccessLevel;

import org.json.JSONArray;
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
import java.util.Iterator;

public class ServerConnectTask {

    public static boolean updateCompleted = false;

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
                case RequestType.LOGOUT:
                    logout(result);
            }

        } catch (IOException e) {
            return new Result.Error(new IOException("Error serverConnect in", e));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new Result.Error(new Exception("Error logging in", e));
        }
        return new Result.Error(new Exception("Error serverConnect"));
    }

    public Result<LoggedInUser> execute(ServerConnectTaskPrams... params) {
        String username = params[0].username;
        String pwd = params[0].password;
        String requestType = params[0].requestType;
        String major = params[0].major;

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
            jsonObject.addProperty("major", major);

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
                    updateCompleted = update(result);
                    break;
                case RequestType.LOGOUT:
                    logout(result);
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

    private boolean logout(String result) {

        return true;
        //        JsonParser jp = new JsonParser();
//
//        JsonObject jo = (JsonObject) jp.parse(result);
//        JsonElement je = jo.get("logout");
//        String respond = je.getAsString();
//        System.out.println(respond);
//
//        if (respond.equals("success")) {
//            return true;
//        } else {
//            return false;
//        }
    }

    private boolean update(String result) {
        int i;
        StudentCareer studentCareer;
            Student student =(Student)(User.getInstance().getUserData());//현재 로그인 한 student 정보
            StudentCareerList studentCareerList = student.getStudentCareerList();//여기에 studentCareer 추가할 것
            SubjectList requiredSubjectList = student.getRequiredSubjectList();
            SubjectList designSubjectList = student.getDesignSubjectList();
            // have to get
            try {
                JSONObject jsonObject = new JSONObject(result);

            // get grade info
            JSONObject gradeInfoList = jsonObject.getJSONObject(JSONKey.GRADE_INFO);

            //init list
            studentCareerList.clear();

            Iterator keyi=gradeInfoList.keys();
            for(i=0;i<gradeInfoList.length();i++) {//객체갯수만큼 반복해서 StudentCareer객체 만들어서 넣기
                //선언
                String jo_name = keyi.next().toString();//key값

                studentCareer = new StudentCareer();
                studentCareer.setName(jo_name);//key값
                studentCareer.setContent(gradeInfoList.getString(jo_name));//value값
                studentCareerList.add(studentCareer);
            }

            // add 공학인증
               Integer sum = new Integer(gradeInfoList.getString("기본소양"))
                        +  new Integer(gradeInfoList.getString("전공기반"))
                        +  new Integer(gradeInfoList.getString("공학전공"));
            studentCareer = new StudentCareer();
            studentCareer.setName("공학인증");
            studentCareer.setContent(sum.toString());
            studentCareerList.add(studentCareer);

                        //get requiredList
            JSONArray required = jsonObject.getJSONArray(JSONKey.REQUIRED_SUBJECT_LIST);
            JSONArray design = jsonObject.getJSONArray(JSONKey.DESIGN_SUBJECT_LIST);

            for (i = 0; i < required.length(); i++) {
                JSONObject object = required.getJSONObject(i);
                Subject subject = new Subject();

                String subject_code = object.getString(DAPATH.SUBJECT_CODE);
                if (subject_code == "") { continue; }
                subject.put(DAPATH.SUBJECT_CODE, subject_code);

                String name = object.getString(DAPATH.SUBJECT_NAME);
                subject.put(DAPATH.SUBJECT_NAME, name);

                String credit = object.getString(DAPATH.SUBJECT_CREDIT);
                subject.put(DAPATH.SUBJECT_CREDIT, credit);

                // put in list
                requiredSubjectList.put(subject_code, subject);
            }

            for (i = 0; i < design.length(); i++) {
                JSONObject object = required.getJSONObject(i);
                Subject subject = new Subject();

                String subject_code = object.getString(DAPATH.SUBJECT_CODE);
                if (subject_code == "") { continue; }
                subject.put(DAPATH.SUBJECT_CODE, subject_code);

                String name = object.getString(DAPATH.SUBJECT_NAME);
                subject.put(DAPATH.SUBJECT_NAME, name);

                String credit = object.getString(DAPATH.SUBJECT_CREDIT);
                subject.put(DAPATH.SUBJECT_CREDIT, credit);

                // put in list
                designSubjectList.put(subject_code, subject);
            }


//            Iterator<String> it = required.keys();
//
//            required.
//
//            while (it.hasNext()) {
//                String key = it.next();
//            }

                //get designList



        }catch(JSONException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static class ServerConnectTaskPrams {
        String username;
        String password;
        String requestType;
        String major;

        ServerConnectTaskPrams(String username, String password, String requestType, String major) {
            this.username = username;
            this.password = password;
            this.major = major;
            this.requestType = requestType;
        }
    }
}

