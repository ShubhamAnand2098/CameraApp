package com.elabs.android.camerademo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    FrameLayout framelayout;
    Button button;
    android.hardware.Camera mCamera;
    CameraPreview cameraPreview;
    android.hardware.Camera.PictureCallback pictureCallback;
    android.hardware.Camera.ShutterCallback shutterCallback;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hides the notification bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        
        //for get the required permission
        getPermission();

        framelayout = (FrameLayout) findViewById(R.id.textureview);
        button = (Button)findViewById(R.id.button);

        startCamera();


        shutterCallback = new android.hardware.Camera.ShutterCallback() {
            @Override
            public void onShutter() {
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.camera_shutter);
                mediaPlayer.start();
            }
        };

        pictureCallback = new android.hardware.Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, android.hardware.Camera camera) {

                Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                intent.putExtra("Image", data);
                startActivity(intent);

                mCamera.startPreview();
            }
        };

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mCamera.takePicture(shutterCallback, null, pictureCallback);



            }
        });



    }

    private void startCamera() {
        mCamera = android.hardware.Camera.open();
        cameraPreview = new CameraPreview(this, mCamera);
        framelayout.addView(cameraPreview);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getPermission() {

        if(checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 0);

        }

        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
