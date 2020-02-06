package com.knucse.knugra.UI_package.home;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.knucse.knugra.DM_package.RequestType;
import com.knucse.knugra.DM_package.ServerConnectTask;
import com.knucse.knugra.PD_package.User_package.User;
import com.knucse.knugra.UI_package.MainActivity;
import com.knucse.knugra.R;

import java.util.ArrayList;
import java.util.Iterator;

import io.grpc.Server;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        ((TextView)((MainActivity)getActivity()).findViewById(R.id.toolbar_title)).setText(R.string.menu_home);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        ArrayList<Button> buttons = new ArrayList<Button>();
        final Button b1 = root.findViewById(R.id.button1_home);
        final Button b2 = root.findViewById(R.id.button2_home);
        buttons.add(b1);buttons.add(b2);

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Iterator<Button> it = buttons.iterator();

        while (it.hasNext()) {

            it.next().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.button1_home:
                            DataLoadingTask dataloading = new DataLoadingTask();
                            dataloading.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            break;
                        case R.id.button2_home:
                            ((MainActivity)getActivity()).navigateTo(R.id.nav_g_info_search);
                            break;
                        default:    break;
                    }
                }
            });
        }
        return root;
    }

    private class DataLoadingTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog dataLoadingProgress = new ProgressDialog((MainActivity)getActivity());
        int timeOut = 0;
        @Override
        protected void onPreExecute() {
            dataLoadingProgress.show();
            dataLoadingProgress.setContentView(R.layout.dataloading_progress_dialog);
            dataLoadingProgress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            while (!ServerConnectTask.updateCompleted && timeOut < 20){
                try {
                    Thread.sleep(1000);
                    timeOut++;
                }catch (Exception e){
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dataLoadingProgress.dismiss();
            if (ServerConnectTask.updateCompleted) {
                ((MainActivity)getActivity()).navigateTo(R.id.nav_career_success);
            } else {
            }
            super.onPostExecute(aVoid);
        }
    }
}