package com.youlubei.youlubei.bean;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class RvBean implements Serializable {
    private String title;
    private String content = "";
    /**
     * 0: ResourcesCompat.getDrawable(getResources(), R.drawable.roug_word_finish_background, null)
     * 1: ResourcesCompat.getDrawable(getResources(), R.drawable.roug_read_finish_background, null)
     * 2:ResourcesCompat.getDrawable(getResources(), R.drawable.roug_study_finish_background, null)
     * 3: ResourcesCompat.getDrawable(getResources(), R.drawable.roug_sport_finish_background, null)
     */
    private int finishColor;
    private boolean finish = false;
    private int lastPosition = 0;
    //每日目标
    private int num = 0;

    public RvBean(String content, int finishColor, boolean finish, int num) {
        this.content = content;
        this.finishColor = finishColor;
        this.finish = finish;
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        if (content == null)
            return "";
        else
            return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFinishColor() {
        return finishColor;
    }

    public void setFinishColor(int finishColor) {
        this.finishColor = finishColor;
    }

    public boolean isFinish() {
        return finish;

    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public int getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
