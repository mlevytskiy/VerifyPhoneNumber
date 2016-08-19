package sms.wumf.com.verifyphonenumber;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;

import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    private final static long WAIT_TIME = TimeUnit.SECONDS.toMillis(20);
    private static final String PHONE_NUMBER = "phone_number";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String phoneNumber = getIntent().getStringExtra(PHONE_NUMBER);
        editText = (EditText) findViewById(R.id.edit_text);
        editText.setText(phoneNumber);
        setResult(RESULT_CANCELED);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, WAIT_TIME);
    }

    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(ReceiveSmsEvent event) {
        setResult(RESULT_OK);
        finish();
    }

    public void onClickVerify(View view) {
        int pin = ((VerifyPhoneNumberApplication) getApplication()).pin = generatePin();
        sendSms(editText.getText().toString(), String.valueOf(pin));
    }

    private int generatePin() {
        return (int)(Math.random()*9000)+1000;
    }

    private static final int MAX_SMS_MESSAGE_LENGTH = 160;
    private static final int SMS_PORT = 8901;
    private static final String SMS_DELIVERED = "SMS_DELIVERED";
    private static final String SMS_SENT = "SMS_SENT";

    private void sendSms(String phonenumber,String message) {

        SmsManager manager = SmsManager.getDefault();
        PendingIntent piSend = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
        PendingIntent piDelivered = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);

        byte[] data = new byte[message.length()];

        for(int index=0; index<message.length() && index < MAX_SMS_MESSAGE_LENGTH; ++index) {
            data[index] = (byte)message.charAt(index);
        }

        manager.sendDataMessage(phonenumber, null, (short) SMS_PORT, data, piSend, piDelivered);

    }


}
