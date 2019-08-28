package com.jwBlog.utils.DataCheck;

import com.jwBlog.frame.exception.MyException;
import com.jwBlog.utils.dp.DoubleUtils;

import java.math.BigDecimal;

/**
 * devFrame Created by 许林 on 2017/2/28.
 */
public class DataCheckUtils {
    public static String checkInteger(String colValue, String showColumnName, Integer length, String isNull) throws MyException {
        try {
            if ("NO".equals(isNull)) if (colValue == null || "".equals(colValue))throw new MyException(showColumnName + "不能为空");
            if (!DataCheck.isNumeric(colValue))throw new MyException(showColumnName + "格式不正确");
            if(length!=0)if (colValue.length() > length) throw new MyException(showColumnName + "数据超长");
            return "";
        } catch (Exception e) {
            throw new MyException(e);
        }
    }
    public static String checkIntegerNoZero(String colValue, String showColumnName, Integer length, String isNull) throws MyException {
        try {
            if ("NO".equals(isNull)) if (colValue == null || "".equals(colValue) || "0".equals(colValue))throw new MyException(showColumnName + "不能为空");
            if (!DataCheck.isNumeric(colValue))throw new MyException(showColumnName + "格式不正确");
            if(length!=0)if (colValue.length() > length) throw new MyException(showColumnName + "数据超长");
            return "";
        } catch (Exception e) {
            throw new MyException(e);
        }
    }
    public static String checkLong(String colValue, String showColumnName, Integer length, String isNull) throws MyException {
        try {
            if ("NO".equals(isNull)) if (colValue == null || "".equals(colValue) || "0".equals(colValue))throw new MyException(showColumnName + "不能为空");
            if (!DataCheck.isNumeric(colValue))throw new MyException(showColumnName + "格式不正确");
            if(length!=0)if (colValue.length() > length)throw new MyException(showColumnName + "数据超长");
            return "";
        } catch (Exception e) {
            throw new MyException(e);
        }
    }

    public static String checkBigDecimal(String colValue, String showColumnName, Integer length, Integer scale, String isNull) throws MyException {
        try {
            if ("NO".equals(isNull)) if (colValue == null || "".equals(colValue) || "0".equals(colValue))throw new MyException(showColumnName + "不能为空");
            if (!DataCheck.isDecimal(colValue))throw new MyException(showColumnName + "格式不正确");
            if ("NO".equals(isNull)) if (DoubleUtils.compare(0D, Double.parseDouble(colValue)) == 0)throw new MyException(showColumnName + "不能等于0");
            String ld_data = DoubleUtils.round(Double.parseDouble(colValue), scale, BigDecimal.ROUND_UP).toString();
            if(length!=0)if (ld_data.length() > length + 1) throw new MyException(showColumnName + "数据超长");
            return "";
        } catch (Exception e) {
            throw new MyException(e);
        }
    }

    public static String checkDouble(String colValue, String showColumnName, Integer length, Integer scale, String isNull) throws MyException {
        try {
            if ("NO".equals(isNull)) if (colValue == null || "".equals(colValue) || "0".equals(colValue))throw new MyException(showColumnName + "不能为空");
            if (!DataCheck.isDecimal(colValue))throw new MyException(showColumnName + "格式不正确");
            if ("NO".equals(isNull)) if (DoubleUtils.compare(0D, Double.parseDouble(colValue)) == 0) throw new MyException(showColumnName + "不能等于0");
            String ld_data = DoubleUtils.round(Double.parseDouble(colValue), scale, BigDecimal.ROUND_UP).toString();
            if(length!=0)if (ld_data.length() > length + 1)throw new MyException(showColumnName + "数据超长");
            return "";
        } catch (Exception e) {
            throw new MyException(e);
        }
    }
    public static String checkDate(String colValue, String showColumnName, String isNull) throws MyException {
        try {
            if ("NO".equals(isNull)) if (colValue == null || "".equals(colValue))throw new MyException(showColumnName + "不能为空");
            if(!DataCheck.isDate(colValue)){throw new MyException(showColumnName+"格式不正确"); }
            return "";
        } catch (Exception e) {
            throw new MyException(e);
        }
    }
    public static String checkString(String colValue, String showColumnName, Integer length, String isNull) throws MyException {
        try {
            if ("NO".equals(isNull)) if (colValue == null || "".equals(colValue))throw new MyException(showColumnName + "不能为空");
            if(length!=0)if(colValue.length()>length)throw new MyException(showColumnName+"数据超长");
            if(DataCheck.isHaveSpecialChar(colValue))throw new MyException(showColumnName+"包含特殊字符");
            return "";
        } catch (Exception e) {
            throw new MyException(e);
        }
    }
    public static String checkString_expression(String colValue, String showColumnName, Integer length, String isNull) throws MyException {
        try {
            if ("NO".equals(isNull)) if (colValue == null || "".equals(colValue))throw new MyException(showColumnName + "不能为空");
            if(length!=0)if(colValue.length()>length)throw new MyException(showColumnName+"数据超长");
            if(DataCheck.isHaveSpecialChar_expression(colValue))throw new MyException(showColumnName+"包含特殊字符");
            return "";
        } catch (Exception e) {
            throw new MyException(e);
        }
    }
    public static String checkString_expression2(String colValue, String showColumnName, Integer length, String isNull) throws MyException {
        try {
            if ("NO".equals(isNull)) if (colValue == null || "".equals(colValue))throw new MyException(showColumnName + "不能为空");
            if(length!=0)if(colValue.length()>length)throw new MyException(showColumnName+"数据超长");
            if(DataCheck.isHaveSpecialChar_expression2(colValue))throw new MyException(showColumnName+"包含特殊字符");
            return "";
        } catch (Exception e) {
            throw new MyException(e);
        }
    }
}
