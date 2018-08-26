package com.example.jc.myapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.jc.myapplication.model.FlatItem;
import com.example.jc.myapplication.model.ImageItem;
import com.example.jc.myapplication.util.DownloadImageChunkResponseListener;
import com.example.jc.myapplication.util.JsonUtilities;
import com.example.jc.myapplication.util.NetworkUtilities;
import com.example.jc.myapplication.util.TagSingleton;
import com.google.gson.Gson;

//import android.support.design.widget.FloatingActionButton;

import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Target;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ShowImageActivity extends AppCompatActivity {

    String targetID;
    String label;
    String[] images;
    TextView textView;
    RequestQueue requestQueue;
    DownloadImageChunkResponseListener mResponseListener;
    ShowImagesAdapter adapter;
    List<Bitmap> mImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_image);

        Intent intent = getIntent();
        targetID = intent.getExtras().getString("id");
        label = intent.getExtras().getString("label");
        String score = intent.getExtras().getString("score");
        String scoreTree = intent.getExtras().getString("scoreTree");
        requestQueue = Volley.newRequestQueue(this);
        mResponseListener = new DownloadImageChunkResponseListener(this);

        Log.i("showimage", "onCreate: " + scoreTree);



        setTitle(label);
        textView = findViewById(R.id.score1);

        if (scoreTree == null)
            textView.setText("score: " + score);
        else
            textView.setText("Flat score: " + score + "  Hierarchical: " + scoreTree);

//        new loadImages(this).execute();

//        NetworkUtilities.downloadImageChunk(this, targetID, 0, 10, mResponseListener, requestQueue);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        RecyclerView rvItems = (RecyclerView) findViewById(R.id.img_rv);


////////////////////////////
//        mImages = new ArrayList<>();
//        for (String str : intent.getStringArrayListExtra("imgs")) {
//            mImages.add(JsonUtilities.getBitmapFromString(str));
//        }
//        ///////////////////////////////
//
//        adapter = new ShowImagesAdapter(mImages);


//        final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(
//                2, StaggeredGridLayoutManager.VERTICAL);
        final GridLayoutManager staggeredGridLayoutManager = new GridLayoutManager(this, 2);
        rvItems.setLayoutManager(staggeredGridLayoutManager);


//        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                NetworkUtilities.downloadImageChunk(getBaseContext(), targetID,
//                        adapter.getItemCount(), 10, mResponseListener, requestQueue);
//            }
//        };
//        rvItems.addOnScrollListener(scrollListener);
//        rvItems.setAdapter(adapter);



        //////////////////     paging
        TagSingleton.getInstance().setContext(this);
        TagSingleton.getInstance().setTag_id(targetID);

        rvItems.setHasFixedSize(true);
        ImageItemViewModel itemViewModel = ViewModelProviders.of(this).get(ImageItemViewModel.class);
        final ImageItemAdapter imgAdapter = new ImageItemAdapter(this);

        itemViewModel.itemPagedList.observe(this, new Observer<PagedList<ImageItem>>() {
            @Override
            public void onChanged(@Nullable PagedList<ImageItem> items) {

                //in case of any changes
                //submitting the items to adapter
                imgAdapter.submitList(items);
            }
        });

        rvItems.setAdapter(imgAdapter);

    }

    public void updateImageList(ArrayList<Bitmap> newList, int curSize) {
        mImages.addAll(newList);
        adapter.notifyItemRangeInserted(curSize, mImages.size() - 1);
    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

//    public class loadImages extends AsyncTask<Void,Void, Void> {
//        Context context;
//        ArrayList<Bitmap> imgs;
//        Bitmap a;
//
//
//        public loadImages(Context context) {
//            imgs = new ArrayList<>();
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
//            /* Do yourxec Task ( Load from URL) and return value */
//            int l = Math.min(images.length, 16);
//            for (int i = 0; i < l; i++) {
//
//                String temp = images[i];
//                if (temp.equals(".DS_Store")) {
//                    if(images.length > 16)
//                        i--;
//                    continue;
//                }
//                String path = targetID + "/" + temp;
//                String url = "/Volumes/WALTER/android-model403/tf_files/ImageNet200/" + path + "/";
////                URL url1  = NetworkUtilities.buildURL(url);
//                Bitmap img = JsonUtilities.getBitmapFromURL(null);
//                imgs.add(img);
//            }
//            Log.i("showimage", "arraylist size: " + imgs.size());
//            return null;
//        }
//
//        /* ***********************************
//         * Post-Execute Method
//         * ********************************** */
//        @Override
//        protected void onPostExecute(Void result) {
//            GridView flatGrid = (GridView) findViewById(R.id.imgShowGridView);
//            ImgAdapter imgAdapter = new ImgAdapter(ShowImageActivity.this, imgs);
//            flatGrid.setAdapter(imgAdapter);
//            flatGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    // set an Intent to Another Activity
//                    Intent intent = new Intent(ShowImageActivity.this, ShowImageActivity.class);
//
//                }
//            });
//
//            super.onPostExecute(result);
//                    /* Do your Post -Execute Tasks */
//        }
//    }
}
