package com.example.garbagesorting.ui.activity;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.garbagesorting.R;
import com.example.garbagesorting.bean.Rubbish;
import com.example.garbagesorting.util.MySqliteOpenHelper;


/**
 * 添加或者修改
 */
public class AddActivity extends AppCompatActivity {
    MySqliteOpenHelper helper = null;
    private TextView tvTitle;
    private Spinner spType;
    private EditText etName;
    private EditText etContent;
    private Rubbish mRubbish;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        tvTitle = findViewById(R.id.tv_title);
        spType = findViewById(R.id.type);
        etName = findViewById(R.id.name);
        etContent = findViewById(R.id.content);
        helper = new MySqliteOpenHelper(this);
        mRubbish = (Rubbish) getIntent().getSerializableExtra("rubbish");
        if (mRubbish !=null) {
            etName.setText(mRubbish.getName());
            etContent.setText(mRubbish.getContent());
            spType.setSelection(mRubbish.getTypeId());
            tvTitle.setText(getText(R.string.text_garbage_modify_title));
        }
    }

    /**
     * 保存
     * @param view
     */
    public void save(View view){
        SQLiteDatabase db = helper.getWritableDatabase();
        String name = etName.getText().toString();
        String content= etContent.getText().toString();
        String type= spType.getSelectedItem().toString();
        Integer typeId= spType.getSelectedItemPosition();
        if ("".equals(name)) {
            Toast.makeText(AddActivity.this,getText(R.string.text_garbage_name_hint),Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(content)) {
            Toast.makeText(AddActivity.this,getText(R.string.text_garbage_content_hint),Toast.LENGTH_SHORT).show();
            return;
        }
        if (mRubbish == null) {//新增
            String sql = "insert into rubbish(name,typeId, type,content) values(?,?,?,?)";
            db.execSQL(sql,new Object[]{name,typeId, type,content});
            Toast.makeText(AddActivity.this, getText(R.string.text_garbage_add_success),Toast.LENGTH_SHORT).show();
        }else {//修改
            db.execSQL("update rubbish set name = ?, typeId = ?, type = ?, content = ? where id=?", new Object[]{name, typeId,type,content, mRubbish.getId()});
            Toast.makeText(AddActivity.this, getText(R.string.text_garbage_update_success),Toast.LENGTH_SHORT).show();
        }
        db.close();
        finish();
    }
    //返回
    public void back(View view){
        finish();
    }
}
