package ru.edu.masu.view.dialogs;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import ru.edu.masu.R;

public class HintFragment extends BasicDialogFragment {

    private static final String IMG = "IMG";
    private static final String DESC = "DESC";

    private int imageResId;
    private String description;

    public HintFragment() {
        // Required empty public constructor
    }

    public static HintFragment newInstance(int imgRes, String desc) {
        HintFragment fragment = new HintFragment();
        Bundle args = packArgs(R.layout.fragment_hint,R.string.ok);
        args.putInt(IMG, imgRes);
        args.putString(DESC, desc);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void unpackArgs(Bundle args){
        super.unpackArgs(args);
        imageResId = args.getInt(IMG);
        description = args.getString(DESC);
    }

    @Override
    protected void onContentViewCreated(View view) {
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(imageResId);
    }
}
