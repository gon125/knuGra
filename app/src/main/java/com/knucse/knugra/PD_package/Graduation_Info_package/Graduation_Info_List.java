package com.knucse.knugra.PD_package.Graduation_Info_package;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.knucse.knugra.DM_package.DAPATH;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Graduation_Info_List{
    private static volatile Graduation_Info_List instance;
    private ArrayList<Graduation_Info> graduation_infos = new ArrayList<>();


    private Graduation_Info_List() {
        // get graduation list from database

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(DAPATH.GRADUATION_INFO_LIST)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Gra
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    public static Graduation_Info_List getInstance() {
        if (instance == null) {
            instance = new Graduation_Info_List();
        }
        return instance;
    }
    //졸업요건정보 검색(졸업트랙)
    //졸업요건정보 업데이트()
    //졸업요건정보 추가()
    //남은 졸업자격정보 열람 요청(졸업트랙)

}
