package me.tahnok.alrm;

import android.os.AsyncTask;

public class NetworkTask extends AsyncTask<Object, Void, Void> {

    @Override
    protected final Void doInBackground(Object... params) {
        if (! (params[0] instanceof  ClockClient) || !(params[1] instanceof  ClockClient.Command)) {
            throw new RuntimeException("Got wrong kind of arguments");
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
