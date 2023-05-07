package com.example.garbagesorting.ui.activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.garbagesorting.R;
import com.example.garbagesorting.bean.User;
import com.example.garbagesorting.util.MySqliteOpenHelper;
import com.example.garbagesorting.util.SPUtils;


/**
 * 个人信息
 */
public class PersonActivity extends AppCompatActivity {
    MySqliteOpenHelper helper = null;
    private Activity mActivity;
    private TextView tvAccount;
    private EditText etNickName;
    private EditText etPhone;//手机号
    private EditText etAddress;//地址
    private RadioGroup rgSex;//性别
    private Button btnSave;//保存
    User mUser = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        mActivity = this;
        helper = new MySqliteOpenHelper(mActivity);
        tvAccount = findViewById(R.id.tv_account);
        etNickName = findViewById(R.id.et_nickName);//获取昵称
        etPhone = findViewById(R.id.et_phone);//获取手机号
        etAddress = findViewById(R.id.et_address);//获取地址
        rgSex = findViewById(R.id.rg_sex);
        btnSave = findViewById(R.id.btn_save);
        initView();
    }

    private void initView() {
        Integer userId = (Integer) SPUtils.get(mActivity,SPUtils.USER_ID,0);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from user where id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(userId)});
        if (cursor != null && cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                Integer dbId = cursor.getInt(0);
                String dbAccount = cursor.getString(1);
                String dbPassword = cursor.getString(2);
                String dbName = cursor.getString(3);
                String dbSex = cursor.getString(4);
                String dbPhone = cursor.getString(5);
                String dbAddress = cursor.getString(6);
                String dbPhoto = cursor.getString(7);
                tvAccount.setText(dbAccount);
                etNickName.setText(dbName);
                etPhone.setText(dbPhone);
                etAddress.setText(dbAddress);
                rgSex.check("男".equals(dbSex)?R.id.rb_male :R.id.rb_woman);
                mUser = new User(dbId, dbAccount, dbPassword, dbName, dbSex, dbPhone, dbAddress, dbPhoto);
            }
        }
        db.close();

        //保存
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getWritableDatabase();
                String nickName= etNickName.getText().toString();
                String phone= etPhone.getText().toString();
                String address= etAddress.getText().toString();
                if ("".equals(nickName)) {//昵称不能为空
                    Toast.makeText(mActivity,getText(R.string.text_personal_nickname_hint), Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("".equals(phone)){//手机号不能为空
                    Toast.makeText(mActivity,getText(R.string.text_personal_mobile_hint), Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(address)){//地址不能为空
                    Toast.makeText(mActivity,getText(R.string.text_personal_address_hint), Toast.LENGTH_LONG).show();
                    return;
                }
                String sex = rgSex.getCheckedRadioButtonId() == R.id.rb_male ? "男" : "女";//性别
                db.execSQL("update user set name = ?,phone=?,address=?,sex=? where id = ?", new Object[]{nickName, phone,address,sex,mUser.getId()});
                Toast.makeText(PersonActivity.this,getText( R.string.text_personal_success),Toast.LENGTH_SHORT).show();
                db.close();
                finish();
            }
        });

    }
    public void back(View view){
        finish();
    }
}
