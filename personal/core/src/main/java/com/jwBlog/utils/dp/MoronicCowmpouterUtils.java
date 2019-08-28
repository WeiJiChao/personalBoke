package com.jwBlog.utils.dp;

import java.math.BigInteger;

/**
 * 进制转换
 * devFrame Created by 许林 on 2017/6/1.
 */
public class MoronicCowmpouterUtils {
    /**
     * 10进制转2进制
     * @param ten
     * @return
     */
    public static String TenToTwo(Integer ten){
        return Integer.toBinaryString(ten);
    }
    /**
     * 10进制转16进制
     * @param ten
     * @return
     */
    public static String TenToSixteen(Integer ten){
        return Integer.toHexString(ten);
    }
    /**
     * 10进制转2进制
     * @param ten
     * @return
     */
    public static String TenToTwo(String ten){
        return TenToTwo(Integer.parseInt(ten));
    }
    /**
     * 10进制转16进制
     * @param ten
     * @return
     */
    public static String TenToSixteen(String ten){
        return TenToSixteen(Integer.parseInt(ten));
    }
    /**
     * 2进制转10进制
     * @param ten
     * @return
     */
    public static String TwoToTen(String ten){
        return new BigInteger(ten,2).toString();
    }
    /**
     * 2进制转16进制
     * @param ten
     * @return
     */
    public static String TwoToSixteen(String ten){
        return Integer.toHexString(Integer.parseInt(new BigInteger(ten,2).toString()));
    }
    /**
     * 16进制转10进制
     * @param ten
     * @return
     */
    public static String SixteenToTen(String ten){
        return Integer.toBinaryString(Integer.parseInt(ten, 16));
    }
    /**
     * 16进制转2进制
     * @param ten
     * @return
     */
    public static String SixteenToTwo(String ten){
        return ""+Integer.parseInt(ten,16);
    }
}
