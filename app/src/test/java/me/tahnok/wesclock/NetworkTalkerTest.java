package me.tahnok.wesclock;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class NetworkTalkerTest {

    private NetworkTalker networkTalker;

    @Before
    public void setup() {
        networkTalker = new NetworkTalker(mock(NetworkTalker.Delegate.class));
    }

    @Test
    public void canTurnOnAndOff() throws InterruptedException {
        networkTalker.sendCommand(NetworkTalker.Command.TURN_ON);
        Thread.sleep(8000);
        networkTalker.sendCommand(NetworkTalker.Command.TURN_OFF);
    }

}