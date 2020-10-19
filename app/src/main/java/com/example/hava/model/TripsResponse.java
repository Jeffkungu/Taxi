package com.example.hava.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TripsResponse implements Parcelable {

    @SerializedName("trips")
    @Expose
    private List<Trip> trips = null;

    public List<Trip> getTrips() {
        return trips;
    }

    protected TripsResponse(Parcel in) {
        in.readList(this.trips, (Trip.class.getClassLoader()));
    }

    public static final Creator<TripsResponse> CREATOR = new Creator<TripsResponse>() {
        @SuppressWarnings({
                "unchecked"
        })

        @Override
        public TripsResponse createFromParcel(Parcel in) {
            return new TripsResponse(in);
        }

        @Override
        public TripsResponse[] newArray(int size) {
            return new TripsResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(trips);
    }
}
