package com.example.jc.myapplication.model;

import android.graphics.Bitmap;

public class ImageItem {
    String image;
    String filename;

    public ImageItem(String image, String filename) {
        this.image = image;
        this.filename = filename;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
