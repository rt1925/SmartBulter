package imooc.com.smartbulter.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.ProgressBar;

import imooc.com.smartbulter.R;
import imooc.com.smartbulter.utils.L;

import static imooc.com.smartbulter.R.id.mProgressBar;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.ui
*文件名：QrResultActivity
*创建者：pavilion15
*创建时间：2017/9/27 17:04
*描述： 二维码扫描结果页
*/
public class QrResultActivity extends BaseActivity{
    private ProgressBar mProgressBar;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrresult);
        initView();
    }

    private void initView() {
        mProgressBar= (ProgressBar) findViewById(R.id.mProgressBar);
        webView= (WebView) findViewById(R.id.mWebView);

        Intent intent=getIntent();
        final String url=intent.getStringExtra("result");
        //设置webview
        webView.getSettings().setJavaScriptEnabled(true);
        //允许网页进行缩放
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        //接口回调
       webView.setWebChromeClient(new QrResultActivity.WebViewClient());
        //本地显示
        webView.setWebViewClient(new android.webkit.WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);
    }
    public class WebViewClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress==100){
                mProgressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view,newProgress);
        }
    }
}
