package com.example.gsorting.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gsorting.R;
import com.example.gsorting.util.StatusBarUtil;



public class RubbishDetailsActivity extends AppCompatActivity {
    private TextView tvTitle;
    private TextView tvContent;
    private Activity myActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity = this;
        setContentView(R.layout.activity_rubbish_details);
        StatusBarUtil.setStatusBar(this,true);
        StatusBarUtil.setStatusBarLightMode(this,true);
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");
        tvTitle.setText(title);
        tvContent.setText(content);

    }


    public void back(View view){
        finish();
    }
}
