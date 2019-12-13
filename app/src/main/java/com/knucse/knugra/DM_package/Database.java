package com.knucse.knugra.DM_package;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.knucse.knugra.DM_package.model.Graduation_Info_List_DAO;
import com.knucse.knugra.PD_package.Graduation_Info_package.Graduation_Info_List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Database {


    // get graduation list from database

    FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(DAPATH.GRADUATION_INFO_LIST)
            .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d(TAG, document.getId() + " => " + document.getData());
                }
            } else {
                Log.w(TAG, "Error getting documents.", task.getException());
            }
        }
    });

        public static Graduation_Info_List getGraduationInfoList() {

        }
}
