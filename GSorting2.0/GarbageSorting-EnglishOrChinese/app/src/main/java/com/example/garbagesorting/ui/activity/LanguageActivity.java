package com.example.garbagesorting.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.garbagesorting.R;
import com.example.garbagesorting.util.LanguageUtil;

import java.util.Locale;

/**
 * 语言设置页面
 */
public class LanguageActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private RadioGroup rgLanguage;//切换语言
    private String language;//切换语言

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity = this;
        setContentView(R.layout.activity_language);
        rgLanguage = findViewById(R.id.rg_language);
        language = LanguageUtil.getAppLanguage(myActivity);//获取保存到本地的语言
        initView();//初始化页面
        setViewListener();//监听事件
    }

    /**
     * 初始化页面
     */
    private void initView() {
        //勾选初始化
        if ("zh".equals(language)) {//简体中文
            rgLanguage.check(R.id.rb_language_chinese);
        } else if ("en".equals(language)) {//英语
            rgLanguage.check(R.id.rb_language_english);
        }
    }

    /**
     * 监听事件
     */
    private void setViewListener() {
        //切换语言
        rgLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_language_chinese) {//简体中文
                    language = "zh";
                } else if (checkedId == R.id.rb_language_english) {//英文
                    language = "en";
                }
            }
        });
    }

    public void save(View view) {
        if ("zh".equals(language)) {
            LanguageUtil.setLocale(Locale.SIMPLIFIED_CHINESE, myActivity);
        } else if ("en".equals(language)) {
            LanguageUtil.setLocale(Locale.ENGLISH, myActivity);
        }
        MainActivity.myActivity.finish();
        Intent intent = new Intent(myActivity, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
