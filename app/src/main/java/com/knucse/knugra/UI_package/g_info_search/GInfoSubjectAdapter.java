package com.knucse.knugra.UI_package.g_info_search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.knucse.knugra.R;

import java.util.ArrayList;

public class GInfoSubjectAdapter extends RecyclerView.Adapter<GInfoSubjectAdapter.ViewHolder> {
    private ArrayList<String[]> mData = null;

    GInfoSubjectAdapter(ArrayList<String[]> list) {
        mData = list;
    }

    @Override
    public GInfoSubjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycler_item_ginfo_subject, parent, false) ;
        GInfoSubjectAdapter.ViewHolder vh = new GInfoSubjectAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(GInfoSubjectAdapter.ViewHolder holder, int position) {

        String[] item = mData.get(position) ;

        holder.required_subject_name.setText(item[0]) ;
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView required_subject_name;

        public ViewHolder(View itemView) {
            super(itemView);

            required_subject_name = itemView.findViewById(R.id.gis_subject_name);
        }
    }
}
