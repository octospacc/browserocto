package org.eu.octt.browserocto;

import android.app.*;
import android.content.*;
import android.widget.*;
import java.io.*;

public class _Util extends Activity {
	public static boolean StartsWithOneOf(String Check, String[] With) {
		for (int i=0; i<With.length; i++) {
			if (Check.startsWith(With[i])) {
				return true;
			};
		};
		return false;
	};

	public static boolean EqualsOneOf(String Check, String[] With) {
		for (int i=0; i<With.length; i++) {
			if (Check.equals(With[i])) {
				return true;
			};
		};
		return false;
	};

	public static void ToastMsg(String Msg, Context c) {
		Toast.makeText(c, Msg, Toast.LENGTH_SHORT).show();
	};
	
	public static boolean IsUrlValid(String Url) {
		Url = Url.trim().toLowerCase();
		String[] Prefixes = {"file://", "http://", "https://", "data:", "javascript:"};
		if (StartsWithOneOf(Url, Prefixes) && !EqualsOneOf(Url, Prefixes)) {
			return true;
		};
		return false;
	};

	// https://stackoverflow.com/a/5713929
	public static String StreamToString(InputStream is) throws IOException {
		if (is != null) {
			Writer writer = new StringWriter();
			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				};
			} finally {
				is.close();
			};
			return writer.toString();
		};
		return "";
	};
};