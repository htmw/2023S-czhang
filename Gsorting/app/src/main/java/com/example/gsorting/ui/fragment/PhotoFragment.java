package com.example.gsorting.ui.fragment;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.gsorting.R;
import com.example.gsorting.bean.ItemResult;
import com.example.gsorting.bean.PictureResult;
import com.example.gsorting.ui.activity.MainActivity;
import com.example.gsorting.ui.activity.ResultActivity;
import com.example.gsorting.util.Base64Util;
import com.example.gsorting.util.GlideEngine;
import com.example.gsorting.util.LanguageUtil;
import com.example.gsorting.util.MySqliteOpenHelper;
import com.example.gsorting.util.OkHttpTool;
import com.example.gsorting.util.translate.TransApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.tools.PictureFileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页
 */
public class PhotoFragment extends Fragment {
    private static String appid = "1113c947654337def72b0ca62b32300d";
    MySqliteOpenHelper helper = null;
    private Activity myActivity;
    private ImageView ivPhotograph;//搜索图标
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private String imagePath;
    private List<ItemResult> list;
    private ProgressDialog progressDialog;
    private String language;//切换语言

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myActivity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        helper = new MySqliteOpenHelper(myActivity);
        ivPhotograph = view.findViewById(R.id.iv_photograph);
        language = LanguageUtil.getAppLanguage(myActivity);//获取保存到本地的语言
        initEvent();
        return view;
    }

    private void initEvent() {
        //拍照
        ivPhotograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectClick();
            }
        });

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
                    progressDialog.dismiss();
                    Toast.makeText(myActivity, getText(R.string.text_home_no_data), Toast.LENGTH_SHORT).show();
                    break;
                case 0://成功回显数据
                    Intent intent = new Intent(myActivity, ResultActivity.class);
                    intent.putExtra("ItemResult",list.get(0));
                    startActivity(intent);
                    progressDialog.dismiss();
                    Toast.makeText(myActivity, getText(R.string.text_home_succeed), Toast.LENGTH_SHORT).show();
                    break;
                case 2://图片识别
                    String pictureUrl = "http://apis.juhe.cn/voiceRubbish/imgDisti";
                    Map<String, Object> pictureMap = new HashMap<>();
                    pictureMap.put("type", 2);//1:模糊 2:精确，默认1
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
