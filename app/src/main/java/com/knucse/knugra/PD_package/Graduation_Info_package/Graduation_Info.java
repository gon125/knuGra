package com.knucse.knugra.PD_package.Graduation_Info_package;

import java.util.ArrayList;

public class Graduation_Info extends ArrayList<Graduation_Info_Item> {
    // track을 세팅


    String info_track;

    public Graduation_Info() {}

    //선택한졸업트랙 생성
    public Graduation_Info(String info_track) {
        this.info_track = info_track;
    }

    public void setInfo_track(String info_track) {
        this.info_track = info_track;
    }
    public String getInfo_track() {
        return info_track;
    }
}
