package com.example.gsorting.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.example.gsorting.R;
import com.example.gsorting.bean.ItemResult;
import com.example.gsorting.ui.activity.MainActivity;
import com.example.gsorting.ui.activity.ResultActivity;
import com.example.gsorting.ui.activity.RubbishActivity;
import com.example.gsorting.util.KeyBoardUtil;
import com.example.gsorting.util.LanguageUtil;
import com.example.gsorting.util.MySqliteOpenHelper;
import com.example.gsorting.util.OkHttpTool;
import com.example.gsorting.util.translate.TransApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页
 */
public class SearchFragment extends Fragment {
    private static String appid = "1113c947654337def72b0ca62b32300d";
    MySqliteOpenHelper helper = null;
    private Activity myActivity;
    private ImageView llOne;
    private ImageView llTow;
    private ImageView llThree;
    private ImageView llFour;
    private EditText etQuery;//搜索内容
    private ImageView ivSearch;//搜索图标
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        helper = new MySqliteOpenHelper(myActivity);
        llOne = view.findViewById(R.id.ll_one);
        llTow = view.findViewById(R.id.ll_two);
        llThree = view.findViewById(R.id.ll_three);
        llFour = view.findViewById(R.id.ll_four);
        etQuery = view.findViewById(R.id.et_query);
        ivSearch = view.findViewById(R.id.iv_search);
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


    private Handler handler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -1://失败
                    progressDialog.dismiss();
                    Toast.makeText(myActivity, getText(R.string.text_home_no_data), Toast.LENGTH_SHORT).show();
                    break;
                case 0://成功回显数据
                    progressDialog.dismiss();
                    Intent intent = new Intent(myActivity, ResultActivity.class);
                    intent.putExtra("ItemResult",list.get(0));
                    startActivity(intent);
                    Toast.makeText(myActivity, getText(R.string.text_home_succeed), Toast.LENGTH_SHORT).show();
                    break;
                case 1://垃圾分类识别--文字
                    String url = "http://apis.juhe.cn/voiceRubbish/search";
                    Map<String, Object> map = new HashMap<>();
                    map.put("type", 2);//1:模糊 2:精确，默认1
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
