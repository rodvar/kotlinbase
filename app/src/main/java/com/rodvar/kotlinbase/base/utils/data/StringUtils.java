package com.rodvar.kotlinbase.base.utils.data;

import java.nio.charset.Charset;
import java.util.Arrays;

import com.rodvar.kotlinbase.base.utils.android.Logger;

/**
 * source: https://stackoverflow.com/questions/3911966/how-to-convert-number-to-words-in-java
 */
public class StringUtils {
    private static final String NO_VALUE = null;
    private static final String TAG = StringUtils.class.getSimpleName();

    public static boolean isEmpty(final String string) {
        //noinspection StringEquality
        return string == NO_VALUE || "".equals(string);
    }

    public static String removeInvisibleChars(final String input) {
        if (Logger.isDebugEnabled(TAG)) {
            Logger.debug(TAG, "Input: '" + input + "' => " + Arrays.toString(input.getBytes(Charset.forName("utf8"))));
        }

        return input.replaceAll("(\\n|\\r|\\t)+", "");
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty())
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String toTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }
        return sb.toString().trim();
    }
}
