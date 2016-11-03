package com.khaibin.harveynorman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean canExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_webview);
        WebView browser = (WebView) findViewById(R.id.webview);
        final ProgressBar progressloader = (ProgressBar) findViewById(R.id.progress_loader);
        browser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressloader.getVisibility() == View.VISIBLE && browser.getVisibility() == View.GONE) {
                    progressloader.setVisibility(View.GONE);
                    browser.setVisibility(View.VISIBLE);
                }
            }
        });
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        browser.setHorizontalScrollBarEnabled(false);
        browser.loadUrl("http://harveynormanweb.github.io/");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        WebView browser = (WebView) findViewById(R.id.webview);

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (browser.canGoBack()) {
                        canExit = false;
                        browser.goBack();
                    } else {
                        if (canExit) {
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
                            canExit = true;
                            new java.util.Timer().schedule(
                                    new java.util.TimerTask() {
                                        @Override
                                        public void run() {
                                            canExit = false;
                                        }
                                    },
                                    1500
                            );
                        }
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

}
