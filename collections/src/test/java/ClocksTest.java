

import com.collections.Clocks;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


import static org.testng.Assert.*;

public class ClocksTest {
    private final String timezoneId = "America/New_York";
    private final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    private final String dateTimeZoneFormat = "dd/MM/yyyy HH:mm:ss zzz";
    private final String dateFormat = "dd-MM-yyyy";

    @Test
    public void testNowDateTime() {
        var nowDate = Clocks.dateNow();
        System.out.println("The date is: " + nowDate);
        assertNotNull(nowDate);
        var nowDateTime = Clocks.dateTimeNow();
        System.out.println("The date and time is: " + nowDateTime);
        assertNotNull(nowDateTime);
        var nowDateTimeZone = Clocks.dateTimeNow(timezoneId);
        System.out.println("The date and time with timezone is: " + nowDateTimeZone);
        assertNotNull(nowDateTimeZone);
    }

    @Test
    public void testNowDateTimeWithTimezone() {
        var nowDateTime = Clocks.dateTimeNow(timezoneId);
        System.out.println("The date and time with timezone is: " + nowDateTime);
        assertNotNull(nowDateTime);
        assertEquals(nowDateTime.getZone(), ZoneId.of(timezoneId));
    }

    @Test
    public void testDate() {
        String date = Clocks.date("");
        System.out.println("The date is: " + date);
        assertNotNull(date);
        assertEquals(date, LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        String date1 = Clocks.date(dateFormat);
        System.out.println("The date is: " + date1);
        assertNotNull(date1);
        assertEquals(date1, LocalDate.now().format(DateTimeFormatter.ofPattern(dateFormat)));
    }

    @Test
    public void testDateOf() {
        String dateString = "31-03-2023";
        LocalDate date = Clocks.parseDate(dateString, dateFormat);
        System.out.println("The date is: " + date);
        assertNotNull(date);
        assertEquals(date, LocalDate.of(2023, 3, 31));
    }

    @Test
    public void testDateTime() {
        String dateTime = Clocks.dateTime(dateTimeFormat);
        System.out.println("The date and time is: " + dateTime);
        assertNotNull(dateTime);
        assertEquals(dateTime, LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateTimeFormat)));
    }

    @Test
    public void testDateTimeWithTimezone() {
        String dateTime = Clocks.dateTime(timezoneId, dateTimeZoneFormat);
        System.out.println("The date and time with timezone is: " + dateTime);
        assertNotNull(dateTime);
        assertEquals(dateTime,
                ZonedDateTime.now(ZoneId.of(timezoneId)).format(DateTimeFormatter.ofPattern(dateTimeZoneFormat)));
    }

    @Test
    public void testDateTimeOfLocalDateTime() {
        String dateTime = "2023-03-31 09:15:30";
        var expectedDateTime = LocalDateTime.of(2023, 3, 31, 9, 15, 30);
        System.out.println("The date and time is: " + expectedDateTime);
        var actualDateTime = Clocks.parseDateTime(dateTime, dateTimeFormat);
        System.out.println("The date and time is: " + actualDateTime);
        assertEquals(actualDateTime, expectedDateTime);
    }

    @Test
    public void testDateTimeOfZonedDateTime() {
        String dateTimeZone = "16/07/2017 19:28:33 America/New_York";
        var expectedDateTime = ZonedDateTime.of(2017, 7, 16, 19, 28, 33, 0, ZoneId.of("America/New_York"));
        var actualDateTime = Clocks.parseDateTimeZone(dateTimeZone, dateTimeZoneFormat);
        assertEquals(actualDateTime, expectedDateTime);
    }

    @Test
    public void testDifference() {
        var start = LocalDateTime.of(2023, 3, 30, 10, 0);
        var end = LocalDateTime.of(2023, 3, 31, 14, 30);

        long daysDiff = Clocks.difference(start, end, "days");
        System.out.println("The difference in days is: " + daysDiff);
        assertEquals(daysDiff, 1);
        long hoursDiff = Clocks.difference(start, end, "hours");
        System.out.println("The difference in hours is: " + hoursDiff);
        assertEquals(hoursDiff, 28);
        long minutesDiff = Clocks.difference(start, end, "minutes");
        System.out.println("The difference in minutes is: " + minutesDiff);
        assertEquals(minutesDiff, 1710);
        long secondsDiff = Clocks.difference(start, end, "seconds");
        System.out.println("The difference in seconds is: " + secondsDiff);
        assertEquals(secondsDiff, 102600);
    }

    @Test
    public void testAdjustment() {
        var inputDate = LocalDate.of(2022, 3, 7);
        var expectedDate = LocalDate.of(2022, 3, 31);
        var actualDate = Clocks.adjust(inputDate, "lastDayOfMonth");
        assertEquals(actualDate, expectedDate);

        var firstDayOfYear = Clocks.adjust(LocalDateTime.now(), "firstDayOfYear");
        assertEquals(firstDayOfYear.toLocalDate(), LocalDate.of(firstDayOfYear.getYear(), 1, 1));
    }

    @Test
    public void testNext() {
        LocalDate date = LocalDate.now();
        System.out.println("The date is: " + date);
        LocalDate nextDay = Clocks.next(date, 1, "days");
        System.out.println("The next day is: " + nextDay);
        assertEquals(nextDay, date.plusDays(1));
        LocalDate nextWeek = Clocks.next(date, 1, "weeks");
        System.out.println("The next week is: " + nextWeek);
        assertEquals(nextWeek, date.plusWeeks(1));
        LocalDate today = LocalDate.now();
        System.out.println("The date is: " + today);
        LocalDate tomorrow = Clocks.next(today, 1, "days");
        System.out.println("The next day is: " + tomorrow);
        assertEquals(tomorrow, today.plusDays(1));
    }

    @Test
    public void testPrevious() {
        LocalDate date = LocalDate.now();
        LocalDate previousDay = Clocks.previous(date, 1, "days");
        assertEquals(previousDay, date.minusDays(1));
        LocalDate previousWeek = Clocks.previous(date, 1, "weeks");
        assertEquals(previousWeek, date.minusWeeks(1));
        LocalDate today = LocalDate.now();
        LocalDate yesterday = Clocks.previous(today, 1, "days");
        assertEquals(yesterday, today.minusDays(1));
    }

    @Test
    public void testWeekend() {
        var inputDate = LocalDate.of(2023, 4, 8);
        assertTrue(Clocks.isWeekend(inputDate));
    }
}
