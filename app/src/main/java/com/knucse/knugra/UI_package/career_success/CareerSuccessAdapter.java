package com.knucse.knugra.UI_package.career_success;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

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
    public void onBindViewHolder(CareerSuccessAdapter.ViewHolder holder, int position) {

        RecyclerItem item = mData.get(position) ;

        holder.sc_item.setText(item.getSc_item()) ;
        holder.sc_pct.setText(item.getSc_pct());
        holder.sc_percent.setText(item.getSc_percent());
        holder.success_prg.setProgress(item.getPrg());
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

        public ViewHolder(View itemView) {
            super(itemView);

            sc_item = itemView.findViewById(R.id.item_career_success);
            sc_pct = itemView.findViewById(R.id.percent_career_success);
            sc_percent = itemView.findViewById(R.id.percent_career_success2);
            success_prg = itemView.findViewById(R.id.success_progress);
        }
    }
}
