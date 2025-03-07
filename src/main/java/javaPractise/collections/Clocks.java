package com.collections;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.*;
import java.util.Map;
import java.util.function.BiFunction;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Optional.ofNullable;

/**
 * A final utility class that represents a clock.
 */
@UtilityClass
public final class Clocks {
    public static final String DATE_FORMAT = "MM/dd/yyyy";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FILE_FORMAT = "ddMMMyyyy-hh-mm-ss";
    public static final String TIMESTAMP_FORMAT = "MM/dd/yyyy hh:mm:ss";
    private static final Map<String, TemporalAdjuster> ADJUSTERS = Map.of(
            "firstDayOfMonth", TemporalAdjusters.firstDayOfMonth(),
            "lastDayOfMonth", TemporalAdjusters.lastDayOfMonth(),
            "firstDayOfNextMonth", TemporalAdjusters.firstDayOfNextMonth(),
            "firstDayOfYear", TemporalAdjusters.firstDayOfYear(),
            "lastDayOfYear", TemporalAdjusters.lastDayOfYear(),
            "firstDayOfNextYear", TemporalAdjusters.firstDayOfNextYear());
    private static final Map<String, TemporalUnit> UNIT_MAPPING = Map.of(
            "years", ChronoUnit.YEARS,
            "months", ChronoUnit.MONTHS,
            "weeks", ChronoUnit.WEEKS,
            "days", ChronoUnit.DAYS);

    /**
     * Return the current date.
     *
     * @return The current date.
     */
    public LocalDate dateNow() {
        return LocalDate.now();
    }

    /**
     * Return the current date and time.
     *
     * @return LocalDateTime.now()
     */
    public LocalDateTime dateTimeNow() {
        return LocalDateTime.now();
    }

    /**
     * Returns the current date and time in the specified timezone.
     *
     * @param timezoneId the timezone identifier, such as
     *                   "America/New_York".
     * @return the current date and time in the specified
     * timezone
     * @throws DateTimeException if the timezone identifier is invalid
     */
    public ZonedDateTime dateTimeNow(String timezoneId) {
        return ZonedDateTime.now()
                .withZoneSameInstant(ZoneId.of(timezoneId));
    }

    /**
     * > Returns the current date in the specified format
     *
     * @param format The format of the date.
     * @return A string representation of the current date in the format
     * specified.
     */
    public String date(final String format) {
        return format(dateNow(), format);
    }

    /**
     * Returns a LocalDate object of the given date and format.
     *
     * @param date   the date to be parsed.
     * @param format the format of the date string.
     * @return a LocalDate object.
     * @throws DateTimeException if the date could not be parsed with the given
     *                           format.
     */
    public LocalDate parseDate(final String date, final String format) {
        var dateTimeFormatter = dateTimeFormatter(format, DATE_FORMAT);
        try {
            return LocalDate.parse(date, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            String newFormat = StringHelper.isEmpty(format) ? DATE_FORMAT : format;
            throw new DateTimeParseException("Failed to parse date : "
                    + date + " with format: " + newFormat,
                    e.getParsedString(), e.getErrorIndex());
        }
    }

    /**
     * Returns the current date and time in the specified format.
     *
     * @param format the format of the date and time.
     * @return a string representation of the current date and time in
     * the specified format.
     */
    public String dateTime(final String format) {
        return format(dateTimeNow(), format);
    }

    /**
     * Returns the current date and time as a string representation in the
     * specified timezone and format.
     *
     * @param timezoneId The timezone ID to use for the date and time.
     * @param format     The format to use for the date and time.
     * @return A string representation of the current date and time
     * in the specified timezone and format.
     */
    public String dateTime(final String timezoneId, final String format) {
        return format(dateTimeNow(timezoneId), format);
    }

    /**
     * Parses a date-time string using the specified format string and returns a
     * {@link TemporalAccessor}.
     *
     * @param dateTime the date-time string to parse
     * @param format   the format string used to parse the date-time
     *                 string
     * @return a {@link TemporalAccessor} object representing
     * the parsed date-time string
     * @throws DateTimeException if the date-time string cannot be parsed with
     *                           the given format string
     */
    public TemporalAccessor asTemporal(final String dateTime, final String format) {
        try {
            return dateTimeFormatter(format, DATE_TIME_FORMAT).parse(dateTime);
        } catch (DateTimeParseException e) {
            String newFormat = StringHelper.isEmpty(format) ? DATE_TIME_FORMAT : format;
            throw new DateTimeParseException("Failed to parse date-time : "
                    + dateTime + " with format: " + newFormat,
                    e.getParsedString(), e.getErrorIndex());
        }
    }

    /**
     * Parses a date-time string using the specified format string and returns a
     * {@link LocalDateTime} object.
     *
     * @param dateTime the date-time string to parse
     * @param format   the format string used to parse the
     *                 date-time string
     * @return a {@link LocalDateTime} object
     * representing the parsed date-time string
     * @throws DateTimeParseException if the date-time string cannot be parsed
     *                                with the given format string
     */
    public LocalDateTime parseDateTime(final String dateTime, final String format) {
        return LocalDateTime.from(asTemporal(dateTime, format));
    }

    /**
     * Parses a date-time string using the specified format string and returns a
     * {@link ZonedDateTime} object.
     *
     * @param dateTime the date-time string to parse
     * @param format   the format string used to parse the
     *                 date-time string
     * @return a {@link ZonedDateTime} object
     * representing the parsed date-time string
     * @throws DateTimeParseException if the date-time string cannot be parsed
     *                                with the given format string
     */
    public ZonedDateTime parseDateTimeZone(final String dateTime, final String format) {
        return ZonedDateTime.from(asTemporal(dateTime, format));
    }

    /**
     * Returns a string representation of the current date and time in the
     * format "yyyy-MM-dd HH:mm:ss.SSS"
     *
     * @return A string of the current date and time in the format of
     * "yyyy-MM-dd HH:mm:ss"
     */
    public String timeStamp() {
        return dateTime(TIMESTAMP_FORMAT);
    }

    /**
     * Converts a long value representing the number of milliseconds since the
     * epoch to a LocalDateTime object, and then formats it using the
     * TIMESTAMP_FORMAT constant.
     *
     * @param epochMilli The epoch time in milliseconds.
     * @return A string representation of the date and time.
     */
    public String timeStamp(long epochMilli) {
        return format(LocalDateTime
                        .ofInstant(Instant.ofEpochMilli(epochMilli), ZoneId.systemDefault()),
                TIMESTAMP_FORMAT);
    }

    /**
     * Formats a temporal object using the specified format string.
     *
     * @param temporal The temporal object to format
     * @param format   The format to use for the date.
     * @return A string representation of the temporal object in the
     * specified format.
     */
    public String format(final Temporal temporal, final String format) {
        String defaultFormat = temporal instanceof LocalDate ? DATE_FORMAT : DATE_TIME_FORMAT;
        return dateTimeFormatter(format, defaultFormat).format(temporal);
    }

    /**
     * If the format is not null and not empty, return the format, otherwise
     * return the default format.
     *
     * @param format        The format to use.
     * @param defaultFormat The default format to use if the format parameter
     *                      is null or empty.
     * @return A DateTimeFormatter
     */
    public DateTimeFormatter dateTimeFormatter(final String format, final String defaultFormat) {
        return ofPattern(ofNullable(format)
                .filter(f -> !f.isEmpty())
                .orElse(defaultFormat));
    }

    /**
     * Adjusts a temporal object using the specified TemporalAdjuster identified
     * by the given name.
     *
     * @param temporal the temporal object to adjust
     * @param name     the name of the TemporalAdjuster to use
     *                 for adjustment
     * @return the adjusted temporal object of type T
     * @throws IllegalArgumentException if the specified name is unknown
     */
    public <T extends Temporal> T adjust(T temporal, @NonNull String name) {
        return ofNullable(ADJUSTERS.get(name))
                .map(temporal::with)
                .map(t -> (T) t)
                .orElseThrow(() -> new IllegalArgumentException("Unknown adjustment: " + name));
    }

    /**
     * Calculates the difference between two Temporal objects in the specified
     * unit.
     *
     * @param start the starting Temporal object.
     * @param end   the ending Temporal object.
     * @param unit  the unit of time to calculate the
     *              difference in (days, hours, minutes, or
     *              seconds).
     * @return the difference between the two Temporal
     * objects in the specified unit.
     * @throws IllegalArgumentException if the specified unit is not supported.
     */
    public long difference(Temporal start, Temporal end, String unit) {
        var chronoUnit = ChronoUnit.valueOf(unit.toUpperCase());
        return chronoUnit.between(start, end);
    }

    /**
     * Returns the temporal object that is the specified number of units after
     * the given temporal object.
     *
     * @param temporal the temporal object
     * @param number   the number of units
     * @param unit     the unit of time
     * @param <T>      the type of the temporal object
     * @return the temporal object that is the
     * specified number of units after the
     * given temporal object
     * @throws IllegalArgumentException if the unit is invalid
     */
    public <T extends Temporal> T next(T temporal, int number, String unit) {
        return performOperation(temporal, number, unit, (t, n) -> (T) t.plus(n, UNIT_MAPPING.get(unit)));
    }

    /**
     * Returns the temporal object that is the specified number of units before
     * the given temporal object.
     *
     * @param temporal the temporal object
     * @param number   the number of units
     * @param unit     the unit of time
     * @param <T>      the type of the temporal object
     * @return the temporal object that is the
     * specified number of units before the
     * given temporal object
     * @throws IllegalArgumentException if the unit is invalid
     */
    public <T extends Temporal> T previous(T temporal, int number, String unit) {
        return performOperation(temporal, number, unit, (t, n) -> (T) t.minus(n, UNIT_MAPPING.get(unit)));
    }

    /**
     * Adds the specified number of units to the given temporal object.
     *
     * @param temporal  the temporal object to modify
     * @param number    the number of units to add
     * @param unit      the unit to add
     * @param operation the operation to perform on the temporal
     *                  object
     * @param <T>       the type of the temporal object
     * @return the modified temporal object
     * @throws IllegalArgumentException if the unit is invalid
     */
    private <T extends Temporal> T performOperation(
            T temporal, int number, String unit, TemporalFunction<T> operation
    ) {
        return ofNullable(UNIT_MAPPING.get(unit))
                .map(u -> operation.apply(temporal, number * (u.isDateBased() ? 1 : 7)))
                .orElseThrow(() -> new IllegalArgumentException("Invalid unit: " + unit));
    }

    /**
     * Checks if the given date is a weekend day (Saturday or Sunday).
     *
     * @param date The date to check.
     * @return true if the date is a weekend day, false otherwise.
     */
    public boolean isWeekend(TemporalAccessor date) {
        var dayOfWeek = DayOfWeek.from(date);
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private interface TemporalFunction<T extends Temporal> extends BiFunction<Temporal, Integer, T> {
        @Override
        T apply(Temporal temporal, Integer number);
    }
}
