package com.example.jc.myapplication.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.jc.myapplication.CropImageActivity;
import com.example.jc.myapplication.model.UploadImageResponse;
import com.example.jc.myapplication.model.UploadImageResponseBody;
import com.google.gson.Gson;

public class UploadImageResponseListener {

    private String response;
    private Context context;

    public UploadImageResponseListener(Context context) {
        response = null;
        this.context = context;
    }

    public void requestStarted() {
        CropImageActivity activity = (CropImageActivity)context;
        activity.openProgressBar();
    }
    public String requestCompleted(Object result) {
        response = (String)result;
        try {
//            Gson gson = new Gson();
//            UploadImageResponse result = gson.fromJson(this.response, UploadImageResponse.class);
            CropImageActivity activity = (CropImageActivity)context;
            activity.closeProgressBar();
            Log.d("Response", response);
            activity.jumpToResultActivity(response);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    public void requestEndedWithError(VolleyError error) {

    }
}
