package com.example.st11_original_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

public class ErrorActivity extends AppCompatActivity {

    private TextView errormessage;
    private Button reloadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        //データ受け取り
        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");
        //データをバインド
        errormessage = findViewById(R.id.errorMessage);
        errormessage.setText(msg);

        //元の画面に戻る
        reloadBtn = findViewById(R.id.reload);
        reloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ホーム画面に戻る
                Intent intent = new Intent(getApplication(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}