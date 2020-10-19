package com.example.hava.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.example.hava.R;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.screen1);
        images.add(R.drawable.screen2);
        images.add(R.drawable.screen3);
        images.add(R.drawable.screen4);

        ViewPager viewPager = findViewById(R.id.viewPagerOboarding);
        viewPager.setAdapter(new MyViewPagerAdapter(this, images));

        getSupportActionBar().hide();

        openMap();
    }

    private void openMap() {
        int interval = 4000;
        Handler handler = new Handler();
        Runnable runnable = new Runnable(){
            public void run() {
                Intent intent=new Intent(OnboardingActivity.this, MainMapActivity.class);
                startActivity(intent);
            }
        };
        handler.postAtTime(runnable, System.currentTimeMillis()+interval);
        handler.postDelayed(runnable, interval);
    }

    private class MyViewPagerAdapter extends PagerAdapter {
        private List<Integer> imageList;
        private Context context;

        public MyViewPagerAdapter(Context context, List<Integer> images) {
            this.context = context;
            imageList = images;
        }

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(context);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_view_pager, container, false);

            ImageView imageView = layout.findViewById(R.id.onBoardingImage);
            imageView.setImageResource(imageList.get(position));

            container.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);;
        }
    }
}
