package com.example.hava.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.hava.model.Trip;

import java.util.List;

@Dao
public interface TripDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Trip trip);

    @Query("SELECT * FROM trip_table")
    LiveData<List<Trip>> getAllTrips();

    @Query("SELECT * FROM trip_table")
    List<Trip> checkIfEmpty();

    @Query("SELECT * FROM trip_table WHERE status=='COMPLETED'")
    LiveData<List<Trip>> getCompletedTrips();

    @Query("SELECT * FROM trip_table WHERE distance<3")
    LiveData<List<Trip>> getTripsUnder3km();

    @Query("SELECT * FROM trip_table WHERE status=='COMPLETED' and distance<3")
    LiveData<List<Trip>> getTripsUnder3kmCompleted();

    @Query("SELECT * FROM trip_table WHERE distance>15")
    LiveData<List<Trip>> getTripsAbove15km();

    @Query("SELECT * FROM trip_table WHERE distance>=:distanceMin and distance<=:distanceMax")
    LiveData<List<Trip>> filterDistance(int distanceMin, int distanceMax);

    @Query("SELECT * FROM trip_table WHERE distance>=:durationMin and distance<=:durationMax")
    LiveData<List<Trip>> filterDuration(int durationMin, int durationMax);

    @Query("SELECT * FROM trip_table WHERE status=='COMPLETED' and distance>=:distanceMin and distance<=:distanceMax")
    LiveData<List<Trip>> filterDistanceCompletedTrips(int distanceMin, int distanceMax);

    @Query("SELECT * FROM trip_table WHERE distance>=:distanceMin and distance<=:distanceMax and duration>=:duationMin and duration<=:durationMax")
    LiveData<List<Trip>> filterDistancedAndDurationTrips(int distanceMin, int distanceMax, int duationMin, int durationMax);

    @Query("SELECT * FROM trip_table WHERE duration<5")
    LiveData<List<Trip>> getTripsUnder5mins();
}
