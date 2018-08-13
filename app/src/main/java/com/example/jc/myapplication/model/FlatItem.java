package com.example.jc.myapplication.model;

import android.graphics.Bitmap;

/**
 * Created by apple on 5/4/18.
 */

public class FlatItem {
    private String tag_id;
    private String label;
    private String score;
    private Bitmap img;

    public FlatItem(String label, String score, Bitmap img, String tag_id) {
        this.label = label;
        this.score = score;
        this.img = img;
        this.tag_id = tag_id;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}
