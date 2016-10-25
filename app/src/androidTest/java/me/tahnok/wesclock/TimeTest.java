package me.tahnok.wesclock;

import org.junit.Test;

import java.util.Calendar;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by wes on 2016-10-24.
 */
public class TimeTest {

    @Test
    public void testToString() {
        Time time = new Time(4, 23);
        assertEquals("04:23", time.toString());
    }

    @Test
    public void testNextOccurrenceTomorrow() {
        Time time = new Time(4, 23);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR, 5);
        calendar.set(Calendar.HOUR, 5);
        int date = calendar.get(Calendar.DATE);

        long futureOccurrence = time.getFutureOccurrence(calendar);

        Calendar futureCalendar = Calendar.getInstance();
        futureCalendar.setTimeInMillis(futureOccurrence);

        assertEquals(4, futureCalendar.get(Calendar.HOUR));
        assertEquals(23, futureCalendar.get(Calendar.MINUTE));
        assertEquals(date + 1, futureCalendar.get(Calendar.DATE));
        assertTrue(futureCalendar.compareTo(calendar) > 0);
    }

    @Test
    public void testNextOccurrenceToday() {
        Time time = new Time(4, 23);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR, 3);
        calendar.set(Calendar.HOUR, 5);
        int date = calendar.get(Calendar.DATE);

        long futureOccurrence = time.getFutureOccurrence(calendar);

        Calendar futureCalendar = Calendar.getInstance();
        futureCalendar.setTimeInMillis(futureOccurrence);

        assertEquals(4, futureCalendar.get(Calendar.HOUR));
        assertEquals(23, futureCalendar.get(Calendar.MINUTE));
        assertEquals(date, futureCalendar.get(Calendar.DATE));
        assertTrue(futureCalendar.compareTo(calendar) > 0);
    }

}