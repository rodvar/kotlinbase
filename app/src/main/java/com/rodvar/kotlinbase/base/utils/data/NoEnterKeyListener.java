package com.rodvar.kotlinbase.base.utils.data;

import android.app.Activity;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.rodvar.kotlinbase.base.utils.android.DeviceUtils;
import com.rodvar.kotlinbase.base.utils.android.Logger;

/**
 * Created by rodvar on 8/3/17.
 * Blocks the ENTER event for EditText
 */
public class NoEnterKeyListener implements KeyListener, View.OnKeyListener {

    private final int inputType;

    public NoEnterKeyListener(int inputType) {
        this.inputType = inputType;
    }

    @Override
    public int getInputType() {
        return this.inputType;
    }

    @Override
    public boolean onKeyDown(View view, Editable text, int keyCode, KeyEvent event) {
        // If the event is a key-down event on the "enter" button
        if ((event.getAction() == android.view.KeyEvent.ACTION_DOWN)) {
            boolean handled = false;
            if (keyCode == android.view.KeyEvent.KEYCODE_ENTER) {
                this.removeViewFocus(view);
                handled = true;
            } else if (keyCode == KeyEvent.KEYCODE_DEL) {
                this.deleteCurrentCharacter(view); // fix for samsung soft keyboards
                handled = true;
            }
            return handled;
        }
        return false;
    }

    public void deleteCurrentCharacter(View view) {
        try {
            EditText editText = (EditText) view;
            int length = editText.getText().length();
            if (length > 0)
                editText.getText().delete(editText.getSelectionStart() - 1, editText.getSelectionEnd());
        } catch (Exception e) {
            Logger.error(getClass().getSimpleName(), "Failed to delete las char", e);
        }
    }

    private void removeViewFocus(View view) {
        try {
            DeviceUtils.hideCurrentKeyboard((Activity) view.getContext());
        } catch (Exception e) {
            Logger.error(getClass().getSimpleName(), "Failed to hide current keyboard", e);
        }
    }

    @Override
    public boolean onKeyUp(View view, Editable text, int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKeyOther(View view, Editable text, android.view.KeyEvent event) {
        return false;
    }

    @Override
    public void clearMetaKeyState(View view, Editable content, int states) {

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return this.onKeyDown(v, null, keyCode, event);
    }
}
