package com.example.garbagesorting.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.garbagesorting.R;
import com.example.garbagesorting.bean.ItemResult;



/**
 * 明细
 */
public class ResultActivity extends AppCompatActivity {
    private TextView tvName;
    private TextView tvType;
    private ImageView ivType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        tvName = findViewById(R.id.tv_name);
        tvType = findViewById(R.id.tv_type);
        ivType = findViewById(R.id.iv_type);
        ItemResult itemResult =(ItemResult) getIntent().getSerializableExtra("ItemResult");
        tvName.setText(itemResult.getItemName());
        tvType.setText(itemResult.getItemCategory());
        if ("湿垃圾".equals(itemResult.getItemCategory()) || "Wet garbage".equals(itemResult.getItemCategory())){
            ivType.setImageResource(R.drawable.ic_a);
        }else  if ("干垃圾".equals(itemResult.getItemCategory()) || "Dry garbage".equals(itemResult.getItemCategory())){
            ivType.setImageResource(R.drawable.ic_b);
        }else  if ("可回收垃圾".equals(itemResult.getItemCategory()) || "Recyclable garbage".equals(itemResult.getItemCategory())){
            ivType.setImageResource(R.drawable.ic_c);
        }else  if ("有害垃圾".equals(itemResult.getItemCategory()) || "Harmful waste".equals(itemResult.getItemCategory())){
            ivType.setImageResource(R.drawable.ic_d);
        }
    }


    //返回
    public void back(View view){
        finish();
    }
}
