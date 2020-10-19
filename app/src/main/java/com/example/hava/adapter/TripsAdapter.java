package com.example.hava.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hava.R;
import com.example.hava.databinding.ItemTripBinding;
import com.example.hava.model.Trip;
import com.example.hava.utils.Constants;
import com.example.hava.view.MyTripActivity;

import java.util.ArrayList;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.TripViewHolder>{
    private Context context;
    private ArrayList<Trip> trips;

    public TripsAdapter(Context context, ArrayList<Trip> trips) {
        this.context = context;
        this.trips = trips;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTripBinding tripsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_trip, parent, false);
        return new TripViewHolder(tripsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip trip = trips.get(position);
        String status = trip.getStatus();

        if(status.equalsIgnoreCase("CANCELED")) {
            holder.itemTripBinding.tvStatus.setTextColor(Color.parseColor("#E74C3C"));
            holder.itemTripBinding.imgStatus.setBackgroundResource(R.drawable.canceled);
            holder.itemTripBinding.setTrip(trip);
        } else {
            holder.itemTripBinding.tvStatus.setTextColor(Color.parseColor("#000000"));
            holder.itemTripBinding.imgStatus.setBackgroundResource(R.drawable.complete);
            holder.itemTripBinding.setTrip(trip);
        }


    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public class TripViewHolder extends RecyclerView.ViewHolder {
        private ItemTripBinding itemTripBinding;

        public TripViewHolder(@NonNull ItemTripBinding itemTripBinding) {
            super(itemTripBinding.getRoot());
            this.itemTripBinding = itemTripBinding;


            itemTripBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION) {
                        Trip selectedTrip = trips.get(position);

                        Intent intent=new Intent(context, MyTripActivity.class);
                        intent.putExtra(Constants.PARSED_TRIP, selectedTrip);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
