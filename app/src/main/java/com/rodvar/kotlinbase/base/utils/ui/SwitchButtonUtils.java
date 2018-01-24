package com.rodvar.kotlinbase.base.utils.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;

/**
 * Created by rodvar on 31/7/17.
 * <p>
 * Helper methods to configure Switch Buttons
 */
public class SwitchButtonUtils {

    /**
     * If the toggle does not exists in the container, it creates a new one and add it in.
     *
     * @param context       where this setup is done (generally the activity involved)
     * @param container     the toggle buttons container
     * @param label         the label for the toggle, used as the toggle id as well
     * @param checked       should be checked?
     * @param toggleChanged on toggle change listener (what to do when the ui component is toggeled)
     * @param layoutResId   id of the layout to be use on instantiation of the element
     */
    public static void updateToggleSetting(final Context context, LinearLayout container, String label, boolean checked,
                                           CompoundButton.OnCheckedChangeListener toggleChanged,
                                           final int layoutResId) {
        RelativeLayout toggleSetting = container.findViewWithTag(label);
        if (toggleSetting == null) {
            toggleSetting = (RelativeLayout) LayoutInflater.from(context)
                    .inflate(layoutResId, null);
            container.addView(toggleSetting);
        }
        toggleSetting.setTag(label);

        ((TextView) toggleSetting.getChildAt(0)).setText(label);
        SwitchButton switchToggle = (SwitchButton) toggleSetting.getChildAt(1);
        switchToggle.setChecked(checked);
        switchToggle.setTag(label);
        switchToggle.setOnCheckedChangeListener(toggleChanged);
    }
}
