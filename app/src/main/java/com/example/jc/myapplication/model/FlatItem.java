package com.example.jc.myapplication.model;

import android.graphics.Bitmap;

/**
 * Created by apple on 5/4/18.
 */

public class FlatItem {
    private String label;
    private String score;
    private Bitmap img;
    private String scoreTree;

    public FlatItem(String label, String score, Bitmap img) {
        this.label = label;
        this.score = score;
        this.img = img;
    }

    public String getScoreTree() {
        return scoreTree;
    }

    public void setScoreTree(String scoreTree) {
        this.scoreTree = scoreTree;
    }

    public FlatItem(String label, String score, Bitmap img, String scoreTree) {

        this.label = label;
        this.score = score;
        this.img = img;
        this.scoreTree = scoreTree;
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
