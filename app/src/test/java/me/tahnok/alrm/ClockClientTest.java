package me.tahnok.alrm;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class ClockClientTest {

    private ClockClient clockClient;

    @Before
    public void setup() {
        clockClient = new ClockClient(mock(ClockClient.Delegate.class));
    }

    @Test
    public void canTurnOnAndOff() throws InterruptedException {
        clockClient.sendCommand(ClockClient.Command.TURN_ON);
        Thread.sleep(8000);
        clockClient.sendCommand(ClockClient.Command.TURN_OFF);
    }

}