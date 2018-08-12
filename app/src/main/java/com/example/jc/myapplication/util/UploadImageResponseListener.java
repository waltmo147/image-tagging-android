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

public class UploadImageResponseListener implements ResponseListener {

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
    public void requestCompleted(Object result) {
        response = (String)result;
        try {
//            Gson gson = new Gson();
//            UploadImageResponse result = gson.fromJson(this.response, UploadImageResponse.class);
            CropImageActivity activity = (CropImageActivity)context;
            activity.closeProgressBar();
            Log.d("Response", response);
            activity.jumpToResultActivity(response);
//            String id = result.getData().getRankArray()[0].getTag_id();
//            NetworkUtilities.downloadImageWithVolley(context, id, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void requestEndedWithError(VolleyError error) {

    }
}
