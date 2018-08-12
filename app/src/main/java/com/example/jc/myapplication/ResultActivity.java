package com.example.jc.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import android.widget.ViewSwitcher;

import com.example.jc.myapplication.model.FlatItem;
import com.example.jc.myapplication.model.Response;
import com.example.jc.myapplication.util.NetworkUtilities;
import com.google.gson.Gson;


import java.net.URL;
import java.util.ArrayList;


public class ResultActivity extends AppCompatActivity {

    ImageView imageView;
    String url;
    Response response;
    ViewSwitcher viewSwitcher;
    View flatView;
    View treeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        setTitle("Result");
        Intent intent = getIntent();

        SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("result", "");
        String imgStr = sharedPreferences.getString("img", "");


        Bitmap queryImage = NetworkUtilities.getBitmapFromString(imgStr);


        Log.i("json", "onCreate: " + json);
        imageView = findViewById(R.id.imageView2);
        imageView.setImageBitmap(queryImage);
        Gson gson = new Gson();
        response = gson.fromJson(json, Response.class);
        viewSwitcher = findViewById(R.id.viewSwitcher);
        View nextView = viewSwitcher.getNextView();

        flatView = findViewById(R.id.gridViewFlat);
        treeView = findViewById(R.id.treeView);



        new loadImage(this).execute();

    }

    public void onClick(View view){

        switch (view.getId()){

            case R.id.flatButton:
                if (viewSwitcher.getCurrentView() != flatView){
                    setTitle("Flat Results (Only CNN)");
                    viewSwitcher.showPrevious();
                };
                break;

            case R.id.treeButton:
                if (viewSwitcher.getCurrentView() != treeView){
                    setTitle("Hierarchical Results (with Tree)");
                    viewSwitcher.showNext();
                }
                break;

        }

//        startActivity(intent);
    }

    public class loadImage extends AsyncTask<Void,Void, Void> {
        Context context;
        ArrayList<FlatItem> flatItems;
        ArrayList<FlatItem> treeItems;
        Bitmap a;


        public loadImage(Context context) {
            flatItems = new ArrayList<>();
            treeItems = new ArrayList<>();
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
            int l = response.getRankArray().length;
//
//            String path = response.getRankArray()[0][0] + "/" + response.getRank_path()[0][0];
//            url = "/Volumes/WALTER/android-model403/tf_files/ImageNet200/" + path + "/";
//            a = NetworkUtilities.downloadImage(url);
            for (int i = 0; i < l; i++) {
                String temp = response.getRank_path()[i][0].equals(".DS_Store") ? response.getRank_path()[i][1] : response.getRank_path()[i][0];
                String path = response.getRankArray()[i][0] + "/" + temp;
                url = "/Volumes/WALTER/android-model403/tf_files/ImageNet200/" + path + "/";
//                URL url1  = NetworkUtilities.buildURL(url);
                Bitmap img = NetworkUtilities.getBitmapFromURL(null);
                FlatItem flatItem = new FlatItem(response.getRankArray()[i][1], response.getRankArray()[i][2], img);
                flatItems.add(flatItem);
            }


            l = response.getOpt_path().length;
            for (int i = 0; i < l; i++) {
                String temp = response.getNode_path()[i][0].equals(".DS_Store") ? response.getNode_path()[i][1] : response.getNode_path()[i][0];
                Bitmap img = null;
                if (!response.getNode_path()[i][0].equals("")) {
                    String path = response.getOpt_path()[i][0] + "/" + temp;
                    url = "/Volumes/WALTER/android-model403/tf_files/ImageNet200/" + path + "/";
//                    URL url1  = NetworkUtilities.buildURL(url);
                    img = NetworkUtilities.getBitmapFromURL(null);
                }

                FlatItem flatItem = new FlatItem(response.getOpt_path()[i][1], response.getOpt_path()[i][3], img, response.getOpt_path()[i][2]);
                treeItems.add(flatItem);
            }

            return null;
        }

        /* ***********************************
         * Post-Execute Method
         * ********************************** */
        @Override
        protected void onPostExecute(Void result) {
            GridView flatGrid = (GridView) findViewById(R.id.gridViewFlat);
            FlatAdapter flatAdapter = new FlatAdapter(ResultActivity.this, flatItems);
            flatGrid.setAdapter(flatAdapter);
            flatGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // set an Intent to Another Activity
                    Intent intent = new Intent(ResultActivity.this, ShowImageActivity.class);
                    intent.putExtra("label", response.getRankArray()[position][1]);
                    intent.putExtra("id", response.getRankArray()[position][0]);
                    intent.putExtra("score", response.getRankArray()[position][2]);

                    Gson gson = new Gson();
                    intent.putExtra("images", gson.toJson(response.getRank_path()[position]));
                    startActivity(intent); // start Intent
                }
            });

            GridView treeGrid = (GridView) findViewById(R.id.treeView);
            TreeAdapter treeAdapter = new TreeAdapter(ResultActivity.this, treeItems);
            treeGrid.setAdapter(treeAdapter);
            treeGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // set an Intent to Another Activity
                    Intent intent = new Intent(ResultActivity.this, ShowImageActivity.class);
                    intent.putExtra("label", response.getOpt_path()[position][1]);
                    intent.putExtra("id", response.getOpt_path()[position][0]);
                    intent.putExtra("score", response.getOpt_path()[position][3]);
                    intent.putExtra("scoreTree", response.getOpt_path()[position][2]);
                    Gson gson = new Gson();
                    intent.putExtra("images", gson.toJson(response.getNode_path()[position]));
                    if (!response.getNode_path()[position][0].equals(""))
                        startActivity(intent); // start Intent
                }
            });

            viewSwitcher.showNext();
            viewSwitcher.showNext();
            super.onPostExecute(result);
                    /* Do your Post -Execute Tasks */
        }
    }

    public class getJson extends AsyncTask<String, Void, String> {
        Bitmap a;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String path = response.getRankArray()[0][0] + "/" + response.getRank_path()[0][0];
            url = "/Volumes/WALTER/android-model403/tf_files/ImageNet200/" + path + "/";
//            URL url1  = NetworkUtilities.buildURL(url);

            a = NetworkUtilities.getBitmapFromURL(null);

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            imageView.setImageBitmap(a);
            super.onPostExecute(s);
        }


    }




}
