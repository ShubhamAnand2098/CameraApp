package com.elabs.android.camerademo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Locale;

public class Main2Activity extends AppCompatActivity {

    Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final Intent intent = new Intent(this,MainActivity.class);
        backButton = (Button)findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        Intent i = getIntent();
        byte[] data = i.getExtras().getByteArray("Image");
        Bitmap bitmap  = BitmapFactory.decodeByteArray(data, 0, data.length);

        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        bitmap = Bitmap.createBitmap(bitmap, 0, 0 ,bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        ImageView imageView = (ImageView)findViewById(R.id.image_view);
        imageView.setImageBitmap(bitmap);
    }
}
