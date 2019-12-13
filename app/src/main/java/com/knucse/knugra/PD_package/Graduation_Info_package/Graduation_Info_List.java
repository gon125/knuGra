package com.knucse.knugra.PD_package.Graduation_Info_package;


import com.knucse.knugra.DM_package.Database;

import java.util.ArrayList;

public class Graduation_Info_List extends ArrayList<Graduation_Info>{
    private static volatile Graduation_Info_List instance;

    private Graduation_Info_List() {
    }

    public static Graduation_Info_List getInstance() {
        if (instance == null) {
            instance = Database.getGraduationInfoList();
        }
        return instance;
    }
    //졸업요건정보 검색(졸업트랙)


    //졸업요건정보 업데이트()
    //졸업요건정보 추가()
    //남은 졸업자격정보 열람 요청(졸업트랙)

}
