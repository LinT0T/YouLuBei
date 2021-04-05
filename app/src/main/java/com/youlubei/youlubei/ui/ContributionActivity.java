package com.youlubei.youlubei.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.youlubei.youlubei.R;
import com.youlubei.youlubei.ui.view.GitHubContributionView;
import com.youlubei.youlubei.utils.SharedPreferenceUtil;
import com.youlubei.youlubei.utils.Utils;

import java.time.Month;
import java.util.Calendar;


public class ContributionActivity extends AppCompatActivity {

    private GitHubContributionView contributionView;
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