package com.jwBlog.utils;


import cn.hutool.core.codec.Base64;
import com.jwBlog.frame.log.BaseSystemOutLog;
import org.springframework.util.StringUtils;

import java.io.*;

public class SerializeUtil {
	/**
	 * 反序列化
	 * @param str 待序列化的字符串
	 * @return
	 */
	public static Object deserialize(String str) {
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			if (StringUtils.isEmpty(str)) {
				return  null;
			}
			bis = new ByteArrayInputStream(Base64.decode(str));
			ois = new ObjectInputStream(bis);
			return ois.readObject();
		} catch (Exception e) {
			throw new RuntimeException("deserialize session error", e);
		} finally {
			try {
				if(ois != null) {
					ois.close();
				}
				if (bis != null) {
					bis.close();
				}
			} catch (IOException e) {
				writeErrorLog("SerializeUtil","反序列化字符串异常",e);
			}

		}
	}

	/**
	 * 对象序列化
	 * @param obj 待序列化的对象
	 * @return
	 */
	public static String serialize(Object obj) {
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			return Base64.encode(bos.toByteArray());
		} catch (Exception e) {
			throw new RuntimeException("serialize session error", e);
		} finally {
			try {
				oos.close();
				bos.close();
			} catch (IOException e) {
				writeErrorLog("SerializeUtil","序列化字符串异常",e);
			}

		}
	}
	private static void writeErrorLog(String method,String content,Exception e){
		BaseSystemOutLog.writeLog(BaseSystemOutLog.LogType_Error, "UpDownloadFile", method, content, e);
	}
}
