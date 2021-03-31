package com.youlubei.youlubei.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.plattysoft.leonids.ParticleSystem;
import com.youlubei.youlubei.R;
import com.youlubei.youlubei.ui.adapter.RvAdapter;
import com.youlubei.youlubei.bean.BackgroundBean;
import com.youlubei.youlubei.bean.ContentBean;
import com.youlubei.youlubei.bean.RvBean;
import com.youlubei.youlubei.utils.SharedPreferenceUtil;

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
    private ImageView backgroundImageView;
    private ImageView clockIn;
    private TextView titleTextView, contentChTextView, contentEngTextView;
    private RecyclerView recyclerView;
    private RvAdapter rvAdapter;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backgroundImageView = findViewById(R.id.img_background_main);
        clockIn = findViewById(R.id.img_clock_in);
        titleTextView = findViewById(R.id.tv_title_main);
        contentChTextView = findViewById(R.id.tv_content_ch_main);
        contentEngTextView = findViewById(R.id.tv_content_en_main);
        recyclerView = findViewById(R.id.rv_main);
        layout = findViewById(R.id.layout_root);
        List<RvBean> list = new ArrayList<RvBean>();
        String firstUse = (String) SharedPreferenceUtil.getInstance().get(this, "first_use", "first");
        assert firstUse != null;
        RvBean rvBean0;
        RvBean rvBean1;
        RvBean rvBean2;
        RvBean rvBean3;
        if (firstUse.equals("first")) {
            rvBean0 = new RvBean("背单词",
                    0,
                    false);
            rvBean1 = new RvBean("阅读",
                    1,
                    false);
            rvBean2 = new RvBean("学习",
                    2,
                    false);
            rvBean3 = new RvBean("运动",
                    3,
                    false);
            SharedPreferenceUtil.getInstance().put(this, "first_use", "false");
        } else {
            rvBean0 = new Gson().fromJson((String) SharedPreferenceUtil.getInstance().get(this, "data0", ""), RvBean.class);
            rvBean1 = new Gson().fromJson((String) SharedPreferenceUtil.getInstance().get(this, "data1", ""), RvBean.class);
            rvBean2 = new Gson().fromJson((String) SharedPreferenceUtil.getInstance().get(this, "data2", ""), RvBean.class);
            rvBean3 = new Gson().fromJson((String) SharedPreferenceUtil.getInstance().get(this, "data3", ""), RvBean.class);
        }
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
        initBar();
        loadBackground(this);
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

    private void initBar() {
        ImmersionBar.with(this).init();
        ImmersionBar.with(this)
                .transparentStatusBar()
                .transparentNavigationBar()
                .fullScreen(true)
                .statusBarDarkFont(true)
                .navigationBarDarkIcon(true)
                .statusBarColor("#FBFBFB")
                .navigationBarColor("#FBFBFB");
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
//                        backgroundImageView.setImageResource(R.mipmap.background);
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
                startActivity(intent);
            }
        });
    }

    /**
     * item正文的点击事件
     *
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {
        //点击item正文的代码逻辑


    }


    /**
     * item的左滑设置
     *
     * @param view
     * @param position
     */
    @Override
    public void onSetBtnCilck(View view, int position) {

        //“设置”点击事件的代码逻辑
        Toast.makeText(MainActivity.this, "请设置", Toast.LENGTH_LONG).show();
        System.out.println("请设置");
    }


    /**
     * item的左滑删除
     *
     * @param view
     * @param position
     */
    @Override
    public void onDeleteBtnCilck(View view, int position, boolean isFinish) {
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
    protected void onPause() {
        rvAdapter.saveData();
        super.onPause();
    }
}