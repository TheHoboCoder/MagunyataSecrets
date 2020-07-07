package ru.edu.masu.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import ru.edu.masu.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        String desc = getIntent().getStringExtra("DESC");
//        TextView v = findViewById(R.id.descTxt);
//        v.setText(desc);
    }

    public void onOkBtnClick(View view) {
        Intent intent = new Intent(DescriptionActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
