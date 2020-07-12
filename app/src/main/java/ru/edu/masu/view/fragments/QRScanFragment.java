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
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

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


    public QRScanFragment() {
        // Required empty public constructor
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
                imagePreview.setSurfaceProvider(cameraView.getPreviewSurfaceProvider());

                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();

                ImageAnalysis analysis = new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1280,720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();
                analysis.setAnalyzer(Executors.newSingleThreadExecutor(), new QRScanFragment.BarcodeAnalyzer());

                cameraProvider.bindToLifecycle(QRScanFragment.this,cameraSelector,imagePreview,analysis);

            } catch (ExecutionException |InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(Objects.requireNonNull(getContext())));

    }

    private class BarcodeAnalyzer implements ImageAnalysis.Analyzer{

        FirebaseVisionBarcodeDetector detector;

        public BarcodeAnalyzer(){
            FirebaseVisionBarcodeDetectorOptions options = new FirebaseVisionBarcodeDetectorOptions.Builder()
                    .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_QR_CODE)
                    .build();
            detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options);
        }

        private int degreesToFirebaseRotation(int degrees) {
            switch (degrees) {
                case 0:
                    return FirebaseVisionImageMetadata.ROTATION_0;
                case 90:
                    return FirebaseVisionImageMetadata.ROTATION_90;
                case 180:
                    return FirebaseVisionImageMetadata.ROTATION_180;
                case 270:
                    return FirebaseVisionImageMetadata.ROTATION_270;
                default:
                    throw new IllegalArgumentException(
                            "Rotation must be 0, 90, 180, or 270.");
            }
        }

        @Override
        public void analyze(@NonNull ImageProxy image) {
            @SuppressLint("UnsafeExperimentalUsageError")
            Image img = image.getImage();
            int firebaseDegreess = degreesToFirebaseRotation(image.getImageInfo().getRotationDegrees());
            FirebaseVisionImage cur = FirebaseVisionImage.fromMediaImage(img,
                    firebaseDegreess);
            detector.detectInImage(cur)
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionBarcode> firebaseVisionBarcodes) {
                            getData(firebaseVisionBarcodes);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast toast = Toast.makeText(getContext(), R.string.failure,Toast.LENGTH_LONG);
                            toast.show();
                            e.printStackTrace();
                        }
                    });
            image.close();

        }

        private void getData(List<FirebaseVisionBarcode> firebaseVisionBarcodes){
            if(firebaseVisionBarcodes.size()>0){
                FirebaseVisionBarcode barcode = firebaseVisionBarcodes.get(0);
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
