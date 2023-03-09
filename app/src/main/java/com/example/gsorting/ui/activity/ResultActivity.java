package com.example.gsorting.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gsorting.R;
import com.example.gsorting.bean.ItemResult;
import com.example.gsorting.util.StatusBarUtil;



public class ResultActivity extends AppCompatActivity {
    private TextView tvName;
    private TextView tvType;
    private ImageView ivType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        StatusBarUtil.setStatusBar(this,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(this,true);//状态栏文字颜色
        tvName = findViewById(R.id.tv_name);
        tvType = findViewById(R.id.tv_type);
        ivType = findViewById(R.id.iv_type);
        ItemResult itemResult =(ItemResult) getIntent().getSerializableExtra("ItemResult");
        tvName.setText(itemResult.getItemName());
        tvType.setText(itemResult.getItemCategory());
        if ("湿垃圾".equals(itemResult.getItemCategory()) || "Wet garbage".equals(itemResult.getItemCategory())){
            ivType.setImageResource(R.drawable.i_1);
        }else  if ("干垃圾".equals(itemResult.getItemCategory()) || "Dry garbage".equals(itemResult.getItemCategory())){
            ivType.setImageResource(R.drawable.i_2);
        }else  if ("可回收垃圾".equals(itemResult.getItemCategory()) || "Recyclable garbage".equals(itemResult.getItemCategory())){
            ivType.setImageResource(R.drawable.i_3);
        }else  if ("有害垃圾".equals(itemResult.getItemCategory()) || "Harmful waste".equals(itemResult.getItemCategory())){
            ivType.setImageResource(R.drawable.i_4);
        }
    }


    public void back(View view){
        finish();
    }
}
