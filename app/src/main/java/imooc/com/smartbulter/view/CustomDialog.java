package imooc.com.smartbulter.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import imooc.com.smartbulter.R;
import imooc.com.smartbulter.utils.L;

import static com.tencent.bugly.crashreport.crash.c.l;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.view
*文件名：CustomDialog
*创建者：pavilion15
*创建时间：2017/8/24 23:08
*描述： 自定义dialog
*/
public class CustomDialog  extends Dialog {
    //定义模板
    public CustomDialog(Context context, int layout ,int style) {
       this(context, WindowManager.LayoutParams.WRAP_CONTENT,
               WindowManager.LayoutParams.WRAP_CONTENT,layout,style, Gravity.CENTER);
    }
    //定义属性
    public CustomDialog(Context context,int width,int height,int layout,int sytle,int anim,int gravity) {
        super(context, sytle);
        //设置属性
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams LayoutParams = window.getAttributes();
        LayoutParams.width = width;
        LayoutParams.height = height;
        LayoutParams.gravity = gravity;
        window.setAttributes(LayoutParams);
        window.setWindowAnimations(anim);
    }
    //实例
    public CustomDialog(Context context,int width,int height,int layout,int sytle,int gravity){
        this(context,width,height,layout,sytle,R.style.pop_anim_style,gravity);
    }
}


