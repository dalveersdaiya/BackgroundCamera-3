package com.example.asus_pc.backgroundcamera;

import android.Manifest;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startBut,stopBut,startCam;

    private static final int REQUEST_CAMERA_PERMISSION = 300;
    private boolean permissionToUseCameraAccepted= false;
    SurfaceView cameraView;

    private String[] permissions = {Manifest.permission.CAMERA};

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (grantResults.length>1) {
                    permissionToUseCameraAccepted = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;
                }
                break;
        }

        if ((!permissionToUseCameraAccepted))
            finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, permissions,
                    REQUEST_CAMERA_PERMISSION);
        }

        startBut = (Button) findViewById(R.id.serviceStart);
        stopBut = (Button) findViewById(R.id.serviceEnd);


        startBut.setOnClickListener(this);
        stopBut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == startBut){
            ComponentName receiver = new ComponentName(this, UnlockScreenReceiver.class);
            PackageManager pm = this.getPackageManager();

            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);
            Toast.makeText(this, "Enabling broadcast receiver", Toast.LENGTH_SHORT).show();

        }else if(view == stopBut){
            ComponentName receiver = new ComponentName(this, UnlockScreenReceiver.class);
            PackageManager pm = this.getPackageManager();

            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
            Toast.makeText(this, "Disabling broadcast receiver", Toast.LENGTH_SHORT).show();

        }
    }


}
