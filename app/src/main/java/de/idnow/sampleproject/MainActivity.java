package de.idnow.sampleproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import de.idnow.R;
import de.idnow.sdk.IDnowSDK;

public class MainActivity extends Activity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        context = this;

        Button startVideoIdentButton = findViewById(R.id.buttonStartVideoIdent);
        DrawableUtils.setProceedButtonBackgroundSelector(startVideoIdentButton);
        startVideoIdentButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    IDnowSDK.getInstance().initialize(MainActivity.this, "");
                    IDnowSDK.setShowVideoOverviewCheck(true, context);
                    IDnowSDK.setShowErrorSuccessScreen(true, context);
                    IDnowSDK.disableLogging();
                    // need to be changed to your own token as described in API documentation, see https://www.idnow.eu/development/api-documentation/
                    IDnowSDK.setTransactionToken("TST-UUHHJ");

                    IDnowSDK.getInstance().start(IDnowSDK.getTransactionToken());
                } catch (Exception e) {
                    // exception handling required
                    e.printStackTrace();
                }
            }
        });

        Button startPhotoIdentButton = findViewById(R.id.buttonStartPhotoIdent);
        DrawableUtils.setProceedButtonBackgroundSelector(startPhotoIdentButton);
        startPhotoIdentButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    IDnowSDK.getInstance().initialize(MainActivity.this, "idnow");
                    IDnowSDK.setShowVideoOverviewCheck(true, context);
                    IDnowSDK.setShowErrorSuccessScreen(true, context);

                    // need to be changed to your own token as described in API documentation, see https://www.idnow.eu/development/api-documentation/

                    IDnowSDK.setTransactionToken("TST-WLJWN");
                    IDnowSDK.getInstance().start(IDnowSDK.getTransactionToken());
                } catch (Exception e) {
                    // exception handling required
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Callback from the SDK
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IDnowSDK.REQUEST_ID_NOW_SDK) {
            StringBuilder toastText;

            switch (resultCode) {

                case IDnowSDK.RESULT_CODE_SUCCESS:
                    toastText = new StringBuilder("Identification performed. ");
                    if (null != data) {
                        toastText.append(data.getStringExtra(IDnowSDK.RESULT_DATA_TRANSACTION_TOKEN));
                    }
                    break;

                case IDnowSDK.RESULT_CODE_CANCEL:
                    toastText = new StringBuilder("Identification canceled. ");
                    if (null != data) {
                        toastText.append(data.getStringExtra(IDnowSDK.RESULT_DATA_ERROR));
                    }
                    break;

                case IDnowSDK.RESULT_CODE_FAILED:
                    toastText = new StringBuilder("Identification failed. ");
                    if (null != data) {
                        toastText.append(data.getStringExtra(IDnowSDK.RESULT_DATA_ERROR));
                    }
                    break;

                default:
                    toastText = new StringBuilder("Result Code: ");
                    toastText.append(resultCode);
            }

            Toast.makeText(this, toastText.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
