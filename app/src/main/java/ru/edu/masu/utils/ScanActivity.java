package ru.edu.masu.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import ru.edu.masu.R;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Size;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;


import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;


public class ScanActivity extends AppCompatActivity {

    public final static int PERMISSION_REQUEST_CODE = 42;
    //request code
    public final static int REQUEST_SCAN_ACTIVITY = 34;
    //result code
    public final static int RESULT_BARCODE_FOUND = 12;
    public final static String BARCODE_TEXT = "BARCODE_TEXT";

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    private PreviewView cameraView;
    private Preview imagePreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_scan);
        cameraView = findViewById(R.id.cameraView);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        if(isPermissionsGranted()){
            cameraView.post(new Runnable() {
                @Override
                public void run() {
                    startCamera();
                }
            });
        }
        else{
            ActivityCompat.requestPermissions(ScanActivity.this,
                    new String[]{Manifest.permission.CAMERA},PERMISSION_REQUEST_CODE);
        }
    }

    private void startCamera(){

      cameraProviderFuture.addListener(() -> {
          try {
              ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
              imagePreview = new Preview.Builder()
                      //.setTargetResolution(new Size(480,640))
                      .build();

              imagePreview.setSurfaceProvider(cameraView.getSurfaceProvider());

              CameraSelector cameraSelector = new CameraSelector.Builder()
                      .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                      .build();

              ImageAnalysis analysis = new ImageAnalysis.Builder()
                      .setTargetResolution(new Size(1280,720))
                      .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                      .build();
              analysis.setAnalyzer(Executors.newSingleThreadExecutor(), new BarcodeAnalyzer());

               cameraProvider.bindToLifecycle(ScanActivity.this,cameraSelector,imagePreview,analysis);

          } catch (ExecutionException|InterruptedException e) {
              e.printStackTrace();
          }
      },ContextCompat.getMainExecutor(this));

    }

    private class BarcodeAnalyzer implements ImageAnalysis.Analyzer{

        BarcodeScanner detector;

        BarcodeAnalyzer(){
            BarcodeScannerOptions options = new BarcodeScannerOptions.Builder()
                                                .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                                                .build();
            detector = BarcodeScanning.getClient(options);
        }

        @Override
        public void analyze(@NonNull ImageProxy imageProxy) {
            @SuppressLint("UnsafeExperimentalUsageError")
            Image mediaImg = imageProxy.getImage();
            if (mediaImg != null){
                InputImage image = InputImage.fromMediaImage(mediaImg,
                        imageProxy.getImageInfo().getRotationDegrees());
                detector.process(image)
                        .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                            @Override
                            public void onSuccess(List<Barcode> barcodes) {
                                getData(barcodes);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast toast = Toast.makeText(ScanActivity.this, R.string.failure,Toast.LENGTH_LONG);
                                toast.show();
                                e.printStackTrace();
                                finish();
                            }
                        })
                        .addOnCompleteListener(result -> mediaImg.close());
            }

        }

        private void getData(List<Barcode> barcodes){
            if(barcodes.size()>0){
                Barcode barcode = barcodes.get(0);
                String rawValue = barcode.getRawValue();
                Intent resIntent = new Intent();
                resIntent.putExtra(BARCODE_TEXT, rawValue);
                setResult(RESULT_BARCODE_FOUND, resIntent);
                finish();
            }
        }

    }

    private boolean isPermissionsGranted(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_REQUEST_CODE){
            if(isPermissionsGranted()){
                cameraView.post(new Runnable() {
                    @Override
                    public void run() {
                        startCamera();
                    }
                });
            }
            else{
                Toast toast = Toast.makeText(this,"Вы должны разрешить доступ к камере",Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        }
    }




}
