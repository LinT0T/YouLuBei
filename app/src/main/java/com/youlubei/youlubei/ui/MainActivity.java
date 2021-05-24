package com.youlubei.youlubei.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.plattysoft.leonids.ParticleSystem;
import com.youlubei.youlubei.R;
import com.youlubei.youlubei.bean.BackgroundBean;
import com.youlubei.youlubei.bean.ContentBean;
import com.youlubei.youlubei.bean.RvBean;
import com.youlubei.youlubei.ui.adapter.RvAdapter;
import com.youlubei.youlubei.utils.SharedPreferenceUtil;
import com.youlubei.youlubei.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements RvAdapter.IonSlidingViewClickListener {

    static final String TAG = "MainActivity";
    private final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false).build();
    /**
     * item正文的点击事件
     *
     * @param view
     * @param position
     */
    AnimatorSet animSet = new AnimatorSet();
    float last = 0f;
    float now = 0f;
    private ImageView backgroundImageView;
    private ImageView clockIn, mineImageView, favoriteImageView;
    private TextView titleTextView, contentChTextView, contentEngTextView;
    private RecyclerView recyclerView;
    private RvAdapter rvAdapter;
    private ConstraintLayout layout;
    private View container;
    private String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAnim();
        ImageView homeImageView = findViewById(R.id.img_home);
        homeImageView.setSelected(true);
        mineImageView = findViewById(R.id.img_mine);

        backgroundImageView = findViewById(R.id.img_background_main);
        clockIn = findViewById(R.id.img_clock_in);
        titleTextView = findViewById(R.id.tv_title_main);
        favoriteImageView = findViewById(R.id.favorite_view);
        contentChTextView = findViewById(R.id.tv_content_ch_main);
        contentEngTextView = findViewById(R.id.tv_content_en_main);
        recyclerView = findViewById(R.id.rv_main);
        layout = findViewById(R.id.layout_root);
        container = findViewById(R.id.main_container);
        List<RvBean> list = new ArrayList<>();

        initData(list);

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "TENET";
            String description = "MESSAGE";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            int noFinish = 4 - rvAdapter.checkFinish();
            String s1, s2;
            if (noFinish == 0) {
                s1 = "今天任务都完成啦！";
                s2 = "放松一下吧~";
            } else {
                s1 = "今日还有" + noFinish + "个任务没完成哦~";
                s2 = "加油！";
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                    .setSmallIcon(R.drawable.ic_good)
                    .setContentTitle(s1)
                    .setContentText(s2)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    // Set the intent that will fire when the user taps the notification
                    .setAutoCancel(true);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

            // notificationId is a unique int for each notification that you must define
            notificationManagerCompat.notify(1, builder.build());

        }
    }

    private void initAnim() {
        Transition transition = new TransitionSet()
                .addTransition(new Slide(Gravity.START)
                        .addTarget(R.id.tv_title_main)
                        .addTarget(R.id.tv_content_ch_main)
                        .addTarget(R.id.tv_content_en_main)

                )
                .addTransition(new Slide(Gravity.BOTTOM).addTarget(R.id.root_item))
                .addTransition(new Fade().addTarget(R.id.img_background_main).addTarget(R.id.img_home).addTarget(R.id.img_mine))
                .addTransition(new Slide(Gravity.END).addTarget(R.id.img_clock_in))
                .setDuration(500);

        getWindow().setExitTransition(transition);
        getWindow().setEnterTransition(transition);
        getWindow().setReenterTransition(transition);
    }

    private void initData(List<RvBean> list) {
        String firstUse = (String) SharedPreferenceUtil.getInstance().get(this, "first_use", "first");
        assert firstUse != null;
        RvBean rvBean0;
        RvBean rvBean1;
        RvBean rvBean2;
        RvBean rvBean3;
        int dayOfYearInData = (int) SharedPreferenceUtil.getInstance().get(this, "date", -1);
        int dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        if (firstUse.equals("first")) {
            rvBean0 = new RvBean("背单词",
                    0,
                    false,
                    30);
            rvBean1 = new RvBean("阅读",
                    1,
                    false,
                    30);
            rvBean2 = new RvBean("学习",
                    2,
                    false,
                    120);
            rvBean3 = new RvBean("运动",
                    3,
                    false,
                    30);
            SharedPreferenceUtil.getInstance().put(this, "first_use", "false");

            Utils.showToast(this, "任务更新了哦");

        } else {
            rvBean0 = new Gson().fromJson((String) SharedPreferenceUtil.getInstance().get(this, "data0", ""), RvBean.class);
            rvBean1 = new Gson().fromJson((String) SharedPreferenceUtil.getInstance().get(this, "data1", ""), RvBean.class);
            rvBean2 = new Gson().fromJson((String) SharedPreferenceUtil.getInstance().get(this, "data2", ""), RvBean.class);
            rvBean3 = new Gson().fromJson((String) SharedPreferenceUtil.getInstance().get(this, "data3", ""), RvBean.class);
            if (rvBean0 == null || rvBean1 == null || rvBean2 == null || rvBean3 == null) {
                rvBean0 = new RvBean("背单词",
                        0,
                        false,
                        30);
                rvBean1 = new RvBean("阅读",
                        1,
                        false,
                        30);
                rvBean2 = new RvBean("学习",
                        2,
                        false,
                        120);
                rvBean3 = new RvBean("运动",
                        3,
                        false,
                        30);
            } else {
                if (dayOfYear != dayOfYearInData) {
                    RvBean bean0 = new Gson().fromJson((String) SharedPreferenceUtil.getInstance().get(this, "data0", ""), RvBean.class);
                    RvBean bean1 = new Gson().fromJson((String) SharedPreferenceUtil.getInstance().get(this, "data0", ""), RvBean.class);
                    RvBean bean2 = new Gson().fromJson((String) SharedPreferenceUtil.getInstance().get(this, "data0", ""), RvBean.class);
                    RvBean bean3 = new Gson().fromJson((String) SharedPreferenceUtil.getInstance().get(this, "data0", ""), RvBean.class);
                    rvBean0 = new RvBean("背单词",
                            0,
                            false,
                            bean0.getNum());
                    rvBean1 = new RvBean("阅读",
                            1,
                            false,
                            bean1.getNum());
                    rvBean2 = new RvBean("学习",
                            2,
                            false,
                            bean2.getNum());
                    rvBean3 = new RvBean("运动",
                            3,
                            false,
                            bean3.getNum());
                    Utils.showToast(this, "任务更新了哦");
                }
            }

        }

        SharedPreferenceUtil.getInstance().put(this, "date", Calendar.getInstance().get(Calendar.DAY_OF_YEAR));

        list.add(rvBean0);
        list.add(rvBean1);
        list.add(rvBean2);
        list.add(rvBean3);
        rvAdapter = new RvAdapter(this, list);
        recyclerView.setAdapter(rvAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new OvershootInLeftAnimator());
        Objects.requireNonNull(recyclerView.getItemAnimator()).setAddDuration(300);
//        int count = 0;
//        for (RvBean rvBean : list) {
//            if (rvBean.isFinish()) {
//                count++;
//            }
//        }
        createNotificationChannel();
        Utils.initBar(this);
        imgUrl = getIntent().getStringExtra("url");
        if (imgUrl != null) {
            Glide.with(this).load(imgUrl).into(backgroundImageView);
        } else {
            loadBackground(this);
        }
        getDate(titleTextView);
        loadContent();
        clockInListener();
    }

    private void getDate(TextView tv) {
        Calendar calendar = Calendar.getInstance();
        int nowTime = calendar.get(Calendar.HOUR_OF_DAY);
        if (nowTime < 11) {
            tv.setText("早上好");
        } else if (nowTime < 13) {
            tv.setText("中午好");
        } else if (nowTime < 18) {
            tv.setText("下午好");
        } else {
            tv.setText("晚上好");
        }

    }

    private void loadBackground(Activity activity) {
        String url = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: ");
                runOnUiThread(() -> {
                    backgroundImageView.setImageResource(R.mipmap.background);
                });

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Gson gson = new Gson();
                BackgroundBean backgroundBean = gson.fromJson(Objects.requireNonNull(response.body()).string(), BackgroundBean.class);
                String imgUrl = "https://www.bing.com" + backgroundBean.getImages().get(0).getUrl();
                runOnUiThread(() -> Glide.with(activity).load(imgUrl).into(backgroundImageView));

                Log.d(TAG, "onResponse: " + imgUrl);

            }
        });
    }

    private void loadContent() {
        String url = "http://open.iciba.com/dsapi/";
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: ");
                e.printStackTrace();


            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Gson gson = new Gson();
                ContentBean contentBean = gson.fromJson(Objects.requireNonNull(response.body()).string(), ContentBean.class);

                runOnUiThread(() -> {
                    contentChTextView.setText(contentBean.getNote());
                    contentEngTextView.setText(contentBean.getContent());
                });


            }
        });
    }

    private void clockInListener() {
        clockIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContributionActivity.class);
                intent.putExtra("level", rvAdapter.checkFinish());
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                startActivity(intent, options.toBundle());
            }
        });
        mineImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MineActivity.class);
                intent.putExtra("level", rvAdapter.checkFinish());
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                startActivity(intent, options.toBundle());
            }
        });
        favoriteImageView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(intent);
        });
    }

    /**
     * item的左滑设置
     *
     * @param view
     * @param rvBean
     */
    @Override
    public void onSetBtnClick(View view, int position, RvBean rvBean) {

        //“设置”点击事件的代码逻辑
//        Toast.makeText(MainActivity.this, "请设置", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, SetActivity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        i.putExtra("url", imgUrl);
        i.putExtra("type", rvBean.getContent());
        i.putExtra("num", String.valueOf(rvBean.getNum()));
        i.putExtra("position", position);
        startActivityForResult(i, 1, options.toBundle());
        rvAdapter.closeMenu();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //此处可以根据两个Code进行判断，本页面和结果页面跳过来的值
        if (requestCode == 1 && resultCode == 3) {
            String result = data.getStringExtra("result");
            int position = data.getIntExtra("position", -1);
            if (!result.equals("") && position != -1) {
                rvAdapter.changeNum(position, result);
            }
        }
    }

    @Override
    public void onItemClick(View view, int position, RvBean rvBean) {
        //点击item正文的代码逻辑
        now += 100;
        ObjectAnimator moveX = ObjectAnimator.ofFloat(titleTextView, "translationX", now, 500f);
        moveX.setDuration(1000);
        animSet.play(moveX);
        animSet.start();
        last = now;
    }

    /**
     * item的左滑删除
     *
     * @param view
     * @param position
     */
    @Override
    public void onDeleteBtnClick(View view, int position, boolean isFinish) {
        rvAdapter.removeData(position);
        if (!isFinish) {
            new ParticleSystem(this, 1000, R.drawable.ic_good, 3000)
                    .setSpeedModuleAndAngleRange(0.05f, 0.2f, -135, -45)
                    .setRotationSpeedRange(-30, 30)
                    .setAcceleration(0.001f, 90)
                    .oneShot(view, 10);
        }

    }

    @Override
    public void onAllFinish() {
//        new ParticleSystem(this, 1000, R.drawable.flower, 3000)
//                .setSpeedModuleAndAngleRange(0.05f, 0.2f, 0, 180)
//                .setRotationSpeedRange(-30, 30)
//                .setAcceleration(0.001f, 90)
//                .emit(container, 30, 3000);

        new ParticleSystem(this, 1000, R.drawable.flower, 3000)
                .setSpeedModuleAndAngleRange(0.05f, 0.2f, 0, 90)
                .setRotationSpeed(60)
                .setAcceleration(0.00005f, 90)
                .emit(0, -100, 30, 1500);
        new ParticleSystem(this, 1000, R.drawable.flower, 3000)
                .setSpeedModuleAndAngleRange(0.05f, 0.2f, 90, 180)
                .setRotationSpeed(60)
                .setAcceleration(0.00005f, 90)
                .emit(Utils.getScreenWidth(this), -100, 30, 1500);
        Utils.showToast(this, "赞！任务全部完成啦");
    }

    @Override
    protected void onPause() {
        rvAdapter.saveData();
        super.onPause();
    }
}