package sms.wumf.com.verifyphonenumber;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import de.greenrobot.event.EventBus;

/**
 * Created by maks on 8/19/16.
 * email: m.levytskiy@gmail.com
 */
public class MySMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        EventBus.getDefault().post(new ReceiveSmsEvent());
    }

}
