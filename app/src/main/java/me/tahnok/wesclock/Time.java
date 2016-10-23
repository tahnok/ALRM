package me.tahnok.wesclock;

public class Time {

    private final int hour;
    private final int minute;

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public String toString() {
        return String.format("%02d:%02d", this.hour, this.minute);
    }

}
