package com.campusbuzzlive.campusbuzzlive;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class GuestNotification extends AppCompatActivity {
    public WebView mWebView;
    private ProgressBar progressBarT4 = null;
    String googleDocs = "https://docs.google.com/viewer?url=";

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_notification);
        progressBarT4 = (ProgressBar) findViewById(R.id.progressBarT4);
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int progress) {
                progressBarT4.setVisibility(View.VISIBLE);
                progressBarT4.setProgress(progress);
                if (progress == 100) {
                    progressBarT4.setVisibility(View.GONE); // Make the bar disappear after URL is loaded
                }
            }
        });
        progressBarT4.setVisibility(View.VISIBLE);



        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(false);

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());

        mWebView.setWebViewClient(new HelloWebViewClient());
        mWebView.loadUrl("http://www.kashmiruniversity.net/notifications.aspx");

    }
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webview, String url){
            if (url.endsWith(".pdf"))
            {
                String googleDocs = "https://docs.google.com/viewer?url=";
                mWebView.loadUrl(googleDocs + url);
                return true;
            }
            else
            {
                // Load all other urls normally.
                mWebView.loadUrl("http://www.kashmiruniversity.net/notifications.aspx");
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            view.loadUrl("javascript:document.getElementsByClassName('wrapper row1').style.display = 'none';");
        }

    }

}
