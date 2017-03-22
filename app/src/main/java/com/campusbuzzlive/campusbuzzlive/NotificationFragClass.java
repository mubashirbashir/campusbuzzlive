package com.campusbuzzlive.campusbuzzlive;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;



public class NotificationFragClass extends Fragment {
    public WebView mWebView;
    String googleDocs = "https://docs.google.com/viewer?url=";

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.notification_frag_layout, container, false);




        mWebView = (WebView) v.findViewById(R.id.webview);
        mWebView.loadUrl("http://www.kashmiruniversity.net/notifications.aspx");

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebViewClient(new HelloWebViewClient());

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
                webview.loadUrl(url);
            }
            return true;
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Notifications");
    }
}
