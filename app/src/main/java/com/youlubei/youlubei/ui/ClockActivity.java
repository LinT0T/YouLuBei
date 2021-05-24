package com.youlubei.youlubei.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.youlubei.youlubei.R;
import com.youlubei.youlubei.ui.view.FocusCountDownView;
import com.youlubei.youlubei.utils.Utils;

public class ClockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        Utils.initBar(this);
        FocusCountDownView focusCountDownView = findViewById(R.id.clock);
        int time = getIntent().getIntExtra("time", 0);
        focusCountDownView.setTime(time * 60 * 1000);
        Button button = findViewById(R.id.btn_clock);
        Button stopButton = findViewById(R.id.btn_clock_stop);
        stopButton.setVisibility(View.GONE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (focusCountDownView.isCount()) {
                    focusCountDownView.reset();

                    ObjectAnimator anim = ObjectAnimator.ofFloat(stopButton, "translationY", 0f, 500f);
                    anim.setInterpolator(new AccelerateDecelerateInterpolator());
                    ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(stopButton, "alpha", 1f, 0f);
                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(alphaAnim, anim);
                    set.setDuration(500);
                    set.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            stopButton.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    set.start();
                    ObjectAnimator alphaAnim2 = ObjectAnimator.ofFloat(button, "alpha", 1f, 0f);
                    alphaAnim2.setDuration(150);
                    alphaAnim2.setInterpolator(new AccelerateDecelerateInterpolator());
                    ObjectAnimator alphaAnim3 = ObjectAnimator.ofFloat(button, "alpha", 0f, 1f);
                    alphaAnim3.setDuration(150);
                    alphaAnim3.setInterpolator(new AccelerateDecelerateInterpolator());
                    alphaAnim2.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            button.setEnabled(false);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            button.setText("开始");
                            button.setEnabled(true);
                            button.setBackgroundResource(R.drawable.roug_clock_start_background);
                            alphaAnim3.start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    alphaAnim2.start();
                } else {
                    focusCountDownView.start();

                    stopButton.setVisibility(View.VISIBLE);
                    ObjectAnimator anim = ObjectAnimator.ofFloat(stopButton, "translationY", 500f, 0f);
                    anim.setInterpolator(new AccelerateDecelerateInterpolator());
                    ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(stopButton, "alpha", 0f, 1f);
                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(alphaAnim, anim);
                    set.setDuration(500);
                    set.start();
                    ObjectAnimator alphaAnim2 = ObjectAnimator.ofFloat(button, "alpha", 1f, 0f);
                    alphaAnim2.setDuration(150);
                    alphaAnim2.setInterpolator(new AccelerateDecelerateInterpolator());
                    ObjectAnimator alphaAnim3 = ObjectAnimator.ofFloat(button, "alpha", 0f, 1f);
                    alphaAnim3.setDuration(150);
                    alphaAnim3.setInterpolator(new AccelerateDecelerateInterpolator());
                    alphaAnim2.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            button.setEnabled(false);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            button.setText("停止");
                            button.setEnabled(true);
                            button.setBackgroundResource(R.drawable.roug_clock_stop_background);
                            alphaAnim3.start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    alphaAnim2.start();
                }

            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (focusCountDownView.isPause()) {
                    focusCountDownView.continueCount();
                    ObjectAnimator alphaAnim2 = ObjectAnimator.ofFloat(stopButton, "alpha", 1f, 0f);
                    alphaAnim2.setDuration(150);
                    alphaAnim2.setInterpolator(new AccelerateDecelerateInterpolator());
                    ObjectAnimator alphaAnim3 = ObjectAnimator.ofFloat(stopButton, "alpha", 0f, 1f);
                    alphaAnim3.setDuration(150);
                    alphaAnim3.setInterpolator(new AccelerateDecelerateInterpolator());
                    alphaAnim2.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            stopButton.setEnabled(false);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            stopButton.setText("暂停");
                            stopButton.setEnabled(true);
                            stopButton.setBackgroundResource(R.drawable.roug_clock_pause_background);
                            alphaAnim3.start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    alphaAnim2.start();
                } else {
                    focusCountDownView.pause();

                    ObjectAnimator alphaAnim2 = ObjectAnimator.ofFloat(stopButton, "alpha", 1f, 0f);
                    alphaAnim2.setDuration(150);
                    alphaAnim2.setInterpolator(new AccelerateDecelerateInterpolator());
                    ObjectAnimator alphaAnim3 = ObjectAnimator.ofFloat(stopButton, "alpha", 0f, 1f);
                    alphaAnim3.setDuration(150);
                    alphaAnim3.setInterpolator(new AccelerateDecelerateInterpolator());
                    alphaAnim2.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            stopButton.setEnabled(false);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            stopButton.setText("继续");
                            stopButton.setEnabled(true);
                            stopButton.setBackgroundResource(R.drawable.roug_clock_continue_background);
                            alphaAnim3.start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    alphaAnim2.start();
                }

            }
        });

    }

}