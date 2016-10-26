package me.tahnok.wesclock;

import android.support.annotation.NonNull;

import java.util.Calendar;

public class Time {

    public long getFutureOccurance() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        return getFutureOccurrence(calendar);
    }

    public long getFutureOccurrence(Calendar calendar) {
        Calendar newCalendar = cloneCalendar(calendar);
        if (calendar.get(Calendar.HOUR_OF_DAY) > hour || (calendar.get(Calendar.HOUR_OF_DAY) == hour && calendar.get(Calendar.MINUTE) > minute)) {
            newCalendar.add(Calendar.DATE, 1);
        }
        newCalendar.set(Calendar.HOUR_OF_DAY, hour);
        newCalendar.set(Calendar.MINUTE, minute);
        return newCalendar.getTimeInMillis();
    }

    @NonNull
    private Calendar cloneCalendar(Calendar calendar) {
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTimeInMillis(calendar.getTimeInMillis());
        return newCalendar;
    }

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
