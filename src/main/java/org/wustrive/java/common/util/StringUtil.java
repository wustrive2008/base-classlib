package org.wustrive.java.common.util;

import com.xiaoleilu.hutool.log.Log;
import com.xiaoleilu.hutool.log.LogFactory;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

/**
 *
 *
 * @author wustrive
 * @Title: MyStringUtil.java
 * @ClassName: org.wustrive.java.common.string.MyStringUtil
 * @Description: 补充的字符串工具类
 * <p>
 * Copyright 2015-2017 维创盈通 - Powered By 研发中心 V1.0.0
 * @date 2017年3月11日 下午12:08:06
 */
public class StringUtil extends StringUtils {
    protected static final Log log = LogFactory.get(StringUtil.class);

    /**
     * 字符串编码转换
     *
     * @param param
     * @return
     */
    public static String convertEncode(String sourceCharsetName, String targetCharsetName,
                                       String str) {
        String ret = "";
        if (StringUtils.isNotBlank(str)) {
            try {
                ret = new String(str.getBytes(sourceCharsetName), targetCharsetName).trim();
            } catch (UnsupportedEncodingException e) {
                log.error("字符串编码转换异常:原编码{},目标编码{}", sourceCharsetName, targetCharsetName, e);
                ret = "";
            }
        }
        return ret;
    }

    /**
     * 不带中划线的UUID
     *
     * @return
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18)
                + uuid.substring(19, 23) + uuid.substring(24);
    }


    /**
     * List转换为string 分隔符分割
     *
     * @param list
     * @param separator
     * @return
     */
    public static String listToString(List<String> list, String separator) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : list) {
            stringBuffer.append(separator).append(str);
        }
        stringBuffer.deleteCharAt(0);
        return stringBuffer.toString();
    }


    /**
     * 过滤emoji表情
     *
     * @param str
     * @return
     */
    public static String filterEmojiCharacter(String str) {
        if (str == null)
            return null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (isEmojiCharacter(c))
                sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 判断是否是emoji表情
     *
     * @param code
     * @return
     */
    private static boolean isEmojiCharacter(char code) {
        return (code == 0x0) || (code == 0x9) || (code == 0xA) || (code == 0xD)
                || ((code >= 0x20) && (code <= 0xD7FF)) || ((code >= 0xE000) && (code <= 0xFFFD))
                || ((code >= 0x10000) && (code <= 0x10FFFF));
    }


    /**
     * 替换字符串后几位
     *
     * @param str
     * @param len
     * @param replaceChar
     * @return
     */
    public static String replayStrOfEnd(String str, int len, String replaceChar) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        if (str.length() <= len) {

            len = str.length();
        }
        String preStr = str.substring(0, str.length() - len);
        String hideStr = copyStr(replaceChar, len);
        return preStr + hideStr;
    }


    /**
     * 复制字符串 a->aaaa
     *
     * @param srcStr
     * @param num
     * @return
     */
    public static String copyStr(String srcStr, int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append(srcStr);
        }
        return sb.toString();
    }

    /**
     * 切分字符串<br>
     * from jodd
     *
     * @param str       被切分的字符串
     * @param delimiter 分隔符
     * @return 字符串
     */
    public static String[] split(String str, String delimiter) {
        if (str == null) {
            return null;
        }
        if (str.trim().length() == 0) {
            return new String[]{str};
        }

        int dellen = delimiter.length(); // del length
        int maxparts = (str.length() / dellen) + 2; // one more for the last
        int[] positions = new int[maxparts];

        int i, j = 0;
        int count = 0;
        positions[0] = -dellen;
        while ((i = str.indexOf(delimiter, j)) != -1) {
            count++;
            positions[count] = i;
            j = i + dellen;
        }
        count++;
        positions[count] = str.length();

        String[] result = new String[count];

        for (i = 0; i < count; i++) {
            result[i] = str.substring(positions[i] + dellen, positions[i + 1]);
        }
        return result;
    }

    /**
     * 是否是整数
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        if (StringUtil.isBlank(str))
            return false;
        return str.matches("^-?\\d+");
    }


    /**
     * 是否所有都为空
     * @param css
     * @return
     */
    public static boolean isAllBlank(final String... css){
        if (ArrayUtils.isEmpty(css)) {
            return true;
        }
        for (final String cs : css){
            if (org.apache.commons.lang.StringUtils.isNotBlank(cs)) {
                return false;
            }
        }
        return true;
    }

}
