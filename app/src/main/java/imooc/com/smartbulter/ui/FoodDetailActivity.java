package imooc.com.smartbulter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import imooc.com.smartbulter.R;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.ui
*文件名：FoodDetailActivity
*创建者：pavilion15
*创建时间：2017/9/20 23:18
*描述： 菜谱详情
*/
public class FoodDetailActivity extends BaseActivity {
    private TextView tv_food_title,tv_food_yuanliao,tv_food_tiaoliao,tv_food_zuofa,tv_food_tishi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        intiView();
    }

    private void intiView() {
        tv_food_title= (TextView) findViewById(R.id.tv_food_title);
        tv_food_yuanliao= (TextView) findViewById(R.id.tv_food_yuanliao);
        tv_food_tiaoliao= (TextView) findViewById(R.id.tv_food_tiaoliao);
        tv_food_zuofa= (TextView) findViewById(R.id.tv_food_zuofa);
        tv_food_tishi= (TextView) findViewById(R.id.tv_food_tishi);

        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        String tiaoliao=intent.getStringExtra("tiaoliao");
        String yuanliao=intent.getStringExtra("yuanliao");
        String tishi=intent.getStringExtra("tishi");
        String zuofa=intent.getStringExtra("zuofa");

        //设置标题
        getSupportActionBar().setTitle(title);

        tv_food_title.setText(title);
        tv_food_yuanliao.setText(yuanliao);
        tv_food_tiaoliao.setText(tiaoliao);
        tv_food_zuofa.setText(zuofa);
        tv_food_tishi.setText(tishi);

    }
}
