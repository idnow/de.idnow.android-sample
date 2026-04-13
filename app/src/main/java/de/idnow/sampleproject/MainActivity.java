package de.idnow.sampleproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import de.idnow.R;
import de.idnow.sdk.IDnowSDK;
import de.idnow.sdk.util.IDnowErrorCode;

public class MainActivity extends Activity {

    private Context context;
    private String TAG = "IDNowSDK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        context = this;

        Button startVideoIdentButton = findViewById(R.id.buttonStartVideoIdent);
        DrawableUtils.setProceedButtonBackgroundSelector(startVideoIdentButton);
        startVideoIdentButton.setOnClickListener(v -> {
            try {
                IDnowSDK.getInstance().initialize(MainActivity.this, "");
                IDnowSDK.setShowVideoOverviewCheck(true, context);
                IDnowSDK.setShowErrorSuccessScreen(true, context);
                IDnowSDK.setOverrideEntryActivity(true);

                // need to be changed to your own token as described in API documentation, see https://www.idnow.eu/development/api-documentation/
                IDnowSDK.setTransactionToken("XXX-XXXX");

                IDnowSDK.getInstance().start(IDnowSDK.getTransactionToken());
            } catch (Exception e) {
                // exception handling required
                Log.e(TAG, "SDK initialization failed: ", e);
            }
        });
    }

    /**
     * Callback from the SDK
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String resultMessage = "";
        if (requestCode == IDnowSDK.REQUEST_ID_NOW_SDK) {
            if (resultCode == IDnowSDK.RESULT_CODE_SUCCESS) {
                if (data != null) {
                    String transactionToken = data.getStringExtra(IDnowSDK.RESULT_DATA_TRANSACTION_TOKEN);
                    resultMessage = "success, transaction token: " + transactionToken;
                }
            } else if (resultCode == IDnowSDK.RESULT_CODE_CANCEL) {
                if (data != null) {
                    String transactionToken = data.getStringExtra(IDnowSDK.RESULT_DATA_TRANSACTION_TOKEN);
                    IDnowErrorCode errorCode = (IDnowErrorCode) data.getSerializableExtra(IDnowSDK.RESULT_ERROR_CODE);
                    String errorMessage = data.getStringExtra(IDnowSDK.RESULT_DATA_ERROR);
                    int serverCode = data.getIntExtra(IDnowSDK.RESULT_SERVER_STATUS_CODE, 0);
                    resultMessage = "failed, transaction token: " + transactionToken + ", error: "
                            + errorMessage + "Error code: " + errorCode.toString() + " Server status code: " + serverCode;
                }
            } else if (resultCode == IDnowSDK.RESULT_CODE_FAILED) {
                if (data != null) {
                    String transactionToken = data.getStringExtra(IDnowSDK.RESULT_DATA_TRANSACTION_TOKEN);
                    String errorMessage = data.getStringExtra(IDnowSDK.RESULT_DATA_ERROR);
                    resultMessage = "failed, transaction token: " + transactionToken + ", error: " + errorMessage;
                }
            } else if (resultCode == IDnowSDK.RESULT_USER_IN_QUEUE) {
               resultMessage = "User enrolled into the waiting list and will be notified via SMS ";
            } else {
                resultMessage = "Result Code: " + resultCode;
            }
            Toast.makeText(this, resultMessage, Toast.LENGTH_LONG).show();
            Log.i(TAG, resultMessage);
        }
    }
}
