package com.example.hava.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.hava.model.Trip;
import com.example.hava.model.TripsResponse;
import com.example.hava.service.RetrofitInstance;
import com.example.hava.service.TripDataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

@Database(entities = {Trip.class}, version = 1)
public abstract class TripDatabase extends RoomDatabase {
    private static TripDatabase instance;
    public abstract TripDao tripDao();

    public static synchronized TripDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(), TripDatabase.class,
                    "books_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    private static void fetchTrips(final TripDatabase tripDatabase) {
        TripDataService tripDataService = RetrofitInstance.getService();
        Call<TripsResponse> call = tripDataService.getTrips();

        call.enqueue(new retrofit2.Callback<TripsResponse>() {
            @Override
            public void onResponse(Call<TripsResponse> call, Response<TripsResponse> response) {
                TripsResponse tripsResponse = response.body();

                if(tripsResponse != null && tripsResponse.getTrips() != null){
                    ArrayList<Trip> trips = (ArrayList<Trip>) tripsResponse.getTrips();
                    new InsertTripAsyncTask(tripDatabase).execute(trips);
                }
            }

            @Override
            public void onFailure(Call<TripsResponse> call, Throwable t) {

            }
        });
    }

    private static class InsertTripAsyncTask extends AsyncTask<List<Trip>, Void, Void> {
        private TripDao tripDao;

        public InsertTripAsyncTask(TripDatabase tripDao) {
            this.tripDao = tripDao.tripDao();
        }

        @Override
        protected Void doInBackground(List<Trip>... lists) {
            for(int i = 0; i < lists[0].size(); ++i){
                tripDao.insert(lists[0].get(i));
            }
            return null;
        }
    }
}
