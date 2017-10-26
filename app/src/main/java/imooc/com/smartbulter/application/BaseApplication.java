package imooc.com.smartbulter.application;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;
import imooc.com.smartbulter.utils.StaticClass;

import static imooc.com.smartbulter.utils.StaticClass.BMOB_APPLICATION_ID;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.application
*文件名：BaseApplication
*创建者：pavilion15
*创建时间：2017/8/23 21:30
*描述： application
*/
public class BaseApplication extends Application {
    @Override
    public  void onCreate(){
        super.onCreate();
        //初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);
        //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_APPLICATION_ID);
        // 请勿在“=”与appid之间添加任何空字符或者转义符
       // SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID +"="+StaticClass.VIOCE_KEY);

    }
}
