package org.eu.octt.browserocto;

import android.app.*;
import android.content.*;
import android.os.*;
import android.webkit.*;
import android.widget.*;
import android.util.*;
import java.io.*;
import java.net.*;

public class WebWindowActivity extends Activity
{
	WebView Web0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webwindow);
		final Bundle Extra = getIntent().getExtras();
		LinearLayout LayMain = findViewById(R.id.LayMain);

		/*final WebView */Web0 = findViewById(R.id.Web0);
		final String WebCacheDir = "/data/data/" + getPackageName() + "/cache/WebCache/";
		
		Web0.setWebViewClient(new WebViewClient() {
			// https://gist.github.com/kmerrell42/b4ff31733c562a3262ee9a42f5704a89
			@Override
			public WebResourceResponse shouldInterceptRequest(WebView v, String Url) {
				return HandleRequest(Url);
			};
			@Override
			public WebResourceResponse shouldInterceptRequest(WebView v, WebResourceRequest Req) {
				return HandleRequest(Req.getUrl().toString());
			};
			// TODO: Save all HTTP headers too and return them somehow
			private WebResourceResponse HandleRequest(String Url) {
				try {
					final String UrlDir = WebCacheDir + Url + "_";
					final File UrlFile = new File(UrlDir, "Content");
					if (!UrlFile.exists() || !Extra.getBoolean("Cache")) {
						final URL Url_ = new URL(Url);
						final HttpURLConnection Connection = (HttpURLConnection)Url_.openConnection();
						_Util.WriteStreamToFile(new BufferedInputStream(Connection.getInputStream()), UrlDir, "Content");
						_Util.WriteStreamToFile(new ByteArrayInputStream(Connection.getContentType().getBytes()), UrlDir, "Content-Type");
					};
					String ContentType = _Util.StreamToString(new FileInputStream(new File(UrlDir, "Content-Type")));
					final String ContentEncoding = null;
					if (ContentType != null) {
						//ContentEncoding = ContentType.split("=")[1];
						ContentType = ContentType.split(";")[0];
					};
					return new WebResourceResponse(ContentType, ContentEncoding, new FileInputStream(UrlFile));
				} catch (Exception Ex) {
					Log.e("browserocto/Log", "", Ex);
				};
				return null;
			};
		});

		Web0.getSettings().setJavaScriptEnabled(true);
		Web0.getSettings().setDomStorageEnabled(true);
		Web0.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//WhichCacheMode(Extra.getBoolean("Cache")));

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

	@Override
	public void onBackPressed() {
		if (Web0.canGoBack()) {
			Web0.goBack();
		} else {
			super.onBackPressed();
		};
	};
};
