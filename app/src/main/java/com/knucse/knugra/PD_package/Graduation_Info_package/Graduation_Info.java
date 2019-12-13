package com.knucse.knugra.PD_package.Graduation_Info_package;

import java.util.ArrayList;

public class Graduation_Info extends ArrayList<Graduation_Info_Item> {
    public String getInfo_track() {
        return info_track;
    }

    public void setInfo_track(String info_track) {
        this.info_track = info_track;
    }

     String info_track;
    int info_credit;//총 이수학점
    int info_liberal_arts;//인문교양학점
    int info_liberal;//교양학점
}
