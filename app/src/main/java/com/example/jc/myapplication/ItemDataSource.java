//package com.example.jc.myapplication;
//
//
//import android.arch.paging.PageKeyedDataSource;
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.util.Log;
//
//import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.Volley;
//import com.example.jc.myapplication.model.ImageItem;
//import com.example.jc.myapplication.util.DownloadImageChunkResponseListener;
//import com.example.jc.myapplication.util.NetworkUtilities;
//
//
//public class ItemDataSource extends PageKeyedDataSource<Integer, ImageItem> {
//
//    //the size of a page that we want
//    public static final int PAGE_SIZE = 50;
//
//    //we will start from the first page which is 1
//    private static final int FIRST_PAGE = 1;
//
//    //we need to fetch from stackoverflow
//    private static final String SITE_NAME = "stackoverflow";
//
//    private String targetID;
//
//    private Context mContext;
//
//    private RequestQueue requestQueue;
//
//    private DownloadImageChunkResponseListener mResponseListener;
//
//    public Context getmContext() {
//        return mContext;
//    }
//
//    public void setmContext(Context mContext) {
//        this.mContext = mContext;
//    }
//
//    public String getTargetID() {
//        return targetID;
//    }
//
//    public void setTargetID(String targetID) {
//        this.targetID = targetID;
//    }
//
//    public ItemDataSource(String targetID, Context mContext) {
//        this.targetID = targetID;
//        this.mContext = mContext;
//
//        requestQueue = Volley.newRequestQueue(mContext);
//        mResponseListener = new DownloadImageChunkResponseListener(mContext);
//    }
//
//    //this will be called once to load the initial data
//    @Override
//    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, ImageItem> callback) {
//        RetrofitClient.getInstance()
//                .getApi().getAnswers(FIRST_PAGE, PAGE_SIZE, SITE_NAME)
//                .enqueue(new Callback<StackApiResponse>() {
//                    @Override
//                    public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {
//                        if (response.body() != null) {
//                            callback.onResult(response.body().items, null, FIRST_PAGE + 1);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<StackApiResponse> call, Throwable t) {
//
//                    }
//                });
//        NetworkUtilities.downloadImageChunk(getmContext(), targetID,
//                0, 10, mResponseListener, requestQueue, callback);
//    }
//
//    //this will load the previous page
//    @Override
//    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, ImageItem> callback) {
//        RetrofitClient.getInstance()
//                .getApi().getAnswers(params.key, PAGE_SIZE, SITE_NAME)
//                .enqueue(new Callback<StackApiResponse>() {
//                    @Override
//                    public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {
//
//                        //if the current page is greater than one
//                        //we are decrementing the page number
//                        //else there is no previous page
//                        Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
//                        if (response.body() != null) {
//
//                            //passing the loaded data
//                            //and the previous page key
//                            callback.onResult(response.body().items, adjacentKey);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<StackApiResponse> call, Throwable t) {
//
//                    }
//                });
//    }
//
//    //this will load the next page
//    @Override
//    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, ImageItem> callback) {
//        RetrofitClient.getInstance()
//                .getApi()
//                .getAnswers(params.key, PAGE_SIZE, SITE_NAME)
//                .enqueue(new Callback<StackApiResponse>() {
//                    @Override
//                    public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {
//
//                        if (response.body() != null) {
//                            //if the response has next page
//                            //incrementing the next page number
//                            Integer key = response.body().has_more ? params.key + 1 : null;
//
//                            //passing the loaded data and next page value
//                            callback.onResult(response.body().items, key);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<StackApiResponse> call, Throwable t) {
//
//                    }
//                });
//    }
//}