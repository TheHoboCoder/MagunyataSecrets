package ru.edu.masu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

// Начальный экран приложения
public class StartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public void onStartGame(View view) {
        Intent intent = new Intent(StartActivity.this, MeetActivity.class);
        startActivity(intent);

    }
}
