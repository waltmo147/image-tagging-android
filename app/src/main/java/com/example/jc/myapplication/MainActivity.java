package com.example.jc.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jc.myapplication.util.NetworkUtilities;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ImageButton gallaryButton;
    ImageButton cameraButton;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    Bitmap targetImage;
    protected static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Welcome!");
        gallaryButton = findViewById(R.id.gallaryButton);
        cameraButton = findViewById(R.id.cameraButton);

    }

    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.gallaryButton:
                openGallary();
                break;

            case R.id.cameraButton:
                // set an Intent to Another Activity
                Intent intent = new Intent(this, CropImageActivity.class);

                intent.putExtra("uri", "camera");

                startActivity(intent); // start Intent
                //                openCamera();

                break;

        }

//        startActivity(intent);
    }


    private void openGallary() {
        Intent gallary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallary, PICK_IMAGE);
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
                imageUri = data.getData();
//                targetImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//                imageView.setImageURI(imageUri);

                data.setClass(this, CropImageActivity.class);
                data.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                data.putExtra("uri", "gallery");
                startActivity(data);


            }
        } catch (Exception e) {
            Log.i("Gallary", "EXCEPTION");
            e.printStackTrace();
        }


        if (requestCode == 0 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            data.setClass(this, CropImageActivity.class);
            data.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            data.putExtra("uri", "camera");
            startActivity(data);
        }

    }

}
