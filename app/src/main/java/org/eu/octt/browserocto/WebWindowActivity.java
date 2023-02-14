package org.eu.octt.browserocto;

import android.app.*;
import android.content.*;
import android.os.*;
import android.webkit.*;
import android.widget.*;
import android.util.*;
import java.io.*;
import java.net.*;

public class WebWindowActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webwindow);
		Bundle Extra = getIntent().getExtras();
		LinearLayout LayMain = findViewById(R.id.LayMain);

		WebView Web0 = findViewById(R.id.Web0);
		//Web0.setWebViewClient(new WebViewClient());

		Web0.setWebViewClient(new WebViewClient() {
			// https://gist.github.com/kmerrell42/b4ff31733c562a3262ee9a42f5704a89
			@Override
			public WebResourceResponse shouldInterceptRequest(WebView v, String Url) {
				return HandleRequest(Url);
			}
			@Override
			public WebResourceResponse shouldInterceptRequest(WebView v, WebResourceRequest Req) {
				return HandleRequest(Req.getUrl().toString());
			}
			private WebResourceResponse HandleRequest(String Url) {
				//Thread thread = new Thread(new Runnable() {
				//	@Override
				//	public void run() {
				try {
					//URL url;
					//HttpURLConnection Connection = null;
					URL Url_ = new URL(Url);
					HttpURLConnection Connection = (HttpURLConnection)Url_.openConnection();
					String ContentType = Connection.getContentType();
					String ContentEncoding = null;
					if (ContentType != null) {
						ContentType = ContentType.split("\\;")[0];
						ContentEncoding = ContentType.split("\\=")[1];
					}
					InputStream In = new BufferedInputStream(Connection.getInputStream());
					return new WebResourceResponse(ContentType, ContentEncoding, In);
				} catch (Exception Ex) {
					Log.e("browserocto/Log", "", Ex);
				};
				//	};//});
				return null;
			};
		});

		Web0.getSettings().setJavaScriptEnabled(true);
		Web0.getSettings().setDomStorageEnabled(true);
		Web0.getSettings().setCacheMode(WhichCacheMode(Extra.getBoolean("Cache")));

		// This is apparently not working like I want (force caching of all site resources in spite of bad Cache-Control HTTP headers)
		//webView.getSettings().setAppCacheMaxSize(1024*1024*8);
		//webView.getSettings().setAppCachePath("/data/data/" + getPackageName() + "/cache");
		//webView.getSettings().setAllowFileAccess(true);
		//webView.getSettings().setAppCacheEnabled(true);

		Web0.loadUrl(Extra.getString("Url"));
		_Util.ToastMsg(Extra.getString("Url"), this);

//		Thread thread = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				URL url;
//				HttpURLConnection urlConnection = null;
//				try {
//					url = new URL("http://www.android.com/");
//					urlConnection = (HttpURLConnection) url.openConnection();
//					InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//					runOnUiThread(new Runnable() {
//						@Override
//						public void run() {
//							try {
//								_Util.ToastMsg(_Util.StreamToString(in), getApplicationContext());
//							} catch (Exception Ex) {
//								Log.e("browserocto/Log", "", Ex);
//							};
//						};
//					});
//				} catch (Exception Ex) {
//					Log.e("browserocto/Log", "", Ex);
//				} finally {
//					urlConnection.disconnect();
//				};
//			};
//		});
//		thread.start();
	};

	public int WhichCacheMode(boolean Opt) {
		if (Opt) {
			return WebSettings.LOAD_CACHE_ELSE_NETWORK;
		};
		return WebSettings.LOAD_NO_CACHE;
	};
};