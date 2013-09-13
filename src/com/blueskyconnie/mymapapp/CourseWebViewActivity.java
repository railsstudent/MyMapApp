package com.blueskyconnie.mymapapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

@SuppressLint("SetJavaScriptEnabled")
public class CourseWebViewActivity extends Activity {

	private static final String URL = "http://www1.fevaworks.com/portal/site/course.asp?code=%s&categoryid=14";

	private WebView webVw;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.activity_course_web_view);

		Intent it = this.getIntent();
		String code = it.getStringExtra("code");
		webVw = (WebView) this.findViewById(R.id.webview);
		webVw.getSettings().setJavaScriptEnabled(true);
		webVw.getSettings().setBuiltInZoomControls(true);
		webVw.invokeZoomPicker();
		// set initial scale to 50%
		webVw.setInitialScale(50);
		
		webVw.setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				Toast.makeText(CourseWebViewActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
			}
		});
		webVw.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int newProgress) {
				setProgress(newProgress * 100);
			}
		});
		webVw.loadUrl(String.format(URL, code));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.course_web_view, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && webVw.canGoBack()) {
			webVw.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	
}
