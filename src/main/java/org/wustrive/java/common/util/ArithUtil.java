package org.wustrive.java.common.util;

import java.math.BigDecimal;

/**
 * @Title: Arith.java
 * @ClassName: org.vcxx.common.utils.Arith
 * @Description: 浮点算法 
 * @author zhaoqt
 * @date Mar 16, 2016 1:22:05 AM
 */
public class ArithUtil {
	
	 private static final int DEF_DIV_SCALE = 10;
	 
	/**
	 * 两个Double数相加
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Double add(Double v1, Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.add(b2).doubleValue();
	}
	
	/**
	 * 
	 * 两个Double数相减
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Double sub(Double v1, Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.subtract(b2).doubleValue();
    }
	
	/**
     * 两个Double数相乘
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double mul(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.multiply(b2).doubleValue();
    }
    
    /**
     * 两个Double数相除
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double div(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
     * 两个Double数相除，并保留scale位小数
     * 
     * @param v1
     * @param v2
     * @param scale
     * @return Double
     */
    public static Double div(Double v1,Double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
            "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
