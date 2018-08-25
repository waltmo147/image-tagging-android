package com.example.jc.myapplication.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.jc.myapplication.ResultActivity;

import com.example.jc.myapplication.model.TreeItem;

import org.json.JSONObject;

import java.util.ArrayList;

public class DownloadTreeImageResponseListener {
    private JSONObject response;
    private Context context;
    private int requestPending;
    private ArrayList<TreeItem> treeItems;

    public DownloadTreeImageResponseListener(Context context) {
        response = null;
        this.context = context;
        requestPending = 0;
        treeItems = new ArrayList<>();
    }

    public void requestStarted() {
        requestPending += 1;
    }
    public String requestCompleted(JSONObject response, TreeItem treeItem) {
        this.response = response;
        Log.d("Response", response.toString());
        requestPending -= 1;
        Bitmap image = JsonUtilities.getBitmapFromGetResponse(response);
        treeItem.setImg(image);
        ResultActivity activity = (ResultActivity)context;
        addPathItem(treeItem);
        if (requestPending <= 0) {
            activity.setTreeView(treeItems);
        }
        return null;
    }
    public void requestEndedWithError(VolleyError error) {
        requestPending -= 1;
    }

    public void addPathItem(TreeItem treeItem) {
        treeItems.add(treeItem);
    }
}
