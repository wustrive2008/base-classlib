package org.wustrive.java.common.regular;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * @Title: RegularUtil.java
 * @ClassName: org.wustrive.java.common.regular.RegularUtil
 * @author wustrive
 * @date 2017年3月11日 上午11:48:39
 */
public class RegularUtil {
    
    /**
     * 判断字符串与正则是否匹配
     * @param pattern
     * @param input
     * @return
     */
    public static boolean isMatch(String pattern, String input){
        return Pattern.matches(pattern, input);
    }
    
    /**
     * 字符串中是否存在匹配，不要求全匹配
     * @param pattern
     * @param input
     * @return
     */
    public static boolean isFind(String pattern, String input){
        Pattern p = Pattern.compile(pattern); 
        Matcher m = p.matcher(input); 
        return m.find();
    }
    
    /**
     * 返回匹配组
     * @param pattern
     * @param input
     * @return
     */
    public static List<String> findGroup(String pattern, String input){
        Pattern p=Pattern.compile(pattern); 
        Matcher m=p.matcher(input); 
        List<String> res = Lists.newArrayList();
        while(m.find()) { 
            res.add(m.group());
        } 
        
        return res;
    }
    
    
    //测试
    public static void main(String[] args) {
        System.out.println(isMatch(RegularConstants.REGULAR_CHINESE, "sfds中文"));
        
        for (String string : findGroup("\\d+", "我的QQ是:456456 我的电话是:0532214 我的邮箱是:aaa123@aaa.com")) {
            System.out.println(string);
        }
    }
}
