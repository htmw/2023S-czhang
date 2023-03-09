package com.example.gsorting.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gsorting.R;
import com.example.gsorting.util.LanguageUtil;
import com.example.gsorting.util.StatusBarUtil;

import java.util.Locale;


public class LanguageActivity extends AppCompatActivity {
    private Activity myActivity;
    private RadioGroup rgLanguage;
    private String language;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity = this;
        setContentView(R.layout.activity_language);

        StatusBarUtil.setStatusBar(this,true);
        StatusBarUtil.setStatusBarLightMode(this,true);
        rgLanguage = findViewById(R.id.rg_language);
        language = LanguageUtil.getAppLanguage(myActivity);
        initView();
        setViewListener();
    }


    private void initView() {

        if ("zh".equals(language)) {
            rgLanguage.check(R.id.rb_language_chinese);
        } else if ("en".equals(language)) {
            rgLanguage.check(R.id.rb_language_english);
        }
    }


    private void setViewListener() {

        rgLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_language_chinese) {
                    language = "zh";
                } else if (checkedId == R.id.rb_language_english) {
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
