package com.example.cuboo.mtablay;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new_tab();
    }
    private void new_tab(){
        tabLayout = (TabLayout) findViewById(R.id.tab);
        //tabLayout.addTab(tabLayout.newTab().setText("周边").setIcon(R.drawable.location));
        //tabLayout.addTab(tabLayout.newTab().setText("路线").setIcon(R.drawable.route));
        // tabLayout.addTab(tabLayout.newTab().setText("导航").setIcon(R.drawable.orient));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tab_icon("周边",R.drawable.location)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tab_icon("路线",R.drawable.route)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tab_icon("导航",R.drawable.orient)));

    }
    private View tab_icon(String name,int iconID){
        View newtab =  LayoutInflater.from(this).inflate(R.layout.icon_view,null);
        TextView tv = (TextView) newtab.findViewById(R.id.tabtext);
        tv.setText(name);
        ImageView im = (ImageView)newtab.findViewById(R.id.tabicon);
        im.setImageResource(iconID);
        return newtab;
    }


}
