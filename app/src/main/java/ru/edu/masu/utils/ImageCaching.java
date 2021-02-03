package ru.edu.masu.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageCaching {

    public static void loadIn(Context context, String url, ImageView view){
        Glide.with(context)
                .load(url)
                .fitCenter()
                .into(view);
    }
}
