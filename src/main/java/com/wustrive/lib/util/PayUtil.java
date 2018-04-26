package com.wustrive.lib.util;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付工具类
 * @Title: PayUtil.java
 * @ClassName: com.cedu.util.PayUtil
 *
 * Copyright  2015-2016 维创盈通 - Powered By 研发中心 V1.0.0
 * @author wustrive
 * @date 2017年1月13日 上午10:45:48
 */
public class PayUtil {
    /**金额为分的格式 */    
    private static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";

    /**
     * 生成订单号  
     * 规则：yyMMddHHmmssSSS+random(5)
     * @return
     */
    public static String generateOrderNum(){
        Date date = new Date();
        String suffix = RandomUtil.getRandomNumBySalt(5, System.nanoTime());
        return DateTimeUtils.parseDateToString("yyMMddHHmmssSSS", date) + suffix;
    }


    /**
     * 分转元
     * @param amount
     * @return
     * @throws Exception
     */
    public static String changeF2Y(String amount) throws Exception{    
        if(!amount.matches(CURRENCY_FEN_REGEX)) {    
            throw new Exception("金额格式有误");    
        }    
        return BigDecimal.valueOf(Long.parseLong(amount)).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 分转元
     * @param amount
     * @return
     * @throws Exception
     */
    public static String changeF2YOfInteger(Integer amount){    
        if(null == amount){
            return "0.0";
        }
        if(!amount.toString().matches(CURRENCY_FEN_REGEX)) {    
            return "金额错误";
        }    
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP).toString();
    }    
    
    /**
     * 分转元
     * @param amount  double
     * @return
     * @throws Exception
     */
    public static Double changeF2YOfDouble(Integer amount){    
        if(null == amount){
            return 0.0;
        }
        if(!amount.toString().matches(CURRENCY_FEN_REGEX)) {    
            return 0.0;
        }    
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }  
    
    /**   
     * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额  
     *   
     * @param amount  
     * @return  
     */    
    public static String changeY2F(String amount){    
        String currency =  amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额    
        int index = currency.indexOf(".");    
        int length = currency.length();    
        Long amLong;
        if(index == -1){    
            amLong = Long.valueOf(currency+"00");    
        }else if(length - index >= 3){    
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));    
        }else if(length - index == 2){    
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);    
        }else{    
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");    
        }    
        return amLong.toString();    
    }    
    
    /**   
     * 将元为单位的转换为分 （乘100）  
     *   
     * @param amount  
     * @return  
     */    
    public static String changeY2F(Double amount){    
        return BigDecimal.valueOf(amount).multiply(new BigDecimal(100)).toString();    
    }    
    
    /**
     * 将元为单位的转换为分  返回 Integer
     * @param amount
     * 
     * @return
     */
    public static Integer changeY2FToInteger(Double amount){    
        return BigDecimal.valueOf(amount).multiply(new BigDecimal(100)).intValue();    
    }  
}
