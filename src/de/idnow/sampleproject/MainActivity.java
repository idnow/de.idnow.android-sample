package de.idnow.sampleproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import de.idnow.sdk.IDnowSDK;

public class MainActivity extends Activity
{
	private Context	context;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );

		context = this;

		Button startVideoIdentButton = ( Button ) findViewById( R.id.buttonStartVideoIdent );
		Drawable_Utils.setProceedButtonBackgroundSelector( startVideoIdentButton );
		startVideoIdentButton.setOnClickListener( new OnClickListener()
		{

			@Override
			public void onClick( View v )
			{
				try
				{

					IDnowSDK.getInstance().initialize( MainActivity.this, "ihrebank" );
					IDnowSDK.setTransactionToken( "DEV-TXJKC", context );
					IDnowSDK.setShowVideoOverviewCheck( true, context );
					IDnowSDK.setShowErrorSuccessScreen( true, context );
					IDnowSDK.getInstance().start( IDnowSDK.getTransactionToken( context ) );

				}
				catch ( Exception e )
				{
					e.printStackTrace();
				}
			}
		} );

		Button startPhotoIdentButton = ( Button ) findViewById( R.id.buttonStartPhotoIdent );
		Drawable_Utils.setProceedButtonBackgroundSelector( startPhotoIdentButton );
		startPhotoIdentButton.setOnClickListener( new OnClickListener()
		{

			@Override
			public void onClick( View v )
			{
				try
				{
					IDnowSDK.getInstance().initialize( MainActivity.this, "idnow" );
					IDnowSDK.setTransactionToken( "DEV-LDFRG", context );
					IDnowSDK.setShowVideoOverviewCheck( true, context );
					IDnowSDK.setShowErrorSuccessScreen( true, context );

					IDnowSDK.getInstance().start( IDnowSDK.getTransactionToken( context ) );

				}
				catch ( Exception e )
				{
					e.printStackTrace();
				}
			}
		} );
	}

	/**
	 * Callback from the SDK
	 */
	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data )
	{
		if ( requestCode == IDnowSDK.REQUEST_ID_NOW_SDK )
		{
			if ( resultCode == IDnowSDK.RESULT_CODE_SUCCESS )
			{
				StringBuilder toastText = new StringBuilder( "Identification performed. " );
				if ( null != data )
				{
					toastText.append( data.getStringExtra( IDnowSDK.RESULT_DATA_TRANSACTION_TOKEN ) );
				}
				Toast.makeText( this, toastText.toString(), Toast.LENGTH_LONG ).show();
			}
			else if ( resultCode == IDnowSDK.RESULT_CODE_CANCEL )
			{
				StringBuilder toastText = new StringBuilder( "Identification canceled. " );
				if ( null != data )
				{
					toastText.append( data.getStringExtra( IDnowSDK.RESULT_DATA_ERROR ) );
				}
				Toast.makeText( this, toastText.toString(), Toast.LENGTH_LONG ).show();
			}
			else if ( resultCode == IDnowSDK.RESULT_CODE_FAILED )
			{
				StringBuilder toastText = new StringBuilder( "Identification failed. " );
				if ( null != data )
				{
					toastText.append( data.getStringExtra( IDnowSDK.RESULT_DATA_ERROR ) );
				}
				Toast.makeText( this, toastText.toString(), Toast.LENGTH_LONG ).show();
			}
			else
			{
				StringBuilder toastText = new StringBuilder( "Result Code: " );
				toastText.append( resultCode );
				Toast.makeText( this, toastText.toString(), Toast.LENGTH_LONG ).show();
			}
		}
	}
}
