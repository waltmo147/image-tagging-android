package com.example.jc.myapplication.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.example.jc.myapplication.model.ImageItem;
import com.example.jc.myapplication.model.ImagesResponse;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JC on 3/3/18.
 */

public final class JsonUtilities {

    public static ArrayList parseJson(String json, String type) {
        try {
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static ArrayList<Bitmap> getImageListFromGetResponse(String json) {
        ArrayList<Bitmap> images = new ArrayList<>();
        Gson gson = new Gson();
        ImagesResponse response = gson.fromJson(json, ImagesResponse.class);
        Log.d("JsonUtil", "getImageListFromGetResponse: " + json);
        for (ImageItem item : response.getImages()) {
            Bitmap img = getBitmapFromString(item.getImage());
            images.add(img);
        }
        return images;
    }

    public static ArrayList<ImageItem> getImageItemListFromGetResponse(ImagesResponse response) {
        ArrayList<ImageItem> images = new ArrayList<>();
        Gson gson = new Gson();

        return response.getImages();
    }

    public static Bitmap getBitmapFromGetResponse(JSONObject jsonObject) {

        try {
//            JSONObject jsonObject = new JSONObject(json);
            String imageString = jsonObject.getString("image");
            Bitmap image = getBitmapFromString(imageString);
            return image;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
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


//    public static
}
