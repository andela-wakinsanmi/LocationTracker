package com.spykins.locationtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.spykins.locationtracker.db.DbManager;
import com.spykins.locationtracker.ui.GeoRecyclerViewAdapter;

public class ViewGeoListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private GeoRecyclerViewAdapter mGeoRecyclerViewAdapter;
    private DbManager mDbManager;
    private TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_geo_list);

        mDbManager = Injector.provideDbManager();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mGeoRecyclerViewAdapter = new GeoRecyclerViewAdapter();
        mRecyclerView = findViewById(R.id.recycleView);
        mEmptyTextView = findViewById(R.id.emptyList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mGeoRecyclerViewAdapter);
        mGeoRecyclerViewAdapter.setGeoDataItems(mDbManager.getGeoDatabase().geoDataDao().fetchAllGeodata());
        if (mGeoRecyclerViewAdapter.getItemCount() == 0) {
            mEmptyTextView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
