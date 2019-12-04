package com.knucse.knugra.UI_package.career_success;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.knucse.knugra.R;

public class CareerSuccessFragment extends Fragment {
    private CareerSuccessViewModel careerSuccessViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        careerSuccessViewModel =
                ViewModelProviders.of(this).get(CareerSuccessViewModel.class);
        View root = inflater.inflate(R.layout.fragment_career_success, container, false);
        final TextView textView = root.findViewById(R.id.text_career_success);
        careerSuccessViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    public static CareerSuccessFragment newInstance() {
        CareerSuccessFragment fragment = new CareerSuccessFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

}
