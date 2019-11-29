package com.knucse.knugra.UI_package.qa;

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

public class QAFragment extends Fragment {

    private QAViewModel QAViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        QAViewModel =
                ViewModelProviders.of(this).get(QAViewModel.class);
        View root = inflater.inflate(R.layout.fragment_qa, container, false);
        final TextView textView = root.findViewById(R.id.text_qa);
        QAViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}