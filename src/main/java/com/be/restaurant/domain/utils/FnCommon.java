package com.be.restaurant.domain.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FnCommon {
    private final static String YYYY_MM_dd_HH_mm_ss = "YYYY/MM/dd HH:mm:ss";

    public static String convertDateToStringFormat(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
    public static String getCurrentTime() {
        return convertDateToStringFormat(new Date(), YYYY_MM_dd_HH_mm_ss);
    }
}
