package com.elabs.android.camerademo;

import android.content.Context;
import android.hardware.Camera;
import android.view.Display;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;

import static android.content.Context.WINDOW_SERVICE;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private Camera mCamera;
    private SurfaceHolder mHolder;

    public CameraPreview(Context context, Camera camera) {
        super(context);

        mCamera = camera;
        mCamera.setDisplayOrientation(90);

        Camera.Parameters cameraParameters = mCamera.getParameters();
        cameraParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        mCamera.setParameters(cameraParameters);

        mHolder = getHolder();
        mHolder.addCallback(this);




    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {



        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {



    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        mCamera.release();
    }
}
