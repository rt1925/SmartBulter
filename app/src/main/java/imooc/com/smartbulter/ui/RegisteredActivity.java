package imooc.com.smartbulter.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import imooc.com.smartbulter.R;
import imooc.com.smartbulter.entity.MyUser;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.tencent.bugly.crashreport.crash.c.e;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.ui
*文件名：RegisteredActivity
*创建者：pavilion15
*创建时间：2017/8/24 0:38
*描述： 注册
*/
public class RegisteredActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_name, password1, password2;
    private EditText et_age;
    private EditText et_mtail;
    private EditText et_desc;
    private Button btn_registered;
    private RadioGroup mRadioGroup;
    private boolean isGender = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        initView();
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_age = (EditText) findViewById(R.id.et_age);
        et_desc = (EditText) findViewById(R.id.desc);
        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
        password1 = (EditText) findViewById(R.id.password1);
        password2 = (EditText) findViewById(R.id.password2);
        et_mtail = (EditText) findViewById(R.id.et_mtail);
        btn_registered = (Button) findViewById(R.id.btnregistered);

        btn_registered.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnregistered:
                //获取输入框值
                String name = et_name.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String desc = et_desc.getText().toString().trim();
                String pass = password1.getText().toString();
                String password = password2.getText().toString();
                String mtail = et_mtail.getText().toString().trim();
                //输入框是否为空
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(age) & !TextUtils.isEmpty(pass)
                        & !TextUtils.isEmpty(password) & !TextUtils.isEmpty(mtail)) {

                    //密码是否一致
                    if (pass.equals(password)) {

                        //判断性别
                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int CheckedId) {
                                if (CheckedId == R.id.et_boy) {
                                    isGender = true;
                                    Toast.makeText(RegisteredActivity.this, "男", Toast.LENGTH_SHORT).show();
                                }
                                if (CheckedId == R.id.et_girl) {
                                    isGender = false;
                                    Toast.makeText(RegisteredActivity.this, "女", Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(RegisteredActivity.this, "判断性别成功", Toast.LENGTH_SHORT).show();
                            }

                        });

                        //判断简介
                        if (TextUtils.isEmpty(desc)) {
                            desc = ("这个人很懒，什么都没有留下");
                        }

                        //注册
                        MyUser myUser = new MyUser();
                        myUser.setUsername(name);
                        myUser.setPassword(password);
                        myUser.setDesc(desc);
                        myUser.setAge(Integer.parseInt(age));
                        myUser.setSex(isGender);
                        myUser.setEmail(mtail);
                        myUser.signUp(new SaveListener<MyUser>() {
                            @Override
                            public void done(MyUser s, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(RegisteredActivity.this, "注册成功:", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisteredActivity.this, LoginActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(RegisteredActivity.this, "注册失败:" + e.toString(), Toast.LENGTH_SHORT).show();

                                }

                            }

                        });

                    } else {
                        Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }



    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(RegisteredActivity.this, LoginActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RegisteredActivity.this, LoginActivity.class));
        finish();
    }
}