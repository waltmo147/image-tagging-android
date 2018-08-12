package com.example.jc.myapplication.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jc.myapplication.GetImage;
import com.example.jc.myapplication.MainActivity;
import com.example.jc.myapplication.R;
import com.example.jc.myapplication.RetrofitInterface;
//import com.example.jc.myapplication.model.Response;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

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

    public static URL buildURL(String path){
        try{
            String specificURL = BASE_URL+path;
            URL queryUrl = new URL(specificURL);
            return queryUrl;
        }catch (MalformedURLException e){
            e.printStackTrace();
            Log.i("tag","not even parsing url");
            return null;
        }

    }

    public static String getObjectType(URL url){
        String urlString = url.toString();
        String[] paths = urlString.split("/");
        return paths[paths.length-1];
    }

    public static String getResponseFromHttp(URL url){

        HttpURLConnection urlConnection = null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            int responseCode=urlConnection.getResponseCode();
            Log.i("tag",""+responseCode);
            if(responseCode == HttpsURLConnection.HTTP_OK) {
                InputStream in = urlConnection.getInputStream();
                InputStreamReader read = new InputStreamReader(in);

                BufferedReader reader = new BufferedReader(read);

                StringBuffer buf = new StringBuffer("");
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buf.append(line);
                }
                String json = buf.toString();
                Log.i("tag",""+json);
                reader.close();
                return json;
            }else {
                return null;
            }

        }
        catch (IOException e){
            Log.i(TAG,"IO EXCEPTION");
            e.printStackTrace();
            return null;
        }

    }

    public static String postHomeworkToServer(URL url, Bitmap targetImage){

        HttpURLConnection urlConnection = null;

        try{

            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            urlConnection.setConnectTimeout(30000);
            urlConnection.setDoOutput(true);

            String imageString = getBase64StringFromBitmap(targetImage);

            JSONObject body = new JSONObject();
            body.put("image", imageString);


            DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            wr.write(body.toString());
            wr.flush();
            wr.close();
//            os.write(imageString);
//            os.flush();
//            os.close();

            int responseCode=urlConnection.getResponseCode();
            Log.i("tag",""+responseCode);
            if(responseCode == HttpsURLConnection.HTTP_OK) {
                InputStream in = urlConnection.getInputStream();
                InputStreamReader read = new InputStreamReader(in);

                BufferedReader reader = new BufferedReader(read);

                StringBuffer buf = new StringBuffer("");
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buf.append(line);
                }
                String json = buf.toString();
                Log.i("tag",""+json);
                reader.close();
                return json;
            }else {
                return null;
            }

        }catch(IOException e){
            e.printStackTrace();
            Log.i(TAG,"IOEXCEPT");
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            Log.i(TAG,e.getMessage());
            return null;
        }
//        catch(JSONException e){
//            e.printStackTrace();
//            Log.i(TAG,"JSONEXCEPT");
//            return null;
//        }
        finally{
            urlConnection.disconnect();
        }
    }


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


//    public static Response uploadImage(byte[] imageBytes, Context context) {
//
//        final Context context1 = context;
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient)
//                .build();
//
//        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
//
//        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageBytes);
//
//        MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
//        Call<Response> call = retrofitInterface.uploadImage(body);
//
//        Response result = null;
//
////        call.enqueue(new Callback<Response>() {
////            @Override
////            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
////                Log.i("tag", "Success: "+response.body().toString());
////                Response responseBody = response.body();
////                result = response.body();
////            }
////
////            @Override
////            public void onFailure(Call<Response> call, Throwable t) {
////                Toast.makeText(context1, "Failed connecting the server", Toast.LENGTH_LONG).show();
////                Log.d("tag", "onFailure: "+t.getLocalizedMessage());
////
////            }
////        });
//
//        try {
//            result = call.execute().body();
//        }
//        catch (IOException e) {
//            Log.i("retrofit call","IOEXCEPTION");
//            e.printStackTrace();
//        }
//        return result;
//    }

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
        final PostResponseListener mPostResponse = new UploadImageResponseListener(context);
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
                        if (error == null) {
                            Log.d("Error.Response", "null");
                        }
                        else {
                            Log.d("Error.Response", error.getMessage());
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


}

