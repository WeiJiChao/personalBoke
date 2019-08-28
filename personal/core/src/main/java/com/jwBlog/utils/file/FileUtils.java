package com.jwBlog.utils.file;

import com.jwBlog.base.Constants;
import com.jwBlog.frame.log.BaseSystemOutLog;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件获取
 * Created by 许林 on 2016/3/17.
 */
public class FileUtils {
    public static String getApplicationPath(String path) {
        String strPath = Constants.param_default_application_path;
        if (!path.substring(0, 1).equals("/")) {
            return strPath + "/" + path;
        } else {
            return strPath + path;
        }
    }

    public static String getTomcatPath(String path) {
        String strpath = "";
        try {
            strpath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
        } catch (URISyntaxException e) {
            writeErrorLog("", "", e);
        }
        if (!path.substring(0, 1).equals("/")) {
            return strpath.replace("/WEB-INF/classes/", "/") + path;
        } else {
            return strpath.replace("/WEB-INF/classes/", "") + path;
        }
    }

    /**
     * 得到当前路径下所有文件
     *
     * @param filePath
     * @return
     */
    public static List<File> getFilesThePath(String filePath) {
        List<File> rtnFileList = new ArrayList<File>();
        try {
            File file = new File(filePath);
            if (!file.exists()) throw new Exception("路径不存在");
            if (file.isDirectory()) {//是路径
                for (File fileItem : file.listFiles()) {
                    if (fileItem.isFile()) {
                        rtnFileList.add(fileItem);
                    }
                }
            } else if (file.isFile()) {//是文件
                rtnFileList.add(file);
            }
        } catch (Exception e) {
            writeErrorLog("", "", e);
        }
        return rtnFileList;
    }

    /**
     * 得到当前路径下某格式所有文件
     *
     * @param filePath
     * @param fileSurfix      后缀 .txt
     * @param fileNameContent 包含内容
     * @return
     */
    public static List<File> getFilesThePath(String filePath, final String fileSurfix, final String filePrefix, final String fileNameContent) {
        List<File> rtnFileList = new ArrayList<File>();
        try {
            //文件过滤器
            FilenameFilter fileNameFilter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    boolean lb_isAccept = true;
                    if (lb_isAccept && !"".equals(filePrefix) && name.indexOf(filePrefix) != 0) {//后缀
                        lb_isAccept = false;
                    }
                    if (lb_isAccept && !"".equals(fileSurfix) && name.lastIndexOf('.') > 0) {//后缀
                        int lastIndex = name.lastIndexOf('.');
                        String str = name.substring(lastIndex);
                        if (!str.equals(fileSurfix)) {
                            lb_isAccept = false;
                        }
                    }
                    if (lb_isAccept && !"".equals(fileNameContent) && name.indexOf(fileNameContent) < 0) {//后缀
                        lb_isAccept = false;
                    }
                    return lb_isAccept;
                }
            };
            File file = new File(filePath);
            if (!file.exists()) throw new Exception("路径不存在");
            if (file.isDirectory()) {//是路径
                for (File fileItem : file.listFiles(fileNameFilter)) {
                    if (fileItem.isFile()) {
                        rtnFileList.add(fileItem);
                    }
                }
            } else if (file.isFile()) {//是文件
                boolean lb_isAccept = true;
                String name = file.getName();
                if (lb_isAccept && !"".equals(filePrefix) && name.indexOf(filePrefix) != 0) {//后缀
                    lb_isAccept = false;
                }
                if (lb_isAccept && !"".equals(fileSurfix) && name.lastIndexOf('.') > 0) {//后缀
                    int lastIndex = name.lastIndexOf('.');
                    String str = name.substring(lastIndex);
                    if (!str.equals(fileSurfix)) {
                        lb_isAccept = false;
                    }
                }
                if (lb_isAccept && !"".equals(fileNameContent) && name.indexOf(fileNameContent) < 0) {//后缀
                    lb_isAccept = false;
                }
                if (lb_isAccept) {
                    rtnFileList.add(file);
                }
            }
        } catch (Exception e) {
            writeErrorLog("", "", e);
        }
        return rtnFileList;
    }

    /**
     * 得到当前路径下某格式所有文件名
     *
     * @param filePath
     * @param fileSurfix      后缀 .txt
     * @param fileNameContent 包含内容
     * @return
     */
    public static List<String> getFileNamesThePath(String filePath, final String fileSurfix, final String filePrefix, final String fileNameContent) {
        List<String> rtnFileList = new ArrayList<String>();
        try {
            //文件过滤器
            FilenameFilter fileNameFilter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    boolean lb_isAccept = true;
                    if (lb_isAccept && !"".equals(filePrefix) && name.indexOf(filePrefix) != 0) {//后缀
                        lb_isAccept = false;
                    }
                    if (lb_isAccept && !"".equals(fileSurfix) && name.lastIndexOf('.') > 0) {//后缀
                        int lastIndex = name.lastIndexOf('.');
                        String str = name.substring(lastIndex);
                        if (!str.equals(fileSurfix)) {
                            lb_isAccept = false;
                        }
                    }
                    if (lb_isAccept && !"".equals(fileNameContent) && name.indexOf(fileNameContent) < 0) {//后缀
                        lb_isAccept = false;
                    }
                    return lb_isAccept;
                }
            };
            File file = new File(filePath);
            if (!file.exists()) throw new Exception("路径不存在");
            if (file.isDirectory()) {//是路径
                for (File fileItem : file.listFiles(fileNameFilter)) {
                    if (fileItem.isFile()) {
                        rtnFileList.add(fileItem.getAbsolutePath());
                    }
                }
            } else if (file.isFile()) {//是文件
                boolean lb_isAccept = true;
                String name = file.getName();
                if (lb_isAccept && !"".equals(filePrefix) && name.indexOf(filePrefix) != 0) {//后缀
                    lb_isAccept = false;
                }
                if (lb_isAccept && !"".equals(fileSurfix) && name.lastIndexOf('.') > 0) {//后缀
                    int lastIndex = name.lastIndexOf('.');
                    String str = name.substring(lastIndex);
                    if (!str.equals(fileSurfix)) {
                        lb_isAccept = false;
                    }
                }
                if (lb_isAccept && !"".equals(fileNameContent) && name.indexOf(fileNameContent) < 0) {//后缀
                    lb_isAccept = false;
                }
                if (lb_isAccept) {
                    rtnFileList.add(filePath);
                }
            }
        } catch (Exception e) {
            writeErrorLog("", "", e);
        }
        return rtnFileList;
    }

    /**
     * 得到路径下所有文件
     *
     * @param filePath
     * @return
     */
    public static List<File> getFilesAll(String filePath) {
        List<File> rtnFileList = new ArrayList<File>();
        try {
            File file = new File(filePath);
            if (!file.exists()) throw new Exception("路径不存在");
            if (file.isDirectory()) {//是路径
                for (File fileItem : file.listFiles()) {
                    rtnFileList.addAll(getFilesAll(fileItem));
                }
            } else if (file.isFile()) {//是文件
                rtnFileList.add(file);
            }
        } catch (Exception e) {
            writeErrorLog("", "", e);
        }
        return rtnFileList;
    }

    /**
     * 得到路径下所有文件
     *
     * @param file
     * @return
     */
    public static List<File> getFilesAll(File file) {
        List<File> rtnFileList = new ArrayList<File>();
        try {
            if (!file.exists()) throw new Exception("路径不存在");
            if (file.isDirectory()) {//是路径
                for (File fileItem : file.listFiles()) {
                    rtnFileList.addAll(getFilesAll(fileItem));
                }
            } else if (file.isFile()) {//是文件
                rtnFileList.add(file);
            }
        } catch (Exception e) {
            writeErrorLog("", "", e);
        }
        return rtnFileList;
    }

    /**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     */
    public static String readFileByChars(String fileName) {
        Reader reader = null;
        StringBuffer rtnStr = new StringBuffer("");
        try {
            // System.out.println("以字符为单位读取文件内容，一次读多个字节：");
            // 一次读多个字符
            char[] tempchars = new char[30];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(fileName));
            // 读入多个字符到字符数组中，charread为一次读取字符数
            while ((charread = reader.read(tempchars)) != -1) {
                // 同样屏蔽掉\r不显示
                if ((charread == tempchars.length) && (tempchars[tempchars.length - 1] != '\r')) {
                    rtnStr.append(tempchars);
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                            rtnStr.append(tempchars[i]);
                        }
                    }
                }
            }
            return htmlspecialchars(rtnStr.toString());
        } catch (Exception e1) {
            writeErrorLog("", "", e1);
            return "";
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static String readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer rtnStr = new StringBuffer("");
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                rtnStr.append(tempString);
            }
            reader.close();
            return htmlspecialchars(rtnStr.toString());
        } catch (IOException e) {
            writeErrorLog("", "", e);
            return "";
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public static int getTotalLines(File file, String encoding) throws IOException {

        FileInputStream input = null;
        InputStreamReader reader = null;
        BufferedReader br = null;
        Integer li_rowCount = 0;
        try {
            input = new FileInputStream(file);
            if (encoding == null) {
                reader = new InputStreamReader(input);
            } else {
                reader = new InputStreamReader(input, encoding);
            }
            br = new BufferedReader(reader, 10 * 1024 * 1024);
            String strInString;
            while ((strInString = br.readLine()) != null) {
                li_rowCount++;
            }
        } catch (Exception e) {
            writeErrorLog("", "", e);
        } finally {
            if (input != null) {
                input.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (br != null) {
                br.close();
            }
        }
        return li_rowCount;
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static String readFileByLinesByLine(String fileName, String encoding) {
        File file = new File(fileName);
        InputStreamReader reader = null;
        StringBuffer rtnStr = new StringBuffer("");
        BufferedReader br = null;
        try {
            FileInputStream input = new FileInputStream(file);
            if (encoding == null) {
                reader = new InputStreamReader(input);
            } else {
                reader = new InputStreamReader(input, encoding);
            }
            br = new BufferedReader(reader, 1 * 1024 * 1024);
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = br.readLine()) != null) {
                rtnStr.append(tempString).append("\r\n");
            }
            reader.close();
            return htmlspecialchars(rtnStr.toString());
        } catch (IOException e) {
            writeErrorLog("", "", e);
            return "";
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    /**
     * 以字节读取文件，返回字节数组
     */
    public static byte[] readFileByByteToArr(String fileName) {
        File file = new File(fileName);
//        InputStreamReader reader = null;
//        StringBuffer rtnStr = new StringBuffer("");
//        byte[] result = new byte[1024 * 1024 << 2];
        BufferedInputStream ins = null;
        try {
            FileInputStream input = new FileInputStream(file);

//            br = new BufferedReader(reader,1 * 1024 * 1024);
            ins = new BufferedInputStream(input);
            // 一次读入一行，直到读入null为文件结束
            byte[] buffer = new byte[ins.available()];
            System.err.println("readFileByByteToArr---------读取的文件的大小-----"+buffer.length);
            ins.read(buffer);
            ins.close();
            return buffer;
        } catch (IOException e) {
            writeErrorLog("", "", e);
            return new byte[0];
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public static String readTxtFile(String filePath) {
        try {
            String encoding = "utf-8";
            StringBuffer rtnStr = new StringBuffer("");
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    rtnStr.append(lineTxt);
                }
                read.close();
                return htmlspecialchars(rtnStr.toString());
            } else {
                writeSystemLog("readTxtFile", "找不到指定的文件");
                return "";
            }
        } catch (Exception e) {
            writeSystemLog("readTxtFile", "读取文件内容出错");
            writeErrorLog("", "", e);
            return "";
        }
    }

    public static String readTxtFile(String filePath, String encoding) {
        try {
            StringBuffer rtnStr = new StringBuffer("");
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    rtnStr.append(lineTxt);
                }
                read.close();
                return htmlspecialchars(rtnStr.toString());
            } else {
                writeSystemLog("readTxtFile", "找不到指定的文件");
                return "";
            }
        } catch (Exception e) {
            writeSystemLog("readTxtFile", "读取文件内容出错");
            writeErrorLog("", "", e);
            return "";
        }
    }

    /**
     * 随机读取文件内容
     */
    public static String readFileByRandomAccess(String fileName) {
        RandomAccessFile randomFile = null;
        StringBuffer rtnStr = new StringBuffer("");
        try {
            // 打开一个随机访问文件流，按只读方式
            randomFile = new RandomAccessFile(fileName, "r");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 读文件的起始位置
            int beginIndex = (fileLength > 4) ? 4 : 0;
            // 将读文件的开始位置移到beginIndex位置。
            randomFile.seek(beginIndex);
            byte[] bytes = new byte[10];
            int byteread = 0;
            // 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
            // 将一次读取的字节数赋给byteread
            while ((byteread = randomFile.read(bytes)) != -1) {
                rtnStr.append(new String(bytes, 0, byteread));
            }
            return htmlspecialchars(rtnStr.toString());
        } catch (IOException e) {
            writeErrorLog("", "", e);
            return "";
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public static String htmlspecialchars(String str) {
        str = str.replaceAll("&", "&amp;");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("\"", "&quot;");
        return str;
    }

    private static void writeErrorLog(String method, String content, Exception e) {
        BaseSystemOutLog.writeLog(BaseSystemOutLog.LogType_Error, "FileUtils", method, content, e);
    }

    private static void writeSystemLog(String method, String content) {
        BaseSystemOutLog.writeLog(BaseSystemOutLog.LogType_Debug, "FileUtils", method, content);
    }
}
