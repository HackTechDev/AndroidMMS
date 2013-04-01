package com.wzx.andapp.shh;

import android.app.Activity;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class RssWebActivity extends Activity {
	private WebView webView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		String lurl = null;
		if (intent.hasExtra("lurl")) {
			lurl = intent.getStringExtra("lurl");
		}
		if (lurl == null) {
			lurl = "http://www.51cto.com/";
		}
		setContentView(R.layout.webv);
		webView = (WebView) findViewById(R.id.webview);
		// 设置WebView属性，能够执行Javascript脚本
		webView.getSettings().setJavaScriptEnabled(true);
		// 加载需要显示的网页
		webView.loadUrl(lurl);
		// 设置当前网页的链接仍在webView中跳转
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
			// 处理https请求
			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				handler.proceed();
				// handler.cancel();
				// handler.handleMessage(null);
			}
		});

		// 页面加载进度
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				setTitle("页面加载中，请稍候..." + progress + "%");
				setProgress(progress * 100);

				if (progress == 100) {
					setTitle(R.string.app_name);
				}
			}
		});
		

		webView.getSettings().setJavaScriptEnabled(true);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (webView.canGoBack() && event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0) {
			webView.goBack();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
}