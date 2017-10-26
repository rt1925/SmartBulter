package imooc.com.smartbulter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import imooc.com.smartbulter.MainActivity;
import imooc.com.smartbulter.R;
import imooc.com.smartbulter.utils.ShareUtils;
import imooc.com.smartbulter.utils.StaticClass;
import imooc.com.smartbulter.utils.UtilTools;

/**
 * Created by pavilion15 on 2017/8/23.
 */

public class SplashActivity extends AppCompatActivity {
    /**
     * 1.延时2s 2.判断是否第一次运行 3.自定义字体 4.activity全屏主题
     **/

    private TextView tv_splash;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.HANDLER_SPLASH:
                    if(isFirst()){
                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                    }else
                    {
                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    }
                    finish();
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        intiView();
    }

    private void intiView() {
        // 延时2s
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 2000);
        tv_splash = (TextView) findViewById(R.id.tv_splash);
        UtilTools.setFont(this, tv_splash);

    }
    //判断是否第一次运行
    private boolean isFirst() {
        boolean isFirst= ShareUtils.getBoolean(this, StaticClass.SHARE_IS_FIRST, true);
        if(isFirst){
            ShareUtils.putBoolean(this, StaticClass.SHARE_IS_FIRST, false);
            return true;
        }else {
            return false;
        }

    }
    //禁止返回键
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

}
