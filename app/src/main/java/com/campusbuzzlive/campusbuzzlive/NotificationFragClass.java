package com.campusbuzzlive.campusbuzzlive;


import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


public class NotificationFragClass extends Fragment {
    public WebView mWebView;
    private ProgressBar progressBarT4 = null;
    String googleDocs = "https://docs.google.com/viewer?url=";

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.notification_frag_layout, container, false);

        progressBarT4 = (ProgressBar) v.findViewById(R.id.progressBarT4);
        mWebView = (WebView) v.findViewById(R.id.webview);
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

        return v;


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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("News & Notifications");
    }
}
