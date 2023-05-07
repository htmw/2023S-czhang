package com.example.garbagesorting.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.garbagesorting.R;


/**
 * 垃圾明细
 */
public class RubbishDetailsActivity extends AppCompatActivity {
    private TextView tvTitle;//标题
    private TextView tvContent;//内容
    private Activity myActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity = this;
        setContentView(R.layout.activity_rubbish_details);
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        String title = getIntent().getStringExtra("title");//标题
        String content = getIntent().getStringExtra("content");//内容
        tvTitle.setText(title);//设置标题
        tvContent.setText(content);//设置内容

    }

    //返回
    public void back(View view){
        finish();
    }
}
