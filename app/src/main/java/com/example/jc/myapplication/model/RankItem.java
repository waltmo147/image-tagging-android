package com.example.jc.myapplication.model;

public class RankItem {
    private String tag_id;
    private String tag;
    private String score;

    public RankItem(String tag_id, String tag, String score) {
        this.tag_id = tag_id;
        this.tag = tag;
        this.score = score;
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

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
