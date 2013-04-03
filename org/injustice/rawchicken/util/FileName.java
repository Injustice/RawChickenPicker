package org.injustice.rawchicken.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.injustice.rawchicken.util.Var.runTime;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 01/04/13
 * Time: 19:46
 * To change this template use File | Settings | File Templates.
 */
class FileName {
    static String name;
    protected static String getElapsedString() {
        return runTime.toElapsedString().replaceAll(":", ".");
    }

    protected String getStatus(String status) {
        return status;
    }

    protected static String getDate() {
        final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr",
                "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec"};
        Calendar currentDate = Calendar.getInstance();
        int month = currentDate.get(Calendar.MONTH);
        String getMonth = MONTHS[month];
        SimpleDateFormat formatter =
                new SimpleDateFormat("-dd - HH_mm");
        String dateNow = formatter.format(currentDate.getTime());
        return getMonth + dateNow;
    }

    protected static String format(String... text) {
        String plus = " + ";
        return text[0] + plus + text[1];
    }

    protected static String getName() {
        return format(getDate(), getElapsedString());
    }

    protected void setName(String name) {
        this.name = name;
    }
}

