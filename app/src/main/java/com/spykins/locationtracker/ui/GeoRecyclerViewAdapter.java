package com.spykins.locationtracker.ui;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spykins.locationtracker.R;
import com.spykins.locationtracker.model.GeoData;

import java.util.List;

public class GeoRecyclerViewAdapter extends RecyclerView.Adapter<GeoRecyclerViewAdapter.GeoRecyclerVieHolder> {



    private List<GeoData> mGeoDataItems;

    @Override
    public GeoRecyclerVieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.geo_recyclerview_item, parent, false);
        return new GeoRecyclerVieHolder(view);
    }

    @Override
    public void onBindViewHolder(GeoRecyclerVieHolder holder, int position) {
        holder.bind(mGeoDataItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mGeoDataItems.size();
    }

    public void setGeoDataItems(List<GeoData> geoDataItems) {
        mGeoDataItems = geoDataItems;
    }

    public class GeoRecyclerVieHolder extends RecyclerView.ViewHolder {

        private TextView mGeoItemTimeEntered;
        private TextView mGeoItemDate;
        private TextView mGeoItemTimeLeft;
        private TextView mGeoItemAddress;
        private TextView mTimeSpent;
        public GeoRecyclerVieHolder(View itemView) {
            super(itemView);
            mGeoItemDate = itemView.findViewById(R.id.geo_item_date);
            mGeoItemTimeEntered = itemView.findViewById(R.id.geo_item_time_entered);
            mGeoItemTimeLeft = itemView.findViewById(R.id.geo_item_time_left);
            mGeoItemAddress = itemView.findViewById(R.id.geo_item_address);
            mTimeSpent = itemView.findViewById(R.id.time_spent);
        }

        public void bind(GeoData geoData) {
            mGeoItemDate.setText(geoData.getDate().toString());
            mGeoItemTimeEntered.setText(String.valueOf(geoData.getEnteredTime()));
            mGeoItemTimeLeft.setText(String.valueOf(geoData.getExitTime()));
            mTimeSpent.setText(String.valueOf(geoData.getTimeSpent()));
            mGeoItemAddress.setText(geoData.getAddress());
        }
    }
}
