package com.example.jc.myapplication.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public class DownloadImageResponseListener implements ResponseListener {
    private JSONObject response;
    private Context context;

    public DownloadImageResponseListener(Context context) {
        response = null;
        context = context;
    }

    public void requestStarted() {

    }
    public void requestCompleted(Object response) {
        this.response = (JSONObject)response;
        Log.d("Response", response.toString());
    }
    public void requestEndedWithError(VolleyError error) {

    }
}
