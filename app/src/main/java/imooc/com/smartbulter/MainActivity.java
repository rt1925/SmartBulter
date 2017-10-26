package imooc.com.smartbulter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import imooc.com.smartbulter.fragment.MapFragment;
import imooc.com.smartbulter.fragment.NewFragment;
import imooc.com.smartbulter.fragment.ServiceFragment;
import imooc.com.smartbulter.fragment.SocialFragment;
import imooc.com.smartbulter.fragment.UserFragment;
import imooc.com.smartbulter.ui.QrResultActivity;
import imooc.com.smartbulter.utils.L;
import imooc.com.smartbulter.view.MainViewPager;




public class MainActivity extends AppCompatActivity implements  View.OnClickListener {
    private TabLayout mTabLayout;
    //ViewPager
    private MainViewPager mViewPager ;
    //Fragment
    private List<Fragment> mFragment;
    //ToolBar
    private Toolbar mToobar;
    private ImageView iv_scan,iv_icon;
    private TextView tv_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiData();
        initView();
    }

    //初始化数据
    private void intiData() {

        mFragment = new ArrayList<Fragment>();
        mFragment.add(new ServiceFragment());
        mFragment.add(new MapFragment());
        mFragment.add(new NewFragment());
        mFragment.add(new UserFragment());

    }

    //初始化view
    private void initView() {
        mTabLayout= (TabLayout) findViewById(R.id.mTabLayout);
        iv_scan= (ImageView) findViewById(R.id.iv_scan);
        iv_scan.setOnClickListener(this);
        mToobar= (Toolbar) findViewById(R.id.mToobar);


        mViewPager = (MainViewPager) findViewById(R.id.mViewPager);
        //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());
        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            //选中的item
            @Override
            public Fragment getItem(int postion) {
                return mFragment.get(postion);
            }

            //返回item的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }

        });

       mTabLayout.setupWithViewPager(mViewPager);
        new_tab();
    }
    private void new_tab(){
        mTabLayout.getTabAt(0).setCustomView((tab_icon("生活",R.drawable.life)));
        mTabLayout.getTabAt(1).setCustomView((tab_icon("地图",R.drawable.map)));
        mTabLayout.getTabAt(2).setCustomView((tab_icon("新闻",R.drawable.news)));
        mTabLayout.getTabAt(3).setCustomView((tab_icon("我的",R.drawable.my)));
        //Tablayout自定义view绑定ViewPager
        //mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        //mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }
    private View tab_icon(String name,int iconID){
        View newtab =  LayoutInflater.from(this).inflate(R.layout.icon_view,null);
        TextView tv_icon = (TextView) newtab.findViewById(R.id.tv_icon);
        tv_icon.setText(name);
        ImageView iv_icon = (ImageView)newtab.findViewById(R.id.iv_icon);
        iv_icon.setImageResource(iconID);
        return newtab;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.iv_scan:
                Intent openCameraIntent = new Intent(this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent ResultData) {
        if (resultCode == RESULT_OK) {
            Bundle bundle = ResultData.getExtras();
            String scanResult = bundle.getString("result");
            Intent intent=new Intent(MainActivity.this,QrResultActivity.class);
            intent.putExtra("result",scanResult);
            startActivity(intent);
        }
    }

}

