package com.example.opinion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class OpinionActivity extends Activity {

    String imageUrl = "http://content.52pk.com/files/100623/2230_102437_1_lit.jpg";

    private Context context = OpinionActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.n_fillopinion);
        final String hint = "请填入您的常用意见。";

        Button cOpinion = (Button) findViewById(R.id.commomOpinion);
        Button fillOpinionNext = (Button) findViewById(R.id.fillOpinionNext);
        fillOpinionNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.viewFipperFillOpinion);
                //viewFlipper.setDisplayedChild(1);
                Intent intent = new Intent(context, SelectLinkActivity.class);
                startActivity(intent);
            }
        });

        cOpinion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                isNet = ToolsUtil.isNet(context);
//                if (isNet) {
                    /* 弹出常用意见选择单选窗口 */
                final EditText edit = new EditText(context);
                edit.setHint(hint);
                edit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                edit.setMinLines(8);
                edit.setGravity(Gravity.TOP);

                AlertDialog ad = new AlertDialog.Builder(context)
                        .setTitle("常用意见")
                        .setView(edit)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String oText = edit.getText().toString();
                                //fillOpinionText.setText(oText); // 放开使用
                                //selectCommonOpinion = oText;// 放开使用
                                System.out.println("selectCommonOpinion:" + oText);
                                dialog.dismiss();
                            }
                        }).create();
                // commomOpinionListView = ad.getListView();
                ad.show();
//                }
            }
        });
    }
}
