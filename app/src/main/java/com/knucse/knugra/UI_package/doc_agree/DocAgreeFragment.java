package com.knucse.knugra.UI_package.doc_agree;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.knucse.knugra.R;
import com.knucse.knugra.UI_package.std_info_input.StdInfoInputViewModel;

public class DocAgreeFragment extends Fragment {
    private DocAgreeViewModel docAgreeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        docAgreeViewModel =
                ViewModelProviders.of(this).get(DocAgreeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_doc_agree, container, false);
        final TextView textView = root.findViewById(R.id.text_doc_agree);
        docAgreeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
