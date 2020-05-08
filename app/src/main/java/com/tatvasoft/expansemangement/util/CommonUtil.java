package com.tatvasoft.expansemangement.util;

import android.text.TextUtils;
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
}
