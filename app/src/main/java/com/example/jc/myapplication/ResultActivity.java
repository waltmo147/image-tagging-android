package com.example.jc.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.jc.myapplication.model.FlatItem;
import com.example.jc.myapplication.model.PathItem;
import com.example.jc.myapplication.model.RankItem;
import com.example.jc.myapplication.model.Response;
import com.example.jc.myapplication.model.TreeItem;
import com.example.jc.myapplication.model.UploadImageResponse;
import com.example.jc.myapplication.model.UploadImageResponseBody;
import com.example.jc.myapplication.util.DownloadFlatImageResponseListener;
import com.example.jc.myapplication.util.DownloadTreeImageResponseListener;
import com.example.jc.myapplication.util.JsonUtilities;
import com.example.jc.myapplication.util.NetworkUtilities;
import com.google.gson.Gson;


import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ResultActivity extends AppCompatActivity {

    private ImageView imageView;
    private UploadImageResponse response;
    private ViewSwitcher viewSwitcher;
    private View flatView;
    private View treeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        setTitle("Result");
        Intent intent = getIntent();

        String json = intent.getStringExtra("data");
        String imgStr = intent.getStringExtra("img");
        Bitmap queryImage = JsonUtilities.getBitmapFromString(imgStr);


        Log.i("json", "onCreate: " + json);
        imageView = findViewById(R.id.imageView2);
        imageView.setImageBitmap(queryImage);
        try {
            Gson gson = new Gson();
            response = gson.fromJson(json, UploadImageResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }

        viewSwitcher = findViewById(R.id.viewSwitcher);
        View nextView = viewSwitcher.getNextView();

        flatView = findViewById(R.id.gridViewFlat);
        treeView = findViewById(R.id.treeView);
//        String s = String.format(("%.2f", number)

        downloadImages();




//        new loadImage(this).execute();

    }

    private void downloadImages() {
        UploadImageResponseBody responseBody = response.getData();
        RankItem[] rankArray = responseBody.getRankArray();
        PathItem[] optPath = responseBody.getOptPath();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        DownloadFlatImageResponseListener flatImageResponseListener = new DownloadFlatImageResponseListener(this);
        DownloadTreeImageResponseListener treeImageResponseListener = new DownloadTreeImageResponseListener(this);


        // make get request for flat results
        for (int i = 0; i < rankArray.length; i++) {
            FlatItem flatItem = new FlatItem(rankArray[i].getTag(), rankArray[i].getScore(),
                    null, rankArray[i].getTag_id());
            NetworkUtilities.downloadFlatImageWithVolley(this,
                    rankArray[i].getTag_id(), flatItem, flatImageResponseListener, requestQueue);
        }

        // make get request for tree results
        for (int i = 0; i < optPath.length; i++) {
            TreeItem treeItem = new TreeItem(optPath[i].getTag(), optPath[i].getTree_score(),
                    optPath[i].getFlat_score(), null, optPath[i].getTag_id());
            NetworkUtilities.downloadTreeImageWithVolley(this,
                    optPath[i].getTag_id(), treeItem, treeImageResponseListener, requestQueue);
        }



    }

    public void setFlatView(final ArrayList<FlatItem> flatItems) {
        Collections.sort(flatItems, new Comparator<FlatItem>() {
            @Override
            public int compare(FlatItem o1, FlatItem o2) {
                return (int)(Float.valueOf(o2.getScore()) * 10000 - Float.valueOf(o1.getScore()) * 10000);
            }
        });
        GridView flatGrid = (GridView) findViewById(R.id.gridViewFlat);
        FlatAdapter flatAdapter = new FlatAdapter(ResultActivity.this, flatItems);
        flatGrid.setAdapter(flatAdapter);
        flatGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set an Intent to Another Activity
                Intent intent = new Intent(ResultActivity.this, ShowImageActivity.class);
                intent.putExtra("label", flatItems.get(position).getLabel());
                intent.putExtra("id", flatItems.get(position).getTag_id());
                intent.putExtra("score", flatItems.get(position).getScore());



//                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                ArrayList<String> imgs = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
                    imgs.add(JsonUtilities.getBase64StringFromBitmap(flatItems.get(2).getImg()));
                }
                intent.putExtra("imgs", imgs);


                startActivity(intent); // start Intent
            }
        });
        viewSwitcher.showNext();
    }

    public void setTreeView(final ArrayList<TreeItem> treeItems) {
        Collections.sort(treeItems, new Comparator<TreeItem>() {
            @Override
            public int compare(TreeItem o1, TreeItem o2) {
                return (int)(Float.valueOf(o2.getTree_score()) * 10000 - Float.valueOf(o1.getTree_score()) * 10000);
            }
        });
        GridView treeGrid = (GridView) findViewById(R.id.treeView);
        TreeAdapter treeAdapter = new TreeAdapter(this, treeItems);
        treeGrid.setAdapter(treeAdapter);
        treeGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                 set an Intent to Another Activity
                Intent intent = new Intent(ResultActivity.this, ShowImageActivity.class);
                intent.putExtra("label", treeItems.get(position).getTag());
                intent.putExtra("id", treeItems.get(position).getTag_id());
                intent.putExtra("score", treeItems.get(position).getFlat_score());
                intent.putExtra("scoreTree", treeItems.get(position).getTree_score());
                startActivity(intent); // start Intent
            }
        });
        viewSwitcher.showNext();

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

//    public class loadImage extends AsyncTask<Void,Void, Void> {
//        Context context;
//
//        Bitmap a;
//
//
//        public loadImage(Context context) {
//            flatItems = new ArrayList<>();
//            treeItems = new ArrayList<>();
//            this.context = context;
//
//        }
//
//        /* ***********************************
//         * Pre-Execute Method
//         * ********************************** */
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        /* Do your Pre-Execute Configuration */
//        }
//
//        /* ***********************************
//         * Execute Method
//         * ********************************** */
//        @Override
//        protected Void doInBackground(Void... arg0) {
//            UploadImageResponseBody responseBody = response.getData();
//            /* Do yourxec Task ( Load from URL) and return value */
//            RankItem[] rankArray = responseBody.getRankArray();
//            PathItem[] optPath = responseBody.getOptPath();
////
////            String path = response.getRankArray()[0][0] + "/" + response.getRank_path()[0][0];
////            url = "/Volumes/WALTER/android-model403/tf_files/ImageNet200/" + path + "/";
////            a = NetworkUtilities.downloadImage(url);
//            for (int i = 0; i < rankArray.length; i++) {
//
//                FlatItem flatItem = new FlatItem(response.getRankArray()[i][1], response.getRankArray()[i][2], img);
//                flatItems.add(flatItem);
//            }
//
//
//            l = response.getOpt_path().length;
//            for (int i = 0; i < l; i++) {
//                String temp = response.getNode_path()[i][0].equals(".DS_Store") ? response.getNode_path()[i][1] : response.getNode_path()[i][0];
//                Bitmap img = null;
//                if (!response.getNode_path()[i][0].equals("")) {
//                    String path = response.getOpt_path()[i][0] + "/" + temp;
//                    url = "/Volumes/WALTER/android-model403/tf_files/ImageNet200/" + path + "/";
////                    URL url1  = NetworkUtilities.buildURL(url);
//                    img = NetworkUtilities.getBitmapFromURL(null);
//                }
//
//                FlatItem flatItem = new FlatItem(response.getOpt_path()[i][1], response.getOpt_path()[i][3], img, response.getOpt_path()[i][2]);
//                treeItems.add(flatItem);
//            }
//
//            return null;
//        }
//
//        /* ***********************************
//         * Post-Execute Method
//         * ********************************** */
//        @Override
//        protected void onPostExecute(Void result) {
//            GridView flatGrid = (GridView) findViewById(R.id.gridViewFlat);
//            FlatAdapter flatAdapter = new FlatAdapter(ResultActivity.this, flatItems);
//            flatGrid.setAdapter(flatAdapter);
//            flatGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    // set an Intent to Another Activity
//                    Intent intent = new Intent(ResultActivity.this, ShowImageActivity.class);
//                    intent.putExtra("label", response.getRankArray()[position][1]);
//                    intent.putExtra("id", response.getRankArray()[position][0]);
//                    intent.putExtra("score", response.getRankArray()[position][2]);
//
//                    Gson gson = new Gson();
//                    intent.putExtra("images", gson.toJson(response.getRank_path()[position]));
//                    startActivity(intent); // start Intent
//                }
//            });
//
//            GridView treeGrid = (GridView) findViewById(R.id.treeView);
//            TreeAdapter treeAdapter = new TreeAdapter(ResultActivity.this, treeItems);
//            treeGrid.setAdapter(treeAdapter);
//            treeGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    // set an Intent to Another Activity
//                    Intent intent = new Intent(ResultActivity.this, ShowImageActivity.class);
//                    intent.putExtra("label", response.getOpt_path()[position][1]);
//                    intent.putExtra("id", response.getOpt_path()[position][0]);
//                    intent.putExtra("score", response.getOpt_path()[position][3]);
//                    intent.putExtra("scoreTree", response.getOpt_path()[position][2]);
//                    Gson gson = new Gson();
//                    intent.putExtra("images", gson.toJson(response.getNode_path()[position]));
//                    if (!response.getNode_path()[position][0].equals(""))
//                        startActivity(intent); // start Intent
//                }
//            });
//
//            viewSwitcher.showNext();
//            viewSwitcher.showNext();
//            super.onPostExecute(result);
//                    /* Do your Post -Execute Tasks */
//        }
//    }


}
