package com.example.hava.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.example.hava.R;
import com.example.hava.utils.Constants;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup distanceRadioGroup;
    private RadioGroup durationRadioGroup;
    private RadioButton radioDistanceAny;
    private RadioButton radioLess3km;
    private RadioButton radio3and8km;
    private RadioButton radio8and15km;
    private RadioButton radioMoreThan15km;
    private RadioButton radioDurationAny;
    private RadioButton radioLess5mins;
    private RadioButton radio5and10mins;
    private RadioButton radio10and20mins;
    private RadioButton radioMoreThan20mins;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean isCanceled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences(Constants.MAIN_SHARED_PREF, Context.MODE_PRIVATE);
        Switch switchButton = findViewById(R.id.switchButton);
        distanceRadioGroup = findViewById(R.id.distanceRadioGroup);
        durationRadioGroup = findViewById(R.id.durationRadioGroup);

        radioDistanceAny = findViewById(R.id.radioDistanceAny);
        radioLess3km = findViewById(R.id.radioLess3);
        radio3and8km = findViewById(R.id.radio3and8);
        radio8and15km = findViewById(R.id.radio8and15);
        radioMoreThan15km = findViewById(R.id.radioMoreThan15);
        radioDurationAny = findViewById(R.id.radioDurationAny);
        radioLess5mins = findViewById(R.id.radioLess5mins);
        radio5and10mins = findViewById(R.id.radio5and10mins);
        radio10and20mins = findViewById(R.id.radio10and20mins);
        radioMoreThan20mins = findViewById(R.id.radioMoreThan20mins);

        getSupportActionBar().setTitle("Search Trip");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button buttonMainMap = findViewById(R.id.buttonMainMap);

        setRadioButtonGroupChecked();

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isCanceled = true;
                } else {
                    isCanceled = false;
                }
                editor = sharedPreferences.edit();
                editor.putBoolean(Constants.GET_CANCELED, isCanceled);
                editor.clear();
                editor.apply();
            }
        });

        buttonMainMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, TripsActivity.class);
                startActivity(intent);
            }
        });

        distanceRadioGroup.setOnCheckedChangeListener(this);
        durationRadioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setRadioButtonGroupChecked();
    }

    public void setRadioButtonGroupChecked() {
        int distanceFilterId = sharedPreferences.getInt(Constants.FILTER_DISTANCE_ID, 0);
        int timeFilterId = sharedPreferences.getInt(Constants.FILTER_TIME_ID, 0);

        switch (distanceFilterId) {
            case 0:
                radioDistanceAny.setChecked(true);
                break;
            case 1:
                radioLess3km.setChecked(true);
                break;
            case 2:
                radio3and8km.setChecked(true);
                break;
            case 3:
                radio8and15km.setChecked(true);
                break;
            case 4:
                radioMoreThan15km.setChecked(true);
                break;
            default:
                break;
        }

        switch (timeFilterId) {
            case 0:
                radioDurationAny.setChecked(true);
                break;
            case 1:
                radioLess5mins.setChecked(true);
                break;
            case 2:
                radio5and10mins.setChecked(true);
                break;
            case 3:
                radio10and20mins.setChecked(true);
                break;
            case 4:
                radioMoreThan20mins.setChecked(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == distanceRadioGroup) {
            switch (group.getCheckedRadioButtonId()) {
                case R.id.radioDistanceAny:
                    sharedPreferences.edit().putInt(Constants.FILTER_DISTANCE_ID, 0).apply();
                    break;

                case R.id.radioLess3:
                    sharedPreferences.edit().putInt(Constants.FILTER_DISTANCE_ID, 1).apply();
                    break;

                case R.id.radio3and8:
                    sharedPreferences.edit().putInt(Constants.FILTER_DISTANCE_ID, 2).apply();
                    break;

                case R.id.radio8and15:
                    sharedPreferences.edit().putInt(Constants.FILTER_DISTANCE_ID, 3).apply();
                    break;

                case R.id.radioMoreThan15:
                    sharedPreferences.edit().putInt(Constants.FILTER_DISTANCE_ID, 4).apply();
                    break;

                default:
                    break;
            }
        }
        if (group == distanceRadioGroup) {
            switch (group.getCheckedRadioButtonId()) {
                case R.id.radioDurationAny:
                    sharedPreferences.edit().putInt(Constants.FILTER_TIME_ID, 0).apply();
                    break;

                case R.id.radioLess5mins:
                    sharedPreferences.edit().putInt(Constants.FILTER_TIME_ID, 1).apply();
                    break;

                case R.id.radio5and10mins:
                    sharedPreferences.edit().putInt(Constants.FILTER_TIME_ID, 2).apply();
                    break;

                case R.id.radio10and20mins:
                    sharedPreferences.edit().putInt(Constants.FILTER_TIME_ID, 3).apply();
                    break;

                case R.id.radioMoreThan20mins:
                    sharedPreferences.edit().putInt(Constants.FILTER_TIME_ID, 4).apply();
                    break;

                default:
                    break;
            }
        }
    }
}
