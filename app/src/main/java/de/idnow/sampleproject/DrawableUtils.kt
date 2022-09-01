package de.idnow.sampleproject

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import de.idnow.R

object DrawableUtils {
    fun setProceedButtonBackgroundSelector(view: View) {
        val buttonColor = view.resources.getColor(R.color.proceed_button_background)
        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(
            intArrayOf(android.R.attr.state_pressed),
            ColorDrawable(getPressedButtonStateColor(buttonColor))
        )
        stateListDrawable.addState(intArrayOf(android.R.attr.state_enabled), ColorDrawable(buttonColor))
        stateListDrawable.addState(intArrayOf(), ColorDrawable(getInactiveButtonStateColor(buttonColor)))
        view.background = stateListDrawable
    }

    private fun getInactiveButtonStateColor(buttonColor: Int): Int {
        return Color.argb(119, Color.red(buttonColor), Color.green(buttonColor), Color.blue(buttonColor))
    }

    private fun getPressedButtonStateColor(buttonColor: Int): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(buttonColor, hsv)

        // reduce brightness by 12%
        hsv[2] -= 0.12.toFloat()
        if (hsv[2] < 0) {
            hsv[2] = 0f
        }
        return Color.HSVToColor(hsv)
    }
}