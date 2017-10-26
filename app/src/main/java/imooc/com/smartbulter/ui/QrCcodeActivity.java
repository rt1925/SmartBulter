package imooc.com.smartbulter.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.xys.libzxing.zxing.encoding.EncodingUtils;

import imooc.com.smartbulter.R;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.ui
*文件名：QrCcodeActivity
*创建者：pavilion15
*创建时间：2017/9/12 15:42
*描述： TODO
*/
public class QrCcodeActivity extends BaseActivity{
    private ImageView iv_qrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        initView();
    }

    private void initView() {
        iv_qrcode= (ImageView) findViewById(R.id.iv_qrcode);

        //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（*350350）
        //int width=getResources().getDisplayMetrics().widthPixels;
        Bitmap qrCodeBitmap = EncodingUtils.createQRCode("智能管家", 350, 350,
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher) );
        iv_qrcode.setImageBitmap(qrCodeBitmap);
    }
}
