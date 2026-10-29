package com.yusufelbeses.nasrlibrary;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public final class MainActivity extends Activity {
    private static final String SITE_URL = "https://albshshy-alt.github.io/alnasrshopap4/";
    private static final String SITE_HOST = "albshshy-alt.github.io";

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(31, 58, 46));
        getWindow().setNavigationBarColor(Color.rgb(31, 58, 46));

        webView = new WebView(this);
        webView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(false);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                if (isSiteUrl(uri)) {
                    return false;
                }
                openExternal(uri);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                if (isSiteUrl(uri)) {
                    return false;
                }
                openExternal(uri);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (request.isForMainFrame()) {
                    showOfflineMessage();
                }
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                WebView popup = new WebView(MainActivity.this);
                WebSettings popupSettings = popup.getSettings();
                popupSettings.setJavaScriptEnabled(true);
                popupSettings.setDomStorageEnabled(true);
                popup.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView child, WebResourceRequest request) {
                        return handlePopupUrl(child, request.getUrl());
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView child, String url) {
                        return handlePopupUrl(child, Uri.parse(url));
                    }

                    @Override
                    public void onPageStarted(WebView child, String url, Bitmap favicon) {
                        Uri uri = Uri.parse(url);
                        if (!isSiteUrl(uri)) {
                            openExternal(uri);
                            child.stopLoading();
                            child.destroy();
                        }
                    }
                });

                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(popup);
                resultMsg.sendToTarget();
                return true;
            }
        });

        setContentView(webView);
        webView.loadUrl(SITE_URL);
    }

    private boolean handlePopupUrl(WebView popup, Uri uri) {
        if (!isSiteUrl(uri)) {
            openExternal(uri);
            popup.stopLoading();
            popup.destroy();
            return true;
        }
        return false;
    }

    private boolean isSiteUrl(Uri uri) {
        return "https".equalsIgnoreCase(uri.getScheme())
                && SITE_HOST.equalsIgnoreCase(uri.getHost());
    }

    private void openExternal(Uri uri) {
        if (uri.getScheme() == null) {
            return;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            Toast.makeText(this, "لا يوجد تطبيق لفتح هذا الرابط", Toast.LENGTH_LONG).show();
        }
    }

    private void showOfflineMessage() {
        String page = "<!doctype html><html lang='ar' dir='rtl'><meta charset='utf-8'>"
                + "<meta name='viewport' content='width=device-width,initial-scale=1'>"
                + "<body style='font-family:sans-serif;background:#f7f1e3;color:#1f3a2e;"
                + "display:grid;place-items:center;height:90vh;text-align:center;padding:24px'>"
                + "<div><h2>تعذر تحميل مكتبة النصر</h2><p>تحقق من اتصال الإنترنت ثم أعد المحاولة.</p>"
                + "<button onclick=\"location.href='" + SITE_URL + "'\" style='padding:12px 20px;"
                + "background:#1f3a2e;color:white;border:0;border-radius:8px'>إعادة المحاولة</button>"
                + "</div></body></html>";
        webView.loadDataWithBaseURL(null, page, "text/html", "UTF-8", null);
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
