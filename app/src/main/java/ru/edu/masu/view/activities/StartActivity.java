package ru.edu.masu.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import ru.edu.masu.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

// Начальный экран приложения
public class StartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

    }

    public void onStartGame(View view) {
        Intent intent = new Intent(StartActivity.this, MeetActivity.class);
        //пользователь не должен возвращаться на активити с монстрами,
        //если он решит завершить квест и нажмет кнопку назад на MainActivity,
        // он должен быть возвращен на StartActivity
        //поэтому MeetActivity не должна добавляться в стек переходов, так же как и активити с
        //предысторией квеста
        //также любая активити, запускаемая с главной активити, не должна добавлятся в стек переходов
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    //при нажатии на текст вступить и на лого магу будет открываться группа вк
    public void openVK(View view) {
        Uri link = Uri.parse(getString(R.string.masu_site));
        Intent intent = new Intent(Intent.ACTION_VIEW, link);
        startActivity(intent);
    }
}
