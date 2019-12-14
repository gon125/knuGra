package com.knucse.knugra.UI_package.career_success;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.knucse.knugra.PD_package.Graduation_Info_package.Graduation_Info_List;
import com.knucse.knugra.R;

public class CareerSuccessFragment extends Fragment {
    private CareerSuccessViewModel careerSuccessViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        careerSuccessViewModel =
                ViewModelProviders.of(this).get(CareerSuccessViewModel.class);
        View root = inflater.inflate(R.layout.fragment_career_success, container, false);
        final TextView textView = root.findViewById(R.id.text1_career_success);
        final Spinner trackSpinner = (Spinner)root.findViewById(R.id.track_spinner);
        final ArrayAdapter trackAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.track, android.R.layout.simple_dropdown_item_1line);
        trackSpinner.setAdapter(trackAdapter);
        trackSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Graduation_Info_List.Graduation_Info_compare((String)parent.getItemAtPosition(position));
                switch (position) {
                    case 0:
                        break;
                    default:    break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
