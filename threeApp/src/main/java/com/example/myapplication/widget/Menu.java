package com.example.myapplication.widget;

import android.content.Intent;

/**
 * Created by Administrator on 2017/5/25.
 */

public class Menu {

    private String text;
    private Intent intent;
    private String name;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
