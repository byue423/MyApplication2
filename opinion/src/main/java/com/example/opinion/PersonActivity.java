package com.example.opinion;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by wangjj on 2017/5/31.
 */

public class PersonActivity extends Activity {
    private Context context = PersonActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n_fillopinion);

    }
}
