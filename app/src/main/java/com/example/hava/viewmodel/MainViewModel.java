package com.example.hava.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.hava.model.Trip;
import com.example.hava.database.TripsRepository;
import com.example.hava.utils.Constants;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private TripsRepository tripsRepository;
    private LiveData<List<Trip>> liveData;
    private int FILTER_DISTANCE_ID = 0;
    private int FILTER_TIME_ID = 0;
    private boolean GET_CANCELED;

    public MainViewModel(@NonNull Application application) {
        super(application);
        tripsRepository = new TripsRepository(application);
        SharedPreferences sharedPreferences = application.getSharedPreferences(Constants.MAIN_SHARED_PREF, application.MODE_PRIVATE);
        FILTER_DISTANCE_ID = sharedPreferences.getInt(Constants.FILTER_DISTANCE_ID, 0);
        FILTER_TIME_ID = sharedPreferences.getInt(Constants.FILTER_TIME_ID, 0);
        GET_CANCELED = sharedPreferences.getBoolean(Constants.GET_CANCELED, false);
    }

    public LiveData<List<Trip>> getTrips() {
        return tripsRepository.getTripsFromDataBase();
    }

    public LiveData<List<Trip>> getCompletedTrips() {
        return tripsRepository.getCompletedTripsFromDataBase();
    }

    public LiveData<List<Trip>> filterInputs() {
        if(GET_CANCELED && FILTER_DISTANCE_ID == 0 && FILTER_TIME_ID == 0){
            liveData = tripsRepository.getTripsFromDataBase();
        }

        if(GET_CANCELED && FILTER_DISTANCE_ID == 1 && FILTER_TIME_ID == 0){
            liveData = tripsRepository.getTripsUnder3km();
        }

        if(GET_CANCELED && FILTER_DISTANCE_ID == 0 && FILTER_TIME_ID == 1){
            liveData = tripsRepository.getTripsUnder5mins();
        }

        if(GET_CANCELED && FILTER_DISTANCE_ID == 0 && FILTER_TIME_ID == 2){
            liveData = tripsRepository.filterDuration(5, 10);
        }

        if(GET_CANCELED && FILTER_DISTANCE_ID == 0 && FILTER_TIME_ID == 3){
            liveData = tripsRepository.filterDuration(10, 20);
        }

        if(GET_CANCELED && FILTER_DISTANCE_ID == 1 && FILTER_TIME_ID == 2){
            liveData = tripsRepository.filterDistancedAndDurationTrips(3, 8, 5, 10);
        }

        if(GET_CANCELED && FILTER_DISTANCE_ID == 1 && FILTER_TIME_ID == 3){
            liveData = tripsRepository.filterDistancedAndDurationTrips(3, 8, 10, 20);
        }

        if(GET_CANCELED && FILTER_DISTANCE_ID == 2 && FILTER_TIME_ID == 2){
            liveData = tripsRepository.filterDistancedAndDurationTrips(3, 8, 10, 20);
        }

        if(GET_CANCELED && FILTER_DISTANCE_ID == 2 && FILTER_TIME_ID == 3){
            liveData = tripsRepository.filterDistancedAndDurationTrips(3, 8, 5, 10);
        }

        if(GET_CANCELED && FILTER_DISTANCE_ID == 2 && FILTER_TIME_ID == 0){
            liveData = tripsRepository.filterDistance(3, 8);
        }

        if(GET_CANCELED && FILTER_DISTANCE_ID == 3 && FILTER_TIME_ID == 0){
            liveData = tripsRepository.filterDistance(8, 15);
        }

        if(GET_CANCELED && FILTER_DISTANCE_ID == 4 && FILTER_TIME_ID == 0){
            liveData = tripsRepository.getTripsAbove15km();
        }

        if(!GET_CANCELED && FILTER_DISTANCE_ID == 1 && FILTER_TIME_ID == 0){
            liveData = tripsRepository.getTripsUnder3kmCompleted();
        }

        if(!GET_CANCELED && FILTER_DISTANCE_ID == 0 && FILTER_TIME_ID == 0){
            liveData = tripsRepository.getCompletedTripsFromDataBase();
        }

        return liveData;
    }

    public void checkIfDbIsEmpty() {
        tripsRepository.fetchTrips();
    }
}
