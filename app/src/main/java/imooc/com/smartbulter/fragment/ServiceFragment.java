package imooc.com.smartbulter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import imooc.com.smartbulter.R;
import imooc.com.smartbulter.entity.WeatherData;
import imooc.com.smartbulter.ui.ButleActivity;
import imooc.com.smartbulter.ui.CourierActivity;
import imooc.com.smartbulter.ui.FoodActivity;
import imooc.com.smartbulter.ui.GirlActivity;
import imooc.com.smartbulter.ui.PhoneActivity;
import imooc.com.smartbulter.utils.L;
import imooc.com.smartbulter.utils.StaticClass;

import static android.R.attr.data;
import static imooc.com.smartbulter.utils.StaticClass.TIANQI_DAILY_WEATHER_URL;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.fragment
*文件名：ServiceFragment
*创建者：pavilion15
*创建时间：2017/9/16 23:40
*描述： 服务
*/
public class ServiceFragment extends Fragment implements View.OnClickListener {
    private LinearLayout ll_courier,ll_phone,ll_food,ll_lottery,ll_robot,ll_girl;
    private TextView tv_whather_text,tv_whather_temperature,tv_whather_last_update,tv_whather_path;
    private List<WeatherData> list=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.fragment_bulter,null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        ll_courier=(LinearLayout) view.findViewById(R.id.ll_courier);
        ll_phone=(LinearLayout) view.findViewById(R.id.ll_phone);
        ll_food=(LinearLayout) view.findViewById(R.id.ll_food);
        ll_lottery=(LinearLayout) view.findViewById(R.id.ll_lottery);
        ll_robot=(LinearLayout) view.findViewById(R.id.ll_robot);
        ll_girl=(LinearLayout) view.findViewById(R.id.ll_girl);
        tv_whather_text=(TextView) view.findViewById(R.id.tv_whather_text);
        tv_whather_temperature=(TextView) view.findViewById(R.id.tv_whather_temperature);
        tv_whather_last_update=(TextView) view.findViewById(R.id.tv_whather_last_update);
        tv_whather_path=(TextView) view.findViewById(R.id.tv_whather_path);



        ll_courier.setOnClickListener(this);
        ll_phone.setOnClickListener(this);
        ll_food.setOnClickListener(this);
        ll_lottery.setOnClickListener(this);
        ll_robot.setOnClickListener(this);
        ll_girl.setOnClickListener(this);
        //天气
        String city="xian";
        String url="https://api.seniverse.com/v3/weather/now.json?key="+ StaticClass.WEATHER_KEY+
                "&location=xian&language=zh-Hans&unit=c";
        RxVolley.get(url, new HttpCallback() {

            @Override
            public void onSuccess(String t) {
                // L.e(t);
                parsingJson(t);
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_courier:
                startActivity(new Intent(getActivity(),CourierActivity.class));
                break;
            case R.id.ll_phone:
                startActivity(new Intent(getActivity(), PhoneActivity.class));
                break;
            case R.id.ll_food:
                startActivity(new Intent(getActivity(), FoodActivity.class));
                break;
            case R.id.ll_lottery:

                break;
            case R.id.ll_robot:
                startActivity(new Intent(getActivity(), ButleActivity.class));
                break;
            case R.id.ll_girl:
                startActivity(new Intent(getActivity(), GirlActivity.class));
                break;

        }
    }

    //results->now + lcoation + last_update
    private void parsingJson(String t) {

        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONArray  jsonArray=jsonObject.getJSONArray("results");

            for(int i=0;i<jsonArray.length();i++){
                JSONObject json= (JSONObject) jsonArray.get(i);
                //last_update
                String last_update=json.getString("last_update");
                tv_whather_last_update.setText(last_update);
                //L.e(last_update);
                //now
                JSONObject jsonNow= json.getJSONObject("now");
                String text=jsonNow.getString("text");
                String temperature=jsonNow.getString("temperature");
               tv_whather_temperature.setText(temperature);
                tv_whather_text.setText(text);
                //L.e(text);
                //L.e(temperature);
                //location
                JSONObject jsonLocation=json.getJSONObject("location");
                String path=jsonLocation.getString("path");
                tv_whather_path.setText(path);


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
