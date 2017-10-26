package imooc.com.smartbulter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import imooc.com.smartbulter.R;
import imooc.com.smartbulter.utils.L;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.fragment
*文件名：NewFragment
*创建者：pavilion15
*创建时间：2017/9/23 17:02
*描述： 新闻
*/
public class NewFragment extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    //Title
    private List<String> mTitle;
    //Fragment
    private List<Fragment> mFragment;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_new,null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        mTabLayout=(TabLayout) view.findViewById(R.id.mTabLayout);
        mViewPager=(ViewPager)view.findViewById(R.id.mViewPager);

        mTitle=new ArrayList<>();
        mTitle.add("社会");
        mTitle.add("国际");
        mTitle.add("娱乐");
        mTitle.add("科技");
        mTitle.add("军事");
        mTitle.add("手机");
        mTitle.add("旅游");
        mTitle.add("健康");
        mTitle.add("奇闻");
        mTitle.add("IT");


        mFragment=new ArrayList<>();
        mFragment.add(new SocialFragment());
        mFragment.add(new InternationalFragment());
        mFragment.add(new EntertainmentFragment());
        mFragment.add(new ScienceFragment());
        mFragment.add(new MilitaryFragment());
        mFragment.add(new MobileFragment());
        mFragment.add(new TouristFragment());
        mFragment.add(new HealthFragment());
        mFragment.add(new AnecdotesFragment());
        mFragment.add(new ITFragment());
        //多层viewpager+fragment嵌套时，要把setAdapter中的getFragmentManager换成getChildFragmentManager，
        //否则它还是父viewpager的fragmentManager
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });

        //绑定
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
