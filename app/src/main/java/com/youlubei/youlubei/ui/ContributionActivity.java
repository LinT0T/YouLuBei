package com.youlubei.youlubei.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.youlubei.youlubei.R;
import com.youlubei.youlubei.ui.view.GitHubContributionView;
import com.youlubei.youlubei.utils.SharedPreferenceUtil;
import com.youlubei.youlubei.utils.Utils;

import java.time.Month;
import java.util.Calendar;


public class ContributionActivity extends AppCompatActivity {

    private GitHubContributionView contributionView;
    private ImageView backImageView;
    private SharedPreferenceUtil sharedPreferences;
    private String str = "";
    private int fmonth;
    private int month;
    private int day;
    private boolean isFirstIn = false;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrbution);
        contributionView = findViewById(R.id.contribution_chart);
        backImageView = findViewById(R.id.img_back_contribution);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });
        Animation animation = new ScaleAnimation(2f,1f,2f,1f);
        animation.setDuration(500);
        Animation animation1 = new AlphaAnimation(0f,1f);
        animation1.setDuration(500);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(animation);
        animationSet.addAnimation(animation1);
        animationSet.setDuration(1000);
        contributionView.startAnimation(animationSet);
        sharedPreferences = SharedPreferenceUtil.getInstance();
        Utils.initBar(this);
        Intent intent = getIntent();
        calendar = Calendar.getInstance();
        int current = intent.getIntExtra("level", 0);
        isFirstIn = (boolean) sharedPreferences.get(this, "isFirstIn", true);
        sharedPreferences.put(this, "isFirstIn", false);
        if (isFirstIn) {
            sharedPreferences.put(this, "fmonth", calendar.get(Calendar.MONTH) + 1);
        }
        fmonth = (int) sharedPreferences.get(this, "fmonth", 0);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        sharedPreferences.put(this, month + day + str, current);
        for (int i = fmonth; i <= month; i++) {
            for (int j = 1; j <= 31; j++) {
                contributionView.setData(2021, i, j, (Integer) sharedPreferences.get(this, i + j + str, 0));
            }
        }
    }
}