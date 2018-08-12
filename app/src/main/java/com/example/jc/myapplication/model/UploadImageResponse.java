package com.example.jc.myapplication.model;

public class UploadImageResponse {
    private String message;
    private UploadImageResponseBody data;

    public UploadImageResponse(String message, UploadImageResponseBody data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UploadImageResponseBody getData() {
        return data;
    }

    public void setData(UploadImageResponseBody data) {
        this.data = data;
    }
}
