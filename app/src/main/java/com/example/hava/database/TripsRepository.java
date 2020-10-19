package com.example.hava.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.hava.database.TripDao;
import com.example.hava.database.TripDatabase;
import com.example.hava.model.Trip;
import com.example.hava.model.TripsResponse;
import com.example.hava.service.RetrofitInstance;
import com.example.hava.service.TripDataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripsRepository {
    private ArrayList<Trip> trips = new ArrayList<>();
    private MutableLiveData<List<Trip>> mutableLiveData = new MutableLiveData<>();
    private TripDao tripDao;

    public TripsRepository(final Application application) {
        TripDatabase tripDatabase = TripDatabase.getInstance(application);
        tripDao = tripDatabase.tripDao();
    }

    public MutableLiveData<List<Trip>> getMutableLiveDataTrips() {
        TripDataService tripDataService = RetrofitInstance.getService();

        Call<TripsResponse> call = tripDataService.getTrips();

        call.enqueue(new Callback<TripsResponse>() {
            @Override
            public void onResponse(Call<TripsResponse> call, Response<TripsResponse> response) {
                TripsResponse tripsResponse = response.body();

                if(tripsResponse != null && tripsResponse.getTrips() != null){
                    trips = (ArrayList<Trip>) tripsResponse.getTrips();
                    mutableLiveData.setValue(trips);
                }
            }

            @Override
            public void onFailure(Call<TripsResponse> call, Throwable t) {

            }
        });

        return this.mutableLiveData;
    }

    public LiveData<List<Trip>> getTripsFromDataBase() {
        return tripDao.getAllTrips();
    }

    public LiveData<List<Trip>> getTripsUnder3kmCompleted() {
        return tripDao.getTripsUnder3kmCompleted();
    }

    public List<Trip> checkIfEmpty() {
        return tripDao.checkIfEmpty();
    }

    public LiveData<List<Trip>> getCompletedTripsFromDataBase() {
        return tripDao.getCompletedTrips();
    }

    public LiveData<List<Trip>> getTripsUnder3km(){
        return tripDao.getTripsUnder3km();
    }

    public LiveData<List<Trip>> getTripsAbove15km(){
        return tripDao.getTripsAbove15km();
    }

    public LiveData<List<Trip>> filterDistance(int distanceMin, int distanceMax){
        return tripDao.filterDistance(distanceMin, distanceMax);
    }

    public LiveData<List<Trip>> filterDuration(int durationMin, int durationMax){
        return tripDao.filterDuration(durationMin, durationMax);
    }

    public LiveData<List<Trip>> filterDistanceCompletedTrips(int distanceMin, int distanceMax){
        return tripDao.filterDistanceCompletedTrips(distanceMin, distanceMax);
    }

    public LiveData<List<Trip>> filterDistancedAndDurationTrips(int distanceMin, int distanceMax, int duationMin, int durationMax){
        return tripDao.filterDistancedAndDurationTrips(distanceMin, distanceMax, duationMin, durationMax);
    }

    public LiveData<List<Trip>> getTripsUnder5mins(){
        return tripDao.getTripsUnder5mins();
    }

    public void fetchTrips() {
        TripDataService tripDataService = RetrofitInstance.getService();
        Call<TripsResponse> call = tripDataService.getTrips();

        call.enqueue(new retrofit2.Callback<TripsResponse>() {
            @Override
            public void onResponse(Call<TripsResponse> call, Response<TripsResponse> response) {
                TripsResponse tripsResponse = response.body();

                if(tripsResponse != null && tripsResponse.getTrips() != null){
                    ArrayList<Trip> trips = (ArrayList<Trip>) tripsResponse.getTrips();
                    saveTrips(trips);
                }
            }

            @Override
            public void onFailure(Call<TripsResponse> call, Throwable t) {

            }
        });
    }

    public void saveTrips(ArrayList<Trip> trips) {
        for(Trip trip : trips){
            new InsertTripAsyncTask(tripDao).execute(trip);
        }
    }

    private static class InsertTripAsyncTask extends AsyncTask<Trip, Void, Void> {
        private TripDao tripDao;

        public InsertTripAsyncTask(TripDao tripDao) {
            this.tripDao = tripDao;
        }

        @Override
        protected Void doInBackground(Trip... trips) {
            tripDao.insert(trips[0]);
            return null;
        }
    }
}
