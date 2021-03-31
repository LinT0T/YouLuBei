package com.youlubei.youlubei.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.youlubei.youlubei.R;
import com.youlubei.youlubei.bean.BackgroundBean;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = findViewById(R.id.img_splash);
        textView = findViewById(R.id.tv_splash);

        textView.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.INVISIBLE);

        loadBackground(this);
        intent = new Intent(SplashActivity.this, MainActivity.class);

        new Handler().postDelayed(() -> {
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        }, 2000);
    }

    private void loadBackground(Activity activity) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false).build();
        String url = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("splash:", "onFailure: ");
                runOnUiThread(() -> {
                    imageView.setImageResource(R.mipmap.background);
                });

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Gson gson = new Gson();
                BackgroundBean backgroundBean = gson.fromJson(Objects.requireNonNull(response.body()).string(), BackgroundBean.class);
                String imgUrl = "https://www.bing.com" + backgroundBean.getImages().get(0).getUrl();
                String text = backgroundBean.getImages().get(0).getCopyright();
                runOnUiThread(() -> {
                    Glide.with(activity).load(imgUrl).into(imageView);
                    textView.setVisibility(View.VISIBLE);
                    Animation animation = new AlphaAnimation(0,1);
                    animation.setDuration(1000);
                    textView.setText(text);
                    textView.startAnimation(animation);
                    imageView.setVisibility(View.VISIBLE);
                    imageView.startAnimation(animation);
                    intent.putExtra("url",imgUrl);
                });

                Log.d("splash", "onResponse: " + imgUrl);

            }
        });
    }
}