package me.tahnok.wesclock;

import android.os.AsyncTask;
import android.util.Pair;

/**
 * Created by wes on 2016-10-29.
 */

public class NetworkTalkerTask extends AsyncTask<Pair<NetworkTalker, NetworkTalker.Command>, Void, Void> {

    @Override
    protected Void doInBackground(Pair<NetworkTalker, NetworkTalker.Command>... networkTalkers) {
        Pair<NetworkTalker, NetworkTalker.Command> args = networkTalkers[0];
        NetworkTalker networkTalker = args.first;
        networkTalker.sendCommand(args.second);
        return null;
    }
}
