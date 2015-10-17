package fi.ptm.serviceexample;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by PTM on 17/10/15.
 */
public class MyService extends Service {
    public static final String NEW_DATA_FOUND = "fi.ptm.serviceexample.NEW_DATA_FOUND";
    private Timer updateTimer;

    @Override
    public void onCreate() {
        Log.d("MyService","onCreate()");
        // create a new timer task to refresh data
        updateTimer = new Timer("dataUpdates");
        updateTimer.scheduleAtFixedRate(doRefresh, 0, 5000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d("MyService","onStartCommand()");
        // service is explicitly started and stopped as needed
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("MyService","onBind()");
        // don't bind service and activity together
        return null;
    }

    @Override
    public void onDestroy() {
        updateTimer.cancel();
        Log.d("MyService","onDestroy()");
        super.onDestroy();
    }

    private TimerTask doRefresh = new TimerTask() {
        public void run() {
            refreshData();
        }
    };

    // demonstrate a new data available here in service
    // send intent back to main app
    private void refreshData() {
        Log.d("MyService","refreshData()");
        Intent intent = new Intent(NEW_DATA_FOUND);
        intent.putExtra("data",Math.random());
        sendBroadcast(intent);
    }
}
