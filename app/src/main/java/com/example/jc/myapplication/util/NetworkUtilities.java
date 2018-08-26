package com.example.jc.myapplication.util;

import android.arch.paging.PageKeyedDataSource;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jc.myapplication.GetImage;
import com.example.jc.myapplication.model.FlatItem;
import com.example.jc.myapplication.model.ImageItem;
import com.example.jc.myapplication.model.PathItem;
import com.example.jc.myapplication.model.TreeItem;
//import com.example.jc.myapplication.model.Response;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.jc.myapplication.util.JsonUtilities.getBase64StringFromBitmap;

/**
 * Created by JC on 2/24/18.
 */

public final class NetworkUtilities {
    private static final String TAG = NetworkUtilities.class.getSimpleName();
    private static final String BASE_URL = Constants.UPLOAD_IMAGE_URL;
    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build();

    public static Bitmap downloadImage(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        GetImage getImage = retrofit.create(GetImage.class);
        Call<ResponseBody> call = getImage.fetchCaptcha(url);
        Bitmap result = null;

        try {
            result = BitmapFactory.decodeStream(call.execute().body().byteStream());

        }
        catch (IOException e) {
            Log.i("retrofit call","IOEXCEPTION");
            e.printStackTrace();
        }


        //Log.i("retrofit call",result.toString());
        return result;
    }


    /**
     * volley network utils of uploading
     * @return
     */
    public static void uploadImageWithVolley(Context context, Bitmap image) {
        final String imageString = getBase64StringFromBitmap(image);
        final UploadImageResponseListener mPostResponse = new UploadImageResponseListener(context);
        mPostResponse.requestStarted();
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, Constants.UPLOAD_IMAGE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        mPostResponse.requestCompleted(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        try {
                            Log.d("Error.Response", error.getMessage());
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("image", imageString);
                params.put("user", "1");
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }

    public static String generateUrl(String baseUrl, Map<String, String> params) {
        baseUrl += "?";
        if (params.size() > 0) {
            for (Map.Entry<String, String> parameter: params.entrySet()) {
                if (parameter.getKey().trim().length() > 0)
                    baseUrl += "&" + parameter.getKey() + "=" + parameter.getValue();
            }
        }
        return baseUrl;
    }

    public static void downloadFlatImageWithVolley(final Context context,
                                                   String tagId,
                                                   final FlatItem flatItem,
                                                   final DownloadFlatImageResponseListener mGetResponse,
                                                   RequestQueue queue) {
        // prepare the Request
        if (context == null) {
            Log.d(TAG, "downloadImageWithVolley: context is null");
        }
        
        mGetResponse.requestStarted();

        Map<String, String> params = new HashMap<>();
        params.put("tag_id", tagId);
        params.put("num", "" + 0);
        params.put("count", "" + 1);

        String url = generateUrl(Constants.UPLOAD_IMAGE_URL, params);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        mGetResponse.requestCompleted(response, flatItem);
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        mGetResponse.requestEndedWithError(error);
                        try {
                            Log.d("Error.Response", error.getMessage());
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        // add it to the RequestQueue
        queue.add(getRequest);

    }

    public static void downloadTreeImageWithVolley(final Context context,
                                                   String tagId,
                                                   final TreeItem treeItem,
                                                   final DownloadTreeImageResponseListener mGetResponse,
                                                   RequestQueue queue) {
        // prepare the Request
        if (context == null) {
            Log.d(TAG, "downloadImageWithVolley: context is null");
        }

        mGetResponse.requestStarted();

        Map<String, String> params = new HashMap<>();
        params.put("tag_id", tagId);
        params.put("num", "" + 0);
        params.put("count", "" + 1);


        String url = generateUrl(Constants.UPLOAD_IMAGE_URL, params);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        mGetResponse.requestCompleted(response, treeItem);
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        mGetResponse.requestEndedWithError(error);
                        try {
                            Log.d("Error.Response", error.getMessage());
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        // add it to the RequestQueue
        queue.add(getRequest);
    }

    public static void downloadImageChunk(final Context context,
                                          String tagId,
                                          final int offset,
                                          int count,
                                          final DownloadImageChunkResponseListener mGetResponse,
                                          RequestQueue queue,
                                          PageKeyedDataSource.LoadInitialCallback<Integer, ImageItem> callback) {
        if (context == null) {
            Log.d(TAG, "downloadImageWithVolley: context is null");
        }

        mGetResponse.requestStarted();

        Map<String, String> params = new HashMap<>();
        params.put("tag_id", tagId);
        params.put("num", "" + offset);
        params.put("count", "" + count);


        String url = generateUrl(Constants.UPLOAD_IMAGE_URL, params);

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // display response
                        mGetResponse.requestCompleted(response, offset);
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        mGetResponse.requestEndedWithError(error);
                        try {
                            Log.d("Error.Response", error.getMessage());
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        // add it to the RequestQueue
        queue.add(getRequest);
    }



}

