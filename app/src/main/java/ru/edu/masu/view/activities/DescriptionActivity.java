package ru.edu.masu.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import ru.edu.masu.R;
import ru.edu.masu.utils.ImageCaching;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DescriptionActivity extends AppCompatActivity {

    public static final String DESC = "desc";
    public static final String IMG_PATH = "img_Path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        String desc = getIntent().getStringExtra(DESC);
        String imgPath = getIntent().getStringExtra(IMG_PATH);
        ImageView v = findViewById(R.id.descImg);
        TextView textView = findViewById(R.id.descTxt);
        textView.setText(desc);
        ImageCaching.loadIn(this, imgPath, v);
    }

    public void onOkBtnClick(View view) {
        Intent intent = new Intent(DescriptionActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
