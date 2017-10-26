package imooc.com.smartbulter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import imooc.com.smartbulter.R;
import imooc.com.smartbulter.adapter.NewAdapter;
import imooc.com.smartbulter.entity.NewData;
import imooc.com.smartbulter.ui.WeChatActivity;
import imooc.com.smartbulter.utils.StaticClass;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.fragment
*文件名：ITFragment
*创建者：pavilion15
*创建时间：2017/9/23 17:32
*描述： IT资讯
*/
public class ITFragment extends Fragment {
    private ListView mListView;
    private NewAdapter adapter;
    private List<NewData> mList=new ArrayList<>();
    private List<String> mListTitle=new ArrayList<>();
    private List<String>mListUrl=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_social,null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        mListView=(ListView)view.findViewById(R.id.mListView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(),WeChatActivity.class);
                intent.putExtra("url",mListUrl.get(i));
                intent.putExtra("title",mListTitle.get(i));
                startActivity(intent);
            }
        });


        String url="http://api.tianapi.com/it/?key="+ StaticClass.GRIL_KEY+"&num=30";
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //L.i(t);
                parsingJson(t);
            }
        });
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONArray jsonArray=jsonObject.getJSONArray("newslist");

            for(int i=0;i<jsonArray.length();i++){
                JSONObject json= (JSONObject) jsonArray.get(i);
                NewData data= new NewData();

                String title=json.getString("title");
                data.setTitle(title);
                String url=json.getString("url");
                data.setUrl(url);
                data.setDescription(json.getString("description"));
                data.setPicUrl(json.getString("picUrl"));

                mList.add(data);
                mListTitle.add(title);
                mListUrl.add(url);
            }
            adapter=new NewAdapter(mList,getActivity());
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
