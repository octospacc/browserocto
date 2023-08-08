package org.eu.octt.browserocto;
import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.webkit.*;
import android.widget.AbsoluteLayout.*;
import android.graphics.drawable.*;
import android.widget.PopupMenu.*;
import android.util.*;
import java.util.*;
import android.content.*;
import android.text.*;

public class BrowserActivity extends Activity
{
	Boolean WasPopOptOpen = new Boolean(false);
	/*public Boolean GetWasPopOptOpen(){
		return WasPopOptOpen;
	};
	public void SetWasPopOptOpen(Boolean Open){
		WasPopOptOpen = true;
	};*/
	SiteSettings CurSiteSettings, DefSiteSettings;
	public SiteSettings GetDefSiteSettings(){
		return DefSiteSettings;
	}
	public void SetCurSiteSettings(SiteSettings Settings){
		CurSiteSettings = Settings;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browser);
		final Bundle Extra = getIntent().getExtras();
		final LinearLayout LayMain = findViewById(R.id.LayMain);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		getActionBar().hide();
		TypedValue ThemeBackgroundValue = new TypedValue();
		getTheme().resolveAttribute(android.R.attr.windowBackground, ThemeBackgroundValue, true);
		Drawable ThemeBackgroundDrawable = getResources().getDrawable(ThemeBackgroundValue.resourceId);
		
		final LinearLayout LayOmni = new LinearLayout(this);
		LayOmni.setOrientation(LinearLayout.HORIZONTAL);
		LayMain.addView(LayOmni);
		
		final EditText EditOmnibar = new EditText(this);
		EditOmnibar.setInputType(InputType.TYPE_CLASS_TEXT);
		EditOmnibar.setMaxLines(1);
		EditOmnibar.setLayoutParams(new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));
		EditOmnibar.setText("https://example.com");
		LayOmni.addView(EditOmnibar);
		
		final WebView Weber = new WebView(this);
		LayMain.addView(Weber);
		
		EditOmnibar.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
					Weber.loadUrl(EditOmnibar.getText().toString());
				};
				return false;
			};
		});
		
		final Button BtnOpt = new Button(this);
		registerForContextMenu(BtnOpt);
		LayOmni.addView(BtnOpt);
		
		final ScrollView _LayOpt = new ScrollView(this);
		final LinearLayout LayOpt = new LinearLayout(this);
		LayOpt.setOrientation(LinearLayout.VERTICAL);
		_LayOpt.addView(LayOpt);
		
		//HashMap NavActions = new HashMap();
		//NavActions.put("Tabs", new HashMap().put("Type", "Button"));
		//NavActions.put("History", new HashMap().put("Type", "Button"));
		//NavActions.put("Favourites", new HashMap().put("Type", "Button"));
		//NavActions.put("Reload/Stop", new HashMap().put("Type", "Button"));
		//NavActions.put("Tabs", new HashMap().put("Type", "Button"));
		
		//BrowserNavigationButtons NavButtons = new BrowserNavigationButtons(this);
		
		HashMap SiteSettingsCollection = new HashMap();
		final SiteSettings DefSiteSettings = new SiteSettings();
		DefSiteSettings.Viewformat = "Auto"; //new RadioValue("Auto");
		DefSiteSettings.Useragent = "Auto";
		DefSiteSettings.BlockBadware = true;
		DefSiteSettings.SuppressBanners = true;
		DefSiteSettings.AllowJs = true;
		DefSiteSettings.AllowCss = true;
		DefSiteSettings.AllowStorage = true;
		DefSiteSettings.Autofill = true;
		DefSiteSettings.ForceCache = false;
		DefSiteSettings.Sitetweaks = true;
		DefSiteSettings.PolyfillJs = false;
		DefSiteSettings.PolyfillCss = false;
		DefSiteSettings.Devtools = false;
		SiteSettingsCollection.put("Default", DefSiteSettings);
		SetCurSiteSettings(GetDefSiteSettings());
		
		/*final TextView ITest = new TextView(this);
		ITest.setText("Test");
		ITest.setPadding(8,8,8,8);
		ITest.setClickable(true);
		ITest.setFocusable(true);
		TypedValue typedValue = new TypedValue();
		getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
		ITest.setBackgroundResource(typedValue.resourceId); // getResources().getDrawable(android.R.attr.selectableItemBackground)); //Drawable(getResources().getDrawable(getResources().getIdentifier("?android:attr/selectableItemBackground", "drawable", getPackageName())));
		LayOpt.addView(ITest);*/
		
		final Button BtnOptTabs = (Button)_Util.AddLayoutChild(_Util.MakeButton(this, "Tabs"), LayOpt);
		final Button BtnOptHistory = (Button)_Util.AddLayoutChild(_Util.MakeButton(this, "History"), LayOpt);
		final Button BtnOptBookmarks = (Button)_Util.AddLayoutChild(_Util.MakeButton(this, "Bookmarks"), LayOpt);
		final Button BtnOptLoad = (Button)_Util.AddLayoutChild(_Util.MakeButton(this, "Reload/Stop"), LayOpt);
		final Button BtnOptPrev = (Button)_Util.AddLayoutChild(_Util.MakeButton(this, "Prev"), LayOpt);
		final Button BtnOptNext = (Button)_Util.AddLayoutChild(_Util.MakeButton(this, "Next"), LayOpt);
		
		final RadioGroup RadioOptSettingsNamespace = (RadioGroup)_Util.AddLayoutChild(new RadioGroup(this), LayOpt);
		RadioOptSettingsNamespace.setOrientation(RadioGroup.HORIZONTAL);
		RadioOptSettingsNamespace.addView(_Util.MakeTextView(this, "Set: "));
		final RadioButton RadioOptSettingsNamespaceDefault = (RadioButton)_Util.AddLayoutChild(_Util.MakeRadioButton(this, false, "Default"), RadioOptSettingsNamespace);
		final RadioButton RadioOptSettingsNamespaceSite = (RadioButton)_Util.AddLayoutChild(_Util.MakeRadioButton(this, true, "Site"), RadioOptSettingsNamespace);
		RadioOptSettingsNamespaceDefault.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				//RadioOptSettingsNamespaceDefault.setChecked(true);
				RadioOptSettingsNamespaceSite.setChecked(false);
				RadioOptSettingsNamespaceDefault.setChecked(true);
				SetCurSiteSettings(GetDefSiteSettings());
			};
		});
		RadioOptSettingsNamespaceSite.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				RadioOptSettingsNamespaceDefault.setChecked(false);
				RadioOptSettingsNamespaceSite.setChecked(true);
				//RadioOptSettingsNamespaceSite.setChecked(true);
			};
		});
		
		final CheckBox CheckOptBlockBadware = (CheckBox)_Util.AddLayoutChild(_Util.MakeCheckBox(this, DefSiteSettings.BlockBadware, "Block Badware"), LayOpt);
		final CheckBox CheckOptSuppressBanners = (CheckBox)_Util.AddLayoutChild(_Util.MakeCheckBox(this, DefSiteSettings.SuppressBanners, "Suppress Cookie Banners"), LayOpt);
		final CheckBox CheckOptAllowJs = (CheckBox)_Util.AddLayoutChild(_Util.MakeCheckBox(this, DefSiteSettings.AllowJs, "Allow JavaScript/WASM"), LayOpt);
		final CheckBox CheckOptAllowCss = (CheckBox)_Util.AddLayoutChild(_Util.MakeCheckBox(this, DefSiteSettings.AllowCss, "Allow Styles"), LayOpt);
		final CheckBox CheckOptAllowStorage = (CheckBox)_Util.AddLayoutChild(_Util.MakeCheckBox(this, DefSiteSettings.AllowStorage, "Allow Site Storage"), LayOpt);
		final CheckBox CheckOptAutofill = (CheckBox)_Util.AddLayoutChild(_Util.MakeCheckBox(this, DefSiteSettings.Autofill, "Fields Autofill"), LayOpt);
		final CheckBox CheckOptForceCache = (CheckBox)_Util.AddLayoutChild(_Util.MakeCheckBox(this, DefSiteSettings.ForceCache, "Force Offline Caching"), LayOpt);
		final CheckBox CheckOptSitetweaks = (CheckBox)_Util.AddLayoutChild(_Util.MakeCheckBox(this, DefSiteSettings.Sitetweaks, "SiteTweaks"), LayOpt);
		final CheckBox CheckOptPolyfillJs = (CheckBox)_Util.AddLayoutChild(_Util.MakeCheckBox(this, DefSiteSettings.PolyfillJs, "Polyfill JavaScript"), LayOpt);
		final CheckBox CheckOptPolyfillCss = (CheckBox)_Util.AddLayoutChild(_Util.MakeCheckBox(this, DefSiteSettings.PolyfillCss, "Polyfill CSS"), LayOpt);
		final CheckBox CheckOptDevtools = (CheckBox)_Util.AddLayoutChild(_Util.MakeCheckBox(this, DefSiteSettings.Devtools, "DevTools"), LayOpt);
		final Button BtnOptCustomJs = (Button)_Util.AddLayoutChild(_Util.MakeButton(this, "Custom JavaScript"), LayOpt);
		final Button BtnOptCustomCss = (Button)_Util.AddLayoutChild(_Util.MakeButton(this, "Custom CSS"), LayOpt);
		final Button BtnOptCustomRedirects = (Button)_Util.AddLayoutChild(_Util.MakeButton(this, "Custom Redirects"), LayOpt);
		
		//(CheckBox)LayOpt.findViewById("CheckDevtools").setText("Test");
		
		final PopupWindow PopOpt = new PopupWindow(this);
		PopOpt.setContentView(_LayOpt);
		//PopOpt.setBackgroundDrawable(new ColorDrawable());
		PopOpt.setBackgroundDrawable(ThemeBackgroundDrawable);
		//PopOpt.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.ic_dialog_email));
		PopOpt.setOutsideTouchable(true);
		
		// TODO: Emulate the proper behavior of Firefox options button (clicking on it after opening would still close the menu)
		BtnOpt.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				/*
				PopupMenu menu = new PopupMenu(BrowserActivity.this, v);
				menu.getMenu().add("Reload/Stop");
				menu.getMenu().add("Back");
				menu.getMenu().add("Front");
				menu.getMenu().add("Tabs");
				menu.show();
				*/
				/*if (WasPopOptOpen) {
					PopOpt.dismiss();
					WasPopOptOpen = false;
				} else {*/
				BtnOpt.setEnabled(false);
				PopOpt.showAsDropDown(v);
				/*	WasPopOptOpen = true;
				};*/
			};
		});
		/*BtnOpt.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						return true;
					case MotionEvent.ACTION_UP:
						if (WasPopOptOpen) {
							PopOpt.dismiss();
							WasPopOptOpen = false;
						} else {
							PopOpt.showAsDropDown(v);
							WasPopOptOpen = true;
						};
						WasPopOptOpen = !WasPopOptOpen;
						return true;
				}
				return false;
			}
		});*/
		PopOpt.setOnDismissListener(new PopupWindow.OnDismissListener(){
			@Override
			public void onDismiss(){
				//WasPopOptOpen = false;
				BtnOpt.setEnabled(true);
			};
		});
		
		Weber.loadUrl(EditOmnibar.getText().toString());
		//Weber.getSettings().setJavaScriptEnabled(CurSiteSettings.AllowJs);
	};
	
	/*
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, 1, Menu.NONE, "Item name");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 1:
				return true;
			default:
				return false;
		}
	}
	*/
};

/*class BrowserNavigationButtons
{
	Context c;
	BrowserNavigationButtons(Context c) {
		Button BtnTabs = new Button(c);
		Button BtnHistory;
		Button BtnFavorites;
		Button BtnLoad;
		Button BtnPrev;
		Button BtnNext;
	};
};*/

/*class RadioValue {
	String Value;
	RadioValue(String Value) {
		String Option = Value;
	}
};*/

class SiteSettings {
	/*RadioValue*/String Viewformat, Useragent;
	Boolean BlockBadware, SuppressBanners;
	Boolean AllowJs, AllowCss;
	Boolean AllowStorage, Autofill;
	Boolean ForceCache;
	Boolean Sitetweaks;
	Boolean PolyfillJs, PolyfillCss;
	Boolean Devtools;
	String CustomJs, CustomCss, CustomRedirects;
};
