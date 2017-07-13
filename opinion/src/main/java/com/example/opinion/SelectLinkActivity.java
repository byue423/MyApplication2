package com.example.opinion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wangjj on 2017/5/31.
 */

public class SelectLinkActivity extends Activity {
    private Context context = SelectLinkActivity.this;
    private List<HashMap<Object, Object>> n_SelectList = new ArrayList<HashMap<Object, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n_fillopinion_selectlink);

        LinearLayout cOpinion = (LinearLayout) findViewById(R.id.n_selected);

        init();

//        Button fillOpinionNext = (Button) findViewById(R.id.fillOpinionNext);
//        fillOpinionNext.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//            }
//        });

        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setStretchAllColumns(true);//全部列自动填充空白处
        TableRow tRow = new TableRow(context);

        //tv用于显示
        TextView tv = new TextView(this);
        tv.setText("000");
        tv.setBackgroundResource(R.drawable.table_frame_gray);
        //tRow.addView(tv);

        TextView tv1 = new TextView(this);
        tv1.setText("111");
        tv1.setBackgroundResource(R.drawable.table_frame_gray);
        //tRow.addView(tv1);

        TableRow tRow1 = new TableRow(context);

        LinearLayout layout = addLinearLay();
        layout.addView(addTvByWeight(context, "titl888812345678e", 15, 2));
        layout.addView(addTvByWeight(context, "111111111", 15, 4));
        tRow1.addView(layout);

        tableLayout.addView(tRow);
        tableLayout.addView(tRow1);

        cOpinion.addView(tableLayout);
    }

    private void init() {
        LinearLayout links = (LinearLayout) findViewById(R.id.n_links);
        TextView tv = addTv(context, "多环节提交", 16);
        tv.setGravity(Gravity.LEFT);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        Drawable drawable= getResources().getDrawable(R.mipmap.ic_launcher);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawables(null,null,drawable,null);
        tv.setBackgroundColor(0xffececec);
        tv.setPadding(6,6,6,6);
        tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        links.addView(tv);

        TextView tv1 = addTv(context, "返回起草人", 16);
        tv1.setGravity(Gravity.LEFT);
        tv1.setPadding(6,6,6,6);
        tv1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        links.addView(tv1);
    }

    private LinearLayout addLinearLay() {
        LinearLayout lay = new LinearLayout(context);
        lay.setOrientation(LinearLayout.HORIZONTAL);
        return lay;
    }

    private TextView addTv(Context context, String text, int textSize) {
        TextView tv = new TextView(context);
        tv.setText(text);
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(textSize);
        tv.setSingleLine();
        tv.setBackgroundResource(R.drawable.table_frame_gray);
        return tv;
    }

    private TextView addTvByWeight(Context context, String text, int textSize, int weight) {
        TextView tv = new TextView(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, weight);
        tv.setLayoutParams(lp);
        tv.setText(text);
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(textSize);
        tv.setSingleLine();
        tv.setBackgroundResource(R.drawable.table_frame_gray);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        return tv;
    }

    public static void scanPhotos(String filePath, Context context) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(new File(filePath));
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    private TableRow addTableRow(Context context) {
        TableRow tRow = new TableRow(context);
        return tRow;
    }

    private TableLayout addTableLay(Context context) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setStretchAllColumns(true);//全部列自动填充空白处
        return tableLayout;
    }
}
