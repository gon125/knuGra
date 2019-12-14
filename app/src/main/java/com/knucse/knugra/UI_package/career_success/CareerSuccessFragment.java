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
import com.knucse.knugra.UI_package.MainActivity;

import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class CareerSuccessFragment extends Fragment {
    private CareerSuccessViewModel careerSuccessViewModel;
    private static ArrayList<String[]> tableViewDatas =  new ArrayList<String[]>();
    private static final String[] TABLE_HEADERS = { "항목", "졸업기준", "학생", "달성도" };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        careerSuccessViewModel =
                ViewModelProviders.of(this).get(CareerSuccessViewModel.class);
        View root = inflater.inflate(R.layout.fragment_career_success, container, false);
        final Spinner trackSpinner = (Spinner)root.findViewById(R.id.track_spinner);
        final ArrayAdapter trackAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.track, android.R.layout.simple_dropdown_item_1line);
        final TableView<String[]> tableView = root.findViewById(R.id.tableView);

        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this.getActivity(), TABLE_HEADERS));
        tableView.setColumnCount(4);

        trackSpinner.setAdapter(trackAdapter);
        trackSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 tableViewDatas = Graduation_Info_List.Graduation_Info_compare((String)parent.getItemAtPosition(position));
                 tableView.setDataAdapter(new SimpleTableDataAdapter(getActivity(), tableViewDatas));
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
