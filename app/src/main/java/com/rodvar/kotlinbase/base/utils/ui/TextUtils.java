package com.rodvar.kotlinbase.base.utils.ui;

import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UpdateAppearance;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.Callable;

import com.rodvar.kotlinbase.R;
import com.rodvar.kotlinbase.base.utils.android.Logger;
import com.rodvar.kotlinbase.base.utils.data.EmojiExcludeFilter;
import com.rodvar.kotlinbase.base.utils.data.KeyboardDoneButtonActionListener;
import com.rodvar.kotlinbase.base.utils.data.NoEnterKeyListener;

/**
 * Created by rodvar on 20/3/17.
 * utilitary methods to execute on textviews texts
 */
public class TextUtils {

    public static final int DEFAULT_MAX_CHARS = 20;
    private static final java.lang.String SPACE = " ";

    /**
     * Bolds toBold text in the specified color
     *
     * @param textView
     * @param original
     * @param toBold
     * @param color
     */
    public static void bold(TextView textView, String original, String toBold, int color) {
        try {
            final int index = original.indexOf(toBold);
            updateAppearance(textView, original, index, index + toBold.length(),
                    new ForegroundColorSpan(color), false);
        } catch (Exception e) {
            Logger.error(TextUtils.class.getSimpleName(), "Failed to bold text: ", e);
        }
    }

    public static void clickable(TextView textView, String original, String toClickable,
                                 final View.OnClickListener listener) {
        clickable(textView, original, toClickable, listener, false);
    }


    /**
     * Makes the toClickable text clickable using the default app color.
     *
     * @param textView
     * @param original
     * @param toClickable
     * @param listener
     * @param error       needs to update error field?
     */
    public static void clickable(TextView textView, String original, String toClickable,
                                 final View.OnClickListener listener, boolean error) {
        try {
            final int index = original.indexOf(toClickable);
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    listener.onClick(textView);
                }
            };
            updateAppearance(textView, original, index, index + toClickable.length(), clickableSpan
                    , error);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        } catch (Exception e) {
            Logger.error(TextUtils.class.getSimpleName(), "Failed to clickable text: ", e);
        }
    }

    /**
     * @param textView
     * @param original
     * @param indexFrom
     * @param indexTo
     * @param span
     * @param error     updates error field if true, otherwise just set text
     */
    private static void updateAppearance(TextView textView, String original, int indexFrom,
                                         int indexTo, UpdateAppearance span, boolean error) {
        SpannableString spannableString = hasSpannable(textView) ? (SpannableString) textView.getTag(R.integer.spannable_id)
                : new SpannableString(original);
        spannableString.setSpan(span, indexFrom, indexTo, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        if (error) {
            try {
                TextInputLayout textInputLayout = TextUtils.obtainTextInputLayout(textView);
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setError(spannableString);
            } catch (Exception e) {
                Logger.error(TextUtils.class.getSimpleName(), "Failed to iupdate appearance", e);
                textView.setError(spannableString);
            }
        } else
            textView.setText(spannableString);
        textView.setTag(R.integer.spannable_id, spannableString);
    }

    /**
     * @param sourceView from where to get the parent text input layout
     * @return the parent input text view layout
     */
    public static
    @Nullable
    TextInputLayout obtainTextInputLayout(final TextView sourceView) {
        if (sourceView.getParent().getClass().equals(TextInputLayout.class))
            return (TextInputLayout) sourceView.getParent();
        else
            return (TextInputLayout) sourceView.getParent().getParent();
    }

    private static boolean hasSpannable(TextView textView) {
        return textView.getTag(R.integer.spannable_id) != null;
    }

    /**
     * @param editText
     * @param maxChars
     */
    public static void noEmojisWithLimitedChars(EditText editText, int maxChars) {
        editText.setFilters(new InputFilter[]{new EmojiExcludeFilter(),
                new InputFilter.LengthFilter(maxChars)});
    }

    /**
     * Use this to make the soft keyboard enter execute a next
     *
     * @param editText
     * @param inputType
     */
    public static void configureAsNextButton(EditText editText, int inputType) {
        editText.setKeyListener(new NoEnterKeyListener(inputType));
    }

    public static void configureAsDoneButton(EditText editText, Callable<Boolean> callable) {
        editText.setOnEditorActionListener(new KeyboardDoneButtonActionListener(callable));
    }

    public static String capitalizeFirst(String fullText) {
        StringBuilder sb = new StringBuilder();
        String[] strings = fullText.split(SPACE);
        for (int i = 0; i < strings.length; i++)
            if (strings[i].length() > 1)
                sb.append(strings[i].toLowerCase().substring(0, 1).toUpperCase() +
                        strings[i].toLowerCase().substring(1) + SPACE);
        return sb.toString().substring(0, sb.length() - 1);
    }
}
