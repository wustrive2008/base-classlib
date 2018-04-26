package com.wustrive.lib.util;

import java.util.Date;
import java.util.Random;

/**
 *
 * Description: 随机数工具类
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/3/26 17:36
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
public class RandomUtil {

    public static Random random = new Random();

    /**
     * 生成指定长度的随机字符
     *
     * @param length
     * @return
     */
    public static String getRandom(int length) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < length; i++) {
            boolean isChar = (random.nextInt(2) % 2 == 0);// 输出字母还是数字
            if (isChar) { // 字符串
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
                ret.append((char) (choice + random.nextInt(26)));
            } else { // 数字
                ret.append(Integer.toString(random.nextInt(10)));
            }
        }
        return ret.toString();
    }


    /**
     * 生成指定长度的数字字符串
     * @param length
     * @return
     */
    public static String getRandomNum(int length) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < length; i++) {
            ret.append(Integer.toString(random.nextInt(10)));
        }
        return ret.toString();
    }


    /**
     * 获取快照版本
     *
     * @return
     */
    public static String generateSnapshotVersion() {
        String prefix = DateTimeUtils.formatDateToString(DateTimeUtils.pattern_yyyyMMddHHmmssSSS, new Date());
        String suffix = RandomUtil.getRandomBySalt(5, System.currentTimeMillis());
        return prefix + suffix;
    }

    /**
     * 生成包含字母和数字的随机数
     *
     * @param length
     * @param salt
     * @return
     */
    public static String getRandomBySalt(int length, long salt) {
        random = new Random(salt);//作为种子数传入到Random的构造器中
        return getRandom(length);
    }

    /**
     * 获得指定范围内的随机数 [0,limit)
     *
     * @param limit 限制随机数的范围，不包括这个数
     * @return 随机数
     */
    public static int randomInt(int limit) {
        Random random = new Random();
        return random.nextInt(limit);
    }

    /**
     * 生成随机数字
     *
     * @param length
     * @param salt
     * @return
     */
    public static String getRandomNumBySalt(int length, long salt) {
        Random random = new Random(salt);
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < length; i++) {
            ret.append(Integer.toString(random.nextInt(10)));
        }
        return ret.toString();
    }

    /**
     * 在指定范围取随机数
     *
     * @param min
     * @param max
     * @return
     */
    public static int getRandomOfRange(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

}
