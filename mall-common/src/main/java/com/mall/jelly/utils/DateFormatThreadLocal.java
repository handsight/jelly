package com.mall.jelly.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatThreadLocal {
    private static final ThreadLocal<DateFormat> threadLocalDate = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    private static final ThreadLocal<DateFormat> threadLocalDate2 = ThreadLocal.withInitial(() -> new SimpleDateFormat("HH:mm"));
    private static final ThreadLocal<DateFormat> threadLocalDate3 = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy/MM/dd"));
    private static final ThreadLocal<DateFormat> threadLocalDate4 = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy.MM.dd"));
    private static final ThreadLocal<DateFormat> threadLocalDate5 = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));


    public static String format5(Date source)  {
        return  threadLocalDate5.get().format(source);
    }

    public static Date convert(String source) throws ParseException {
        return  threadLocalDate.get().parse(source);
    }

    public static String format(Date source)  {
        return  threadLocalDate.get().format(source);
    }
    public static String format2(Date source)  {
        return  threadLocalDate2.get().format(source);
    }
    public static String format3(Date source)  {
        return  threadLocalDate3.get().format(source);
    }

    public static String format4(Date source)  {
        return  threadLocalDate4.get().format(source);
    }

}
