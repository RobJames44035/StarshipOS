/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class GregorianAdapter extends GregorianCalendar {
    static final int ALL_FIELDS = (1 << FIELD_COUNT) - 1;

    public GregorianAdapter() {
        super();
    }

    public GregorianAdapter(TimeZone tz) {
        super(tz);
    }

    public GregorianAdapter(Locale loc) {
        super(loc);
    }

    public GregorianAdapter(TimeZone tz, Locale loc) {
        super(tz, loc);
    }

    public void computeTime() {
        super.computeTime();
    }

    public void computeFields() {
        super.computeFields();
    }

    public void complete() {
        super.complete();
    }

    StringBuffer msg = new StringBuffer();

    void initTest() {
        msg = new StringBuffer();
    }

    String getMessage() {
        String s = msg.toString();
        msg = new StringBuffer();
        return "    " + s;
    }

    void setMessage(String msg) {
        this.msg = new StringBuffer(msg);
    }

    void appendMessage(String msg) {
        this.msg.append(msg);
    }

    boolean getStatus() {
        return msg.length() == 0;
    }

    int getSetStateFields() {
        int mask = 0;
        for (int i = 0; i < FIELD_COUNT; i++) {
            if (isSet(i)) {
                mask |= 1 << i;
            }
        }
        return mask;
    }

    int[] getFields() {
        int[] fds = new int[fields.length];
        System.arraycopy(fields, 0, fds, 0, fds.length);
        return fds;
    }

    boolean checkInternalDate(int year, int month, int dayOfMonth) {
        initTest();
        checkInternalField(YEAR, year);
        checkInternalField(MONTH, month);
        checkInternalField(DAY_OF_MONTH, dayOfMonth);
        return getStatus();
    }

    boolean checkInternalDate(int year, int month, int dayOfMonth, int dayOfWeek) {
        initTest();
        checkInternalField(YEAR, year);
        checkInternalField(MONTH, month);
        checkInternalField(DAY_OF_MONTH, dayOfMonth);
        checkInternalField(DAY_OF_WEEK, dayOfWeek);
        return getStatus();
    }

    boolean checkInternalField(int field, int expectedValue) {
        int val;
        if ((val = internalGet(field)) != expectedValue) {
            appendMessage("internalGet(" + CalendarAdapter.FIELD_NAMES[field] + "): got " + val +
                          ", expected " + expectedValue + "; ");
            return false;
        }
        return true;
    }
}
