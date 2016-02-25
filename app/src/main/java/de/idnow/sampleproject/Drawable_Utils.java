package de.idnow.sampleproject;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;

import de.idnow.R;

/**
 * Created by Mathias Grasy on 14.10.2015.
 */
public class Drawable_Utils
{
	public static void setProceedButtonBackgroundSelector( View view )
	{
		int buttonColor = view.getResources().getColor( R.color.proceed_button_background );

		StateListDrawable stateListDrawable = new StateListDrawable();
		stateListDrawable.addState( new int[]{ android.R.attr.state_pressed }, new ColorDrawable( getPressedButtonStateColor( buttonColor ) ) );
		stateListDrawable.addState( new int[]{ android.R.attr.state_enabled }, new ColorDrawable( buttonColor ) );
		stateListDrawable.addState( new int[]{}, new ColorDrawable( getInactiveButtonStateColor( buttonColor ) ) );
		view.setBackgroundDrawable( stateListDrawable );
	}

	public static int getInactiveButtonStateColor( int buttonColor )
	{
		int transColor = Color.argb( 119, Color.red( buttonColor ), Color.green( buttonColor ), Color.blue( buttonColor ) );
		return transColor;
	}

	public static int getPressedButtonStateColor( int buttonColor )
	{
		float[] hsv = new float[ 3 ];
		Color.colorToHSV( buttonColor, hsv );

		// reduce brightness by 12%
		hsv[ 2 ] -= 0.12;
		if ( hsv[ 2 ] < 0 )
		{
			hsv[ 2 ] = 0f;
		}

		return Color.HSVToColor( hsv );
	}
}
