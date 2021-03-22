package com.youlubei.youlubei;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.youlubei.youlubei.bean.BackgroundBean;
import com.youlubei.youlubei.bean.ContentBean;
import com.youlubei.youlubei.bean.RvBean;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "MainActivity";
    private final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false).build();
    private ImageView backgroundImageView;
    private TextView titleTextView,contentChTextView,contentEngTextView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backgroundImageView = findViewById(R.id.img_background_main);
        titleTextView = findViewById(R.id.tv_title_main);
        contentChTextView = findViewById(R.id.tv_content_ch_main);
        contentEngTextView = findViewById(R.id.tv_content_en_main);
        recyclerView = findViewById(R.id.rv_main);
        List<RvBean> list = new ArrayList<RvBean>();
            RvBean rvBean0 = new RvBean("背单词");
        RvBean rvBean1 = new RvBean("背单词");
        RvBean rvBean2 = new RvBean("背单词");
        RvBean rvBean3 = new RvBean("背单词");
//        RvAdapter rvAdapter = new RvAdapter(this,);
        initBar();
        loadBackground(this);
        getDate(titleTextView);
        loadContent();
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

    private void loadContent(){
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
}