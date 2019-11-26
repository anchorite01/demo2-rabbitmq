package com.example.demo2.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 日期工具类
 * @Author zhenghao
 * @Date 2019/11/26 10:21
 */
public class DateUtil {
    public static final String YMD = "yyyy-MM-dd HH:mm:ss.SSS";

    public static String dateFormat(Date date) {
        return new SimpleDateFormat(DateUtil.YMD).format(date);
    }
    public static String nowDateFormat() {
        return dateFormat(new Date());
    }
}
