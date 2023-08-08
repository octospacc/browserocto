package org.eu.octt.browserocto;

import android.app.*;
import android.content.*;
import android.widget.*;
import java.io.*;
import android.graphics.drawable.*;
import android.util.*;
import android.view.*;

public class _Util extends Activity
{
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
	
	/*public static Drawable DrawableFromId(Integer Id, Context c){
		TypedValue typedValue = new TypedValue();
		c.getTheme().resolveAttribute(Id, typedValue, true);
		//return typedValue.;
	};*/
	
	public static TextView MakeTextView(Context c, String Text){
		TextView Label = new TextView(c);
		Label.setText(Text);
		return Label;
	};
	
	public static Button MakeButton(Context c, String Text){
		Button Btn = new Button(c);
		Btn.setText(Text);
		return Btn;
	};
	
	public static RadioButton MakeRadioButton(Context c, Boolean Checked, String Text){
		RadioButton Radio = new RadioButton(c);
		Radio.setText(Text);
		if (Checked != null)
			Radio.setChecked(Checked);
		return Radio;
	};
	
	public static CheckBox MakeCheckBox(Context c, Boolean Checked, String Text){
		CheckBox Check = new CheckBox(c);
		Check.setText(Text);
		if (Checked != null)
			Check.setChecked(Checked);
		return Check;
	};
	
	public static View AddLayoutChild(View Child, LinearLayout Parent) {
		Parent.addView(Child);
		return Child;
	}
};
