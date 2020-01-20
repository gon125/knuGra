package com.knucse.knugra.PD_package.User_package.Student_package;

import com.knucse.knugra.PD_package.Subject_package.Subject;
import com.knucse.knugra.PD_package.Subject_package.SubjectList;
import com.knucse.knugra.PD_package.User_package.UserData;

public class Student extends UserData {
    private StudentBasicInfo studentBasicInfo;
    private StudentCareerList studentCareerList;
    private SubjectList requiredSubjectList;
    private SubjectList designSubjectList;

    public Student() {
        this.studentCareerList = new StudentCareerList();
        this.studentBasicInfo = new StudentBasicInfo();
        this.requiredSubjectList = new SubjectList();
        this.designSubjectList = new SubjectList();
    }

    public Student(StudentCareerList studentCareerList, StudentBasicInfo studentBasicInfo,
                    SubjectList requiredSubjectList, SubjectList designSubjectList){
        this.studentBasicInfo = studentBasicInfo;
        this.studentCareerList = studentCareerList;
        this.requiredSubjectList = requiredSubjectList;
        this.designSubjectList = designSubjectList;
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

    public SubjectList getRequiredSubjectList() {
        return requiredSubjectList;
    }

    public void setRequiredSubjectList(SubjectList requiredSubjectList) {
        this.requiredSubjectList = requiredSubjectList;
    }

    public SubjectList getDesignSubjectList() {
        return designSubjectList;
    }

    public void setDesignSubjectList(SubjectList designSubjectList) {
        this.designSubjectList = designSubjectList;
    }
}