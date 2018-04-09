package com.example.asus_pc.backgroundcamera;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ASUS-PC on 4/7/2018.
 */

public class UnlockScreenReceiver extends BroadcastReceiver {

    Camera camera;
    Context context1;
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Saving Image", Toast.LENGTH_SHORT).show();
        context1 = context;

        try {
            camera = Camera.open();
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
                    camera.release();
                    Toast.makeText(context1,"Image 'image.jpeg' created", Toast.LENGTH_SHORT).show();
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
}
