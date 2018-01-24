package com.rodvar.kotlinbase.base.utils.data;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by rodvar on 5/6/17.
 */

public class EmojiExcludeFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        for (int i = start; i < end; i++) {
            int type = Character.getType(source.charAt(i));
            if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                return "";
            }
        }
        return null;
    }
}
