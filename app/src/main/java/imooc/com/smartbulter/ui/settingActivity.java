package imooc.com.smartbulter.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

import org.json.JSONException;
import org.json.JSONObject;

import imooc.com.smartbulter.MainActivity;
import imooc.com.smartbulter.R;
import imooc.com.smartbulter.service.SmsService;
import imooc.com.smartbulter.utils.L;
import imooc.com.smartbulter.utils.ShareUtils;
import imooc.com.smartbulter.utils.StaticClass;
import imooc.com.smartbulter.utils.UtilTools;

import static com.iflytek.speech.Version.getVersionName;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.ui
*文件名：settingActivity
*创建者：pavilion15
*创建时间：2017/8/23 1:21
*描述： 设置
*/
public class settingActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout ll_update,ll_about;
    private TextView tv_version,tv_about_version;
    //版本号
    private String VersionName;
    //版本代码
    private int VersionCode;
    private String url;
    private String content;
    //提示框
    private AlertDialog.Builder dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        iniView();
    }

    private void iniView() {
        tv_version= (TextView) findViewById(R.id.tv_version);
        tv_about_version= (TextView) findViewById(R.id.tv_about_version);
        dialog=new AlertDialog.Builder(settingActivity.this);
        ll_update=(LinearLayout)findViewById(R.id.ll_update);
        ll_update.setOnClickListener(this);
        ll_about= (LinearLayout) findViewById(R.id.ll_about);
        ll_about.setOnClickListener(this);

        boolean isSms = ShareUtils.getBoolean(this, "isSms", true);

        //获取版本信息
        VersionCode= UtilTools.getVersionCode(this);
        VersionName= UtilTools.getVersionName(this);
        tv_about_version.setText("当前版本为："+VersionName);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.ll_update:
                /*
                * 1.请求数据
                * 2.比较
                * 3.弹出提示框
                * 4.跳转到更新页面
                * */
                RxVolley.get(StaticClass.UPDATE_URL, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        L.i("json"+t);
                        parsingJson(t);
                    }
                });
                break;
            case R.id.ll_about:
                startActivity(new Intent(this,AboutActivity.class));
                break;
        }
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject=new JSONObject(t);
            int code=jsonObject.getInt("VersionCode");
            
            if(code>VersionCode){
                showAlertDialog();
            }else{
                Toast.makeText(this, "当前是最新版本", Toast.LENGTH_SHORT).show();
            }
            
            VersionName=jsonObject.getString("VersionName");
            content=jsonObject.getString("content");
            url=jsonObject.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void showAlertDialog() {
        dialog.setTitle("检测到有新版本更新")
                .setMessage(content)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(settingActivity.this,UpdateActivity.class);
                        intent.putExtra("url",url);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                }).show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            Toast.makeText(this, scanResult, Toast.LENGTH_SHORT).show();
        }
    }
}
