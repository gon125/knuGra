package com.knucse.knugra.UI_package.career_success;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knucse.knugra.PD_package.Graduation_Info_package.Graduation_Info;
import com.knucse.knugra.PD_package.Graduation_Info_package.Graduation_Info_List;
import com.knucse.knugra.R;

import java.util.ArrayList;

public class CareerSuccessAdapter extends RecyclerView.Adapter<CareerSuccessAdapter.ViewHolder> {
    private ArrayList<RecyclerItem> mData = null;

    CareerSuccessAdapter(ArrayList<RecyclerItem> list) {
        mData = list;
    }

    @Override
    public CareerSuccessAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycler_item_career_success, parent, false) ;
        CareerSuccessAdapter.ViewHolder vh = new CareerSuccessAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(final CareerSuccessAdapter.ViewHolder holder, int position) {

        RecyclerItem item = mData.get(position) ;

        holder.sc_item.setText(item.getSc_item()) ;
        holder.sc_pct.setText(item.getSc_pct());
        holder.sc_percent.setText(item.getSc_percent());
        holder.success_prg.setProgress(item.getPrg());
        switch (item.getSc_item()){
            case "필수과목": case "설계과목": case "SW필수": case "SW교양": //case "창업역량":
                holder.recyclerView_expand.setVisibility(View.VISIBLE);
                holder.recyclerView_expand.setImageResource((holder.subject_layout.getVisibility() == LinearLayout.GONE) ? R.drawable.ic_expand_more_black_24dp : R.drawable.ic_expand_less_black_24dp);
                ArrayList<String[]> mSubjectData;
                switch (item.getSc_item()){
                    case "필수과목":
                        holder.subject_recycler_name.setText(R.string.required_subject_complete_status);
                        mSubjectData = Graduation_Info_List.getResultRequired();
                        break;
                    case "설계과목":
                        holder.subject_recycler_name.setText(R.string.design_subject_complete_status);
                        mSubjectData = Graduation_Info_List.getResultDesign();
                        break;
                    case "SW필수":
                        holder.subject_recycler_name.setText(R.string.common_subject_complete_status);
                        mSubjectData = Graduation_Info_List.getResultCommonMajor();
                        break;
                    case "SW교양":
                        holder.subject_recycler_name.setText(R.string.general_subject_complete_status);
                        mSubjectData = Graduation_Info_List.getResultGeneral();
                        break;
//                    case "창업역량":
//                        holder.subject_recycler_name.setText(R.string.startup_subject_complete_status);
//                        mSubjectData = Graduation_Info_List.getResultStartup();
//                        break;
                    default:
                        mSubjectData = Graduation_Info_List.getResultRequired();
                        break;
                }
                mSubjectData.remove(0);
                holder.subject_recyclerView.setAdapter(new SubjectCompleteAdapter(mSubjectData));
                holder.subject_recyclerView.addItemDecoration(new DividerItemDecoration(holder.subject_recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                holder.recyclerView_expand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.subject_layout.setVisibility((holder.subject_layout.getVisibility() == LinearLayout.GONE) ? LinearLayout.VISIBLE : LinearLayout.GONE);
                        holder.recyclerView_expand.setImageResource((holder.subject_layout.getVisibility() == LinearLayout.GONE) ? R.drawable.ic_expand_more_black_24dp : R.drawable.ic_expand_less_black_24dp);
                    }
                });
                break;
            default:
                break;
        }
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView sc_item;
        TextView sc_pct;
        TextView sc_percent;
        ProgressBar success_prg;
        LinearLayout subject_layout;
        TextView subject_recycler_name;
        RecyclerView subject_recyclerView;
        ImageView recyclerView_expand;

        public ViewHolder(View itemView) {
            super(itemView);

            sc_item = itemView.findViewById(R.id.item_career_success);
            sc_pct = itemView.findViewById(R.id.percent_career_success);
            sc_percent = itemView.findViewById(R.id.percent_career_success2);
            success_prg = itemView.findViewById(R.id.success_progress);
            subject_layout = itemView.findViewById(R.id.cs_subject_layout);
            subject_recycler_name = itemView.findViewById(R.id.cs_subject);
            subject_recyclerView = itemView.findViewById(R.id.recycler_subject_complete);
            subject_recyclerView.setHasFixedSize(true);
            subject_recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            recyclerView_expand = itemView.findViewById(R.id.recyclerview_expand);
        }
    }
}
