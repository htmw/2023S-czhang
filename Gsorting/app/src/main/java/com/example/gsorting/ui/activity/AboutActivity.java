package com.example.gsorting.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.gsorting.R;
import com.example.gsorting.util.StatusBarUtil;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        StatusBarUtil.setStatusBar(this,true);
        StatusBarUtil.setStatusBarLightMode(this, true);
    }


    public void back(View view){
        finish();
    }
}