package com.example.okgoutils;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.byue.okgo.tools.NetUtil;
import com.byue.okgo.tools.WebService;
import com.google.gson.Gson;
import com.util.StringEmpty;
import com.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {
    private Context context = MainActivity.this;

    private TextView tv;
    //private Handler mHandler = new BaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button jk = (Button) findViewById(R.id.jk);
        tv = (TextView) findViewById(R.id.tx);

        jk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("username", "jiangdongyi");
            map.put("password", "111111");
            Object obj = (Object) map;

            WebService wb = new WebService(context, NetUtil.WebConfig.POST, NetUtil.WebConfig.USERLOGIN, obj, NetUtil.WebConfig.GET_LOGIN);
            new Thread(wb).start();
            }
        });
    }

    //处理子线程反馈的信息
    @Override
    public void handlerMessage(Message msg) {

        dismissLoading();
        //处理与子线程的交互逻辑
        String jsonString = (String) msg.obj;
        Gson gson = new Gson();

        if (StringEmpty.isGoodJson(jsonString)) {
            try {
                JSONObject jsonObj = new JSONObject(jsonString);
                String resultCode = jsonObj.getString("resultCode");
                if ("0".equals(resultCode)) {
                    switch (msg.what) {
                        case NetUtil.WebConfig.GET_LOGIN:
                            ToastUtil.toast(context, "登录成功");
                            break;
                        case NetUtil.WebConfig.ERROR_MSG:
                            ToastUtil.toast(context, jsonString);
                            break;
                    }
                } else {
                    ToastUtil.toast(context, jsonString);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            ToastUtil.toast(context, jsonString);
        }

    }
}
