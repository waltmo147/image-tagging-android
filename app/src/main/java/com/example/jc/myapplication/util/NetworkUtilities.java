package com.example.jc.myapplication.util;

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



    public static byte[] getStringFromBitmap(Bitmap bitmapPicture) {
 /*
 * This functions converts Bitmap picture to a string which can be
 * JSONified.
 * */
        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.JPEG, COMPRESSION_QUALITY,byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        return b;
    }

    public static String getBase64StringFromBitmap(Bitmap bitmapPicture) {
        /*
         * This functions converts Bitmap picture to a string which can be
         * JSONified.
         * */
        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.JPEG, COMPRESSION_QUALITY,byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }

    public static Bitmap getBitmapFromString(String jsonString) {
/*
* This Function converts the String back to Bitmap
* */
        byte[] decodedString = Base64.decode(jsonString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public static Bitmap getBitmapFromURL(URL url) {
        try {

            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



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
        final ResponseListener mPostResponse = new UploadImageResponseListener(context);
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

    public static void downloadImageWithVolley(final Context context, String tagId, int num) {
        final ResponseListener mGetResponse = new DownloadImageResponseListener(context);
        // prepare the Request
        if (context == null) {
            Log.d(TAG, "downloadImageWithVolley: context is null");
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        
        mGetResponse.requestStarted();

        Map<String, String> params = new HashMap<>();
        params.put("tag_id", tagId);
        params.put("num", "" + num);

        String url = generateUrl(Constants.UPLOAD_IMAGE_URL, params);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        mGetResponse.requestCompleted(response);
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
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
        );

        // add it to the RequestQueue
        queue.add(getRequest);

    }



}

