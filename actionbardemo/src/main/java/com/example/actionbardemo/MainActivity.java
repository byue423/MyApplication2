package com.example.actionbardemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int USER_SEARCH = 0;
    private static final int USER_ADD = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        final PopMenuMore mMenu = new PopMenuMore(this);
        mMenu.setBackgroundColor(Color.parseColor("#ffffff"));
        ArrayList<PopMenuMoreItem> items = new ArrayList<>();
        items.add(new PopMenuMoreItem(USER_SEARCH, "搜索dfdfdfdfadfdsafa"));
        items.add(new PopMenuMoreItem(USER_ADD, "添加"));

        mMenu.addItems(items);
        mMenu.setOnItemSelectedListener(new PopMenuMore.OnItemSelectedListener() {
            @Override
            public void selected(View view, PopMenuMoreItem item, int position) {
                switch (item.id) {
                    case USER_SEARCH:
//                        startActivity(new Intent(this, UserSearchActivity.class));
                        break;
                    case USER_ADD:
//                        startActivity(new Intent(getActivity(), UserAddActivity.class));
                        break;
                }
            }
        });

        final Button addMenu = (Button)findViewById(R.id.addmenu);
        addMenu.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mMenu.showAsDropDown(addMenu);
            }
        });

    }

/*

    private void initMenu() {
        mMenu = new PopMenuMore(this);
        // mMenu.setCorner(R.mipmap.demand_icon_location);
        // mMenu.setBackgroundColor(Color.parseColor("#ff8800"));
        ArrayList<PopMenuMoreItem> items = new ArrayList<>();
        items.add(new PopMenuMoreItem(USER_SEARCH, "搜索"));
        items.add(new PopMenuMoreItem(USER_ADD, "添加"));
        items.add(new PopMenuMoreItem(USER_SEARCH, "搜索"));
        items.add(new PopMenuMoreItem(USER_ADD, "添加"));
        items.add(new PopMenuMoreItem(USER_SEARCH, "搜索"));
        items.add(new PopMenuMoreItem(USER_ADD, "添加"));
        */
/*items.add(new PopMenuMoreItem(USER_SEARCH, R.mipmap.demand_icon_number, "搜索"));
        items.add(new PopMenuMoreItem(USER_ADD, R.mipmap.demand_icon_location, "添加"));
        items.add(new PopMenuMoreItem(USER_SEARCH, R.mipmap.demand_icon_number, "搜索"));
        items.add(new PopMenuMoreItem(USER_ADD, R.mipmap.demand_icon_location, "添加"));
        items.add(new PopMenuMoreItem(USER_SEARCH, R.mipmap.demand_icon_number, "搜索"));
        items.add(new PopMenuMoreItem(USER_ADD, R.mipmap.demand_icon_location, "添加"));*//*


        mMenu.addItems(items);
        mMenu.setOnItemSelectedListener(new PopMenuMore.OnItemSelectedListener() {
            @Override
            public void selected(View view, PopMenuMoreItem item, int position) {
                switch (item.id) {
                    case USER_SEARCH:
//                        startActivity(new Intent(this, UserSearchActivity.class));
                        break;
                    case USER_ADD:
//                        startActivity(new Intent(getActivity(), UserAddActivity.class));
                        break;
                }
            }
        });
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
