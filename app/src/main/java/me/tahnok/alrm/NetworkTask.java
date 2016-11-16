package me.tahnok.alrm;

import android.os.AsyncTask;
import android.util.Log;

public class NetworkTask extends AsyncTask<Object, Void, Void> {

    private static final int TEN_SECONDS = 10 * 1000;
    private static final String LOG_TAG = "NetworkTask";

    @Override
    protected final Void doInBackground(Object... params) {
        if (! (params[0] instanceof  ClockClient) || !(params[1] instanceof  ClockClient.Command)) {
            throw new RuntimeException("Got wrong kind of arguments");
        }
        try {
            Thread.sleep(TEN_SECONDS);
        } catch (InterruptedException e) {
            Log.e(LOG_TAG, "couldn't sleep", e);
        }
        ClockClient clockClient = (ClockClient) params[0];
        ClockClient.Command command = (ClockClient.Command) params[1];
        clockClient.sendCommand(command);
        return null;
    }

    public void execute(ClockClient clockClient, ClockClient.Command command) {
        execute(clockClient, command, "hack");
    }


}
