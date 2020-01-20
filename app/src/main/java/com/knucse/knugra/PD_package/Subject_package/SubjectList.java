package com.knucse.knugra.PD_package.Subject_package;

import android.app.Activity;

import com.knucse.knugra.DM_package.Database;
import com.knucse.knugra.PD_package.Graduation_Info_package.Graduation_Info_List;

import java.util.HashMap;

public class SubjectList extends HashMap<String, Subject> {
    private static volatile SubjectList instance;


    public static SubjectList getInstance(String Subject_Info) {
        if (instance == null) {
            instance = new SubjectList();
            if(Subject_Info.compareTo("Design")==1) {
                //Database.getDesignSubjectList(Context.getContext());
            }
            else if(Subject_Info.compareTo("Required")==1){
                //Database.getRequiredSubjectList(View.getContext());
            }
        }

        return instance;
    }
}
