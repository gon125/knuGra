package com.knucse.knugra.PD_package.User_package.Student_package;

import java.util.ArrayList;

public class StudentCareerList extends ArrayList<StudentCareer> {

    public void setCareer_track(String career_track) {
        this.career_track = career_track;
    }

    String career_track;//트랙

/*  int career_credit;//총 이수학점
    boolean career_english;//영어성적 통과/불통과
    int career_liberal;//교양학점
    int career_major;//전공학점
    int career_practice;//실습학점
    int career_counsel;//상담횟수*/
    //학생경력항목 추가하기

    // 선택한 졸업트랙 종류 요청
    public String getCareer_track() {
        return career_track;
    }
}
