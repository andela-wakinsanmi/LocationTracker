package com.spykins.locationtracker.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spykins.locationtracker.R;

public class GeoRecyclerViewAdapter extends RecyclerView.Adapter<GeoRecyclerViewAdapter.GeoRecyclerVieHolder> {


    @Override
    public GeoRecyclerVieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.geo_recyclerview_item, parent, false);
        return new GeoRecyclerVieHolder(view);
    }

    @Override
    public void onBindViewHolder(GeoRecyclerVieHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class GeoRecyclerVieHolder extends RecyclerView.ViewHolder {

        public GeoRecyclerVieHolder(View itemView) {
            super(itemView);
        }
    }
}
