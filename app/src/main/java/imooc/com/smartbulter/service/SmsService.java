package imooc.com.smartbulter.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import imooc.com.smartbulter.R;
import imooc.com.smartbulter.utils.L;
import imooc.com.smartbulter.utils.StaticClass;

import static com.iflytek.sunflower.config.b.l;


/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.service
*文件名：SmsService
*创建者：pavilion15
*创建时间：2017/9/6 20:17
*描述： 智能短信
*/
public class SmsService extends Service implements View.OnClickListener {
    private IntentFilter intentFilter;
    private SmSReceiver smsReceiver;
    //联系人号码
    private String smsPhone;
    //短信内容
    private String smsContent;
    private WindowManager wm;
    private WindowManager.LayoutParams layoutParams;
    //自定義佈局
    private View mview;

    private TextView tv_sms_phone,tv_sms_content;
    private Button btn_sms_send;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();

    }

    private void init() {
        //L.i("init service");
        intentFilter=new IntentFilter();
        intentFilter.addAction(StaticClass.SMS_ACTION);
        //设置权限
        intentFilter.setPriority(Integer.MAX_VALUE);
        smsReceiver=new SmSReceiver();
        //注册Rceiver
        registerReceiver(smsReceiver,intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       //L.i("stop service");
        //取消注册
        unregisterReceiver(smsReceiver);
    }



    class SmSReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String sms=intent.getAction();
            if(sms.equals(StaticClass.SMS_ACTION)){
                L.i("短信来了");

                //获取短信内容是一个object数组
                Object[] objs= (Object[]) intent.getExtras().get("pdus");
                for(Object obj:objs){
                    SmsMessage smsMessage=SmsMessage.createFromPdu((byte[])obj);
                    //发信人
                    smsPhone=smsMessage.getOriginatingAddress();
                    //内容
                    smsContent=smsMessage.getMessageBody();
                    L.i("发信人："+smsPhone+" 内容："+smsContent);
                    showWindow();

                }
            }

        }
    }

    private void showWindow() {
        L.i("showWindow开始");
        //获取系统服务
        wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //获取布局参数
        layoutParams = new WindowManager.LayoutParams();
        //定义宽高
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        //定义标记
        layoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        //定义格式
        layoutParams.format = PixelFormat.TRANSLUCENT;
        //定义类型
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //加载布局
        mview =  View.inflate(getApplicationContext(), R.layout.sms_item, null);

        tv_sms_content=mview.findViewById(R.id.tv_sms_content);
        tv_sms_phone=mview.findViewById(R.id.tv_sms_phone);
        btn_sms_send=mview.findViewById(R.id.btn_sms_send);
        btn_sms_send.setOnClickListener(this);

        tv_sms_phone.setText("联系人:"+smsPhone);
        tv_sms_content.setText(smsContent);
        //添加佈局
        L.i("加载布局");
        wm.addView(mview,layoutParams);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sms_send:
                sendSmS();
                //消失窗口
               if(mview.getParent()!=null){
                   wm.removeView(mview);
               }
               break;
        }
    }

    private void sendSmS() {
        Uri uri=Uri.parse("smsto"+smsPhone);
        Intent intent= new Intent(Intent.ACTION_SENDTO,uri);
        //设置启动模式
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("smsbody","");
        startActivity(intent);
    }
}
