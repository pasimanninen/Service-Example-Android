package fi.ptm.serviceexample;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by PTM on 17/10/15.
 */
public class MainActivity extends Activity {
    private MyDataReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // explicitly start my service
        startService(new Intent(this, MyService.class));
        // create intent filter
        IntentFilter filter = new IntentFilter(MyService.NEW_DATA_FOUND);
        // register intent receiver
        receiver = new MyDataReceiver();
        registerReceiver(receiver, filter);
    }

    @Override
    public void onDestroy() {
        // unregister receiver
        unregisterReceiver(receiver);
        // stop service
        stopService(new Intent(this, MyService.class));
        super.onDestroy();
    }

    // inner class handles intent broadcast from service
    class MyDataReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            TextView textView = (TextView) findViewById(R.id.textView);
            Bundle bundle = intent.getExtras();
            double data = bundle.getDouble("data");
            textView.append("\n- " + data);
        }
    }
}
