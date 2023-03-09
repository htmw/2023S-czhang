package com.example.gsorting.ui.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.gsorting.R;
import com.example.gsorting.ui.fragment.PhotoFragment;
import com.example.gsorting.ui.fragment.SearchFragment;
import com.example.gsorting.ui.fragment.UserFragment;
import com.example.gsorting.util.StatusBarUtil;

/**
 * 普通用户主页面
 */
public class MainActivity extends AppCompatActivity {
    public static Activity myActivity;
    private TextView tvTitle;
    private LinearLayout llContent;
    private RadioButton rbPhoto;
    private RadioButton rbSearch;
    private RadioButton rbUser;
    private Fragment[] fragments = new Fragment[]{null, null, null};//存放Fragment

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity = this;
        setContentView(R.layout.activity_main);
        tvTitle =  (TextView) findViewById(R.id.title);
        llContent =  (LinearLayout) findViewById(R.id.ll_main_content);
        rbPhoto = (RadioButton) findViewById(R.id.rb_main_photo);
        rbSearch = (RadioButton) findViewById(R.id.rb_main_search);
        rbUser = (RadioButton) findViewById(R.id.rb_main_user);
        initView();
        setViewListener();
    }

    private void setViewListener() {
        rbPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTitle.setText(getText(R.string.text_main_photo));
                switchFragment(0);
            }
        });
        rbSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvTitle.setText(getText(R.string.text_main_search));
                switchFragment(1);
            }
        });
        rbUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTitle.setText(getText(R.string.text_main_my));
                switchFragment(2);
            }
        });
    }

    private void initView() {

        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
        //设置导航栏图标样式
        Drawable iconHome=getResources().getDrawable(R.drawable.selector_main_rb_photo);//设置主页图标样式
        iconHome.setBounds(0,0,68,68);//设置图标边距 大小
        rbPhoto.setCompoundDrawables(null,iconHome,null,null);//设置图标位置
        rbPhoto.setCompoundDrawablePadding(5);//设置文字与图片的间距
        Drawable iconSearch=getResources().getDrawable(R.drawable.selector_main_rb_search);//设置主页图标样式
        iconSearch.setBounds(0,0,68,68);//设置图标边距 大小
        rbSearch.setCompoundDrawables(null,iconSearch,null,null);//设置图标位置
        rbSearch.setCompoundDrawablePadding(5);//设置文字与图片的间距
        Drawable iconUser=getResources().getDrawable(R.drawable.selector_main_rb_user);//设置主页图标样式
        iconUser.setBounds(0,0,68,70);//设置图标边距 大小
        rbUser.setCompoundDrawables(null,iconUser,null,null);//设置图标位置
        rbUser.setCompoundDrawablePadding(5);//设置文字与图片的间距
        switchFragment(0);
        rbPhoto.setChecked(true);
    }

    private void switchFragment(int fragmentIndex) {
        FragmentManager fragmentManager=this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragments[fragmentIndex] == null) {
            switch (fragmentIndex) {
                case 0:
                    fragments[fragmentIndex] = new PhotoFragment();
                    break;
                case 1:
                    fragments[fragmentIndex] = new SearchFragment();
                    break;
                case 2:
                    fragments[fragmentIndex] = new UserFragment();
                    break;
            }
            transaction.add(R.id.ll_main_content, fragments[fragmentIndex]);
        }

        for (int i = 0; i < fragments.length; i++) {
            if (fragmentIndex != i && fragments[i] != null) {
                transaction.hide(fragments[i]);
            }
        }
        transaction.show(fragments[fragmentIndex]);

        transaction.commit();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
        }

        return false;
    }

    private long time = 0;

    public void exit() {
        if (System.currentTimeMillis() - time > 2000) {
            time = System.currentTimeMillis();
            Toast.makeText(myActivity, getText(R.string.text_main_exit), Toast.LENGTH_LONG).show();
        } else {
            finish();
        }
    }
}
