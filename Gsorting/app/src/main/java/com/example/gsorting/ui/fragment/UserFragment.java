package com.example.gsorting.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.gsorting.R;
import com.example.gsorting.ui.activity.AboutActivity;
import com.example.gsorting.ui.activity.ExitActivity;
import com.example.gsorting.ui.activity.LanguageActivity;
import com.example.gsorting.util.GlideEngine;
import com.example.gsorting.util.MySqliteOpenHelper;
import com.example.gsorting.util.SPUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.List;



public class UserFragment extends Fragment {
    MySqliteOpenHelper helper = null;
    private Activity mActivity;
    private ImageView ivPhoto;
    private LinearLayout llLanguage;
    private LinearLayout llAbout;
    private Button btnExit;
    private RequestOptions headerRO = new RequestOptions().circleCrop();//圆角变换
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,container,false);
        helper = new MySqliteOpenHelper(mActivity);
        ivPhoto = view.findViewById(R.id.iv_photo);
        llLanguage = view.findViewById(R.id.language);
        llAbout = view.findViewById(R.id.about);
        btnExit = view.findViewById(R.id.exit);
        initView();
        return view;
    }



    private void initView() {
        Glide.with(mActivity)
                .load(R.drawable.ic_logo)
                .apply(headerRO.error(R.drawable.ic_logo))
                .into(ivPhoto);

        llLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mActivity, LanguageActivity.class);
                startActivity(intent);
            }
        });

        llAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mActivity, AboutActivity.class);
                startActivity(intent);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, ExitActivity.class));
                mActivity.finish();
            }
        });
    }

}
