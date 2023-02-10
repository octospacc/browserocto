package org.eu.octt.browserocto;

import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.net.*;
import android.content.pm.*;
import android.graphics.drawable.*;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		final LinearLayout LayMain = findViewById(R.id.LayMain);
		final EditText EditUrl = findViewById(R.id.EditUrl);
		final Button BtnOpen = findViewById(R.id.BtnOpen);
		final Button BtnShortcut = findViewById(R.id.BtnShortcut);
		final Switch SwitchCache = findViewById(R.id.SwitchCache);
		
		BtnOpen.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String Url = EditUrl.getText().toString();
				if (_Util.IsUrlValid(Url)) {
					startActivity(MakeIntBrowse(Url, SwitchCache.isChecked()));
				} else {
					_Util.ToastMsg("URL is invalid!", getApplicationContext());
				};
			};
		});
		
		BtnShortcut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String Url = EditUrl.getText().toString();
				if (_Util.IsUrlValid(Url)) {
					ShowShortcutDial(Url);
				} else {
					_Util.ToastMsg("URL is invalid!", getApplicationContext());
				};
			};
		});
	};
	
	private Intent MakeIntBrowse(String Url, Boolean Cache) {
		Intent IntBrowse = new Intent(getApplicationContext(), WebWindowActivity.class);
		// To get unlimited activities in general but only 1 per site, set only FLAG_ACTIVITY_NEW_DOCUMENT and put site URL in intent.setData
		// NOTE: This only gives a good UX on Android >= 7, for the lower versions we need to implement things differently
		IntBrowse
			.setAction(Intent.ACTION_OPEN_DOCUMENT)
			.setData(Uri.parse(Url))
			.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT /*| Intent.FLAG_ACTIVITY_MULTIPLE_TASK*/)
			.putExtra("Url", Url)
			.putExtra("Cache", Cache);
		return IntBrowse;
	};
	
	private final void CreateShortcut(String Url, String Name) {
		// https://stackoverflow.com/a/51184905
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
			ShortcutManager ShortMan = getApplicationContext().getSystemService(ShortcutManager.class);
			if (ShortMan != null) {
				if (ShortMan.isRequestPinShortcutSupported()) {
					ShortcutInfo shortcut = new ShortcutInfo.Builder(getApplicationContext(), Url) 
						.setShortLabel(Name)
						//.setLongLabel("Open the Android Document")
						.setIcon(Icon.createWithResource(getApplicationContext(), R.drawable.ic_launcher)) // TODO Get site icon, or let user pick
						//.setIcon(IconFromUrlElseDefault(Url))
						.setIntent(MakeIntBrowse(Url, true))
						.build();
					ShortMan.requestPinShortcut(shortcut, null);
				} else {
					CreateShortcutFallback(Url, Name);
				};
			};
		} else {
			CreateShortcutFallback(Url, Name);
		};
	};
	
	private final void CreateShortcutFallback(String Url, String Name) {
		Intent IntExt = new Intent();
		IntExt.putExtra(Intent.EXTRA_SHORTCUT_INTENT, MakeIntBrowse(Url, true));
		IntExt.putExtra(Intent.EXTRA_SHORTCUT_NAME, Name);
		IntExt.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.ic_launcher));

		IntExt.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		//IntExt.putExtra("duplicate", false);  //may it's already there so don't duplicate
		getApplicationContext().sendBroadcast(IntExt);
	};

	
	private void ShowShortcutDial(String Url) {
		final AlertDialog.Builder Dial = new AlertDialog.Builder(this);
		final String Url_ = Url;
		
		final View v = getLayoutInflater().inflate(R.layout.dialmakeshortcut, null);
		Dial.setView(v);
		
		final EditText EditName = v.findViewById(R.id.EditName);
		//final EditText EditIcon = v.findViewById(R.id.EditIcon);
		
		Dial.setTitle("Create Home Shortcut");
		//Dial.setCancelable(false);
		
		Dial.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int w) {
				final String Name = EditName.getText().toString();
				//String IconUrl = EditIcon.getText().toString();
				if (!Name.equals("")) {
					CreateShortcut(Url_, Name);
				} else
				if (Name.equals("")) {
					_Util.ToastMsg("Name can't be empty!", getApplicationContext());
				};
			};
		});
		
		Dial.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int w) {
				
			};
		});

		Dial.show();
	};
};