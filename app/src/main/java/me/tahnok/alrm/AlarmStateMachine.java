package me.tahnok.alrm;

import static me.tahnok.alrm.AlarmStateMachine.State.STARTING;

public class AlarmStateMachine {

    public void reset() {
        state = STARTING;
    }

    private State state;

    public State getState() {
        return state;
    }

    public void step() {
        switch (state) {
            case STARTING:
                state = State.LIGHT;
                break;
            case LIGHT:
                state = State.SOUND;
                break;
            case SOUND:
                state = State.RINGING;
                break;
            case RINGING:
                break;
            default:
            case STOPPED:
                throw new IllegalStateException("Can't step state when STOPPED");
        }
    }

    public void stop() {
        state = State.STOPPED;
    }

    public AlarmStateMachine() {
        state = STARTING;
    }

    public enum State {
        STARTING,
        LIGHT,
        SOUND,
        RINGING,
        STOPPED
    }
}
