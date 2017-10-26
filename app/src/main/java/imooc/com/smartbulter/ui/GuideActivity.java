package imooc.com.smartbulter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import imooc.com.smartbulter.MainActivity;
import imooc.com.smartbulter.R;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.ui
*文件名：GuideActivity
*创建者：pavilion15
*创建时间：2017/8/23 0:36
*描述： 引导页
*/
public class GuideActivity extends AppCompatActivity implements View.OnClickListener {
        private ViewPager mViewPager;
        private View view1,view2,view3;
        private List<View> mList=new ArrayList<View>();
    //小圆点
    private ImageView point1,point2,point3;
    //进入首页
    private Button btn_start;
    //跳过
    private ImageView iv_back;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_guide);
            intiView();
        }

        private void intiView() {

            iv_back=(ImageView )findViewById(R.id.iv_back);
            //iv_back.setOnClickListener(this);

            point1=(ImageView )findViewById(R.id.point1);
            point2=(ImageView )findViewById(R.id.point2);
            point3=(ImageView )findViewById(R.id.point3);



            //设置默认图片
            setPointImg(true,false,false);

            mViewPager = (ViewPager) findViewById(R.id.mViewPager);
            view1=view1.inflate(this, R.layout.page_item_one, null);
            view2=view2.inflate(this, R.layout.page_item_two, null);
            view3=view3.inflate(this, R.layout.page_item_three, null);
            view3.findViewById(R.id.btn_start).setOnClickListener(this);


            mList.add(view1);
            mList.add(view2);
            mList.add(view3);

            //设置适配器
            mViewPager.setAdapter(new GuideAdapter());

            //监听ViewPager
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }
                //pager切换
                @Override
                public void onPageSelected(int position) {
                    switch (position){
                        case 0:
                            setPointImg(true,false,false);
                            //iv_back.setVisibility(View.VISIBLE);
                            break;
                        case 1:
                            setPointImg(false,true,false);
                            //iv_back.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            setPointImg(false,false,true);
                            //iv_back.setVisibility(View.GONE);
                            break;
                        default:
                            break;

                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
             case R.id.btn_start:
            startActivity(new Intent(this, LoginActivity.class));
                 finish();
            break;
        }
    }

    class GuideAdapter extends PagerAdapter{

                    @Override
                    public int getCount() {
                    // TODO Auto-generated method stub
                    return mList.size();
                }

                    @Override
                    public boolean isViewFromObject(View view, Object object) {
                    // 滑动对比
                    return view==object;
                }
                    //删除item
                    @Override
                    public void destroyItem(ViewGroup container, int position, Object object){
                    ((ViewPager)container).removeView(mList.get(position));
                }
                    //添加item
                    @Override
                    public Object instantiateItem(ViewGroup container, int position) {
                    ((ViewPager)container).addView(mList.get(position));
                    return mList.get(position);
                }


                }
                //判断point
                private void setPointImg(boolean isCheck1,boolean isCheck2,boolean isCheck3){
                    if(isCheck1){
                        point1.setImageDrawable(getDrawable(R.drawable.point_on));
                    }else{
                        point1.setImageDrawable(getDrawable(R.drawable.point_off));
                    }
                    if(isCheck2){
                        point2.setImageDrawable(getDrawable(R.drawable.point_on));
                    }else{
                        point2.setImageDrawable(getDrawable(R.drawable.point_off));
                    }
                    if(isCheck3){
                        point3.setImageDrawable(getDrawable(R.drawable.point_on));
                    }else{
                        point3.setImageDrawable(getDrawable(R.drawable.point_off));
                    }
                }

            }



