package com.cxy.oi.kernel.util;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.format.DateUtils;
import android.text.format.Time;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtil {

    public static final long MILLISECONDS_OF_SECOND = 1000;
    public static final long MILLISECONDS_OF_MINUTE = 60 * MILLISECONDS_OF_SECOND;
    public static final long MILLISECONDS_OF_HOUR = 60 * MILLISECONDS_OF_MINUTE;

    private final static long cDawnInMS = 6 * MILLISECONDS_OF_HOUR;
    private final static long cMorningInMS = 12 * MILLISECONDS_OF_HOUR;
    private final static long cNoonInMS = 13 * MILLISECONDS_OF_HOUR;
    private final static long cAfternoonInMS = 18 * MILLISECONDS_OF_HOUR;
    public static final char QUOTE = '\'';
    public static final char AM_PM = 'a';
    public static final char CAPITAL_AM_PM = 'A';
    public static final char DATE = 'd';
    public static final char DAY = 'E';
    public static final char HOUR = 'h';
    public static final char HOUR_OF_DAY = 'k';
    public static final char MINUTE = 'm';
    public static final char MONTH = 'M';
    public static final char STANDALONE_MONTH = 'L';
    public static final char SECONDS = 's';
    public static final char TIME_ZONE = 'z';
    public static final char YEAR = 'y';



    /**
     * Given a format string and a {@link java.util.Calendar} object, returns a
     * CharSequence containing the requested date.
     *
     * @param inFormat
     *            the format string, as described in {@link android.text.format.DateFormat}
     * @param inDate the date to format
     * @return a {@link CharSequence} containing the requested text
     */
    public static CharSequence format(CharSequence inFormat, Time inDate) {
        SpannableStringBuilder s = new SpannableStringBuilder(inFormat);
        int c;
        int count;

        int len = inFormat.length();

        for (int i = 0; i < len; i += count) {
            int temp;

            count = 1;
            c = s.charAt(i);

            if (c == QUOTE) {
                count = appendQuotedText(s, i, len);
                len = s.length();
                continue;
            }

            while ((i + count < len) && (s.charAt(i + count) == c)) {
                count++;
            }

            String replacement = null;

            switch (c) {
                case AM_PM:
                case CAPITAL_AM_PM:
                    replacement = DateUtils.getAMPMString(inDate.hour < 12 ? 0: 1);
                    break;
                case DATE:
                    replacement = zeroPad(inDate.monthDay, count);
                    break;

                case DAY:
                    temp = inDate.weekDay + 1;
                    replacement = DateUtils.getDayOfWeekString(temp,
                            count < 4 ? DateUtils.LENGTH_MEDIUM
                                    : DateUtils.LENGTH_LONG);
                    break;

                case HOUR:
                    temp = inDate.hour;

                    if (0 == temp)
                        temp = 12;
                    if(temp > 12){
                        temp = temp - 12;
                    }
                    replacement = ""+temp; //zeroPad(temp, count);
                    break;

                case HOUR_OF_DAY:
                    replacement = zeroPad(inDate.hour, count);
                    break;

                case MINUTE:
                    replacement = zeroPad(inDate.minute, count);
                    break;

                case MONTH:
                case STANDALONE_MONTH:
                    replacement = getMonthString(inDate, count, c);
                    break;

                case SECONDS:
                    replacement = zeroPad(inDate.second, count);
                    break;

                case TIME_ZONE:
                    replacement = getTimeZoneString(inDate, count);
                    break;

                case YEAR:
                    replacement = getYearString(inDate, count);
                    break;

                default:
                    replacement = null;
                    break;
            }

            if (replacement != null) {
                s.replace(i, i + count, replacement);
                count = replacement.length(); // CARE: count is used in the for
                // loop above
                len = s.length();
            }
        }

        if (inFormat instanceof Spanned)
            return new SpannedString(s);
        else
            return s.toString();
    }

    public static CharSequence formatTime(final long time) {
        Time destTime = new Time();
        Time currentTime = new Time();
        destTime.set(time);
        currentTime.setToNow();

        if (destTime.year == currentTime.year && destTime.yearDay == currentTime.yearDay ) {
            return format("kk:mm", destTime);
        }

        // yesterday
        if (destTime.year == currentTime.year && currentTime.yearDay - destTime.yearDay ==1) {
            return "昨天" + " " + format("kk:mm", destTime);
        }

        // same week
        if (destTime.year == currentTime.year  && destTime.getWeekNumber() == currentTime.getWeekNumber() ) {
            final String dow = "" + format("E ", destTime);
            return dow + format("kk:mm", destTime);
        }

        // same year
        if (destTime.year == currentTime.year) {
            return format("M月d日 " +
                    formatPrefixInDay(destTime.hour * MILLISECONDS_OF_HOUR) + "kk:mm", destTime);
        }

        return format("yyyy年M月d日 " +
                formatPrefixInDay(destTime.hour * MILLISECONDS_OF_HOUR) + "kk:mm", destTime);
    }

    public static CharSequence formatPrefixInDay(final long timeInDay) {
        if (timeInDay < 0) {
            return "";
        } else if (timeInDay < cDawnInMS) { // 00:00 ~ 05:59, dawn
            return "凌晨";
        } else if (timeInDay < cMorningInMS) { // 06:00 ~ 11:59, morning
            return "上午";
        } else if (timeInDay < cNoonInMS) { // 12:00 ~ 12:59, noon
            return "中午";
        } else if (timeInDay < cAfternoonInMS) { // 13:00 ~ 17:59, afternoon
            return "下午";
        } else { // 18:00 ~ 23:59, evening
            return "晚上";
        }
    }

    private static int appendQuotedText(SpannableStringBuilder s, int i, int len) {
        if (i + 1 < len && s.charAt(i + 1) == QUOTE) {
            s.delete(i, i + 1);
            return 1;
        }
        int count = 0;
        s.delete(i, i + 1);
        len--;
        while (i < len) {
            char c = s.charAt(i);
            if (c == QUOTE) {
                if (i + 1 < len && s.charAt(i + 1) == QUOTE) {
                    s.delete(i, i + 1);
                    len--;
                    count++;
                    i++;
                } else {
                    s.delete(i, i + 1);
                    break;
                }
            } else {
                i++;
                count++;
            }
        }

        return count;
    }
    private static String zeroPad(int inValue, int inMinDigits) {
        if (inMinDigits == 2) {
            if (inValue < 10) {
                return "0" + inValue;
            } else {
                return inValue + "";
            }
        }
        return String.format(Locale.getDefault(), "%0" + inMinDigits + "d",
                inValue);
    }
    private static String getMonthString(Time inDate, int count, int kind) {
        int month = inDate.month;

        if (count >= 4) {
            return DateUtils.getMonthString(month, DateUtils.LENGTH_LONG);
        } else if (count == 3) {
            return DateUtils.getMonthString(month, DateUtils.LENGTH_MEDIUM);
        } else {
            // Calendar.JANUARY == 0, add 1 to month.
            return zeroPad(month + 1, count);
        }
    }
    private static String getTimeZoneString(Time inDate, int count) {
        TimeZone tz = TimeZone.getDefault();
        tz.inDaylightTime(new Date(inDate.toMillis(false)));
        if (count < 2) { // FIXME: shouldn't this be <= 2 ?
            return formatZoneOffset(tz.getRawOffset() +
                            inDate.gmtoff,
                    count);
        } else {
            boolean dst = inDate.isDst != 0;
            return tz.getDisplayName(dst, TimeZone.SHORT);
        }
    }
    private static String formatZoneOffset(long l, int count) {
        l /= 1000; // milliseconds to seconds
        StringBuilder tb = new StringBuilder();

        if (l < 0) {
            tb.insert(0, "-");
            l = -l;
        } else {
            tb.insert(0, "+");
        }

        int hours = (int) (l / 3600);
        int minutes = (int) ((l % 3600) / 60);

        tb.append(zeroPad(hours, 2));
        tb.append(zeroPad(minutes, 2));
        return tb.toString();
    }
    private static String getYearString(Time inDate, int count) {
        int year = inDate.year;
        return (count <= 2) ? zeroPad(year % 100, 2) : String.format(
                Locale.getDefault(), "%d", year);
    }

}
