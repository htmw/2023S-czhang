package com.example.gsorting.ui.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.gsorting.R;
import com.example.gsorting.util.StatusBarUtil;


public class ExitActivity extends AppCompatActivity {
    private Button inButton;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);

        // set status bar color to white and text color to dark
        StatusBarUtil.setStatusBar(this,true);
        StatusBarUtil.setStatusBarLightMode(this, true);

        inButton = findViewById(R.id.in);
        countDownTimer = new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                inButton.setText(String.valueOf(millisUntilFinished / 1000));
            }
            public void onFinish() {
                finish();
            }
        };
        countDownTimer.start();

        inButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // cancel timer to avoid memory leak
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}