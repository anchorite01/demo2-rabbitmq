package com.example.demo2.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: TODO
 * @Author zhenghao
 * @Date 2019/11/26 10:21
 */
public class DateUtil {
    public static final String YMD = "yyyy-MM-dd HH:mm:SSS";

    public static String dateFormat(Date date) {
        return new SimpleDateFormat(DateUtil.YMD).format(date);
    }
    public static String nowDateFormat() {
        return dateFormat(new Date());
    }
}
