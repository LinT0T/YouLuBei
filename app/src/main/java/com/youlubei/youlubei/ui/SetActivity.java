package com.youlubei.youlubei.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
    private TextView contentChTextView, contentEngTextView, geTextView, titleTextView;
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
                initView("个", R.color.word, R.drawable.word);
                break;
            case "阅读":
                initView("分钟", R.color.read, R.drawable.read);
                break;
            case "学习":
                initView("分钟", R.color.study, R.drawable.study);
                break;
            case "运动":
                initView("分钟", R.color.sport, R.drawable.sport);
                break;
        }

        String finalType = type;
        numEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    System.out.println("haha    " + s);
                    switch (finalType) {
                        case "背单词":
                            setButton(s, 120, 10);
                            break;
                        case "阅读":
                            setButton(s, 180, 30);
                            break;
                        case "学习":
                            setButton(s, 480, 30);
                            break;
                        case "运动":
                            setButton(s, 300, 10);
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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

    private void setButton(CharSequence s, int max, int min) {
        if (s.equals("")) {
            return;
        }
        if (Integer.parseInt(String.valueOf(s)) > max) {
            setButtonTextAndColor("每日任务过难，是否确定？", R.color.warning);
        } else {
            if (Integer.parseInt(String.valueOf(s)) < min) {
                setButtonTextAndColor("太简单啦，是否确定？", R.color.easy);
            } else {
                setButtonTextAndColor("完成", R.color.white);
            }

        }

    }

    private void setButtonTextAndColor(String s2, int p) {
        mButton.setText(s2);
        mButton.setTextColor(getResources().getColor(p));
    }

    private void initView(String text, int p, int p2) {
        geTextView.setText(text);
        numEditText.setTextColor(getResources().getColor(p));
        mImageView.setImageResource(p2);
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