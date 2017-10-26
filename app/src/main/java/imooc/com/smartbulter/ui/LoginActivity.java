package imooc.com.smartbulter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import imooc.com.smartbulter.MainActivity;
import imooc.com.smartbulter.R;
import imooc.com.smartbulter.entity.MyUser;
import imooc.com.smartbulter.utils.ShareUtils;
import imooc.com.smartbulter.utils.UtilTools;
import imooc.com.smartbulter.view.CustomDialog;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.ui
*文件名：LoginActivity
*创建者：pavilion15
*创建时间：2017/8/24 0:57
*描述： TODO
*/
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //注册按钮
    private Button btn_registered,btn_login;
    private EditText et_user,et_password;
    private CheckBox ck_keeppass;
    private TextView forgetpassword;
    private CustomDialog dialog;
    private ImageView iv_login_image;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        btn_registered=(Button)findViewById(R.id.btn_registered);
        btn_registered.setOnClickListener(this);
        btn_login= (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        et_password= (EditText) findViewById(R.id.et_password);
        et_user= (EditText) findViewById(R.id.et_user);
        ck_keeppass= (CheckBox) findViewById(R.id.ck_keeppass);
        forgetpassword= (TextView) findViewById(R.id.forgetpassword);
        forgetpassword.setOnClickListener(this);
        //设置本地图像
        iv_login_image= (ImageView) findViewById(R.id.iv_login_image);
        UtilTools.getImageToShare(this,iv_login_image);


        dialog=new CustomDialog(this, 260, 260,R.layout.dialog_loading,
                R.style.Theme_Dialog,R.anim.pop_in, Gravity.CENTER);

        //屏幕外点击无效
        dialog.setCancelable(false);

        boolean isCheck=ShareUtils.getBoolean(this,"keeppass",true);
        if(isCheck){
            et_user.setText(ShareUtils.getString(this,"name",""));
            et_password.setText(ShareUtils.getString(this,"password",""));
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_registered:
                startActivity(new Intent(LoginActivity.this,RegisteredActivity.class));
                finish();
                break;
            case R.id.btn_login:
                String user=et_user.getText().toString().trim();
                String password=et_password.getText().toString().trim();
                //移动光标
                //et_user.setSelection(user.length()+1);
                //判断输入框为空
                if(!TextUtils.isEmpty(password) & !TextUtils.isEmpty(user)){
                    dialog.show();
                    //登录
                    MyUser myUser=new MyUser();
                    myUser.setUsername(user);
                    myUser.setPassword(password);
                    myUser.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                         if(e==null){
                             dialog.dismiss();
                             //判断邮箱是否验证
                             if(myUser.getEmailVerified()){
                                 startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                 //是否记住密码
                                 isRemember();
                                 finish();
                             }else{
                                 dialog.dismiss();
                                 Toast.makeText(LoginActivity.this,getString(R.string.email_not),Toast.LENGTH_SHORT).show();
                             }
                         }else{
                             dialog.dismiss();
                             Toast.makeText(LoginActivity.this,getString(R.string.login_fil)+e.getErrorCode(),Toast.LENGTH_SHORT).show();
                         }
                        }
                    });
                }else{
                    dialog.dismiss();
                    Toast.makeText(this,getString(R.string.alert_isempty),Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.forgetpassword:
                startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
                finish();
                break;
        }

    }

    private void isRemember() {
        //保存状态
        ShareUtils.putBoolean(this,"keeppass",ck_keeppass.isChecked());

        //是否记住密码
        if(ck_keeppass.isChecked()){
            //保存密码
            ShareUtils.putString(this,"name",et_user.getText().toString().trim());
            ShareUtils.putString(this,"password",et_password.getText().toString());
        }else{
            ShareUtils.deleShare(this,"keeppass");
        }

    }
}
