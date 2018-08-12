package com.example.jc.myapplication;

/**
 * Created by apple on 4/22/18.
 */

import com.example.jc.myapplication.model.Response;

import okhttp3.MultipartBody;
import retrofit2.Call;

import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitInterface {
    @Multipart
    @POST("/")
    Call<Response> uploadImage(@Part MultipartBody.Part image);
}