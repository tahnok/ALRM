package me.tahnok.alrm;

import org.junit.Test;

import java.util.Calendar;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by wes on 2016-10-24.
 */
public class AlarmTest {

    @Test
    public void testToString() {
        Alarm alarm = new Alarm(4, 23);
        assertEquals("04:23", alarm.toString());
    }

    @Test
    public void testNextOccurrenceTomorrow() {
        Alarm alarm = new Alarm(4, 23);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR, 5);
        calendar.set(Calendar.MINUTE, 5);
        int date = calendar.get(Calendar.DATE);

        long futureOccurrence = alarm.getFutureOccurrence(calendar);

        Calendar futureCalendar = Calendar.getInstance();
        futureCalendar.setTimeInMillis(futureOccurrence);

        assertEquals(4, futureCalendar.get(Calendar.HOUR));
        assertEquals(23, futureCalendar.get(Calendar.MINUTE));
        assertEquals(0, futureCalendar.get(Calendar.SECOND));
        assertEquals(date + 1, futureCalendar.get(Calendar.DATE));
        assertTrue(futureCalendar.compareTo(calendar) > 0);
    }

    @Test
    public void testNextOccurrenceToday() {
        Alarm alarm = new Alarm(4, 23);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR, 3);
        calendar.set(Calendar.MINUTE, 5);
        int date = calendar.get(Calendar.DATE);

        long futureOccurrence = alarm.getFutureOccurrence(calendar);

        Calendar futureCalendar = Calendar.getInstance();
        futureCalendar.setTimeInMillis(futureOccurrence);

        assertEquals(4, futureCalendar.get(Calendar.HOUR));
        assertEquals(23, futureCalendar.get(Calendar.MINUTE));
        assertEquals(0, futureCalendar.get(Calendar.SECOND));
        assertEquals(date, futureCalendar.get(Calendar.DATE));
        assertTrue(futureCalendar.compareTo(calendar) > 0);
    }

}