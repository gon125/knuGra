package com.knucse.knugra.UI_package.career_success;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.knucse.knugra.R;

import java.util.ArrayList;

public class DesignCompleteAdapter extends RecyclerView.Adapter<DesignCompleteAdapter.ViewHolder> {
    private ArrayList<String[]> mData = null;

    DesignCompleteAdapter(ArrayList<String[]> list) {
        mData = list;
    }

    @Override
    public DesignCompleteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycler_item_design_complete, parent, false) ;
        DesignCompleteAdapter.ViewHolder vh = new DesignCompleteAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(DesignCompleteAdapter.ViewHolder holder, int position) {
        String[] item = mData.get(position) ;

        holder.sc_subject.setText(item[0]) ;
        holder.sc_content.setText(item[2]);
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView sc_subject;
        TextView sc_content;

        public ViewHolder(View itemView) {
            super(itemView);

            sc_subject = itemView.findViewById(R.id.subject_design_complete);
            sc_content = itemView.findViewById(R.id.content_design_complete);
        }
    }
}
