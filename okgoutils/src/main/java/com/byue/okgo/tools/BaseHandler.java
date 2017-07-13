package com.byue.okgo.tools;

import android.os.Handler;
import android.os.Message;

import com.example.okgoutils.BaseActivity;

import java.lang.ref.WeakReference;

/**
 * @author:wjj
 * @date: 2017/6/27 15:03.
 * @description:
 */
public class BaseHandler extends Handler {

    private WeakReference<BaseActivity> reference;

    public BaseHandler(BaseActivity activity) {
        reference = new WeakReference<BaseActivity>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        if (reference != null && reference.get() != null) {
            reference.get().handlerMessage(msg);
        }
    }

}
