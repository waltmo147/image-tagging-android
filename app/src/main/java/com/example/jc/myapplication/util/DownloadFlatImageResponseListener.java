package com.example.jc.myapplication.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.jc.myapplication.ResultActivity;
import com.example.jc.myapplication.model.FlatItem;
import com.example.jc.myapplication.model.PathItem;

import org.json.JSONObject;

import java.util.ArrayList;

public class DownloadFlatImageResponseListener {
    private JSONObject response;
    private Context context;
    private int requestPending;
    private ArrayList<FlatItem> flatItems;

    public DownloadFlatImageResponseListener(Context context) {
        response = null;
        this.context = context;
        requestPending = 0;
        flatItems = new ArrayList<>();
    }

    public void requestStarted() {
        requestPending += 1;
    }
    public String requestCompleted(JSONObject response, FlatItem flatItem) {
        this.response = response;
        Log.d("Response", response.toString());
        requestPending -= 1;
        Bitmap image = JsonUtilities.getBitmapFromGetResponse(response);
        flatItem.setImg(image);
        ResultActivity activity = (ResultActivity)context;
        addFlatItem(flatItem);
        if (requestPending <= 0) {
            activity.setFlatView(flatItems);
        }
        return null;
    }
    public void requestEndedWithError(VolleyError error) {
        requestPending -= 1;
    }

    public void addFlatItem(FlatItem flatItem) {
        flatItems.add(flatItem);
    }


}
