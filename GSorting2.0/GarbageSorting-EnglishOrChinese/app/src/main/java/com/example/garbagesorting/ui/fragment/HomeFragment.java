package com.example.garbagesorting.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.garbagesorting.R;
import com.example.garbagesorting.adapter.ItemResultAdapter;
import com.example.garbagesorting.bean.ItemResult;
import com.example.garbagesorting.bean.PictureResult;
import com.example.garbagesorting.ui.activity.RubbishActivity;
import com.example.garbagesorting.util.Base64Util;
import com.example.garbagesorting.util.GlideEngine;
import com.example.garbagesorting.util.KeyBoardUtil;
import com.example.garbagesorting.util.LanguageUtil;
import com.example.garbagesorting.util.MySqliteOpenHelper;
import com.example.garbagesorting.util.OkHttpTool;
import com.example.garbagesorting.util.translate.TransApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页
 */
public class HomeFragment extends Fragment {
    private static String appid = "1113c947654337def72b0ca62b32300d";
    MySqliteOpenHelper helper = null;
    private Activity myActivity;
    private Banner mBanner;//轮播顶部
    private LinearLayout llOne;
    private LinearLayout llTow;
    private LinearLayout llThree;
    private LinearLayout llFour;
    private EditText etQuery;//搜索内容
    private ImageView ivSearch;//搜索图标
    private ImageView ivPhotograph;//搜索图标
    private RecyclerView rvList;
    private LinearLayout llEmpty;
    private ItemResultAdapter itemResultAdapter;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private String imagePath;
    private List<ItemResult> list;
    private String result;
    private String contentStr;
    private ProgressDialog progressDialog;
    private String language;//切换语言

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myActivity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        helper = new MySqliteOpenHelper(myActivity);
        mBanner = view.findViewById(R.id.banner);
        llOne = view.findViewById(R.id.ll_one);
        llTow = view.findViewById(R.id.ll_two);
        llThree = view.findViewById(R.id.ll_three);
        llFour = view.findViewById(R.id.ll_four);
        etQuery = view.findViewById(R.id.et_query);
        ivSearch = view.findViewById(R.id.iv_search);
        ivPhotograph = view.findViewById(R.id.iv_photograph);
        rvList = view.findViewById(R.id.rv_list);
        llEmpty = view.findViewById(R.id.ll_empty);
        language = LanguageUtil.getAppLanguage(myActivity);//获取保存到本地的语言
        initView();
        initEvent();
        return view;
    }

    private void initEvent() {
        llOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myActivity, RubbishActivity.class);
                intent.putExtra("type",getString(R.string.text_home_wet_garbage) );
                intent.putExtra("type1","wet garbage" );
                startActivity(intent);
            }
        });
        llTow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myActivity, RubbishActivity.class);
                intent.putExtra("type", getString(R.string.text_home_dry_refuse));
                intent.putExtra("type1","dry refuse" );
                startActivity(intent);
            }
        });
        llThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myActivity, RubbishActivity.class);
                intent.putExtra("type", getString(R.string.text_home_recyclables));
                intent.putExtra("type1","recyclables" );
                startActivity(intent);
            }
        });
        llFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myActivity, RubbishActivity.class);
                intent.putExtra("type", getString(R.string.text_home_harmful));
                intent.putExtra("type1","harmful" );
                startActivity(intent);
            }
        });
        //搜搜
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyBoardUtil.hideKeyboard(view);//隐藏软键盘
                loadData();
            }
        });
        //拍照
        ivPhotograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectClick();
            }
        });
        //点击软键盘中的搜索
        etQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    KeyBoardUtil.hideKeyboard(v);//隐藏软键盘
                    loadData();//加载数据
                    return true;
                }
                return false;
            }
        });
    }


    private void initView() {
        //图片资源
        int[] imageResourceID = new int[]{R.drawable.ic_a, R.drawable.ic_b, R.drawable.ic_c, R.drawable.ic_d};
        List<Integer> imageList = new ArrayList<>();
        //轮播标题
        for (int i = 0; i < imageResourceID.length; i++) {
            imageList.add(imageResourceID[i]);//把图片资源循环放入list里面
            //设置图片加载器，通过Glide加载图片
            mBanner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(myActivity).load(path).into(imageView);
                }
            });
            //设置轮播的动画效果,里面有很多种特效,可以到GitHub上查看文档。
            mBanner.setImages(imageList);//设置图片资源
            //设置指示器位置（即图片下面的那个小圆点）
            mBanner.setDelayTime(3000);//设置轮播时间3秒切换下一图
            mBanner.start();//开始进行banner渲染
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(myActivity);
        //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //=1.3、设置recyclerView的布局管理器
        rvList.setLayoutManager(layoutManager);
        //==2、实例化适配器
        //=2.1、初始化适配器
        itemResultAdapter = new ItemResultAdapter();
        //=2.3、设置recyclerView的适配器
        rvList.setAdapter(itemResultAdapter);
    }

    private void pictureLoadData() {
        progressDialog = new ProgressDialog(myActivity);
        progressDialog.setMessage(getString(R.string.text_home_loading));
        progressDialog.show();
        progressDialog.setCancelable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                message.what = 2;
                handler.sendMessage(message);
            }
        }).start();


    }


    private void loadData() {
        contentStr = etQuery.getText().toString();
        if ("".equals(contentStr)) {
            Toast.makeText(myActivity, getString(R.string.text_home_name_hint), Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog = new ProgressDialog(myActivity);
        progressDialog.setMessage(getString(R.string.text_home_loading));
        progressDialog.show();
        progressDialog.setCancelable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                //获取搜索内容
                if ("en".equals(language)) {//英文
                    contentStr = TransApi.getTransResultToZh(contentStr);//英文转中文
                }
                message.what = 1;
                handler.sendMessage(message);

            }
        }).start();

    }

    /**
     * 选择图片
     */
    private void selectClick() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofAll())
                .imageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(1)
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        for (int i = 0; i < result.size(); i++) {
                            // onResult Callback
                            LocalMedia media = result.get(i);
                            String path;
                            // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                            boolean compressPath = media.isCompressed() || (media.isCut() && media.isCompressed());
                            // 裁剪过
                            boolean isCutPath = media.isCut() && !media.isCompressed();

                            if (isCutPath) {
                                path = media.getCutPath();
                            } else if (compressPath) {
                                path = media.getCompressPath();
                            } else if (!TextUtils.isEmpty(media.getAndroidQToPath())) {
                                // AndroidQ特有path
                                path = media.getAndroidQToPath();
                            } else if (!TextUtils.isEmpty(media.getRealPath())) {
                                // 原图
                                path = media.getRealPath();
                            } else {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                    path = PictureFileUtils.getPath(myActivity, Uri.parse(media.getPath()));
                                } else {
                                    path = media.getPath();
                                }
                            }
                            imagePath = path;
                            pictureLoadData();
                        }
                    }

                    @Override
                    public void onCancel() {
                        // onCancel Callback
                    }
                });
    }

    private Handler handler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -2://识别失败，可能上传图片太大了
                    progressDialog.dismiss();
                    Toast.makeText(myActivity,getText( R.string.text_home_failed), Toast.LENGTH_SHORT).show();
                    break;
                case -1://失败
                    rvList.setVisibility(View.GONE);
                    llEmpty.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                    Toast.makeText(myActivity, getText(R.string.text_home_no_data), Toast.LENGTH_SHORT).show();
                    break;
                case 0://成功回显数据
                    itemResultAdapter.addItem(list);
                    rvList.setVisibility(View.VISIBLE);
                    llEmpty.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    Toast.makeText(myActivity, getText(R.string.text_home_succeed), Toast.LENGTH_SHORT).show();
                    break;
                case 1://垃圾分类识别--文字
                    String url = "http://127.0.0.1/search";
                    Map<String, Object> map = new HashMap<>();
                    map.put("type", 1);//1:模糊 2:精确，默认1
                    map.put("q", contentStr);
                    map.put("key", appid);
                    OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
                        @Override
                        public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                            myActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Message message = handler.obtainMessage();
                                    if (isSuccess && responseCode == 200) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            result = jsonObject.getString("result");
                                            Type type = new TypeToken<List<ItemResult>>() {
                                            }.getType();//列表信息
                                            list = gson.fromJson(result, type);
                                            if (list != null) {
                                                message.what = 3;
                                            } else {
                                                message.what = -1;
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    handler.sendMessage(message);
                                }
                            });
                        }
                    });
                    break;
                case 2://图片识别
                    String pictureUrl = "http://127.0.0.1/imgDisti";
                    Map<String, Object> pictureMap = new HashMap<>();
                    pictureMap.put("type", 1);//1:模糊 2:精确，默认1
                    pictureMap.put("image", Base64Util.imageToBase64(imagePath));
                    pictureMap.put("key", appid);
                    OkHttpTool.httpPost(pictureUrl, pictureMap, new OkHttpTool.ResponseCallback() {
                        @Override
                        public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                            myActivity.runOnUiThread(new Runnable() {
                                @SuppressLint("HandlerLeak")
                                @Override
                                public void run() {
                                    Message message = handler.obtainMessage();
                                    if (isSuccess && responseCode == 200) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            String reason = jsonObject.getString("reason");
                                            if ("success".equals(reason)) {
                                                String result = jsonObject.getString("result");
                                                Type type = new TypeToken<List<PictureResult>>() {
                                                }.getType();//列表信息
                                                List<PictureResult> pictureResults = gson.fromJson(result, type);
                                                if (pictureResults.get(0).getList() != null && pictureResults.get(0).getList().size() > 0) {
                                                    list = pictureResults.get(0).getList();
                                                    message.what = 3;
                                                } else {
                                                    message.what = -1;
                                                }
                                            } else {
                                                message.what = -2;
                                            }

                                        } catch (JSONException e) {
                                            message.what = -2;
                                            e.printStackTrace();
                                        }
                                    } else {
                                        message.what = -2;
                                    }
                                    handler.sendMessage(message);
                                }
                            });
                        }
                    });
                    break;
                case 3://把识别后的数据转成英文
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message = handler.obtainMessage();
                            if ("en".equals(language)) {//英文
                                for (ItemResult itemResult : list) {
                                    itemResult.setItemName(TransApi.getTransResultToEn(itemResult.getItemName()));
                                    itemResult.setItemCategory(TransApi.getTransResultToEn(itemResult.getItemCategory()));
                                }
                            }else {
                                for (ItemResult itemResult : list) {
                                    itemResult.setItemName(itemResult.getItemName());
                                    itemResult.setItemCategory(itemResult.getItemCategory());
                                }
                            }
                            message.what = 0;
                            handler.sendMessage(message);
                        }
                    }).start();
                    break;
            }
        }
    };

}
