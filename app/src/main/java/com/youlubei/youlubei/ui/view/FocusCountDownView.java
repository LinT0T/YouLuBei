package com.youlubei.youlubei.ui.view;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;


import androidx.annotation.Nullable;

import com.youlubei.youlubei.R;

public class FocusCountDownView extends View {
    /**
     * 底环画笔
     */
    private Paint mRingPaint;
    private Paint mLightPaint;
    private int mLightRadius;
    private int mBlueRadius;
    private int mBlueCx;
    private int mBlueCy;
    private int mDarkBlueColor = 0xff0029ff;
    private RectF mLightRedRect;
    private int mLightRedColor = 0xffd712fc;
    private RectF mWhiteRect;
    private int mWhiteColor = 0xffffffff;
    private RectF mLightBlueRect;
    private int mLightBlueColor = 0xff1ad7ff;
    private RectF mPurpleRect;
    private int mPurpleColor = 0xff5a00ff;
    private int mDarkPurpleRadius;
    private CountDownTimer mCountDownTimer;
    private long mResetMillis;
    private long mTotalMillis;
    private long mMillisUntilFinished;
    private long mMillisPaused;
    /**
     * 文字颜色
     */
    private int mTextColor = 0xFFFFFFFF;
    /**
     * 文字大小
     */
    private float mTextSize;
    /**
     * 文字画笔
     */
    private Paint mTextPaint;
    /**
     * Text的基线
     */
    private float mTextBaseLine;

    private int mLightRedRotate = -10;
    private int mWhiteRotate = 10;
    private int mLightBlueRotate = 50;
    private int mPurpleRotate = 30;
    private boolean isCount = false;
    private boolean isPause = false;

    public FocusCountDownView(Context context) {
        this(context, null);
    }

    public FocusCountDownView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FocusCountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    private void initPaint() {
        if (mRingPaint == null) {
            int w = getWidth();
            int h = getHeight();
//            mDarkPurpleRadius = (int) getResources().getDimension(R.dimen.dp_96);
            mDarkPurpleRadius = 96;
            mRingPaint = new Paint();
            mRingPaint.setAntiAlias(true);

            mRingPaint.setColor(0xFF0029FF);

            mLightPaint = new Paint();
            mLightPaint.setAntiAlias(true);// 抗锯齿效果
            mLightPaint.setColor(0xFF5A00FF);
            mLightRadius = 105;
            mLightPaint.setMaskFilter(new BlurMaskFilter(mLightRadius + 50, BlurMaskFilter.Blur.OUTER));

            mBlueRadius = 105;
            mBlueCx = w / 2;
            mBlueCy = h / 2;

            int lightW = 210;
            int lightH = 187;
            int paddingX = (w - lightW) / 2 - 200;
            int paddingY = (h - lightH) / 2 - 200;
            mLightRedRect = new RectF(paddingX, paddingY, w - paddingX, h - paddingY);

            lightW = 166;
            lightH = 147;
            paddingX = (w - lightW) / 2 - 200;
            paddingY = (h - lightH) / 2 - 200;
            mWhiteRect = new RectF(paddingX, paddingY, w - paddingX, h - paddingY);

            lightW = 206;
            lightH = 185;
            paddingX = (w - lightW) / 2 - 200;
            paddingY = (h - lightH) / 2 - 200;
            mLightBlueRect = new RectF(paddingX, paddingY, w - paddingX, h - paddingY);

            lightW = 195;
            lightH = 200;
            paddingX = (w - lightW) / 2 - 200;
            paddingY = (h - lightH) / 2 - 200;
            mPurpleRect = new RectF(paddingX, paddingY, w - paddingX, h - paddingY);

            mTextPaint = new Paint();
            mTextSize = 100;
            mTextPaint.setTextSize(mTextSize);
            mTextPaint.setColor(mTextColor);
            mTextPaint.setAntiAlias(true);
            Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
            mTextBaseLine = getHeight() / 2f - metrics.descent + (metrics.bottom - metrics.top) / 2;
        }
    }

    private void changeRotate() {
        mLightRedRotate += 2;
        mWhiteRotate += 3;
        mLightBlueRotate += 4;
        mPurpleRotate += 5;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        canvas.drawCircle(cx, cy, mLightRadius + 200, mLightPaint);
        mRingPaint.setColor(mDarkBlueColor);
        canvas.drawCircle(mBlueCx, mBlueCy, mBlueRadius + 200, mRingPaint);

        canvas.save();
        mRingPaint.setColor(mLightRedColor);
        int translateY;
        int translateX = 2;
        canvas.translate(-translateX, -translateX);
        canvas.rotate(mLightRedRotate, cx, cy);
        canvas.drawOval(mLightRedRect, mRingPaint);
        canvas.restore();

        canvas.save();
        mRingPaint.setColor(mWhiteColor);
        translateX = 15;
        canvas.translate(-translateX, -translateX);
        canvas.rotate(mWhiteRotate, cx, cy);
        canvas.drawOval(mWhiteRect, mRingPaint);
        canvas.restore();

        canvas.save();
        mRingPaint.setColor(mLightBlueColor);
        translateX = 2;
        canvas.translate(translateX, 0);
        canvas.rotate(mLightBlueRotate, cx, cy);
        canvas.drawOval(mLightBlueRect, mRingPaint);
        canvas.restore();

        canvas.save();
        mRingPaint.setColor(mPurpleColor);
        translateY = 2;
        canvas.translate(0, translateY);
        canvas.rotate(mPurpleRotate, cx, cy);
        canvas.drawOval(mPurpleRect, mRingPaint);
        canvas.restore();

        mRingPaint.setColor(0xFF1E1C42);
        canvas.drawCircle(cx, cy, mDarkPurpleRadius + 200, mRingPaint);

        int minute = (int) (mMillisUntilFinished / 1000 / 60);
        int second = (int) (mMillisUntilFinished / 1000 % 60);
        String text = (minute < 10 ? ("0" + minute) : minute) + ":" + (second < 10 ? ("0" + second) : second);
        mTextPaint.measureText(text);
        float textWidth = mTextPaint.measureText(text);
        canvas.drawText(text, (getWidth() - textWidth) / 2, mTextBaseLine, mTextPaint);
        changeRotate();
    }

    public void setTime(long millis) {
        reset();
        mResetMillis = millis;
        mTotalMillis = millis;
        mMillisUntilFinished = mTotalMillis;
        invalidate();
    }

    public void start() {
        mCountDownTimer = new CountDownTimer(mTotalMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mMillisUntilFinished = millisUntilFinished;
                invalidate();
            }

            @Override
            public void onFinish() {
                reset();
            }
        };
        isCount = true;
        mCountDownTimer.start();

    }

    public void reset() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
            mTotalMillis = mResetMillis;
            mMillisUntilFinished = mTotalMillis;
            invalidate();
            isCount = false;
        }
    }

    public void pause() {
        if (mCountDownTimer != null) {
            mMillisPaused = mMillisUntilFinished;
            mCountDownTimer.cancel();
            isPause = true;
        }
    }

    public void continueCount() {
        mTotalMillis = mMillisPaused;
        isPause = false;
        start();
    }

    public boolean isCount() {
        return isCount;
    }

    public boolean isPause() {
        return isPause;
    }
}