package ru.edu.masu.view.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import ru.edu.masu.R;
import ru.edu.masu.model.CodeQuestPass;
import ru.edu.masu.model.IQuestPass;
import ru.edu.masu.utils.ScanActivity;
import ru.edu.masu.viewmodel.QuestPassVM;
import ru.edu.masu.viewmodel.QuestPassVMFactory;

public class QRScanFragment extends Fragment {

    private final static int PERMISSION_REQUEST_CODE = 42;
    private static final String QUEST_PASS = "QUEST_PASS";
    private QuestPassVM questPassVM;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private PreviewView cameraView;
    private Preview imagePreview;
    private BarcodeScanner detector;

    public QRScanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detector.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        questPassVM = new ViewModelProvider(requireActivity())
                .get(QuestPassVM.class);
        cameraView = view.findViewById(R.id.cameraView);
        cameraProviderFuture = ProcessCameraProvider.getInstance(Objects.requireNonNull(getContext()));
        if(isPermissionsGranted()){
            cameraView.post(new Runnable() {
                @Override
                public void run() {
                    startCamera();
                }
            });
        }
        else{
            requestPermissions(new String[]{Manifest.permission.CAMERA},PERMISSION_REQUEST_CODE);
        }
        return view;
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

                BarcodeScannerOptions options = new BarcodeScannerOptions.Builder()
                        //.setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                        .build();
                detector = BarcodeScanning.getClient(options);

                ImageAnalysis analysis = new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1280,720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();
                analysis.setAnalyzer(Executors.newSingleThreadExecutor(),
                                    new QRScanFragment.BarcodeAnalyzer(detector));

                cameraProvider.bindToLifecycle((LifecycleOwner)this,cameraSelector,imagePreview,analysis);

            } catch (ExecutionException |InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(Objects.requireNonNull(getContext())));

    }

    private class BarcodeAnalyzer implements ImageAnalysis.Analyzer{

        BarcodeScanner detector;

        BarcodeAnalyzer(BarcodeScanner detector){
            this.detector = detector;
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
                                Toast toast = Toast.makeText(getContext(), R.string.failure,Toast.LENGTH_LONG);
                                toast.show();
                            }
                        })
                        .addOnCompleteListener(result -> {
                            imageProxy.close();
                        });
            }

        }

        private void getData(List<Barcode> barcodes){
            if(barcodes.size()>0){
                Barcode barcode = barcodes.get(0);
                String rawValue = barcode.getRawValue();
                checkPassStatus(rawValue);
            }
        }

    }

    private void checkPassStatus(String code){
        CodeQuestPass codeQuestPass = (CodeQuestPass) questPassVM.provideQuestPass();
        codeQuestPass.enterCode(code);
        if(!codeQuestPass.isPassed()){
            Toast toast = Toast.makeText(getContext(), "Неверный код", Toast.LENGTH_SHORT);
            toast.show();
        }
        questPassVM.check(codeQuestPass);
    }


    private boolean isPermissionsGranted(){
        return ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.CAMERA)
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
                Toast toast = Toast.makeText(getContext(),"Вы должны разрешить доступ к камере",Toast.LENGTH_SHORT);
                toast.show();
                Objects.requireNonNull(getActivity()).finish();
            }
        }
    }

}
