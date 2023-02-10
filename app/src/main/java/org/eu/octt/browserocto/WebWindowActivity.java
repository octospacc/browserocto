package org.eu.octt.browserocto;

import android.app.*;
import android.content.*;
import android.os.*;
import android.webkit.*;
import android.widget.*;

public class WebWindowActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webwindow);
		
		Bundle Extra = getIntent().getExtras();
		LinearLayout LayMain = findViewById(R.id.LayMain);
		
		WebView Web0 = findViewById(R.id.Web0);
		Web0.setWebViewClient(new WebViewClient());
		Web0.getSettings().setJavaScriptEnabled(true);
		Web0.getSettings().setDomStorageEnabled(true);
		Web0.getSettings().setCacheMode(WhichCacheMode(Extra.getBoolean("Cache")));
		Web0.loadUrl(Extra.getString("Url"));
		
		_Util.ToastMsg(Extra.getString("Url"), getApplicationContext());
	};
	
	public int WhichCacheMode(boolean Opt) {
		if (Opt) {
			return WebSettings.LOAD_CACHE_ELSE_NETWORK;
		};
		return WebSettings.LOAD_NO_CACHE;
	};
};