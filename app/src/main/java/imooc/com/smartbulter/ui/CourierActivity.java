package imooc.com.smartbulter.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.internal.bind.CollectionTypeAdapterFactory;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.toolbox.Loger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import imooc.com.smartbulter.R;
import imooc.com.smartbulter.adapter.CourierAdapter;
import imooc.com.smartbulter.entity.CourierData;
import imooc.com.smartbulter.utils.L;
import imooc.com.smartbulter.utils.StaticClass;

import static android.R.attr.data;
import static android.R.attr.key;
import static android.R.string.no;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.ui
*文件名：CourierActivity
*创建者：pavilion15
*创建时间：2017/8/26 15:17
*描述： 快递查询
*/
public class CourierActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_courier_name,et_courier_num;
    private Button btn_courier;
    private ListView mListView;
    private List<CourierData> mList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);

        initView();
    }

    private void initView() {
        et_courier_name= (EditText) findViewById(R.id.et_courier_name);
        et_courier_num= (EditText) findViewById(R.id.et_courier_num);
        btn_courier= (Button) findViewById(R.id.btn_courier);
        mListView= (ListView) findViewById(R.id.mListView);

        btn_courier.setOnClickListener(this);

}

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_courier:
                /**
                 * 1.获取输入框的内容
                 * 2.判断是否为空
                 * 3.拿到数据去请求数据（Json）
                 * 4.解析Json
                 * 5.listview适配器
                 * 6.实体类（item）
                 * 7.设置数据/显示效果
                 */
                // 1.获取输入框的内容
                 String name=et_courier_name.getText().toString().trim();
                 String num=et_courier_num.getText().toString().trim();
                //2.判断是否为空
                if(!TextUtils.isEmpty(name)&!TextUtils.isEmpty(num)){
                    final String uri="http://v.juhe.cn/exp/index?key="+ StaticClass.COURIER_KEY+"&com="+name+"&no="+num;
                    //3.拿到数据去请求数据（Json）
                    RxVolley.get(uri, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            //4.解析Json
                            parsingJson(t);
                            L.i(t);
                        }
                    });
                }else{
                    Toast.makeText(this, getString(R.string.alert_isempty), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);

                CourierData data = new CourierData();
                data.setRemark(json.getString("remark"));
                data.setZone(json.getString("zone"));
                data.setDataTime(json.getString("datetime"));
                mList.add(data);
            }
            //倒序
            Collections.reverse(mList);

            CourierAdapter adapter = new CourierAdapter(this,mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
