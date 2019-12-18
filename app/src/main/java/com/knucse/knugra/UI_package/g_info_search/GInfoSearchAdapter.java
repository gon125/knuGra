package com.knucse.knugra.UI_package.g_info_search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.knucse.knugra.R;
import com.knucse.knugra.UI_package.career_success.RecyclerItem;

import java.util.ArrayList;

public class GInfoSearchAdapter extends RecyclerView.Adapter<GInfoSearchAdapter.ViewHolder> {
    private ArrayList<String[]> mData = null;

    GInfoSearchAdapter(ArrayList<String[]> list) {
        mData = list;
    }

    @Override
    public GInfoSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycler_item_ginfo_search, parent, false) ;
        GInfoSearchAdapter.ViewHolder vh = new GInfoSearchAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(GInfoSearchAdapter.ViewHolder holder, int position) {

        String[] item = mData.get(position) ;

        holder.gis_item.setText(item[0]) ;
        holder.gis_gra.setText(item[1]);
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView gis_item;
        TextView gis_gra;

        public ViewHolder(View itemView) {
            super(itemView);

            gis_item = itemView.findViewById(R.id.item_ginfo_search);
            gis_gra = itemView.findViewById(R.id.gra_ginfo_search);
        }
    }
}
