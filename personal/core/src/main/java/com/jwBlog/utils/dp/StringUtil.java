package com.jwBlog.utils.dp;

/**
 * @author Yueyang Created on 2007-3-7
 */

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yueyang Created on 2007-3-7
 */
public class StringUtil {

	private static final String FUNCTION_TITLE_SET = "set";
	
	private static final String FUNCTION_TITLE_GET = "get";
	
	private static Logger log = Logger.getLogger(StringUtil.class.toString());

	public static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

	private static Pattern NUMBER_PATTERN = Pattern.compile("\\D");

	private static Pattern EMAIL_PATTERN = Pattern
			.compile("^([a-z0-9A-Z]+[_-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
	
	public static final String NULL = "NULL";
	
	public static final String DEFAULT_STRING_SEPERATOR = ",";

	/**
	 * 国内身份证号
	 */
	private static Pattern CHINA_ID_PATTERN = Pattern
			.compile("\\d{15}(\\b|(\\d{3}|\\d{2}(x|y|X|Y))\\b)");

	private static String DEFAULT_SPLITER = ",";

	public static String transformByteToString(byte[] b) {
		return transformByteToString(b, CHARSET_UTF8);
	}

	public static String transformByteToString(byte[] b, Charset charset) {
		if (b == null || b.length == 0)
			return "";
		return new String(b, charset);
	}

	/**
	 * 按照指定长度取传入字符串长度个数的字字符串 如 abcde,2,则返回{"ab","bc","cd","de"}
	 * 
	 * @param size
	 * @return
	 * @deprecated replaced by segmentString()
	 */
	public static String[] splitString(String inStr, int size) {
		return segmentString(inStr, size);
	}

	/**
	 * 按照指定长度取传入字符串长度个数的字字符串 如 abcde,2,则返回{"ab","bc","cd","de"}
	 * 
	 * @param size
	 * @return
	 */
	public static String[] segmentString(String inStr, int size) {
		// 不大于指定分隔长度的直接返回
		if ((inStr != null && inStr.length() < (size + 1)) || size < 1) {
			String[] tmpList = new String[1];
			tmpList[0] = inStr;
			return tmpList;
		}

		int splitTimes = inStr.length() - size + 1;
		String[] returnStrList = new String[splitTimes];
		for (int i = 0; i < splitTimes; i++) {
			returnStrList[i] = inStr.substring(i, size + i);
		}
		return returnStrList;
	}

	/**
	 * 用指定分隔符连接一个String数组的内容,返回String 若不指定分隔符则直接连接
	 * 
	 * @param inStrArray
	 * @param split
	 * @return
	 */
	public static String getStringFromStringArray(String[] inStrArray,
			String split) {
		StringBuilder sb = new StringBuilder();
		String spliter = split;
		if (spliter == null) {
			spliter = "";
		}
		if (inStrArray != null && inStrArray.length > 0) {
			// sb.append(inStrArray.length);
			// sb.append(" Elements:");
			for (int i = 0; i < inStrArray.length; i++) {
				sb.append(inStrArray[i]);
				sb.append(spliter);
			}
			sb.delete(sb.length() - 1, sb.length());
			return sb.toString();
		} else {
			return "";
		}
	}

	/**
	 * 用指定分隔符连接一个int数组的内容,返回String 若不指定分隔符则直接连接
	 * 
	 * @param inIntArray
	 * @param spliter
	 * @return
	 */
	public static String getStringFromIntArray(int[] inIntArray, String spliter) {
		if (null == inIntArray || inIntArray.length == 0) {
			return "";
		}
		String[] inStrArray = new String[inIntArray.length];
		for (int i = 0; i < inIntArray.length; i++) {
			inStrArray[i] = String.valueOf(inIntArray[i]);
		}
		return getStringFromStringArray(inStrArray, spliter);
	}

	/**
	 * added by zhangjing 字符串替换，使用指定字符串替换源中所有的目标字符串
	 * 
	 * @param str
	 *            源字符串
	 * @param problemStr
	 *            被替换的字符串
	 * @param replace
	 *            所需替换成的字符串
	 * @return
	 */
	public static String replaceStr(String srcStr, String problemStr,
			String replace) {
		if (problemStr == null || problemStr.length() == 0 || srcStr == null
				|| srcStr.length() == 0) {
			return srcStr;
		}
		for (int i = srcStr.lastIndexOf(problemStr); i >= 0; i = srcStr
				.lastIndexOf(problemStr, i - 1)) {
			if (i == 0) {
				srcStr = replace
						+ srcStr.substring(i + problemStr.length(), srcStr
								.length());
			} else {
				srcStr = srcStr.substring(0, i)
						+ replace
						+ srcStr.substring(i + problemStr.length(), srcStr
								.length());
			}// end if
		}// end for
		return srcStr;
	}

	/**
	 * added by zhangjing 替换文本中的空格和回车，但是对于html标记<font color>中间的空格和回车等不替换
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceToHtml(String inStr) {
		if (isEmpty(inStr)) {
			return inStr;
		}
		String str = inStr;

		// str = replaceStr(str," ","&nbsp;&nbsp;");
		// str = replaceStr(str,"\n","<br>");
		int i = -1;
		while ((i = str.indexOf(" ", i + 1)) >= 0) {
			if ((str.lastIndexOf(">", i)) >= (str.lastIndexOf("<", i))
					&& (str.indexOf("<", i)) <= (str.indexOf(">", i))) {
				str = str.substring(0, i) + "&nbsp;"
						+ str.substring(i + 1, str.length());
			}
		}
		while ((i = str.indexOf("\n", i + 1)) >= 0
				|| (i = str.indexOf("\r", i + 1)) >= 0) {
			if ((str.lastIndexOf(">", i)) >= (str.lastIndexOf("<", i))
					&& (str.indexOf("<", i)) <= (str.indexOf(">", i))) {
				str = str.substring(0, i) + "<br/>"
						+ str.substring(i + 1, str.length());
			}
		}
		return str;
	}
	
	/**
	 * 
	 * 去除字符串中的回车、换行符、制表符
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
//            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

	/**
	 * 将字符形式的整型数组进行排序后返回 如果非数字,跳过
	 * 
	 * @param inStringArray
	 * @return
	 */
	public static String[] sortStringArray(String[] inStringArray) {
		if (null == inStringArray || inStringArray.length == 0) {
			return inStringArray;
		}
		String[] returnStrArr = new String[inStringArray.length];
		int[] strInt = new int[inStringArray.length];
		for (int i = 0; i < inStringArray.length; i++) {
			try {
				strInt[i] = Integer.parseInt(inStringArray[i]);
			} catch (NumberFormatException ex) {
				// strInt[i] = Integer.MIN_VALUE;
				continue;
			}
		}
		Arrays.sort(strInt);

		for (int i = 0; i < strInt.length; i++) {
			returnStrArr[i] = String.valueOf(strInt[i]);
		}
		return returnStrArr;
	}

	/**
	 * 对字符串中保存的以指定分隔符保存的数列进行排序后返回
	 * 
	 * @param inString
	 * @param spliter
	 *            分隔符
	 * @return
	 */
	public static String sortArrayInString(String inString, String spliter) {
		if (null == inString || inString.length() == 0) {
			return inString;
		}
		String[] strArray = inString.split(spliter);
		strArray = sortStringArray(strArray);
		return getStringFromStringArray(strArray, spliter);
	}

	/**
	 * 向字符串数组添加字符，不允许重复
	 * 
	 * @param strToAdd
	 * @param strArrayAddIn
	 * @return
	 */
	public static String[] addStringToArray(String strToAdd,
			String[] strArrayAddIn) {
		if (strArrayAddIn == null || strArrayAddIn.length == 0) {
			if (isEmpty(strToAdd)) {
				return new String[0];
			} else {
				String[] strarray = { strToAdd };
				return strarray;
			}
		}

		for (String str : strArrayAddIn) {
			if (str.equals(strToAdd)) {
				return strArrayAddIn;
			}
		}
		String[] newArray = new String[strArrayAddIn.length + 1];
		int i = 0;
		for (i = 0; i < strArrayAddIn.length; i++) {
			newArray[i] = strArrayAddIn[i];
		}
		newArray[i] = strToAdd;
		return newArray;
	}

	/**
	 * 从字符串数组中移除字符，不允许重复
	 * 
	 * @param strToAdd
	 * @param strArrayAddIn
	 * @return
	 */
	public static String[] removeStringFromArray(String strToAdd,
			String[] strArrayAddIn) {
		if (strArrayAddIn == null || strArrayAddIn.length == 0) {
			return new String[0];
		}

		boolean exist = false;
		int i = 0;
		for (String str : strArrayAddIn) {
			if (str.equals(strToAdd)) {
				exist = true;
				break;
			}
			i++;
		}
		if (!exist) {
			return strArrayAddIn;
		}

		String[] newArray = new String[strArrayAddIn.length - 1];

		for (int j = 0; j < i; j++) {
			newArray[j] = strArrayAddIn[j];
		}
		for (int j = i + 1; j < strArrayAddIn.length; j++) {
			newArray[j - 1] = strArrayAddIn[j];
		}

		return newArray;
	}

	/**
	 * 向实际存储数组的字符串中增加值，不允许重复，返回增加后的字符串
	 * 
	 * @param strToAdd
	 * @param arrayInStr
	 * @param split
	 * @return
	 */
	public static String addStringToArrayInString(String strToAdd,
			String arrayInStr, String split) {
		if (isEmpty(arrayInStr)) {
			return strToAdd;
		}
		if (isEmpty(strToAdd)) {
			return arrayInStr;
		}

		String[] addedArray = addStringToArray(strToAdd, arrayInStr
				.split(split));
		return StringUtil.getStringFromStringArray(addedArray, split);
	}

	/**
	 * 从实际存储数组的字符串中删除值，不允许重复，返回删除后的字符串
	 * 
	 * @param strToRemove
	 * @param arrayInStr
	 * @param split
	 * @return
	 */
	public static String removeStringFromArrayInString(String strToRemove,
			String arrayInStr, String split) {
		if (isEmpty(arrayInStr)) {
			return "";
		}
		String[] removededArray = removeStringFromArray(strToRemove, arrayInStr
				.split(split));
		return StringUtil.getStringFromStringArray(removededArray, split);
	}

	/**
	 * 将实际存储在字符中的整型数组转换为整型数组
	 * 
	 * @param strArray
	 * @param spliter
	 * @return
	 */
	public static int[] getIntArrayFromArrayInString(String strArray,
			String spliter) {
		if (isEmpty(strArray)) {
			return new int[0];
		}
		spliter = (isEmpty(spliter)) ? DEFAULT_SPLITER : spliter;
		String[] strs = strArray.split(spliter);
		ArrayList<Integer> intArray = new ArrayList<Integer>();
		for (String str : strs) {
			try {
				intArray.add(Integer.parseInt(str));
			} catch (Exception ignore) {

			}
		}
		int[] result = new int[intArray.size()];
		int i = 0;
		for (Integer r : intArray) {
			result[i] = r.intValue();
			i++;
		}
		return result;
	}

	/**
	 * 连接不定个数字符串
	 * 
	 * @param inStrs
	 * @return
	 */
	public static StringBuilder concat(String... inStrs) {
		StringBuilder sb = new StringBuilder();
		for (String inStr : inStrs) {
			sb.append(inStr);
		}
		return sb;
	}

	/**
	 * 连接不定个数字符串,并返回指定的StringBuilder
	 * 
	 * @param initStr
	 * @param inStrs
	 * @return
	 */
	public static StringBuilder concat(StringBuilder initStr, String... inStrs) {
		StringBuilder sb = new StringBuilder();
		for (String inStr : inStrs) {
			sb.append(inStr);
		}
		return sb;
	}

	/**
	 * 是否某字符串为空，或全部为空格
	 * 
	 * @param inStr
	 * @return
	 */
	public static boolean isStringEmpty(String inStr) {
		return (inStr == null || inStr.equals("") || inStr.trim().equals("") || inStr
				.toLowerCase().trim().equals("null"));
	}

	public static void notEmpty(String inStr, String defaultValue) {
		if (isStringEmpty(inStr)) {
			inStr = defaultValue;
		}
	}

	/**
	 * is alias to isStringEmpty
	 * 
	 * @param inStr
	 * @return
	 */
	public static boolean isEmpty(String inStr) {
		return isStringEmpty(inStr);
	}

	public static boolean isEmail(String inStr) {
		if (isStringEmpty(inStr)) {
			return false;
		}
		return EMAIL_PATTERN.matcher(inStr).matches();
	}

	public static boolean isNumber(String inStr) {
		if (isStringEmpty(inStr)) {
			return false;
		}
		inStr = inStr.trim();
		return !NUMBER_PATTERN.matcher(inStr).matches();

	}

	/**
	 * 国内ID匹配
	 * 
	 * @param inStr
	 * @return
	 */
	public static boolean isIdNumber(String inStr) {
		if (isStringEmpty(inStr)) {
			return false;
		}
		inStr = inStr.trim();
		return CHINA_ID_PATTERN.matcher(inStr).matches();

	}

	/**
	 * 去掉<>及其中内容
	 */
	public static String removeHtmlTag(String inHtmlSrc) {
		if (isStringEmpty(inHtmlSrc)) {
			return "";
		}
		return inHtmlSrc.replaceAll("<.*?>", "");
	}

	/**
	 * 返回各字符的UTF内码，用指定分割符分隔
	 * 
	 * @param inStr
	 * @param inSpliter
	 *            指定分隔符，如果为零长度，则用,
	 * @return
	 */
	public static String toCharValue(String inStr, String inSpliter) {
		String spliter = inSpliter;
		if (isStringEmpty(inStr)) {
			return "";
		}
		if (spliter == null) {
			spliter = ",";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < inStr.length(); i++) {
			sb.append(((int) inStr.charAt(i))).append(spliter);
		}
		return sb.toString();
	}

	/**
	 * 按照指定长度分割字符串为字符串列表
	 * 
	 * @param inStr
	 * @param length
	 * @return
	 */
	public static List<String> splitStringByLength(String inStr, int length) {
		List<String> result = new ArrayList<String>();
		if (isEmpty(inStr)) {
			return result;
		}
		String str = inStr;
		String left = str;
		if (str.length() <= length) {
			result.add(str);
		} else {
			while (left.length() > 0) {
				result.add(left.substring(0, (left.length() > length) ? length
						: left.length()));
				left = (left.length() > length) ? left.substring(length) : "";
			}
		}
		return result;
	}
	
	public static List<String> toList(String text) {
		
		return toList(text, DEFAULT_STRING_SEPERATOR);
	}
	
	public static List<String> toList(String text, String seperator) {
		
		List<String> res = new ArrayList();
		if(StringUtil.isNull(text)) {
			return res;
		}
		if(StringUtil.isNull(seperator)) {
			res.add(text);
			return res;
		}
		for(String str : text.split(seperator)) {
			if(StringUtil.isNull(str)) {
				continue;
			}
			res.add(str);
		}
		return res;
	}
	
	public static String toString(Object obj) {
		return toString(obj, "");
	}
	
	public static String toString(Object obj, String defaultValue) {
		
		if(isNull(obj)) {
			return defaultValue;
		}
		return obj.toString();
	}

	public static String toString(String[] src, String separator) {
		
		String res = "";
		if(null == src || 0 >= src.length) {
			return res;
		}
		for(String str : src) {
			res += separator + str;
		}
		return res.substring(separator.length()); 
	}
	
	public static String toString(List<String> list) {
		
		return toString(list, ",");
	}
	
	public static String toString(List<String> list, String sepertor) {
			
		String res = "";
		if(null == list || 0 >= list.size()) {
			return "";
		}
		if(null == sepertor) {
			sepertor = ",";
		}
		for(String s : list) {
			res += sepertor + s.trim();
		}
		return res.substring(sepertor.length());
	}
	
	public static String toUnicode(List<String> list) {
		
		String res = "";
		if(null == list || list.size() <= 0) {
			return res;
		}
		for(String str: list) {
			res += "," + toUnicode(str);
		}
		return res.substring(1);
	}
	
	public static String toUnicode(String str) {
		str = (str == null ? "" : str);
		String tmp;
		StringBuffer sb = new StringBuffer(1000);
		char c;
		int i, j;
		sb.setLength(0);
		for (i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			sb.append("\\u");
			j = (c >>> 8); // 取出高8位
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);
			j = (c & 0xFF); // 取出低8位
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);

		}
		return (new String(sb));
	}

	public static String revertUnicode(String str) {
		str = (str == null ? "" : str);
		if (str.indexOf("\\u") == -1)// 如果不是unicode码则原样返回
			return str;

		StringBuffer sb = new StringBuffer(1000);

		for (int i = 0; i <= str.length() - 6;) {
			String strTemp = str.substring(i, i + 6);
			String value = strTemp.substring(2);
			int c = 0;
			for (int j = 0; j < value.length(); j++) {
				char tempChar = value.charAt(j);
				int t = 0;
				switch (tempChar) {
				case 'a':
					t = 10;
					break;
				case 'b':
					t = 11;
					break;
				case 'c':
					t = 12;
					break;
				case 'd':
					t = 13;
					break;
				case 'e':
					t = 14;
					break;
				case 'f':
					t = 15;
					break;
				default:
					t = tempChar - 48;
					break;
				}

				c += t * ((int) Math.pow(16, (value.length() - j - 1)));
			}
			sb.append((char) c);
			i = i + 6;
		}
		return sb.toString();
	}

	
	public static boolean isNull(Object str) {
		
		boolean flag = false;
		if(null == str || "".equals(str.toString().trim())) {
			flag = true;
		}
		return flag;
	}
	
	public static boolean isNullString(Object str) {
		boolean flag = false;
		if(null == str || "".equals(str.toString().trim()) || "null".equalsIgnoreCase(str.toString())) {
			flag = true;
		}
		return flag;
	}

	public static String[] convertObjectToString(Object[] obj) {
		
		String[] str = new String[obj.length];
		for(int i=0 ; i<obj.length ; i++) {
			if(null == obj[i]) {
				str[i] = "";
			} else {
				str[i] = obj[i].toString();
			}
		}
		return str;
	}

	
	public static String[] filter(String[] source) {

		String[] res;
		List<String> list = new ArrayList();
		for(int i=0 ; i<source.length ; i++) {
			if(null == source[i] || "".equals(source[i].trim())) {
				continue;
			}
			list.add(source[i].trim());
		}
		Object[] obj = list.toArray();
		res = convertObjectToString(obj);
		return res;
	}
	
	public static String[] filterRepeat(String[] src) {
		
		List<String> list = new ArrayList();
		for(String str : src) {
			if(isNull(str) == false && list.contains(str.trim()) == false) {
				list.add(str);
			} 
		}
		return list.toArray(new String[list.size()]);
	}
	
	public static List filterRepeat(List<String> srcList) {

		List<String> res = new ArrayList();
		if(null == srcList || 0 >= srcList.size()) {
			return res;
		}
		for(String str : srcList) {
			if(isNull(str) == false && res.contains(str.trim()) == false) {
				res.add(str);
			} 
		}
		return res;
	}
	
	public static String toUpper4FirstChar(String word) {
		
		if(StringUtil.isNull(word)) {
			return word;
		}
		word = word.trim();
		return word.trim().substring(0, 1).toUpperCase() + word.trim().substring(1);
	}

	public static double toDouble(Object obj, double def) {
		
		double res = def;
		if(StringUtil.isNull(obj)) {
			return def;
		}
		try{
			res = Double.parseDouble(obj.toString());
		} catch(Exception e) {
			res = def;
		}
		return res;
	}

	public static double toDouble(Object obj) {

		return toDouble(obj, 0);
	}
	
	public static long toLong(Object obj, long def) {
		
		return (long)toDouble(obj, def);
	}
	
	public static long toLong(Object obj) {
		
		return toLong(obj, 0);
	}
	
	public static int toInteger(Object obj, int def) {
		
		return (int)toDouble(obj, def);
	}
	
	public static int toInteger(Object obj) {
		
		return (int)toInteger(obj, 0);
	}
	
	public static boolean toBoolean(Object obj, boolean def) {
		
		boolean res = def;
		if(StringUtil.isNull(obj)) {
			return def;
		}
		try{
			res = Boolean.valueOf(obj.toString());
		} catch(Exception e) {
			res = def;
		}
		return def;
	}
	
	public static boolean toBoolean(Object obj) {
		return toBoolean(obj, false);
	}
	
	public static String getFunction4Set(String field) {
		

		if(StringUtil.isNull(field)) {
			return field;
		}
		return FUNCTION_TITLE_SET + StringUtil.toUpper4FirstChar(field);
	}
	
	public static String getFunction4Get(String field) {
		

		if(StringUtil.isNull(field)) {
			return field;
		}
		return FUNCTION_TITLE_GET + StringUtil.toUpper4FirstChar(field);
	}
}
