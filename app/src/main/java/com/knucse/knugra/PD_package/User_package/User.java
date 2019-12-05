package com.knucse.knugra.PD_package.User_package;
import com.knucse.knugra.DM_package.model.LoggedInUser;
import com.knucse.knugra.PD_package.User_package.Student_package.Student;

public class User {
    private static volatile User instance = null;
    private int accessLevel;
    private LoggedInUser loggedInUser;
    private UserData userData;

    private User(LoggedInUser user, int accessLevel) {
        this.loggedInUser = user;
        this.accessLevel = accessLevel;

        // set user data by access level
        switch (accessLevel) {
            case UserAccessLevel.STUDENT :
                userData = new Student();
            case UserAccessLevel.ADMIN :
                userData = new Adminstaff();
            case UserAccessLevel.MANAGER :
                userData = new Manager();
            case UserAccessLevel.PROFESSOR :
                userData = new Professor();
        }
    }

    public static User getInstance(LoggedInUser user, int accessLevel) {
        if (instance == null) {
            return new User(user, accessLevel);
        } else return instance;
    }

    public static User getInstance() {
        return instance;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public LoggedInUser getLoggedInUser() {
        return loggedInUser;
    }

    public UserData getUserData() {
        return userData;
    }
}
