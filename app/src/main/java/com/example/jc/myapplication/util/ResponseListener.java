package com.example.jc.myapplication.util;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public interface ResponseListener {
    void requestStarted();
    void requestCompleted(Object response);
    void requestEndedWithError(VolleyError error);
}
