package com.jwBlog.utils.dp;

import java.math.BigDecimal;


public class DoubleUtils {
	
	
	/**
	 * 由字符串生成BigDecimal
	 * @param value
	 * @return
	 */
	public static BigDecimal toDecimal(String value){
		return new BigDecimal(value);
	}
	/**
	 * 由Double生成BigDecimal
	 * @param value
	 * @return
	 */
	public static BigDecimal toDecimal(Double value){
		return new BigDecimal(Double.toString(value));
	}
	
    /**  
    * 对double数据进行取精度.  
    * @param value  double数据.  
    * @param scale  精度位数(保留的小数位数).  
    * @param roundingMode  精度取值方式.  
    * @return 精度计算后的数据.  
    */  
//   public static String roundToFen(double value) {   
//	   return com.capinfo.common.util.MoneyUtil.roundToFen(String.valueOf(value));
//   } 
   /**  
    * 对double数据进行取精度.  
    * @param value  double数据.  
    * @param scale  精度位数(保留的小数位数).  
    * @param roundingMode  精度取值方式.  
    * @return 精度计算后的数据.  
    */  
   public static String roundToWanDigits(double value) {   
	   Double a1=div(value,10000,7);
	   BigDecimal n_money = new BigDecimal(a1);
	    return n_money.setScale(6, 4).toString();
   } 
   /**  
    * 对double数据进行取精度.  
    * @param value  double数据.  
    * @param scale  精度位数(保留的小数位数).  
    * @param roundingMode  精度取值方式.  
    * @return 精度计算后的数据.  
    */  
//   public static String roundToWanDigitsTwo(double value) {   
//	   Double a1=div(value,10000,2);	   
//	   return com.capinfo.common.util.MoneyUtil.roundToYuan(a1.toString());
//   }
   /**  
    * 对double数据进行取精度.  
    * @param value  double数据.  
    * @param scale  精度位数(保留的小数位数).  
    * @param roundingMode  精度取值方式.  
    * @return 精度计算后的数据.  
    */  
//   public static String roundToWan(double value) {   
//	   Double a1=div(value,10000,0);
//	   return String.valueOf(com.capinfo.common.util.MoneyUtil.roundUp(a1));
//   } 
   /**
    * 保留两位小数
    * @param value
    * @return
    */
//   public static String roundToFenDigits(double value) {   
//	   return com.capinfo.common.util.MoneyUtil.roundToYuan(String.valueOf(value));
//   }
   public static Double round(double value, int scale, 
           int roundingMode) {  
      BigDecimal bd = new BigDecimal(value);   
      bd = bd.setScale(scale, roundingMode);   
      double d = bd.doubleValue();   
      bd = null;   
      return d;   
  }
   
    /** 
    * double 相加 
    * @param d1 
    * @param d2 
    * @return 
    */ 
   public static double sum(double d1,double d2){ 
       BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
       BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
       return bd1.add(bd2).doubleValue(); 
   } 


   /** 
    * double 相减 
    * @param d1 
    * @param d2 
    * @return 
    */ 
   public static double sub(double d1,double d2){ 
       BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
       BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
       return bd1.subtract(bd2).doubleValue(); 
   } 

   /** 
    * double 乘法 
    * @param d1 
    * @param d2 
    * @return 
    */ 
   public static double mul(double d1,double d2, int scale){ 
       BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
       BigDecimal bd2 = new BigDecimal(Double.toString(d2));
       
       BigDecimal bd = new BigDecimal(bd1.multiply(bd2).doubleValue());   
       bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP); 
       double d = bd.doubleValue();   
       bd = null;
       return d; 
   } 


   /** 
    * double 除法 
    * @param d1 
    * @param d2 
    * @param scale 四舍五入 小数点位数 
    * @return 
    */ 
   public static double div(double d1,double d2,int scale){ 
       //  当然在此之前，你要判断分母是否为0，   
       //  为0你可以根据实际需求做相应的处理 

       BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
       BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
       if(compare(d2,0)==0)return 0;
       return bd1.divide 
              (bd2,scale,BigDecimal.ROUND_HALF_UP).doubleValue(); 
   } 
   /** 
    * double 除法 
    * @param d1 
    * @param d2 
    * @param scale 直接进位
    * @return 
    */ 
   public static double divmod(double d1,double d2,int scale){ 
       BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
       BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
       return bd1.divide 
              (bd2,scale,BigDecimal.ROUND_UP).doubleValue(); 
   }
   /**
    * 获取用,分割的金额现实形式
    * @param moneyvalue
    * @return
    */
//   public static String Commasplit(Double moneyvalue){	   
//	   return Commasplit(DoubleUtils.roundToFenDigits(moneyvalue));
//   }
   public static String Commasplit(String moneyvalue){
	  // System.out.println("moneyvalue===="+moneyvalue);
	   String strleft="",strright="";
	   if(moneyvalue.indexOf(".") >0){
		   strleft=moneyvalue.substring(0,moneyvalue.indexOf("."));
		   strright=moneyvalue.substring(moneyvalue.indexOf("."));		 
	   }else{
		   strleft=moneyvalue;
	   }
	   //System.out.println("strleft===="+strleft);
	   //System.out.println("strright===="+strright);
	   String strrtn="";
	   while(strleft.length()>3){
		   String stritem=strleft.substring(strleft.length()-3);
		   strleft=strleft.substring(0,strleft.length()-3);
		   strrtn=","+stritem+strrtn;
	   }
	   strrtn=strleft+strrtn+strright;
	   return strrtn;
   }
   public static Integer compare(double d1,double d2) {
	   BigDecimal data1 = new BigDecimal(d1); 
	   BigDecimal data2 = new BigDecimal(d2);
	   String result = ""; 
	   if (data1.compareTo(data2) < 0) { 
		   result = "第二位数大！"; 
		   return -1;
	   } 
	   if (data1.compareTo(data2) == 0) { 
		   result = "两位数一样大！"; 
		   return 0;
		}
	   if (data1.compareTo(data2) > 0) { 
		   result = "第一位数大！"; 
		   return 1;
		} 
	   return 0; 
   }
}
