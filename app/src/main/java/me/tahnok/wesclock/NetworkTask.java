package me.tahnok.wesclock;

import android.os.AsyncTask;
import android.util.Pair;

public class NetworkTask extends AsyncTask<Pair<Network, Network.Command>, Void, Void> {

    @SafeVarargs
    @Override
    protected final Void doInBackground(Pair<Network, Network.Command>... networkTalkers) {
        Pair<Network, Network.Command> args = networkTalkers[0];
        Network network = args.first;
        network.sendCommand(args.second);
        return null;
    }
}
