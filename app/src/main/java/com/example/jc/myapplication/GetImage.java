package com.example.jc.myapplication;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by apple on 4/26/18.
 */


public interface GetImage {
    // don't need add 'Content-Type' header, it not works for Retrofit 2.0.0
    // @Headers({"Content-Type: image/png"})
    @GET
    Call<ResponseBody> fetchCaptcha(@Url String url);
}