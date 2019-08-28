package com.jwBlog.utils.dp;

import com.jwBlog.base.Constants;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.springframework.util.StringUtils {
	/**
	 * 数组转为字符串
	 * @param join
	 * @param strAry
	 * @return
	 */
	public static String join(String join, String[] strAry) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strAry.length; i++) {
			if (i == (strAry.length - 1)) {
				sb.append(strAry[i]);
			} else {
				sb.append(strAry[i]).append(join);
			}
		}

		return new String(sb);
	}
	/**
	 * List转为字符串
	 * @param join
	 * @param strList
	 * @return
	 */
	public static String join(String join, List<String> strList) {
		String[] strAry=strList.toArray(new String[]{});
		return join(join,strAry);
	}
	/**
	 * 数组转为字符串
	 * @param join
	 * @param strAry
	 * @return
	 */
	public static String joinInteger(String join, Integer[] strAry) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strAry.length; i++) {
			if (i == (strAry.length - 1)) {
				sb.append(strAry[i]);
			} else {
				sb.append(strAry[i]).append(join);
			}
		}

		return new String(sb);
	}
	/**
	 * List转为字符串
	 * @param join
	 * @param strList
	 * @return
	 */
	public static String joinInteger(String join, List<Integer> strList) {
		Integer[] strAry=strList.toArray(new Integer[]{});
		return joinInteger(join,strAry);
	}
	/**
	 * List转为字符串
	 * @param join
	 * @param strList
	 * @return
	 */
	public static List<String> join(String join, List<String> strList,Integer fzLength) {
		List<String> joinList=new ArrayList<String>();
		for (int i = 0; i < strList.size(); i = i + fzLength){
			int toIndex= (i + fzLength)>strList.size()?strList.size():(i + fzLength);
			String[] strAry=strList.subList(i, toIndex).toArray(new String[]{});
			joinList.add(join(join,strAry));
		}
		return joinList;
	}
	/**
	 * Unicode转换成string
	 * @param str
	 * @return
	 */
	public static String UnicodeToString(String str) {
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");
		}
		return str;
	}
	/**
	 * string转换成Unicode
	 * @param str
	 * @return
	 */
	public static String StringToUnicode(String str) {
		String rtnstr="";
		for(int i = 0;i<str.length();i++){
			rtnstr+="\\u" + Integer.toHexString(str.charAt(i));
		}
		return rtnstr;
	}
	/**
	 * 由A、B、C的序号转为 1,2,3序号
	 * @param str
	 * @return
	 */
	public static Integer fromCharIndexToIntegerIndex(String str) {
		try {
			List<Integer> intList=new ArrayList<Integer>();
			for (int i = 0; i < str.length(); i++) {
				intList.add(Integer.parseInt(Integer.toHexString(str.charAt(i)), 16) - 64);
			}
			Integer liIndex=0;
			for(int i = 0; i<intList.size(); i++){
				int intSa=intList.size()-i-1;
				int intSAA=1;
				if(intSa>0){
					for(int j=0;j<intSa;j++)intSAA=intSAA*26;
				}
				liIndex+=intList.get(i)*(intSAA);
			}
			return liIndex;
		}catch(Exception e){
			return 1;
		}
	}
	public static String fromIntegerIndexToCharIndex(Integer index) {
		try {
			if(index==0||index<0)return "A";
			else if(index<27){
				return String.valueOf((char)(index.intValue()+64));
			}else{
				if(index % 26 == 0){
					return fromIntegerIndexToCharIndex((index - 1) / 26) + fromIntegerIndexToCharIndex(index % 26);
				}else {
					return fromIntegerIndexToCharIndex(index / 26) + fromIntegerIndexToCharIndex(index % 26);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}

	public static String addQuote(String inputStr){
		String items[]=inputStr.split(",");
		String res="";
		for(String str:items){
			if("".equals(str.trim())) 
				continue;
			res=res+",'"+str+"'";
		}
		res=res.substring(1);
		return res;
	}
	/**
	 * java去除字符串中的空格、回车、换行符、制表符
	 * @return
	 */
	public static String replaceBlank(String str){
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
		/*	    笨方法：String s = "你要去除的字符串";
		        1.去除空格：s = s.replace('\\s','');
		        2.去除回车：s = s.replace('\n','');
		注：\n 回车(\u000a)
		 \t 水平制表符(\u0009)
		 \s 空格(\u0008)
		 \r 换行(\u000d) */

	}
	/**
	 * 根据传入的String数组，清除空值，包括""。
	 * @param objs
	 * @return  Object[]数组
	 */
	public static String[] getArrayByArray(String[] objs){
		if(objs!=null&&objs.length>0){
			List<String> list=new ArrayList<String>();
			for(String o:objs){
				if(hasText(o)){
					list.add(o);
				}
			}
			String[] ss=new String[list.size()];
			for(int i=0,b=list.size();i<b;i++){
				ss[i]=list.get(i);
			}
			return ss;
		}else{
			return new String[]{};
		}
	}
	/**
	 * 获取32位UUID
	 * @return
	 */
	public static String getUUID32(){
		return UUID.randomUUID().toString().replaceAll("-","");
	}
	/**
	 * 获取36位UUID
	 * @return
	 */
	public static String getUUID36(){
		return UUID.randomUUID().toString();
	}
	/**
	 * Object 对象转换成字符串
	 * @param obj
	 * @return
	 */
	public static String toStringByObject(Object obj){
		return toStringByObject(obj,false,null);
	}
	/**
	 * Object 对象转换成字符串
	 * @param obj
	 * @return
	 */
	public static String toStringByObject(Object obj, boolean isqdkg){
		return toStringByObject(obj,isqdkg,null);
	}
	/**
	 * Object 对象转换成字符串，并可以根据参数去掉两端空格
	 * @param obj
	 * @return
	 */
	public static String toStringByObject(Object obj, boolean isqdkg, String datatype){
		if(obj==null){
			return "";
		}else{
			if(isqdkg){
				return obj.toString().trim();
			}else{
				//如果有设置时间格式类型，这转换
				if(StringUtils.hasText(datatype)){
					if(obj instanceof Timestamp){
						return DateUtils.formatDate((Timestamp)obj,Constants.param_default_dateTimeFormat);
					}else if(obj instanceof Date){
						return DateUtils.formatDate((Date)obj,Constants.param_default_dateTimeFormat);
					}
				}
				return obj.toString();
			}


		}
	}
	/**
	 * Object 对象转换成Integer
	 * @param obj
	 * @return 转换失败== 0
	 */
	public static Integer toIntegerByObject(Object obj){
		try {
			return Integer.valueOf(toStringByObject(obj,true));
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Object 对象转换成Double
	 * @param obj
	 * @return 转换失败== 0.0
	 */
	public static Double toDoubleByObject(Object obj){
		try {
			return Double.valueOf(toStringByObject(obj,true));
		} catch (Exception e) {
			return 0.0d;
		}
	}
	/**
	 * Object 对象转换成Float
	 * @param obj
	 * @return 转换失败== 0.0
	 */
	public static Float toFloatByObject(Object obj){
		try {
			return Float.valueOf(toStringByObject(obj,true));
		} catch (Exception e) {
			return 0.0f;
		}
	}

	/**
	 * List<String>转换成 xx1,xx2  字符串
	 * @param list
	 * @return
	 */
	public static String toStringBySpilt(List<String> list){
		if(list!=null&&list.size()>0){
			StringBuffer sb=new StringBuffer();
			for(int i=0,b=list.size();i<b;i++){
				String s=list.get(i);
				if(StringUtils.hasText(s)){
					if(i<b-1){
						sb.append(s).append(",");
					}else{
						sb.append(s);
					}
				}
			}
			return sb.toString();
		}else{
			return "";
		}
	}
	/**
	 * List<String>转换成 'xx1','xx2'  字符串
	 * @param list
	 * @return
	 */
	public static String toStringBySqlIn(List<String> list){
		if(list!=null&&list.size()>0){
			StringBuffer sb=new StringBuffer();
			for(int i=0,b=list.size();i<b;i++){
				String s=list.get(i);
				if(StringUtils.hasText(s)){
					if(i<b-1){
						sb.append("'").append(s).append("',");
					}else{
						sb.append("'").append(s).append("'");
					}
				}
			}
			return sb.toString();
		}else{
			return "'-0'";
		}
	}
	/**
	 * Object[] 转换成 'xx1','xx2'  字符串
	 * @return
	 */
	public static String toStringBySqlIn(String[] ss){
		if(ss!=null&&ss.length>0){
			StringBuffer sb=new StringBuffer();
			for(int i=0,b=ss.length;i<b;i++){
				String s=ss[i];
				if(StringUtils.hasText(s)){
					if(i<b-1){
						sb.append("'").append(s).append("',");
					}else{
						sb.append("'").append(s).append("'");
					}
				}
			}
			return sb.toString();
		}else{
			return "";
		}
	}
}
