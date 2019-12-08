package com.knucse.knugra.PD_package.User_package;
import com.knucse.knugra.DM_package.model.LoggedInUser;
import com.knucse.knugra.PD_package.User_package.Student_package.Student;

public class User {
    private static volatile User instance = null;
    private int accessLevel;
    private String id;
    private String password;
    private UserData userData;

    private User(LoggedInUser user, int accessLevel) {
        this.id = user.getUserId();
        this.password = user.getUserPwd();
        this.accessLevel = accessLevel;

        // set user data by access level
        switch (accessLevel) {
            case UserAccessLevel.STUDENT :
                userData = new Student();
                break;
            case UserAccessLevel.ADMIN :
                userData = new Adminstaff();
                break;
            case UserAccessLevel.MANAGER :
                userData = new Manager();
                break;
            case UserAccessLevel.PROFESSOR :
                userData = new Professor();
                break;
        }

    }

    public static User getInstance(LoggedInUser user, int accessLevel) {
        if (instance == null) {
            instance = new User(user, accessLevel);
        }
        return instance;
    }

    public static User getInstance() {
        return instance;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public UserData getUserData() {
        return userData;
    }
}
