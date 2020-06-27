package ru.edu.masu;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HintFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HintFragment extends DialogFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String IMG = "IMG";
    private static final String DESC = "DESC";

    private int imageResId;
    private String description;

    public HintFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param imgRes Parameter 1.
     * @param desc Parameter 2.
     * @return A new instance of fragment HintFragment.
     */
    public static HintFragment newInstance(int imgRes, String desc) {
        HintFragment fragment = new HintFragment();
        Bundle args = new Bundle();
        args.putInt(IMG, imgRes);
        args.putString(DESC, desc);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageResId = getArguments().getInt(IMG);
            description = getArguments().getString(DESC);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hint,container,false);
        //TextView descTxt = view.findViewById(R.id.descTxt);
        //descTxt.setText(description);
//        ImageView imageView = view.findViewById(R.id.imageView);
//        imageView.setImageResource(imageResId);
        Button okBtn = view.findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return view;
    }
}
