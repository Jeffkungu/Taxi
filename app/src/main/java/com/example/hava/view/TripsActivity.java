package com.example.hava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.hava.R;
import com.example.hava.adapter.TripsAdapter;
import com.example.hava.databinding.ActivityTripsBinding;
import com.example.hava.model.Trip;
import com.example.hava.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class TripsActivity extends AppCompatActivity {
    private ActivityTripsBinding activityTripsBinding;
    private MainViewModel mainViewModel;
    private TripsAdapter tripsAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Trip> tripArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        getSupportActionBar().setTitle("Trips");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activityTripsBinding = DataBindingUtil.setContentView(this, R.layout.activity_trips);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        getTrips();
    }

    public void getTrips() {
        mainViewModel.filterInputs().observe(this, new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                tripArrayList = (ArrayList<Trip>) trips;
                showListInRecyclerView();
            }
        });
    }

    public void showListInRecyclerView() {
        int size = tripArrayList.size();
        String title = "Trips" + " " + "(" + size + ")";
        getSupportActionBar().setTitle(title);
        recyclerView = activityTripsBinding.rvTrips;
        tripsAdapter = new TripsAdapter(this, tripArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tripsAdapter);
        tripsAdapter.notifyDataSetChanged();
    }
}
