package imooc.com.smartbulter.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.github.chrisbanes.photoview.PhotoView;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import imooc.com.smartbulter.R;
import imooc.com.smartbulter.adapter.GirlAdapter;
import imooc.com.smartbulter.entity.GirlData;
import imooc.com.smartbulter.utils.PicassoUtils;
import imooc.com.smartbulter.utils.StaticClass;
import imooc.com.smartbulter.view.CustomDialog;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.ui
*文件名：GirlActivity
*创建者：pavilion15
*创建时间：2017/9/19 23:40
*描述： TODO
*/
public class GirlActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private GridView mGridView;
    private List<GirlData> mList=new ArrayList<>();
    private GirlAdapter adapter;
    private CustomDialog dialog;
    private List<String> mListUrl=new ArrayList<>();
    private PhotoView photo_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girl);
        initView();
    }

    private void initView() {
        mGridView = (GridView)findViewById(R.id.mGrivView);
        mGridView.setOnItemClickListener(this);
        //初始化dialog
        dialog=new CustomDialog(this,LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,R.layout.girl_dialog,
                R.style.Theme_Dialog, Gravity.CENTER,R.style.pop_anim_style);
        //屏幕外点击无效
        dialog.setCanceledOnTouchOutside(true);

        photo_view= (PhotoView) dialog.findViewById(R.id.photo_view);

        String url="http://api.tianapi.com/meinv/?key="+ StaticClass.GRIL_KEY+"&num="+50;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //L.i("json"+t);
                //解析数据
                parsingJson(t);
            }
        });
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONArray jsonArray=jsonObject.getJSONArray("newslist");
            for(int i=0;i<jsonArray.length();i++){
                GirlData data=new GirlData();
                JSONObject json= (JSONObject) jsonArray.get(i);

                String url=json.getString("picUrl");
                data.setUrl(url);
                mListUrl.add(url);
                //L.i(data.getUrl());
                mList.add(data);

                adapter=new GirlAdapter(this,mList);
                mGridView.setAdapter(adapter);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //解析图片
        PicassoUtils.loadImageView(this,mListUrl.get(i),photo_view);
        //缩放
        dialog.show();
    }
}
