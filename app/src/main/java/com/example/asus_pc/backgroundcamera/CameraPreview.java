package com.example.asus_pc.backgroundcamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CameraPreview extends AppCompatActivity {


    Button but1;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview);
        but1 = (Button)findViewById(R.id.button_image);
        imageView = (ImageView)findViewById(R.id.camera_view);
    }




    public void takePicture(View view) {
        if(view == but1){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);
            }
        }

    }
}
