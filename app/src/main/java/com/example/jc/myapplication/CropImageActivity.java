package com.example.jc.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.jc.myapplication.model.Response;
import com.example.jc.myapplication.util.JsonUtilities;
import com.example.jc.myapplication.util.NetworkUtilities;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

public class CropImageActivity extends AppCompatActivity {

    Button cropButton;
    Button continueButton;
    ImageView imageView;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    Bitmap targetImage;
    LinearLayout linlaHeaderProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);

        setTitle("Edit Image");

        cropButton = findViewById(R.id.cropButton);
        continueButton = findViewById(R.id.continueButton);
        imageView = findViewById(R.id.croppedImage);
        String method = getIntent().getExtras().getString("uri");

        linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);


        if (method.equals("gallery")) {
            try {
                imageUri = getIntent().getData();
                targetImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imageView.setImageURI(imageUri);

            }
            catch (IOException e){
                Log.i("Gallary","EXCEPTION");
                e.printStackTrace();
            }
        }
        else {

            CropImage.activity(imageUri)
                    .start(this);
        }

    }

    public void jumpToResultActivity(String result) {
        Intent intent = new Intent();
        intent.setClass(this, ResultActivity.class);
        intent.putExtra("data", result);
        intent.putExtra("img", Base64.encodeToString(JsonUtilities.getStringFromBitmap(targetImage), Base64.DEFAULT));
        startActivity(intent);
    }

    public void openProgressBar() {
        linlaHeaderProgress.bringToFront();
        linlaHeaderProgress.setVisibility(View.VISIBLE);
    }

    public void closeProgressBar() {
        linlaHeaderProgress.setVisibility(View.GONE);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    targetImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    imageView.setImageURI(resultUri);
                    imageUri = resultUri;

                }
                catch (IOException e){
                    Log.i("Gallary","EXCEPTION");
                    e.printStackTrace();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void onClick(View view){

        switch (view.getId()){

            case R.id.cropButton:

// start cropping activity for pre-acquired image saved on the device
                CropImage.activity(imageUri)
                        .start(this);
                break;

            case R.id.continueButton:
//                new MainActivity.getJson().execute("");
                NetworkUtilities.uploadImageWithVolley(this, targetImage);
//                new loadingDialog(this).execute();
                break;
        }

//        startActivity(intent);
    }

    public Bitmap getTargetImage() {
        return targetImage;
    }


//    public class loadingDialog extends AsyncTask<Void,Void, Void> {
//        Context context;
//        ProgressDialog progressDialog;
//        Intent intent;
//        Response response;
//
//        public loadingDialog(Context context) {
//
//            this.context = context;
//            progressDialog = new ProgressDialog(context);
//
//        }
//
//
//        /* ***********************************
//         * Pre-Execute Method
//         * ********************************** */
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            intent = new Intent();
//            intent.setClass(context, ResultActivity.class);
//            this.progressDialog.setMessage("Calculating");
//            this.progressDialog.show();
//
//        /* Do your Pre-Execute Configuration */
//        }
//
//        /* ***********************************
//         * Execute Method
//         * ********************************** */
//        @Override
//        protected Void doInBackground(Void... arg0) {
//            /* Do yourxec Task ( Load from URL) and return value */
//            NetworkUtilities.uploadImageWithVolley(context, targetImage);
//
//            return null;
//        }
//
//        /* ***********************************
//         * Post-Execute Method
//         * ********************************** */
//        @Override
//        protected void onPostExecute(Void result) {
//            progressDialog.dismiss();
//            if (response != null) {
//                Gson gson = new Gson();
//                String gsonString = gson.toJson(response, Response.class);
//                SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
//                SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
//                prefsEditor.putString("img", Base64.encodeToString(NetworkUtilities.getStringFromBitmap(targetImage), Base64.DEFAULT));
//                prefsEditor.putString("result", gsonString);
//                prefsEditor.apply();
//
//                startActivity(intent);
//                super.onPostExecute(result);
//            }
//
//            else {
//                Toast.makeText(context, "Failed connecting the server", Toast.LENGTH_LONG).show();
//                Log.d("tag", "onFailure");
//            }
//
//
//                    /* Do your Post -Execute Tasks */
//        }
//    }




}
