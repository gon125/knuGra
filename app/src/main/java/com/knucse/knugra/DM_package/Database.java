package com.knucse.knugra.DM_package;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.knucse.knugra.PD_package.Graduation_Info_package.Graduation_Info;
import com.knucse.knugra.PD_package.Graduation_Info_package.Graduation_Info_Item;
import com.knucse.knugra.PD_package.Graduation_Info_package.Graduation_Info_List;

import java.util.Iterator;
import java.util.Map;


public class Database {

    private static volatile Database instance;

    private Database() {

    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

//        db.collection(DAPATH.GRADUATION_INFO_LIST)
//            .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//        @Override
//        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//            if (task.isSuccessful()) {
//                for (QueryDocumentSnapshot document : task.getResult()) {
//                    Log.d(TAG, document.getId() + " => " + document.getData());
//                }
//            } else {
//                Log.w(TAG, "Error getting documents.", task.getException());
//            }
//        }
//    });

        public static void getGraduationInfoList() {

            FirebaseFirestore db = FirebaseFirestore.getInstance();


            db.collection(DAPATH.GRADUATION_INFO_LIST)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Graduation_Info_List gilist = Graduation_Info_List.getInstance();
                                    Map a = document.getData();

                                    Iterator<String> it = a.keySet().iterator();


                                    Graduation_Info gi = new Graduation_Info();

                                    while(it.hasNext()) {
                                        String key = it.next();
                                        String value = (String)a.get(key);
                                        Graduation_Info_Item git = new Graduation_Info_Item();
                                        git.setName(key);
                                        git.setContent(value);

                                        gi.add(git);
                                    }

                                    gilist.add(gi);

                                    Log.d("내꺼", gi.get(0).getContent());
                                }
                            }
                        }
                    });
        }
}
