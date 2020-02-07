package com.knucse.knugra.UI_package.career_success;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import java.util.StringTokenizer;

public class CareerSuccessFragment extends Fragment {
    private CareerSuccessViewModel careerSuccessViewModel;
    private static ArrayList<RecyclerItem> mData = new ArrayList<RecyclerItem>();
    private static ArrayList<String[]> requiredmData = new ArrayList<String[]>();
    private static ArrayList<String[]> designmData = new ArrayList<String[]>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        careerSuccessViewModel =
                ViewModelProviders.of(this).get(CareerSuccessViewModel.class);
        ((TextView)((MainActivity)getActivity()).findViewById(R.id.toolbar_title)).setText(R.string.menu_career_success);
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

        final LinearLayout csRequiredLayout = (LinearLayout)root.findViewById(R.id.cs_required_layout);
        final LinearLayout csDesignLayout = (LinearLayout)root.findViewById(R.id.cs_design_layout);

        final ArrayAdapter trackAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.track, android.R.layout.simple_spinner_dropdown_item);

        trackSpinner.setAdapter(trackAdapter);
        trackSpinner.setSelection(((MainActivity)getActivity()).getMajorposition());
        trackSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String[]> data;
                //기본 졸업요건 항목 달성현황
                data = Graduation_Info_List.Graduation_Info_compare((String)parent.getItemAtPosition(position));
                mData = getCsList(data);
                CareerSuccessAdapter cs_adapter = new CareerSuccessAdapter(mData);
                recyclerView.setAdapter(cs_adapter);
                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

                SubjectCompleteAdapter required_sc_adapter, design_sc_adapter;
                csRequiredLayout.setVisibility(LinearLayout.GONE);
                csDesignLayout.setVisibility(LinearLayout.GONE);
                switch (position) {
                    case 0:
                        //필수과목 이수현황
                        requiredmData = Graduation_Info_List.getResultRequired();
                        requiredmData.remove(0);
                        required_sc_adapter = new SubjectCompleteAdapter(requiredmData);
                        requiredRecyclerView.setAdapter(required_sc_adapter);
                        requiredRecyclerView.addItemDecoration(new DividerItemDecoration(requiredRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        csRequiredLayout.setVisibility(LinearLayout.VISIBLE);
                        //설계과목 이수현황
                        designmData = Graduation_Info_List.getResultDesign();
                        designmData.remove(0);
                        design_sc_adapter = new SubjectCompleteAdapter(designmData);
                        designRecyclerView.setAdapter(design_sc_adapter);
                        designRecyclerView.addItemDecoration(new DividerItemDecoration(designRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        csDesignLayout.setVisibility(LinearLayout.VISIBLE);
                        break;
                    case 1: case 2: case 3:
                        //필수과목 이수현황
                        requiredmData = Graduation_Info_List.getResultRequired();
                        requiredmData.remove(0);
                        required_sc_adapter = new SubjectCompleteAdapter(requiredmData);
                        requiredRecyclerView.setAdapter(required_sc_adapter);
                        requiredRecyclerView.addItemDecoration(new DividerItemDecoration(requiredRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        csRequiredLayout.setVisibility(LinearLayout.VISIBLE);
                        break;
                    case 4: case 5: case 6: case 7:
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
            if (str[0].equals("총  합"))
                item.setSc_pct("");
            else if (str[0].equals("영어성적") || str[0].equals("스타트업") || (str[0].equals("해외역량") && str[1].equals("복수학위취득")))
                item.setSc_pct(str[2]);
            else if (str[0].equals("공학상담") || str[0].equals("상담"))
                item.setSc_pct(str[2] + " / " + str[1] + " 회");
            else if (str[0].equals("필수과목"))
                item.setSc_pct(str[2] + " / " +str[1] + " 과목");
            else
                item.setSc_pct(str[2] + " / " +str[1] + " 학점");
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