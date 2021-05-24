package com.youlubei.youlubei.ui;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.youlubei.youlubei.R;
import com.youlubei.youlubei.ui.view.GitHubContributionView;
import com.youlubei.youlubei.utils.SharedPreferenceUtil;
import com.youlubei.youlubei.utils.Utils;

import java.util.Calendar;

public class MineActivity extends AppCompatActivity {
    private GitHubContributionView contributionView;
    private Calendar calendar;
    private SharedPreferenceUtil sharedPreferences;
    private String str = "";
    private int fmonth;
    private int month;
    private int day;
    private boolean isFirstIn = false;
    private ConstraintLayout userLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        ImageView mineImageView = findViewById(R.id.img_mine);
        ImageView homeImageView = findViewById(R.id.img_home);
        ImageView favoriteView = findViewById(R.id.favorite_view);
        userLayout = findViewById(R.id.root_user);
        userLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MineActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        Transition transition = new TransitionSet()
                .addTransition(new Slide(Gravity.START)
                        .addTarget(R.id.tv_user_name_mine)
                        .addTarget(R.id.tv_jiaru)

                )
//                .addTransition(new Slide(Gravity.BOTTOM).addTarget(R.id.root_item))
                .addTransition(new Fade().addTarget(R.id.root_login))
                .addTransition(new Slide(Gravity.END).addTarget(R.id.img_head))
                .setDuration(500);

//        getWindow().setExitTransition(transition);
        getWindow().setEnterTransition(transition);
//        getWindow().setReenterTransition(transition);
        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });
        mineImageView.setSelected(true);
        favoriteView.setOnClickListener(v -> {
            Intent intent = new Intent(MineActivity.this, FavoriteActivity.class);
            startActivity(intent);
        });
        Utils.initBar(this);
        contributionView = findViewById(R.id.contribution_chart);
        Intent intent = getIntent();
        calendar = Calendar.getInstance();
        sharedPreferences = SharedPreferenceUtil.getInstance();
        int current = intent.getIntExtra("level", 0);
        isFirstIn = (boolean) SharedPreferenceUtil.getInstance().get(this, "isFirstIn", true);
        sharedPreferences.put(this, "isFirstIn", false);
        if (isFirstIn) {
            sharedPreferences.put(this, "fmonth", calendar.get(Calendar.MONTH) + 1);
        }
        fmonth = (int) SharedPreferenceUtil.getInstance().get(this, "fmonth", 0);
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