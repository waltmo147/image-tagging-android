package com.example.jc.myapplication.util;

import android.arch.paging.PageKeyedDataSource;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.jc.myapplication.ResultActivity;
import com.example.jc.myapplication.ShowImageActivity;
import com.example.jc.myapplication.model.ImageItem;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class DownloadImageChunkResponseListener {
    private String response;
    private Context context;

    public DownloadImageChunkResponseListener(Context context) {
        response = null;
        this.context = context;
    }

    public void requestStarted() {
    }
    public String requestCompleted(String response, int offset) {
        this.response = response;
        Log.d("Response", response);
        try {
            ArrayList<Bitmap> images = JsonUtilities.getImageListFromGetResponse(response);
            ShowImageActivity activity = (ShowImageActivity)context;

            activity.updateImageList(images, offset + 1);
        } catch (Exception e) {

        }


        return null;
    }
    public void requestEndedWithError(VolleyError error) {

    }
}
