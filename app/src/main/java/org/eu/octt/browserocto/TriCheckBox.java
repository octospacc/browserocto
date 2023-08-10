package org.eu.octt.browserocto;
import android.widget.*;
import android.content.*;
import android.util.*;

// <http://stackoverflow.com/questions/16511535/ddg#40939367>
public class TriCheckBox extends CheckBox {
    static private final int UNKNOW = -1;
    static private final int UNCHECKED = 0;
    static private final int CHECKED = 1;
	
	static private final int DrawableUndefined = R.drawable.checkbox_undefined; //R.drawable.ic_checkbox_indeterminate_black;
	static private final int DrawableUnchecked = R.drawable.checkbox_unchecked; //android.R.drawable.checkbox_off_background;
	static private final int DrawableChecked = R.drawable.checkbox_checked; //android.R.drawable.checkbox_on_background;
	
    private int state;
	private boolean binary = false; // allow emulating a binary checkbox

    public TriCheckBox(Context context) {
        super(context);
        init();
    }

    public TriCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TriCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        state = BinTriDef();
        updateBtn();

        setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			// checkbox status is changed from uncheck to checked.
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				switch (state) {
					default:
					case UNKNOW:
						state = UNCHECKED;
						break;
					case UNCHECKED:
						state = CHECKED;
						break;
					case CHECKED:
						state = BinTriDef();
						break;
				}
				updateBtn();
			}
		});
    }

    private void updateBtn() {
        int btnDrawable = DrawableUndefined;
        switch (state) {
            default:
            case UNKNOW:
                btnDrawable = DrawableUndefined;
                break;
            case UNCHECKED:
                btnDrawable = DrawableUnchecked;
                break;
            case CHECKED:
                btnDrawable = DrawableChecked;
                break;
        }

        setButtonDrawable(btnDrawable);
    }
	
	private int BinTriDef() {
		if (binary)
        	return UNCHECKED;
		else
			return UNKNOW;
	}

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        updateBtn();
    }
	
	public boolean getBinary() {
		return binary;
	}
	
	public void setBinary(boolean binary) {
		this.binary = binary;
		init();
	}
}
