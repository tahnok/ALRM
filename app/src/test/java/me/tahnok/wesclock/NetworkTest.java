package me.tahnok.wesclock;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class NetworkTest {

    private Network network;

    @Before
    public void setup() {
        network = new Network(mock(Network.Delegate.class));
    }

    @Test
    public void canTurnOnAndOff() throws InterruptedException {
        network.sendCommand(Network.Command.TURN_ON);
        Thread.sleep(8000);
        network.sendCommand(Network.Command.TURN_OFF);
    }

}