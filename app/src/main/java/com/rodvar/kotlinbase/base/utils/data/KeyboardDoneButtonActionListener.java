package com.rodvar.kotlinbase.base.utils.data;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.util.concurrent.Callable;

public class KeyboardDoneButtonActionListener implements OnEditorActionListener {
    private static final KeyEvent NO_KEY_EVENT = null;

    private final Callable<Boolean> onDonePressed;

    public KeyboardDoneButtonActionListener(final Callable<Boolean> onDonePressed) {
        this.onDonePressed = onDonePressed;
    }

    @Override
    public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent keyEvent) {
        if ((keyEvent != NO_KEY_EVENT && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) ||
                (actionId == EditorInfo.IME_ACTION_DONE)) {
            try {
                return onDonePressed.call();
            } catch (Exception e) {
                return false;
            }
        }

        return false;
    }
}
