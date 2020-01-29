package com.knucse.knugra.PD_package.User_package.Student_package;

import com.knucse.knugra.PD_package.Subject_package.SubjectList;
import com.knucse.knugra.PD_package.User_package.UserData;

public class Student extends UserData {

    private StudentBasicInfo studentBasicInfo;
    private StudentCareerList studentCareerList;
    private SubjectList completedSubjectList;

    public Student() {
        this.studentCareerList = new StudentCareerList();
        this.studentBasicInfo = new StudentBasicInfo();
        this.completedSubjectList = new SubjectList();
    }

    public Student(StudentCareerList studentCareerList, StudentBasicInfo studentBasicInfo,
                    SubjectList requiredSubjectList,
                   SubjectList designSubjectList,
                   SubjectList completedSubjectList){
        this.studentBasicInfo = studentBasicInfo;
        this.studentCareerList = studentCareerList;
        this.completedSubjectList = completedSubjectList;
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

    public SubjectList getCompletedSubjectList() { return completedSubjectList; }
}