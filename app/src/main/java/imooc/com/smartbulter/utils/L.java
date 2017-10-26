package imooc.com.smartbulter.utils;

import android.util.Log;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.utils
*文件名：L
*创建者：pavilion15
*创建时间：2017/8/23 1:55
*描述： Log的封装类
*/
public class L {
    //开关
    public static final Boolean DEBUG=true;
    //TAG
    public static final String TAG="SmartBulter";
    //四个等级
    public static void d(String text){
        if(DEBUG){
            Log.d(TAG,text);
        }
    }
    public static void e(String text){
        if(DEBUG){
            Log.e(TAG,text);
        }
    }
    public static void w(String text){
        if(DEBUG){
            Log.w(TAG,text);
        }
    }
    public static void i(String text){
        if(DEBUG){
            Log.i(TAG,text);
        }
    }
}
