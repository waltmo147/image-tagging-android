package com.example.jc.myapplication.model;

public class ImagesResponse {
    private String message;
    private String[] image;
    private Boolean has_more;

    public ImagesResponse(String message, String[] images, Boolean has_more) {
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

    public String[] getImages() {
        return image;
    }

    public void setImages(String[] images) {
        this.image = images;
    }

    public Boolean getHas_more() {
        return has_more;
    }

    public void setHas_more(Boolean has_more) {
        this.has_more = has_more;
    }
}
