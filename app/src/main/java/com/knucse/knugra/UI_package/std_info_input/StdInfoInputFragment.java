package com.knucse.knugra.UI_package.std_info_input;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.knucse.knugra.R;

public class StdInfoInputFragment extends Fragment {

    private StdInfoInputViewModel stdInfoInputViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        stdInfoInputViewModel =
                ViewModelProviders.of(this).get(StdInfoInputViewModel.class);
        View root = inflater.inflate(R.layout.fragment_std_info_input, container, false);
        final TextView textView = root.findViewById(R.id.text_std_info_input);
        stdInfoInputViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}