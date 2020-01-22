package com.knucse.knugra.UI_package.g_info_search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.knucse.knugra.R;
import com.knucse.knugra.UI_package.MainActivity;

import java.util.ArrayList;

public class GInfoSearchFragment extends Fragment {

    private GInfoSearchViewModel gInfoSearchViewModel;
    private static ArrayList<String[]> ginfoDatas = new ArrayList<String[]>();
    private static ArrayList<String> ginfoSubjectDatas = new ArrayList<String>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gInfoSearchViewModel =
                ViewModelProviders.of(this).get(GInfoSearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_g_info_search, container, false);
        final Spinner trackSpinner = (Spinner)root.findViewById(R.id.track_spinner);
        final ArrayAdapter trackAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.track, android.R.layout.simple_spinner_dropdown_item);

        final RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_ginfo_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        final RecyclerView ginfoSubjectRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_ginfo_required_subject);
//        ginfoSubjectRecyclerView.setHasFixedSize(true);
//        ginfoSubjectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        trackSpinner.setAdapter(trackAdapter);
        trackSpinner.setSelection(((MainActivity)getActivity()).getMajorposition());
        trackSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ginfoDatas = Graduation_Info_List.Graduation_Info_search((String)parent.getItemAtPosition(position));
                GInfoSearchAdapter gis_adapter = new GInfoSearchAdapter(ginfoDatas);
                recyclerView.setAdapter(gis_adapter);
                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

                switch (position) {
                    case 0:
//                        GInfoSubjectAdapter subject_adapter = new GInfoSubjectAdapter(ginfoSubjectDatas);
//                        ginfoSubjectRecyclerView.setAdapter(subject_adapter);
//                        ginfoSubjectRecyclerView.addItemDecoration(new DividerItemDecoration(ginfoSubjectRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        break;
                    default:    break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        gInfoSearchViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
}