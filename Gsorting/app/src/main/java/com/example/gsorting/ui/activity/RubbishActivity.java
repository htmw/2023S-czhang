package com.example.gsorting.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsorting.R;
import com.example.gsorting.adapter.RubbishAdapter;
import com.example.gsorting.bean.Rubbish;
import com.example.gsorting.util.MySqliteOpenHelper;
import com.example.gsorting.util.StatusBarUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RubbishActivity extends AppCompatActivity {
    MySqliteOpenHelper helper = null;
    private Activity myActivity;
    private RecyclerView rvPoetryList;
    private RubbishAdapter mRubbishAdapter;
    private List<Rubbish> list;
    private TextView tvTitle;
    private String mType;
    private String mType1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity = this;
        setContentView(R.layout.activity_rubbish);
        StatusBarUtil.setStatusBar(this,true);
        StatusBarUtil.setStatusBarLightMode(this,true);
        helper = new MySqliteOpenHelper(myActivity);
        rvPoetryList =findViewById(R.id.rv_list);
        tvTitle =findViewById(R.id.tv_title);

        LinearLayoutManager layoutManager = new LinearLayoutManager(myActivity);
        rvPoetryList.setLayoutManager(layoutManager);
        mRubbishAdapter =new RubbishAdapter();
        rvPoetryList.setAdapter(mRubbishAdapter);
        mType = getIntent().getStringExtra("type");
        mType1 = getIntent().getStringExtra("type1");
        tvTitle.setText(mType);
        loadData();
        mRubbishAdapter.setItemListener(new RubbishAdapter.ItemListener() {
            @Override
            public void ItemClick(Rubbish rubbish) {

            }

            @Override
            public void ItemLongClick(Rubbish rubbish) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(myActivity);
                dialog.setMessage("Are you sure you want to delete this data");
                dialog.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = helper.getWritableDatabase();
                        if (db.isOpen()) {
                            db.execSQL("delete from rubbish where id = "+rubbish.getId());
                            db.close();
                        }
                        Toast.makeText(myActivity,"successfully delete",Toast.LENGTH_LONG).show();
                        loadData(); 
                    }
                });
                dialog.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }

    private void loadData() {
        list=  new ArrayList<>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor ;
        String sql = "select * from rubbish where type =?";
        cursor = db.rawQuery(sql, new String[]{mType1});
        if (cursor != null && cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                Integer dbId = cursor.getInt(0);
                String dbName = cursor.getString(1);
                Integer dbTypeId = cursor.getInt(2);
                String dbType = cursor.getString(3);
                String dbContent = cursor.getString(4);
                Rubbish rubbish = new Rubbish(dbId,dbName, dbTypeId,dbType,dbContent);
                list.add(rubbish);
            }
        }
        db.close();
        mRubbishAdapter.addItem(list);//添加数据到列表
    }

    //返回
    public void back(View view){
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}