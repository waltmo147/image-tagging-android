package com.example.jc.myapplication;

import com.example.jc.myapplication.model.ImagesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ImageChunkApi {

    @GET("upload-image")
    Call<ImagesResponse> getAnswers(@Query("num") int num, @Query("count") int count, @Query("tag_id") String tag_id);
}
