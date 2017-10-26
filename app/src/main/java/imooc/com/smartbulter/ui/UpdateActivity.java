package imooc.com.smartbulter.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.daimajia.numberprogressbar.OnProgressBarListener;
import com.iflytek.cloud.thirdparty.O;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.ProgressListener;
import com.kymjs.rxvolley.http.VolleyError;
import com.kymjs.rxvolley.toolbox.FileUtils;

import java.io.File;

import imooc.com.smartbulter.R;
import imooc.com.smartbulter.utils.L;

import static java.lang.Long.getLong;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.ui
*文件名：UpdateActivity
*创建者：pavilion15
*创建时间：2017/9/11 15:45
*描述： 更新
*/
public class UpdateActivity extends BaseActivity {
    private TextView tv_update;
    private TextView tv_nofit;
    private NumberProgressBar mNumberProgressBar;
    //下载链接
    private String url;
    //文件下载路径
    private String path;
    //下载中
    private static  final int HANDLER_LOADING=1001;
    //下载完成
    private static  final int HANDLER_FINISH=1002;
    //下载失败
    private static  final int HANDLER_FAILURE=1003;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case HANDLER_LOADING:
                    //实时更新进度
                    Bundle bundle=msg.getData();
                    final long transferredBytes =bundle.getLong("transferredBytes");
                    final long totalSize=bundle.getLong("totalSize");
                    tv_update.setText(transferredBytes+"/"+totalSize);
                    //设置进度
                    mNumberProgressBar.setProgress((int) (((float) transferredBytes / (float) totalSize) * 100));
                    break;
                case HANDLER_FINISH:
                    //自动安装
                    startInstallApk();
                    tv_nofit.setText("下载成功");
                    tv_nofit.setVisibility(View.VISIBLE);
                    break;
                case HANDLER_FAILURE:
                    tv_nofit.setText("下载失败");
                    tv_nofit.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    //自动安装应用
    private void startInstallApk() {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
        startActivity(i);
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        initView();
    }

    private void initView() {

        tv_update= (TextView) findViewById(R.id.tv_update);
        tv_nofit= (TextView) findViewById(R.id.tv_nofit);
        tv_nofit.setVisibility(View.GONE);

        mNumberProgressBar= (NumberProgressBar) findViewById(R.id.mNumberProgressBar);
        mNumberProgressBar.setMax(100);

        path= FileUtils.getSDCardPath() +"/" + System.currentTimeMillis() + ".apk";
        url=getIntent().getStringExtra("url");
        //下载
        RxVolley.download(path, url, new ProgressListener() {
            @Override
            public void onProgress(long transferredBytes, long totalSize) {
               // L.i("transferredBytes"+transferredBytes);
                //L.i("totalSize"+totalSize);
                Message msg=new Message();
                Bundle bundle=new Bundle();
                msg.what=HANDLER_LOADING;
                bundle.putLong("transferredBytes",transferredBytes);
                bundle.putLong("totalSize",totalSize);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        }, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                handler.sendEmptyMessage(HANDLER_FINISH);
            }

            @Override
            public void onFailure(VolleyError error) {
                handler.sendEmptyMessage(HANDLER_FAILURE);
            }
        });
    }
}
