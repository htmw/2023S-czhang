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
 * 欢迎页
 */
public class OpenActivity extends AppCompatActivity {
    private Button in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!LanguageUtil.isSetting(OpenActivity.this)) {//未设置
            Locale locale = LanguageUtil.getAppLocale(this);//获取本地的语言
            LanguageUtil.changeAppLanguage(this,locale,true);//设置语言
        }
        setContentView(R.layout.activity_open);
        in = findViewById(R.id.in);
        Integer userId= (Integer) SPUtils.get(OpenActivity.this, SPUtils.USER_ID,0);
        Integer userType= (Integer) SPUtils.get(OpenActivity.this, SPUtils.USER_TYPE,0);
        CountDownTimer timer = new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                in.setText(millisUntilFinished / 1000 + "");
            }
            public void onFinish() {
                Intent intent = new Intent();
                if (userId > 0) {//已登录
                    intent.setClass(OpenActivity.this, MainActivity.class);
                }else {
                    intent.setClass(OpenActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        };
        timer.start();
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (userId > 0) {//已登录
                    intent.setClass(OpenActivity.this, MainActivity.class);
                }else {
                    intent.setClass(OpenActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                timer.cancel();
                finish();
            }
        });
    }
}