package imooc.com.smartbulter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import imooc.com.smartbulter.R;
import imooc.com.smartbulter.entity.MyUser;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.ui
*文件名：ForgetPasswordActivity
*创建者：pavilion15
*创建时间：2017/8/24 20:42
*描述： 忘记密码
*/
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_resetpassword,btn_sentemail;
    private EditText old_password,new_password1,new_password2,et_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        initView();
    }

    private void initView() {
        btn_resetpassword= (Button) findViewById(R.id.btn_resetpassword);
        btn_sentemail= (Button) findViewById(R.id.btn_sendemail);
        old_password= (EditText) findViewById(R.id.old_password);
        new_password1= (EditText) findViewById(R.id.new__password1);
        new_password2= (EditText) findViewById(R.id.new_password2);
        et_email= (EditText) findViewById(R.id.et_email);

        btn_resetpassword.setOnClickListener(this);
        btn_sentemail.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_resetpassword:
                String oldPassword=old_password.getText().toString();
                String newPasssword1=new_password1.getText().toString();
                String newPassword2=new_password2.getText().toString();
                //判断密码是否一致
                if(newPasssword1.equals(newPassword2)){
                    MyUser.updateCurrentUserPassword(oldPassword, newPasssword1, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                         if(e==null){
                             finish();
                         }else{
                             Toast.makeText(ForgetPasswordActivity.this,
                                     getString(R.string.login_fil)+e.getErrorCode(),Toast.LENGTH_SHORT).show();
                         }
                        }
                    });
                }else{
                    Toast.makeText(this,getString(R.string.password_notequal),Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_sendemail:
                //判断输入框为空
                String email=et_email.getText().toString().trim();
                if(!TextUtils.isEmpty(email)){
                    MyUser user=new MyUser();
                    user.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                          if(e==null){
                             startActivity(new Intent(ForgetPasswordActivity.this,LoginActivity.class));
                          }else{
                              Toast.makeText(ForgetPasswordActivity.this,getString(R.string.update_edit_fil)+e.getErrorCode(),Toast.LENGTH_SHORT).show();
                          }
                        }
                    });
                }else{
                    Toast.makeText(this,getString(R.string.alert_isempty),Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
        super.onBackPressed();
        startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
        finish();
    }
}
