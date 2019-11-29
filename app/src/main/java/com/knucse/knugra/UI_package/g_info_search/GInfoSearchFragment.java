package com.knucse.knugra.UI_package.g_info_search;

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

public class GInfoSearchFragment extends Fragment {

    private GInfoSearchViewModel gInfoSearchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gInfoSearchViewModel =
                ViewModelProviders.of(this).get(GInfoSearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_g_info_search, container, false);
        final TextView textView = root.findViewById(R.id.text_g_info_search);
        gInfoSearchViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}