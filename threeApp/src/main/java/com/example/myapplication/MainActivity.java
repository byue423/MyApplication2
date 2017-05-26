package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.widget.BottomNavigationBar;
import com.example.myapplication.widget.Menu;
import com.example.myapplication.widget.NavigationItem;

/**
 * Created by Administrator on 2017/5/24.
 */

public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bnBar);
        /*
        add bottom navigation item
        param1 bottom text
        param2 bottom image checked
        param3 bottom image unchecked
         */
//        bottomNavigationBar.addItemView("Recents",R.drawable.ic_restore_green_24dp,R.drawable.ic_restore_black_24dp);
//        bottomNavigationBar.addItemView("Favorites", R.drawable.ic_insert_emoticon_check_24dp,R.drawable.ic_insert_emoticon_black_24dp);
//        bottomNavigationBar.addItemView("Nearby",R.drawable.ic_place_green_24dp,R.drawable.ic_place_black_24dp);
        //Object obj = new Object();
        Menu obj = new Menu();
        obj.setText("123");

        Intent intent = new Intent(this, MainActivity.class);
        obj.setIntent(intent);

        bottomNavigationBar.addItemView(0, obj, "opinion",R.drawable.ic_opinion_blue_24dp, R.drawable.ic_opinion_black_24dp);
        bottomNavigationBar.addItemView(0, obj, "submit",R.drawable.ic_submit_blue_24dp, R.drawable.ic_submit_black_24dp);
        bottomNavigationBar.addItemView(0, obj, "circulated",R.drawable.ic_circulated_blue_24dp, R.drawable.ic_circulated_black_24dp);
        bottomNavigationBar.addItemView(0, obj, "more",R.drawable.ic_more_blue_24dp, R.drawable.ic_more_black_24dp);

        bottomNavigationBar.setType(BottomNavigationBar.NavShowType.NORMAL);

        TextView tv = (TextView) this.findViewById(R.id.a);
        tv.setText("张三考上大学了");
        tv.setGravity(Gravity.CENTER);

        bottomNavigationBar.setOnItemViewSelectedListener(new BottomNavigationBar.OnItemViewSelectedListener() {
            @Override
            public void onItemClcik(View v, int index) {
                Menu o = (Menu)v.getTag();
                Log.d("111", o.getText());
                Log.d("222", o.getIntent() + "");
            }
        });
    }
}
