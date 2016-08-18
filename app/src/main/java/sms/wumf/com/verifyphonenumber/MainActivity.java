package sms.wumf.com.verifyphonenumber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String PHONE_NUMBER = "phone_number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String phoneNumber = getIntent().getStringExtra(PHONE_NUMBER);
        Toast.makeText(this, phoneNumber, Toast.LENGTH_LONG).show();
    }

}
