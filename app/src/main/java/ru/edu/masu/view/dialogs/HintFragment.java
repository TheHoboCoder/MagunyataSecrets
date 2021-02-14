package ru.edu.masu.view.dialogs;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.edu.masu.R;
import ru.edu.masu.utils.ImageCaching;

public class HintFragment extends BasicDialogFragment {

    private static final String IMG = "IMG";
    private static final String DESC = "DESC";

    private String imgPath;
    private String description;

    public HintFragment() {
        // Required empty public constructor
    }

    public static HintFragment newInstance(String imgPath, String desc) {
        HintFragment fragment = new HintFragment();
        Bundle args = packArgs(R.layout.fragment_hint,R.string.ok);
        args.putString(IMG, imgPath);
        args.putString(DESC, desc);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void unpackArgs(Bundle args){
        super.unpackArgs(args);
        imgPath = args.getString(IMG);
        description = args.getString(DESC);
    }

    @Override
    protected void onContentViewCreated(View view) {
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView txtView = view.findViewById(R.id.text);
        txtView.setText(description);
        ImageCaching.loadIn(getContext(), imgPath, imageView);
    }
}
