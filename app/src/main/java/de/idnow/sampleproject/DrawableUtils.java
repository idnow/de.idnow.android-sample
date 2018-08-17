package de.idnow.sampleproject;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;

import de.idnow.R;

public class DrawableUtils {

    public static void setProceedButtonBackgroundSelector(View view) {
        int buttonColor = view.getResources().getColor(R.color.proceed_button_background);

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(getPressedButtonStateColor(buttonColor)));
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, new ColorDrawable(buttonColor));
        stateListDrawable.addState(new int[]{}, new ColorDrawable(getInactiveButtonStateColor(buttonColor)));
        view.setBackground(stateListDrawable);
    }

    private static int getInactiveButtonStateColor(int buttonColor) {
        return Color.argb(119, Color.red(buttonColor), Color.green(buttonColor), Color.blue(buttonColor));
    }

    private static int getPressedButtonStateColor(int buttonColor) {
        float[] hsv = new float[3];
        Color.colorToHSV(buttonColor, hsv);

        // reduce brightness by 12%
        hsv[2] -= 0.12;
        if (hsv[2] < 0) {
            hsv[2] = 0f;
        }

        return Color.HSVToColor(hsv);
    }
}
