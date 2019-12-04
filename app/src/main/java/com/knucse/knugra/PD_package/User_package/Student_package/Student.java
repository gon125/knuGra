package com.knucse.knugra.PD_package.User_package.Student_package;

import com.knucse.knugra.PD_package.User_package.UserData;

public class Student extends UserData {
    private StudentBasicInfo studentBasicInfo;
    private StudentCareerList studentCareerList;

    public Student() {

    }

    public Student(StudentCareerList studentCareerList, StudentBasicInfo studentBasicInfo){
        this.studentBasicInfo = studentBasicInfo;
        this.studentCareerList = studentCareerList;
    }

    public StudentBasicInfo getStudentBasicInfo() {
        return studentBasicInfo;
    }

    public void setStudentBasicInfo(StudentBasicInfo studentBasicInfo) {
        this.studentBasicInfo = studentBasicInfo;
    }

    public StudentCareerList getStudentCareerList() {
        return studentCareerList;
    }

    public void setStudentCareerList(StudentCareerList studentCareerList) {
        this.studentCareerList = studentCareerList;
    }
}