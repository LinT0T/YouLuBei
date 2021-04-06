package com.youlubei.youlubei.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.youlubei.youlubei.R;
import com.youlubei.youlubei.bean.BackgroundBean;
import com.youlubei.youlubei.bean.ContentBean;
import com.youlubei.youlubei.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SetActivity extends AppCompatActivity {
    private ImageView backgroundImageView, mImageView;
    private TextView contentChTextView, contentEngTextView, geTextView,titleTextView;
    private EditText numEditText;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        Utils.initBar(this);

        backgroundImageView = findViewById(R.id.img_background_main);
        contentChTextView = findViewById(R.id.tv_content_ch_main);
        contentEngTextView = findViewById(R.id.tv_content_en_main);
        geTextView = findViewById(R.id.tv_ge_set);
        titleTextView = findViewById(R.id.tv_title_set);
        numEditText = findViewById(R.id.edt_set);
        mButton = findViewById(R.id.btn_set);
        mImageView = findViewById(R.id.img_set);
        String imgUrl = getIntent().getStringExtra("url");
        if (imgUrl != null) {
            Glide.with(this).load(imgUrl).into(backgroundImageView);
        } else {
            loadBackground(this);
        }

        String type = "";
        type = getIntent().getStringExtra("type");
        titleTextView.setText(type);
        String num = getIntent().getStringExtra("num");
        numEditText.setHint(num);
        switch (type) {
            case "背单词":
                geTextView.setText("个");
                numEditText.setTextColor(getResources().getColor(R.color.word));
                mImageView.setImageResource(R.drawable.word);
                break;
            case "阅读":
                geTextView.setText("分钟");
                numEditText.setTextColor(getResources().getColor(R.color.read));
                mImageView.setImageResource(R.drawable.read);
                break;
            case "学习":
                geTextView.setText("分钟");
                numEditText.setTextColor(getResources().getColor(R.color.study));
                mImageView.setImageResource(R.drawable.study);
                break;
            case "运动":
                geTextView.setText("分钟");
                numEditText.setTextColor(getResources().getColor(R.color.sport));
                mImageView.setImageResource(R.drawable.sport);
                break;
        }

        String finalType = type;
        numEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                switch (finalType) {
                    case "背单词":
                        if (Integer.parseInt(String.valueOf(s)) > 120) {
                            mButton.setText("每日任务过难，是否确定？");
                            mButton.setTextColor(getResources().getColor(R.color.warning));
                        } else {
                            if (Integer.parseInt(String.valueOf(s)) < 10) {
                                mButton.setText("太简单啦，是否确定？");
                                mButton.setTextColor(getResources().getColor(R.color.easy));
                            } else {
                                mButton.setText("完成");
                                mButton.setTextColor(getResources().getColor(R.color.white));
                            }

                        }
                        break;
                    case "阅读":
                        if (Integer.parseInt(String.valueOf(s)) > 180) {
                            mButton.setText("每日任务过难，是否确定？");
                            mButton.setTextColor(getResources().getColor(R.color.warning));
                        } else {
                            if (Integer.parseInt(String.valueOf(s)) < 30) {
                                mButton.setText("太简单啦，是否确定？");
                                mButton.setTextColor(getResources().getColor(R.color.easy));
                            } else {
                                mButton.setText("完成");
                                mButton.setTextColor(getResources().getColor(R.color.white));
                            }

                        }
                        break;
                    case "学习":
                        if (Integer.parseInt(String.valueOf(s)) > 480) {
                            mButton.setText("每日任务过难，是否确定？");
                            mButton.setTextColor(getResources().getColor(R.color.warning));
                        } else {
                            if (Integer.parseInt(String.valueOf(s)) < 30) {
                                mButton.setText("太简单啦，是否确定？");
                                mButton.setTextColor(getResources().getColor(R.color.easy));
                            } else {
                                mButton.setText("完成");
                                mButton.setTextColor(getResources().getColor(R.color.white));
                            }

                        }
                        break;
                    case "运动":
                        if (Integer.parseInt(String.valueOf(s)) > 300) {
                            mButton.setText("每日任务过难，是否确定？");
                            mButton.setTextColor(getResources().getColor(R.color.warning));
                        } else {
                            if (Integer.parseInt(String.valueOf(s)) < 10) {
                                mButton.setText("太简单啦，是否确定？");
                                mButton.setTextColor(getResources().getColor(R.color.easy));
                            } else {
                                mButton.setText("完成");
                                mButton.setTextColor(getResources().getColor(R.color.white));
                            }

                        }
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        int position = getIntent().getIntExtra("position", -1);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("result", numEditText.getText().toString());
                i.putExtra("position", position);
                setResult(3, i);
                finishAfterTransition();
            }
        });

        setAnim();

        loadContent();
    }

    private void setAnim() {
        Transition transition = new TransitionSet()
                .addTransition(new Slide(Gravity.END).addTarget(R.id.tv_title_main).addTarget(R.id.tv_ge_set).addTarget(R.id.img_set))
                .addTransition(new Fade().addTarget(R.id.img_background_main).addTarget(R.id.edt_set))
                .addTransition(new Slide(Gravity.START).addTarget(R.id.tv_day_set).addTarget(R.id.tv_title_set))
                .addTransition(new Slide(Gravity.BOTTOM).addTarget(R.id.btn_set).addTarget(R.id.tv_content_ch_main)
                        .addTarget(R.id.tv_content_en_main))
                .setDuration(500);
        getWindow().setEnterTransition(transition);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            finishAfterTransition();
        return super.onKeyDown(keyCode, event);
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
                Log.d("TAG", "onFailure: ");
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

                Log.d("TAG", "onResponse: " + imgUrl);

            }
        });
    }

    private void loadContent() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false).build();
        String url = "http://open.iciba.com/dsapi/";
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("TAG", "onFailure: ");
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