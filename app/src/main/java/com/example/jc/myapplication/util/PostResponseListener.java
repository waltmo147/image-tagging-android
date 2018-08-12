package com.example.jc.myapplication.util;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public interface PostResponseListener {
    void requestStarted();
    void requestCompleted(String response);
    void requestEndedWithError(VolleyError error);
}
