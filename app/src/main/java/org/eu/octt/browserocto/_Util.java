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
	public static String StreamToString(InputStream In) throws IOException {
		if (In != null) {
			Writer writer = new StringWriter();
			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(In, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				};
			} finally {
				In.close();
			};
			return writer.toString();
		};
		return "";
	};
	
	// https://stackoverflow.com/a/10857407
	public static void WriteStreamToFile(InputStream In, String Path, String Name) throws IOException {
		try {
			File dir = new File(Path);
			dir.mkdirs();
			File file = new File(Path, Name);
			file.createNewFile();
			try (OutputStream output = new FileOutputStream(file)) {
				byte[] buffer = new byte[4 * 1024];
				int read;

				while ((read = In.read(buffer)) != -1) {
					output.write(buffer, 0, read);
				}

				output.flush();
				}
		} finally {
			In.close();
		}
	};
};
