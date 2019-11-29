package com.knucse.knugra.UI_package.std_info_search;

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

public class StdInfoSearchFragment extends Fragment {

    private StdInfoSearchViewModel stdInfoSearchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        stdInfoSearchViewModel =
                ViewModelProviders.of(this).get(StdInfoSearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_std_info_search, container, false);
        final TextView textView = root.findViewById(R.id.text_std_info_search);
        stdInfoSearchViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}