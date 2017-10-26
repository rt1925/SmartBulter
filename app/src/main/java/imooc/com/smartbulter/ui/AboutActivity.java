package imooc.com.smartbulter.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import imooc.com.smartbulter.R;
import imooc.com.smartbulter.utils.UtilTools;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.ui
*文件名：AboutActivity
*创建者：pavilion15
*创建时间：2017/9/14 22:04
*描述： 版本信息
*/
public class AboutActivity extends BaseActivity {
    private CircleImageView profile_image;
    private ListView mListView;
    private List<String>mList=new ArrayList<>();
    private ArrayAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initView();
    }

    private void initView() {
        profile_image= (CircleImageView) findViewById(R.id.profile_image);
        mListView= (ListView) findViewById(R.id.mListView);

        profile_image.setBorderWidth(0);

        getSupportActionBar().setElevation(0);

        mList.add("应用名:"+ getString(R.string.app_name));
        mList.add(getString(R.string.app_version_zh)+":"+UtilTools.getVersionCode(this));

        mAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,mList);
        mListView.setAdapter(mAdapter);

    }
}
