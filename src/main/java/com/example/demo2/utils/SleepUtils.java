package com.example.demo2.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 休眠工具
 *
 * @author zhenghao
 * @date 2021/8/20 10:40
 */
@Slf4j
public class SleepUtils {

    /**
     * 休眠second秒
     * @param second 秒数
     * @return 是否休眠成功
     */
    public static boolean sleep(double second) {
        try {
            TimeUnit.MILLISECONDS.sleep((long) (second * 1000L));
            return true;
        } catch (InterruptedException e) {
            // 恢复中断标记
            Thread.currentThread().interrupt();
            // 线程中断直接退出重试
            log.warn("[休眠被打断] .. ", e);
            return false;
        }
    }
}
