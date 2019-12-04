package com.knucse.knugra.PD_package.User_package.Student_package;

public class StudentBasicInfo {
    private String name;
    private String studentId;
    private String birth;
    private String major;
    private String grade;
    private String phoneNumber;
    private String nationality;

    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getMajor() {
        return major;
    }

    public String getGrade() {
        return grade;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public String getBirth() {
        return birth;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}

