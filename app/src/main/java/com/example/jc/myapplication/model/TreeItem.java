package com.example.jc.myapplication.model;

import android.graphics.Bitmap;

public class TreeItem {
    private String tag_id;
    private String tag;
    private String tree_score;
    private String flat_score;
    private Bitmap img;

    public TreeItem(String tag, String tree_score, String flat_score, Bitmap img, String tag_id) {
        this.tag = tag;
        this.tree_score = tree_score;
        this.flat_score = flat_score;
        this.img = img;
        this.tag_id = tag_id;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag() {
        return tag;
    }

    public String getTree_score() {
        return tree_score;
    }

    public void setTree_score(String tree_score) {
        this.tree_score = tree_score;
    }

    public String getFlat_score() {
        return flat_score;
    }

    public void setFlat_score(String flat_score) {
        this.flat_score = flat_score;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}
