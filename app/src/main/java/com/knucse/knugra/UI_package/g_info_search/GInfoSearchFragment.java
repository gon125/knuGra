package com.knucse.knugra.UI_package.g_info_search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

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
    private static ArrayList<ArrayList<String[]>> ginfoDatas = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gInfoSearchViewModel =
                ViewModelProviders.of(this).get(GInfoSearchViewModel.class);
        ((TextView)((MainActivity)getActivity()).findViewById(R.id.toolbar_title)).setText(R.string.menu_g_info_search);
        View root = inflater.inflate(R.layout.fragment_g_info_search, container, false);
        final Spinner trackSpinner = (Spinner)root.findViewById(R.id.track_spinner);
        final ArrayAdapter trackAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.track, android.R.layout.simple_spinner_dropdown_item);

        final RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_ginfo_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final RecyclerView ginfoRequiredRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_ginfo_required);
        ginfoRequiredRecyclerView.setHasFixedSize(true);
        ginfoRequiredRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final RecyclerView ginfoDesignRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_ginfo_design);
        ginfoDesignRecyclerView.setHasFixedSize(true);
        ginfoDesignRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final LinearLayout gisRequiredLayout = (LinearLayout)root.findViewById(R.id.gis_required_layout);
        final LinearLayout gisDesignLayout = (LinearLayout)root.findViewById(R.id.gis_design_layout);

        trackSpinner.setAdapter(trackAdapter);
        trackSpinner.setSelection(((MainActivity)getActivity()).getMajorposition());
        trackSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ginfoDatas = Graduation_Info_List.Graduation_Info_search((String)parent.getItemAtPosition(position));
                //기본 졸업요건 항목
                GInfoSearchAdapter gis_adapter = new GInfoSearchAdapter(getGisList(ginfoDatas.get(0)));
                recyclerView.setAdapter(gis_adapter);
                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

                GInfoSubjectAdapter gis_required_adapter, gis_design_adapter;
                gisRequiredLayout.setVisibility(LinearLayout.GONE);
                gisDesignLayout.setVisibility(LinearLayout.GONE);
                switch (position) {
                    case 0: //COMPUTER_ABEEK
                        //필수과목
                        gis_required_adapter = new GInfoSubjectAdapter(ginfoDatas.get(1));
                        ginfoRequiredRecyclerView.setAdapter(gis_required_adapter);
                        ginfoRequiredRecyclerView.addItemDecoration(new DividerItemDecoration(ginfoRequiredRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        gisRequiredLayout.setVisibility(LinearLayout.VISIBLE);
                        //설계과목
                        gis_design_adapter = new GInfoSubjectAdapter(ginfoDatas.get(2));
                        ginfoDesignRecyclerView.setAdapter(gis_design_adapter);
                        ginfoDesignRecyclerView.addItemDecoration(new DividerItemDecoration(ginfoDesignRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        gisDesignLayout.setVisibility(LinearLayout.VISIBLE);
                        break;
                    case 1: case 2: case 3: //GLOBAL_SOFTWARE
                        //필수과목
                        gis_required_adapter = new GInfoSubjectAdapter(ginfoDatas.get(1));
                        ginfoRequiredRecyclerView.setAdapter(gis_required_adapter);
                        ginfoRequiredRecyclerView.addItemDecoration(new DividerItemDecoration(ginfoRequiredRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        gisRequiredLayout.setVisibility(LinearLayout.VISIBLE);
                        break;
                    case 4: case 5: case 6: case 7: //CONNECTED_SOFTWARE
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

    public ArrayList<String[]> getGisList(ArrayList<String[]> data){
        ArrayList<String[]> returnList = new ArrayList<String[]>();

        for (int i=0; i<data.size(); i++){
            String[] item = new String[2];
            String[] str = data.get(i);

            item[0] = str[0];
            if (str[0].equals("영어성적") || str[0].equals("스타트업"))
                item[1] = str[1];
            else if (str[0].equals("공학상담") || str[0].equals("상담"))
                item[1] = str[1] + " 회";
            else if (str[0].equals("필수과목"))
                item[1] = str[1] + " 과목";
            else
                item[1] = str[1] + " 학점";

            returnList.add(item);
        }
        return returnList;
    }
}