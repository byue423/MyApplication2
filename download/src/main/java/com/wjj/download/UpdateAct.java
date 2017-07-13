package com.wjj.download;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.hnpolice.xiaoke.downloadfile.R;
import com.wjj.download.checkversion.CheckVerUtil;
import com.wjj.download.entity.DownloadFile;
import com.wjj.download.entity.Version;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjj on 2017/6/19.
 */

public class UpdateAct extends Activity {

    String url = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button)findViewById(R.id.start);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Version ver = new Version();
                ver.setApkUrl(url);
                ver.setIsForce("0");
                ver.setVerName("2");
                ver.setVerCode("2.1.2");
                ver.setUpdateMsg("1.android增加问卷调查功能;\n 2.增加更新提示功能;\n 3.修复xxxbug问题;");

                List<DownloadFile> dList = new ArrayList<>();
                int downloadType = 0;
                DownloadUIImpl ui = new DownloadUIImpl(UpdateAct.this, dList , 1);
                final DownloadHandler dhandler = new DownloadHandler(ui);

                CheckVerUtil checkVerUtil = new CheckVerUtil(UpdateAct.this, dhandler, ver);
            }
        });
    }
}
