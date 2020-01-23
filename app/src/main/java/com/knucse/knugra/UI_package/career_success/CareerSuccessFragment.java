package com.knucse.knugra.UI_package.career_success;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knucse.knugra.PD_package.Graduation_Info_package.Graduation_Info_List;
import com.knucse.knugra.PD_package.User_package.Student_package.Student;
import com.knucse.knugra.PD_package.User_package.User;
import com.knucse.knugra.R;
import com.knucse.knugra.UI_package.MainActivity;

import java.util.ArrayList;
import java.util.StringTokenizer;


import static com.knucse.knugra.DM_package.DAPATH.*;

public class CareerSuccessFragment extends Fragment {
    private CareerSuccessViewModel careerSuccessViewModel;
    private static ArrayList<RecyclerItem> mData = new ArrayList<RecyclerItem>();
    private static ArrayList<String[]> requiredmData = new ArrayList<String[]>();
    private static ArrayList<String[]> designmData = new ArrayList<String[]>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        careerSuccessViewModel =
                ViewModelProviders.of(this).get(CareerSuccessViewModel.class);
        View root = inflater.inflate(R.layout.fragment_career_success, container, false);
        final Spinner trackSpinner = (Spinner)root.findViewById(R.id.track_spinner);

        final RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_career_success);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final RecyclerView requiredRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_required_subject_complete);
        requiredRecyclerView.setHasFixedSize(true);
        requiredRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final RecyclerView designRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_design_subject_complete);
        designRecyclerView.setHasFixedSize(true);
        designRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final ArrayAdapter trackAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.track, android.R.layout.simple_spinner_dropdown_item);

        trackSpinner.setAdapter(trackAdapter);
        trackSpinner.setSelection(((MainActivity)getActivity()).getMajorposition());
        trackSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String[]> data;
                data = Graduation_Info_List.Graduation_Info_compare((String)parent.getItemAtPosition(position));
                mData = getCsList(data);
                CareerSuccessAdapter cs_adapter = new CareerSuccessAdapter(mData);
                recyclerView.setAdapter(cs_adapter);
                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

                switch (position) {
                    case 0:
                        requiredmData = Graduation_Info_List.Subject_Required_check();
                        RequiredCompleteAdapter required_sc_adapter = new RequiredCompleteAdapter(requiredmData);
                        requiredRecyclerView.setAdapter(required_sc_adapter);
                        requiredRecyclerView.addItemDecoration(new DividerItemDecoration(requiredRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

                        designmData = Graduation_Info_List.Subject_Design_check();
                        DesignCompleteAdapter design_sc_adapter = new DesignCompleteAdapter(designmData);
                        designRecyclerView.setAdapter(design_sc_adapter);
                        designRecyclerView.addItemDecoration(new DividerItemDecoration(designRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
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

    public ArrayList<RecyclerItem> getCsList(ArrayList<String[]> data){
        ArrayList<RecyclerItem> rcItems = new ArrayList<RecyclerItem>();

        for (int i=0; i<data.size(); i++){
            RecyclerItem item = new RecyclerItem();
            String[] str = data.get(i);
            item.setSc_item(str[0]);
            if (str[2].equals(""))
                item.setSc_pct("");
            else
                item.setSc_pct(str[2] + " / " +str[1]);
            item.setSc_percent(str[3]);
            StringTokenizer st = new StringTokenizer(str[3], "%");
            item.setPrg(Integer.parseInt(st.nextToken()));
            ProgressBar pg = item.getSuccess_prg();
            item.setSuccess_prg(pg);
            rcItems.add(item);
        }
        return rcItems;
    }

    public static CareerSuccessFragment newInstance() {
        CareerSuccessFragment fragment = new CareerSuccessFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

}