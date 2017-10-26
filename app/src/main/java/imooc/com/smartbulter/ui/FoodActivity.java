package imooc.com.smartbulter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.iflytek.cloud.thirdparty.C;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import imooc.com.smartbulter.R;
import imooc.com.smartbulter.adapter.FoodAdapter;
import imooc.com.smartbulter.entity.FoodData;
import imooc.com.smartbulter.utils.L;
import imooc.com.smartbulter.utils.StaticClass;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.media.CamcorderProfile.get;
import static com.kymjs.rxvolley.RxVolley.ContentType.JSON;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.ui
*文件名：FoodActivity
*创建者：pavilion15
*创建时间：2017/9/20 18:36
*描述： 菜谱
*/
public class FoodActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private FoodAdapter adapter;
    private ListView mListView;
    private List<FoodData>mlist=new ArrayList<>();
    private Button btn_food;
    private EditText et_food;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        initView();
    }

    private void initView() {
        mListView= (ListView) findViewById(R.id.mListView);
        btn_food= (Button) findViewById(R.id.btn_food);
        et_food= (EditText) findViewById(R.id.et_food);

        btn_food.setOnClickListener(this);
        mListView.setOnItemClickListener(this);

    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONArray jsonArray=jsonObject.getJSONArray("newslist");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject json=jsonArray.getJSONObject(i);
                FoodData data=new FoodData();
                String cp_name=json.getString("cp_name");
               data.setCp_name(cp_name);
                String type_name=json.getString("type_name");
                data.setType_name(type_name);
                String zuofa=json.getString("zuofa");
                data.setZuofa(zuofa);
                String texing=json.getString("texing");
                data.setTexing(texing);
                String tishi=json.getString("tishi");
                data.setTishi(tishi);
                String tiaoliao=json.getString("tiaoliao");
                data.setTiaoliao(tiaoliao);
                String yuanliao=json.getString("yuanliao");
                data.setYuanliao(yuanliao);
                mlist.add(data);

            }
            adapter=new FoodAdapter(this,mlist);
            mListView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(this,FoodDetailActivity.class);
        intent.putExtra("title",mlist.get(i).getCp_name());
        intent.putExtra("tiaoliao",mlist.get(i).getTiaoliao());
        intent.putExtra("yuanliao",mlist.get(i).getYuanliao());
        intent.putExtra("tishi",mlist.get(i).getTishi());
        intent.putExtra("zuofa",mlist.get(i).getZuofa());

        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_food:
                mlist.clear();
                String food=et_food.getText().toString();
                String url="http://api.tianapi.com/txapi/caipu/?key="+ StaticClass.GRIL_KEY+
                        "&num=10&word="+food;
                RxVolley.get(url, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        L.e("食材成功");
                        parsingJson(t);
                    }
                });

                break;
        }
    }
}
