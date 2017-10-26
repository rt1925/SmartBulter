package imooc.com.smartbulter.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import imooc.com.smartbulter.R;
import imooc.com.smartbulter.utils.L;
import imooc.com.smartbulter.utils.StaticClass;

import static android.R.attr.breadCrumbShortTitle;
import static android.R.attr.key;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.ui
*文件名：PhoneActivity
*创建者：pavilion15
*创建时间：2017/8/26 20:09
*描述： 号码归属地查询
*/
public class PhoneActivity extends  BaseActivity implements View.OnClickListener {
    private Button btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_0,btn_del,btn_query;
    private EditText et_number;
    private ImageView iv_company;
    private TextView tv_result;
    private boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        initView();
    }

    private void initView() {
        btn_1= (Button) findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);
        btn_2= (Button) findViewById(R.id.btn_2);
        btn_2.setOnClickListener(this);
        btn_3= (Button) findViewById(R.id.btn_3);
        btn_3.setOnClickListener(this);
        btn_4= (Button) findViewById(R.id.btn_4);
        btn_4.setOnClickListener(this);
        btn_5= (Button) findViewById(R.id.btn_5);
        btn_5.setOnClickListener(this);
        btn_6= (Button) findViewById(R.id.btn_6);
        btn_6.setOnClickListener(this);
        btn_7= (Button) findViewById(R.id.btn_7);
        btn_7.setOnClickListener(this);
        btn_8= (Button) findViewById(R.id.btn_8);
        btn_8.setOnClickListener(this);
        btn_9= (Button) findViewById(R.id.btn_9);
        btn_9.setOnClickListener(this);
        btn_0= (Button) findViewById(R.id.btn_0);
        btn_0.setOnClickListener(this);
        btn_del= (Button) findViewById(R.id.btn_del);
        btn_del.setOnClickListener(this);
        //长按事件
        btn_del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                et_number.setText("");
                return  true;
            }
        });

        btn_query= (Button) findViewById(R.id.btn_query);
        btn_query.setOnClickListener(this);

        et_number= (EditText) findViewById(R.id.et_number);
        iv_company= (ImageView) findViewById(R.id.iv_company);

        tv_result= (TextView) findViewById(R.id.tv_result);


    }

    @Override
    public void onClick(View view) {
        String number=et_number.getText().toString();
        switch (view.getId()){
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
                //判断是否是第一次输入
                if(flag){
                    flag=false;
                    number="";
                    et_number.setText("");
                }
               //获取键盘上数字
                et_number.setText(number+((Button)view).getText());
               //光标向前
                et_number.setSelection(number.length()+1);
                break;
            case R.id.btn_del:
                //删除数据
                et_number.setText(number.substring(0,number.length()-1));
                et_number.setSelection(number.length()-1);
                break;
            case R.id.btn_query:
                //判断输入框是否为空
                if(!TextUtils.isEmpty(number)){
                    String uri="http://apis.juhe.cn/mobile/get?phone="+number+"&key="+ StaticClass.PHONE_KEY;
                    //请求数据
                    RxVolley.get(uri, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            L.i("t:"+t);
                            parsingJson(t);
                        }
                    });

                }else{
                    Toast.makeText(this, getString(R.string.alert_isempty), Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    //解析JSON
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONObject jsonResult=jsonObject.getJSONObject("result");
            String province=jsonResult.getString("province");
            String city=jsonResult.getString("city");
            String zip=jsonResult.getString("zip");
            String areacode=jsonResult.getString("areacode");
            String company="中国"+jsonResult.getString("company");
            tv_result.setText("归属地:"+province+city+"\n"+"区号:"+areacode+"\n"+"邮编:"+zip+"\n"+"营运商:"+company+"\n"+"类型:");
            flag=true;
            //设置图片
            switch (company){
                case "中国移动":
                    iv_company.setBackgroundResource(R.drawable.china_mobile);
                    break;
                case "中国联通":
                    iv_company.setBackgroundResource(R.drawable.china_unicom);
                    break;
                case "中国电信":
                    iv_company.setBackgroundResource(R.drawable.china_telecom);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
