package org.eu.octt.browserocto;

import android.app.*;
import android.content.*;
import android.widget.*;

public class _Util extends Activity {
	public static void ToastMsg(String Msg, Context c) {
		Toast.makeText(c, Msg, Toast.LENGTH_SHORT).show();
	};
	
	public static boolean IsUrlValid(String Url) {
		Url = Url.toLowerCase();
		if (
			(Url.startsWith("file://") || Url.startsWith("http://") || Url.startsWith("https://"))
		&& !(Url.equals("file://") || Url.equals("http://") || Url.equals("https://"))
		) {
			return true;
		};
		return false;
	};
};