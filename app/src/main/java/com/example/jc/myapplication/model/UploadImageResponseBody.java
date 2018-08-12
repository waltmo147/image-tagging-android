package com.example.jc.myapplication.model;

import java.util.List;
import java.util.Map;

public class UploadImageResponseBody {
    private PathItem[] optPath;
    private RankItem[] rankArray;

    public UploadImageResponseBody(PathItem[] optPath, RankItem[] rankArray) {
        this.optPath = optPath;
        this.rankArray = rankArray;
    }

    public PathItem[] getOptPath() {
        return optPath;
    }

    public void setOptPath(PathItem[] optPath) {
        this.optPath = optPath;
    }

    public RankItem[] getRankArray() {
        return rankArray;
    }

    public void setRankArray(RankItem[] rankArray) {
        this.rankArray = rankArray;
    }
}
