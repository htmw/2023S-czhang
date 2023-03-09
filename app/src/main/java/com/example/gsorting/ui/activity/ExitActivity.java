package com.example.gsorting.ui.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gsorting.R;
import com.example.gsorting.util.StatusBarUtil;


public class ExitActivity extends AppCompatActivity {
    private Button in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);

        StatusBarUtil.setStatusBar(this,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(this,true);//状态栏文字颜色
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