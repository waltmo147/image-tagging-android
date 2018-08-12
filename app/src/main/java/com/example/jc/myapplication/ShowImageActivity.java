package com.example.jc.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.jc.myapplication.model.FlatItem;
import com.example.jc.myapplication.util.NetworkUtilities;
import com.google.gson.Gson;

import java.net.URL;
import java.util.ArrayList;


public class ShowImageActivity extends AppCompatActivity {

    String targetID;
    String label;
    String[] images;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_image);

        Intent intent = getIntent();
        targetID = intent.getExtras().getString("id");
        label = intent.getExtras().getString("label");
        String score = intent.getExtras().getString("score");
        String scoreTree = intent.getExtras().getString("scoreTree");

        Log.i("showimage", "onCreate: " + scoreTree);
        Gson gson = new Gson();
        images = gson.fromJson(intent.getExtras().getString("images"), String[].class);

        Log.i("showimage", "onCreate: " + images.toString());


        setTitle(label);
        textView = findViewById(R.id.score1);

        if (scoreTree == null)
            textView.setText("score: " + score);
        else
            textView.setText("Flat score: " + score + "  Hierarchical: " + scoreTree);

        new loadImages(this).execute();
    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

    public class loadImages extends AsyncTask<Void,Void, Void> {
        Context context;
        ArrayList<Bitmap> imgs;
        Bitmap a;


        public loadImages(Context context) {
            imgs = new ArrayList<>();
            this.context = context;

        }

        /* ***********************************
         * Pre-Execute Method
         * ********************************** */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        /* Do your Pre-Execute Configuration */
        }

        /* ***********************************
         * Execute Method
         * ********************************** */
        @Override
        protected Void doInBackground(Void... arg0) {
            /* Do yourxec Task ( Load from URL) and return value */
            int l = Math.min(images.length, 16);
            for (int i = 0; i < l; i++) {

                String temp = images[i];
                if (temp.equals(".DS_Store")) {
                    if(images.length > 16)
                        i--;
                    continue;
                }
                String path = targetID + "/" + temp;
                String url = "/Volumes/WALTER/android-model403/tf_files/ImageNet200/" + path + "/";
                URL url1  = NetworkUtilities.buildURL(url);
                Bitmap img = NetworkUtilities.getBitmapFromURL(url1);
                imgs.add(img);
            }
            Log.i("showimage", "arraylist size: " + imgs.size());
            return null;
        }

        /* ***********************************
         * Post-Execute Method
         * ********************************** */
        @Override
        protected void onPostExecute(Void result) {
            GridView flatGrid = (GridView) findViewById(R.id.imgShowGridView);
            ImgAdapter imgAdapter = new ImgAdapter(ShowImageActivity.this, imgs);
            flatGrid.setAdapter(imgAdapter);
            flatGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // set an Intent to Another Activity
                    Intent intent = new Intent(ShowImageActivity.this, ShowImageActivity.class);

                }
            });

            super.onPostExecute(result);
                    /* Do your Post -Execute Tasks */
        }
    }
}
