package com.example.jc.myapplication.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class UploadImageResponseListener implements PostResponseListener {

    private String response;
    private Context context;

    public UploadImageResponseListener(Context context) {
        response = null;
        context = context;
    }

    public void requestStarted() {

    }
    public void requestCompleted(String response) {
        this.response = response;
        Log.d("Response", response);
    }
    public void requestEndedWithError(VolleyError error) {

    }
}
