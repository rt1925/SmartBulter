package imooc.com.smartbulter.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Environment;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;
import imooc.com.smartbulter.R;
import imooc.com.smartbulter.entity.MyUser;
import imooc.com.smartbulter.ui.CourierActivity;
import imooc.com.smartbulter.ui.LoginActivity;
import imooc.com.smartbulter.ui.PhoneActivity;
import imooc.com.smartbulter.ui.settingActivity;
import imooc.com.smartbulter.utils.L;
import imooc.com.smartbulter.utils.UtilTools;
import imooc.com.smartbulter.view.CustomDialog;

import static com.tencent.bugly.crashreport.crash.c.f;
import static com.tencent.bugly.crashreport.crash.c.i;


/**
 * Created by pavilion15 on 2017/8/22.
 */

public class UserFragment extends Fragment implements View.OnClickListener {
    private Button btn_edit_user,btn_okedit_user,btn_exit_user,btn_setting;
    private EditText et_username,et_userage,et_usersex,et_userdesc;
    private CircleImageView profile_image;
    private CustomDialog dialog;
    //dialog中
    private Button btn_carmera,btn_gallery,btn_cancel;

    private static final String PHOTO_IMAGE_FILE_NAME="imagefile";
    private static final int CARMERA_REQUEST_CODE=100;
    private static final int GALLERY_REQUEST_CODE=101;
    private static final int RESULT_REQUEST_CODE=102;
    private File tempFile=null;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_user,null);
        findView(view);
        return view;

    }

    private void findView(View view) {
        et_username = (EditText) view.findViewById(R.id.et_username);
        et_userage = (EditText) view.findViewById(R.id.et_userage);
        et_usersex = (EditText) view.findViewById(R.id.et_usersex);
        et_userdesc = (EditText) view.findViewById(R.id.et_userdesc);

        btn_edit_user = (Button) view.findViewById(R.id.btn_edit_user);
        btn_okedit_user = (Button) view.findViewById(R.id.btn_okedit_user);
        btn_exit_user = (Button) view.findViewById(R.id.btn_exit_user);
        btn_setting=(Button)view.findViewById(R.id.btn_setting);


        profile_image=(CircleImageView) view.findViewById(R.id.profile_image);
        UtilTools.getImageToShare(getActivity(),profile_image);

        dialog=new CustomDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,R.layout.dialog_photo,
                R.style.pop_anim_style, Gravity.BOTTOM);
        //屏幕外点击
        dialog.setCanceledOnTouchOutside(true);

        //初始化Dialog内控件
        btn_carmera=(Button)dialog.findViewById(R.id.btn_carmera);
        btn_gallery=(Button)dialog.findViewById(R.id.btn_gallery);
        btn_cancel=(Button)dialog.findViewById(R.id.btn_cancel);
        btn_carmera.setOnClickListener(this);
        btn_gallery.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_setting.setOnClickListener(this);
        btn_edit_user.setOnClickListener(this);
        btn_okedit_user.setOnClickListener(this);
        btn_exit_user.setOnClickListener(this);
        profile_image.setOnClickListener(this);

        //Edit不可编辑
        setEnabled(false);


        //获取值
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        et_username.setText(userInfo.getUsername());
        et_userage.setText(userInfo.getAge()+"");
        et_usersex.setText(userInfo.isSex()?getString(R.string.sex_man):getString(R.string.sex_woman));
        et_userdesc.setText(userInfo.getDesc());
    }

    private  void setEnabled(boolean isEnabled){
        et_username.setEnabled(isEnabled);
        et_userage.setEnabled(isEnabled);
        et_usersex.setEnabled(isEnabled);
        et_userdesc.setEnabled(isEnabled);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //编辑资料
            case R.id.btn_edit_user:
                setEnabled(true);
                btn_okedit_user.setVisibility(View.VISIBLE);
                break;
            //确定修改
            case R.id.btn_okedit_user:
                //获取输入框值
                String name=et_username.getText().toString().trim();
                String age=et_userage.getText().toString().trim();
                String sex=et_usersex.getText().toString().trim();
                String desc=et_userdesc.getText().toString().trim();
                //判断输入框是否为空
                if(!TextUtils.isEmpty(name) & !TextUtils.isEmpty(age)
                        &!TextUtils.isEmpty(sex) &!TextUtils.isEmpty(desc)){

                    //设置值
                    MyUser myUser = new MyUser();
                    myUser.setUsername(name);
                    myUser.setAge(Integer.parseInt(age));
                    //判断简介是否为空
                    if(!TextUtils.isEmpty(desc)){
                        myUser.setDesc(desc);
                    }else{
                        myUser.setDesc(getString(R.string.desc));
                    }
                    //判断性别
                    if(sex.equals(getString(R.string.sex_man))){
                        myUser.setSex(true);
                    }
                    if(sex.equals(getString(R.string.sex_woman))){
                        myUser.setSex(false);
                    }

                    //更新
                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    myUser.update(bmobUser.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                            if(e==null){
                                Toast.makeText(getActivity(),getString(R.string.update_suc),Toast.LENGTH_SHORT).show();
                                setEnabled(false);
                                btn_okedit_user.setVisibility(View.GONE);
                            }else{
                                Toast.makeText(getActivity(),getString(R.string.update_fil)+e.getErrorCode(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(getActivity(),getString(R.string.alert_isempty),Toast.LENGTH_SHORT).show();
                }
                break;
            //退出登录
            case R.id.btn_exit_user:
                //清除缓存用户对象
                BmobUser.logOut();
                // 现在的currentUser是null了
                BmobUser currentUser = BmobUser.getCurrentUser(); // 现在的currentUser是null了
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.btn_setting:
                startActivity(new Intent(getActivity(),settingActivity.class));
                break;

            case R.id.profile_image:
                dialog.show();
                break;
            //拍照
            case R.id.btn_carmera:
                toCarmera();
                break;
            //从相册选取一张
            case R.id.btn_gallery:
                toGallery();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
        }
    }



    private void toGallery() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
        dialog.dismiss();

    }

    private void toCarmera() {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存卡是否可用，可用就进行存储
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent,CARMERA_REQUEST_CODE);
        dialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!=getActivity().RESULT_CANCELED){
            switch (requestCode) {
                //相册数据
                case GALLERY_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                //相机数据
                case CARMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE:
                    //有可能点击舍弃
                    if (data != null) {
                        //拿到图片设置
                        setImageToView(data);
                        //既然已经设置了图片，我们原先的就应该删除
                        if (tempFile != null) {
                            tempFile.delete();
                        }
                    }
                    break;
            }
        }
    }

    private void startPhotoZoom(Uri data) {
        if (data == null) {
            L.e("uri == null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
        //设置裁剪
        intent.putExtra("crop", "true");
        //裁剪宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪图片的质量
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        //发送数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    //设置图片
    private void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            profile_image.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        UtilTools.putImageToShare(getActivity(),profile_image);
    }
}
