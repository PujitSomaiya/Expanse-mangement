package com.tatvasoft.expansemangement.util;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class CommonUtil {
    public  static boolean isNotNull(EditText editText)
    {
        return editText!=null;
    }
    public static boolean isEmptyEditText(EditText edt)
    {
        return TextUtils.isEmpty(edt.getText().toString().trim());
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
