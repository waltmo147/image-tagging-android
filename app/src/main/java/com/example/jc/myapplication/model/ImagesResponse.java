package com.example.jc.myapplication.model;

import java.util.ArrayList;

public class ImagesResponse {
    private String message;
    private ArrayList<ImageItem> image;
    private Boolean has_more;

    public ImagesResponse(String message, ArrayList<ImageItem> images, Boolean has_more) {
        this.message = message;
        this.image = images;
        this.has_more = has_more;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<ImageItem> getImages() {
        return image;
    }

    public void setImages(ArrayList<ImageItem> images) {
        this.image = images;
    }

    public Boolean getHas_more() {
        return has_more;
    }

    public void setHas_more(Boolean has_more) {
        this.has_more = has_more;
    }
}
