package com.example.jc.myapplication.model;

import org.json.JSONObject;

/**
 * Created by apple on 4/22/18.
 */



public class Response {

    private String[][] rankArray;
    private String[][] opt_path;
    private String[][] rank_path;
    private String[][] node_path;

    public String[][] getRankArray() {
        return rankArray;
    }

    public void setRankArray(String[][] rankArray) {
        this.rankArray = rankArray;
    }

    public String[][] getOpt_path() {
        return opt_path;
    }

    public void setOpt_path(String[][] opt_path) {
        this.opt_path = opt_path;
    }

    public String[][] getRank_path() {
        return rank_path;
    }

    public void setRank_path(String[][] rank_path) {
        this.rank_path = rank_path;
    }

    public String[][] getNode_path() {
        return node_path;
    }

    public void setNode_path(String[][] node_path) {
        this.node_path = node_path;
    }
}