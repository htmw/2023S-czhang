package com.example.garbagesorting.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.garbagesorting.R;
import com.example.garbagesorting.util.LanguageUtil;
import com.example.garbagesorting.util.SPUtils;

import java.util.Locale;

/**
 * 退出页
 */
public class ExitActivity extends AppCompatActivity {
    private Button in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);
        in = findViewById(R.id.in);
        CountDownTimer timer = new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                in.setText(millisUntilFinished / 1000 + "");
            }
            public void onFinish() {
                finish();
            }
        };
        timer.start();
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                finish();
            }
        });
    }
}