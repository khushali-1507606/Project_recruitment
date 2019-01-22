package com.example.win_10.newapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public class PrscWebsite extends AppCompatActivity {


    WebView webView;
    String url = "http://prsc.gov.in/?AspxAutoDetectCookieSupport=1";
    ProgressBar progressBar;
    FrameLayout frameLt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prsc_website);

        initWebView();
    }

    void initWebView(){
        frameLt=findViewById(R.id.frameLayout);
        progressBar=findViewById(R.id.progressBarH);
        progressBar.setMax(100);

        webView = (WebView)findViewById(R.id.webView);
//        WebChromeClient client = new WebChromeClient();
//        webView.setWebChromeClient(client);

        // Code to show WebPage in WebView
        webView.setWebViewClient(new HelpClient());

        //to show horizontal progress while loading webView
        webView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view,int progress){
                frameLt.setVisibility(View.VISIBLE);
                progressBar.setProgress(progress);
                setTitle("Loading...");

                if(progress==100){
                    frameLt.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    setTitle(view.getTitle());
                }
                super.onProgressChanged(view,progress);
            }
        });
        
        webView.getSettings().setJavaScriptEnabled(true);// webview supports javascript
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.loadUrl(url); // Their must be Internet Connection. You must give a permission in the Manifest File for Internet

    }

    private class HelpClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            frameLt.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==KeyEvent.KEYCODE_BACK){
            if(webView.canGoBack()){
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
