package com.example.asus_pc.backgroundcamera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraPg extends AppCompatActivity {
    ShowCamera showCamera;
    Camera camera;
    //FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_pg);
        //frameLayout = (FrameLayout)findViewById(R.id.camera_frame);

        camera = Camera.open();
        //showCamera = new ShowCamera(this,camera);
       // frameLayout.addView(showCamera);


    }
    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {


            File image_file = getOutputMediaFile();

            if(image_file == null){
                return;
            }else{
                try{
                    FileOutputStream fileOutputStream = new FileOutputStream(image_file);
                    fileOutputStream.write(bytes);
                    fileOutputStream.close();
                    camera.startPreview();
                    Log.d("Check Result","WRITING");
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

        }


    };

    private File getOutputMediaFile() {

            String state = Environment.getExternalStorageState();
            Log.d("Storage State",state);
            if(!state.equals((Environment.MEDIA_MOUNTED))){
                return null;
            }else{

                File outputFile = new File(Environment.getExternalStorageDirectory(),"image.jpg");
                return outputFile;
            }
        }



    public void captureImage(View view){
        try {
            //camera.setPreviewTexture(dummySurfaceTextureF);
            camera.setPreviewTexture(new SurfaceTexture(0));
            camera.startPreview();
        } catch (Exception e) {
            System.out.println("Could not set the surface preview texture");
            e.printStackTrace();
        }
        if(camera != null){
            camera.takePicture(null,null,mPictureCallback);
        }
    }
}
