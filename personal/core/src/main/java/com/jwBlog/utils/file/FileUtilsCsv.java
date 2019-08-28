package com.jwBlog.utils.file;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jwBlog.frame.log.BaseSystemOutLog;
import com.jwBlog.utils.dp.DateUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 许林 on 2016/3/17.
 */
public class FileUtilsCsv {
    private static final String SPECIAL_CHAR_A = "[^\",\\n 　]";
    private static final String SPECIAL_CHAR_B = "[^\",\\n]";
    /**
     * csv文件读取<BR/>
     * 读取绝对路径为argPath的csv文件数据，并以List返回。
     *
     * @param fileName csv文件绝对路径
     * @return csv文件数据（List<String[]>）
     * @throws FileNotFoundException
     * @throws IOException
     */
//    public static List readCsvFile(String fileName,String encoding) throws FileNotFoundException, IOException {
//        File file=new File(fileName);
//        // readCsvFile2(file, "unicode");
//         //readCsvFile2(file, "utf-8");
//         //readCsvFile2(file, "GBK");
//        return readCsvFile2(file, encoding);
//    }
    public static JSONArray readCsvFileJson(String fileName,String encoding) throws FileNotFoundException, IOException {
        File file=new File(fileName);

        return readCsvFile2Json(file, encoding);
    }
    public static Integer readCsvFileLength(String fileName,String encoding) throws FileNotFoundException, IOException {
        File file=new File(fileName);

        return readCsvFile2Length(file, encoding);
    }
    /**
     * csv文件读取<BR/>
     * 读取绝对路径为argPath的csv文件数据，并以List返回。
     *
     * @param file csv文件绝对路径
     * @return csv文件数据（List<String[]>）
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static List readCsvFile3(File file,String encoding) throws FileNotFoundException, IOException {
        List<List<String>> cellList=new ArrayList<List<String>>();
        FileChannel fcin = null;
        ByteBuffer rBuffer = null;
        Pattern pCells = Pattern.compile("(\"[^\"]*(\"{2})*[^\"]*\")*[^,]*,");
        String strReplacePattern = "(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,";
        String strReplacePattern2 = "(?sm)(\"(\"))";
        String strReplacePattern3 = "^'|'*$";//首尾的单引号
        int byteSize = 1024*1024*2;
        try {
            fcin = new RandomAccessFile(file, "r").getChannel();
            rBuffer = ByteBuffer.allocate(byteSize);
            String enterStr = "\n";
            byte[] bs = new byte[byteSize];
            //temp：由于是按固定字节读取，在一次读取中，第一行和最后一行经常是不完整的行，因此定义此变量来存储上次的最后一行和这次的第一行的内容，
            //并将之连接成完成的一行，否则会出现汉字被拆分成2个字节，并被提前转换成字符串而乱码的问题，数组大小应大于文件中最长一行的字节数
            byte[] temp = new byte[4000];
            StringBuilder strBuf = new StringBuilder("");
            while (fcin.read(rBuffer) != -1) {
                int rSize = rBuffer.position();
                rBuffer.rewind();
                rBuffer.get(bs);
                rBuffer.clear();
                //windows下ascii值13、10是换行和回车，unix下ascii值10是换行
                //从开头顺序遍历，找到第一个换行符
                int startNum=0;
                int length=0;
                for(int i=0;i<rSize;i++){
                    if(bs[i]==10){//找到换行字符
                        startNum=i;
                        for(int k=0;k<4000;k++){
                            if(temp[k]==0){//temp已经存储了上一次读取的最后一行，因此遍历找到空字符位置，继续存储此次的第一行内容，连接成完成一行
                                length=i+k;
                                for(int j=0;j<=i;j++){
                                    temp[k+j]=bs[j];
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
                //将拼凑出来的完整的一行转换成字符串
                String tempString1 = new String(temp, 0, length+1, encoding);
                //清空temp数组
                for(int i=0;i<temp.length;i++){
                    temp[i]=0;
                }
                //从末尾倒序遍历，找到第一个换行符
                int endNum=0;
                int k = 0;
                for(int i=rSize-1;i>=0;i--){
                    if(bs[i]==10){
                        endNum=i;//记录最后一个换行符的位置
                        for(int j=i+1;j<rSize;j++){
                            temp[k++]=bs[j];//将此次读取的最后一行的不完整字节存储在temp数组，用来跟下一次读取的第一行拼接成完成一行
                            bs[j]=0;
                        }
                        break;
                    }
                }
                //去掉第一行和最后一行不完整的，将中间所有完整的行转换成字符串
                String tempString2 = new String(bs, startNum+1, endNum-startNum, encoding);

                //拼接两个字符串
                String tempString = tempString1 + tempString2;
                int fromIndex = 0;
                int endIndex = 0;
                while ((endIndex = tempString.indexOf(enterStr, fromIndex)) != -1) {
                    StringBuffer line = new StringBuffer(strBuf.toString());
                    line.append(tempString.substring(fromIndex, endIndex));
                    String inString=line.toString();
                    strBuf.delete(0, strBuf.length());
                    fromIndex = endIndex + 1;
                    if(inString.startsWith("\uFEFF")){inString = inString.replace("\uFEFF", ""); }
                    Matcher mCells = pCells.matcher(inString);
                    List<String> cells=new ArrayList<String>();
                    cells.clear();
                    while (mCells.find()) {
                        String str = mCells.group();
                        str = str.replaceAll(strReplacePattern, "$1");
                        str = str.replaceAll(strReplacePattern2, "$2");
                        str = str.replaceAll(strReplacePattern3, "");
                        cells.add(str);
                    }
                    cellList.add(cells);
                }
                if (rSize > tempString.length()) {
                    strBuf.append(tempString.substring(fromIndex,
                            tempString.length()));
                } else {
                    strBuf.append(tempString.substring(fromIndex, rSize));
                }
            }
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (rBuffer != null) {
                    rBuffer=null;
                }
                if (fcin != null) {
                    fcin.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
        return cellList;
    }
    /**
     * csv文件读取<BR/>
     * 读取绝对路径为argPath的csv文件数据，并以List返回。
     *
     * @param file csv文件绝对路径
     * @return csv文件数据（List<String[]>）
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static List readCsvFile2(File file,String encoding) throws FileNotFoundException, IOException {
        List<List<String>> cellList=new ArrayList<List<String>>();
        FileInputStream input = null;
        InputStreamReader reader = null;
        BufferedReader br=null;
        Pattern pCells = Pattern.compile("\\G(?:^|,)(?:\"([^\"]*+(?:\"\"[^\"]*+)*+)\"|([^\",]*+))" );
        String strReplacePattern = "(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,";
        String strReplacePattern2 = "(?sm)(\"(\"))";
        String strReplacePattern3 = "^'|'*$";//首尾的单引号
        String strReplacePattern4 = "^\"|\"*$";//首尾的单引号
        String strReplacePattern5 = "^(,\")|\"*$";//首尾的单引号
        try {
            input = new FileInputStream(file);
            if (encoding == null)
            {
                reader = new InputStreamReader(input);
            }
            else
            {
                reader = new InputStreamReader(input,encoding);
            }
            br = new BufferedReader(reader,1 * 1024 * 1024);
            String inString = "";

            while((inString = br.readLine())!= null){
                //inString=new String(inString.getBytes(encoding), Constants.ENCODING_Default);
                if(inString.startsWith("\uFEFF")){inString = inString.replace("\uFEFF", ""); }
                Matcher mCells = pCells.matcher(" "+inString);
                Matcher mquote = Pattern.compile("\"\"").matcher("");
                List<String> cells=new ArrayList<String>();
                cells.clear();
                while (mCells.find()) {
                    String str="";
                    if (mCells.start(2) >= 0) {
                        str = mCells.group(2);
                    } else {
                        str = mquote.reset(mCells.group(1)).replaceAll("\"");
                    }
                    //String str = mCells.group();
                    //str = str.replaceAll(strReplacePattern, "$1");
                    //str = str.replaceAll(strReplacePattern2, "$2");
                    str = str.replaceAll(strReplacePattern3, "");
                    str = str.replaceAll(strReplacePattern4, "");
                    str = str.replaceAll(strReplacePattern5, "");
                    str=str.trim();
                    cells.add( str);
//                    cells.add( new DesJs().encrypt(str));
                }
                if(cells==null||cells.size()<1)continue;
                cellList.add(cells);
            }
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (reader != null) {
                    reader.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
        return cellList;
    }
    public static JSONArray readCsvFile2Json(File file,String encoding) throws FileNotFoundException, IOException {
        JSONArray cellList=new JSONArray();
        FileInputStream input = null;
        InputStreamReader reader = null;
        BufferedReader br=null;
        Pattern pCells = Pattern.compile("\\G(?:^|,)(?:\"([^\"]*+(?:\"\"[^\"]*+)*+)\"|([^\",]*+))" );
//        Pattern pCells = Pattern.compile("\\G(?:^|,)(?:\"|=\"([^\"]*+(?:\"\"[^\"]*+)*+)\"|([^\",]*+))" );
        String strReplacePattern = "(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,";
        String strReplacePattern2 = "(?sm)(\"(\"))";
        String strReplacePattern3 = "^'|'*$";//首尾的单引号
        String strReplacePattern4 = "^\"|\"*$";//首尾的单引号
        String strReplacePattern5 = "^(,\")|\"*$";//首尾的单引号
        try {
            input = new FileInputStream(file);
            if (encoding == null)
            {
                reader = new InputStreamReader(input);
            }
            else
            {
                reader = new InputStreamReader(input,encoding);
            }
            br = new BufferedReader(reader,1 * 1024 * 1024);
            String inString = "";
            int i=0;
            while((inString = br.readLine())!= null){
                //inString=new String(inString.getBytes(encoding), Constants.ENCODING_Default);
                if(inString.startsWith("\uFEFF")){inString = inString.replace("\uFEFF", ""); }
                inString=inString.replaceAll("\",=\"",",").replaceAll("=\"","").replaceAll("\"$","");
                Matcher mCells = pCells.matcher(" "+inString);
                Matcher mquote = Pattern.compile("\"\"").matcher("");
                String strCells="";
                strCells="";
                while (mCells.find()) {
                    String str="";
                    if (mCells.start(2) >= 0) {
                        str = mCells.group(2);
                    } else {
                        str = mquote.reset(mCells.group(1)).replaceAll("\"");
                    }
                    //String str = mCells.group();
                    //str = str.replaceAll(strReplacePattern, "$1");
                    //str = str.replaceAll(strReplacePattern2, "$2");
                    str = str.replaceAll(strReplacePattern3, "");
                    str = str.replaceAll(strReplacePattern4, "");
                    str = str.replaceAll(strReplacePattern5, "");
                    str=str.trim();
                    if(!strCells.equals(""))strCells+="||||";
                    if(str==null||"".equals(str))str=" ";
                    strCells+=str;
                }
                if(strCells==null||strCells.equals(""))continue;
                i++;
                if(i==1){
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("key",strCells);
                    jsonObject.put("value","");
                    cellList.add(jsonObject);
                }else{
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("key","");
                    jsonObject.put("value",strCells);
                    cellList.add(jsonObject);
                }
            }
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (reader != null) {
                    reader.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
        return cellList;
    }
    public static Integer readCsvFile2Length(File file,String encoding) throws FileNotFoundException, IOException {
        int indexI=0;
        FileInputStream input = null;
        InputStreamReader reader = null;
        BufferedReader br=null;
        Pattern pCells = Pattern.compile("\\G(?:^|,)(?:\"([^\"]*+(?:\"\"[^\"]*+)*+)\"|([^\",]*+))" );
        String strReplacePattern = "(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,";
        String strReplacePattern2 = "(?sm)(\"(\"))";
        String strReplacePattern3 = "^'|'*$";//首尾的单引号
        String strReplacePattern4 = "^\"|\"*$";//首尾的单引号
        String strReplacePattern5 = "^(,\")|\"*$";//首尾的单引号
        try {
            input = new FileInputStream(file);
            if (encoding == null)
            {
                reader = new InputStreamReader(input);
            }
            else
            {
                reader = new InputStreamReader(input,encoding);
            }
            br = new BufferedReader(reader,1 * 1024 * 1024);
            String inString = "";

            while((inString = br.readLine())!= null){
                //inString=new String(inString.getBytes(encoding), Constants.ENCODING_Default);
                if(inString.startsWith("\uFEFF")){inString = inString.replace("\uFEFF", ""); }
                Matcher mCells = pCells.matcher(" "+inString);
                Matcher mquote = Pattern.compile("\"\"").matcher("");
                String strCells="";
                strCells="";
                while (mCells.find()) {
                    String str="";
                    if (mCells.start(2) >= 0) {
                        str = mCells.group(2);
                    } else {
                        str = mquote.reset(mCells.group(1)).replaceAll("\"");
                    }
                    //String str = mCells.group();
                    //str = str.replaceAll(strReplacePattern, "$1");
                    //str = str.replaceAll(strReplacePattern2, "$2");
                    str = str.replaceAll(strReplacePattern3, "");
                    str = str.replaceAll(strReplacePattern4, "");
                    str = str.replaceAll(strReplacePattern5, "");
                    str=str.trim();
                    if(!strCells.equals(""))strCells+="||||";
                    strCells+=str;
                }
                if(strCells==null||strCells.equals(""))continue;
                indexI++;
            }
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (reader != null) {
                    reader.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
        return indexI-1;
    }
    /**
     * csv文件读取<BR/>
     * 读取绝对路径为argPath的csv文件数据，并以List返回。
     *
     * @param file csv文件绝对路径
     * @return csv文件数据（List<String[]>）
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static List readCsvFile(File file,String encoding) throws FileNotFoundException, IOException {
        List list = new ArrayList();
        InputStreamReader fr = null;
        BufferedReader bufferedReader = null;
        try {
            fr = new InputStreamReader(new FileInputStream(file),encoding);
            bufferedReader = new BufferedReader(fr);
            String regExp = getRegExp();

            // test
            String strLine = "";
            String strShowLinr="";
            String str = "";
            while ((strLine = bufferedReader.readLine()) != null) {
                Pattern pattern = Pattern.compile(regExp);
                Matcher matcher = pattern.matcher(strLine);
                List listTemp = new ArrayList();
                while(matcher.find()) {
                    str = matcher.group();
                    str = str.trim();
                    if (str.endsWith(",")){
                        str = str.substring(0, str.length()-1);
                        str = str.trim();
                    }
                    if (str.startsWith("\"") && str.endsWith("\"")) {
                        str = str.substring(1, str.length()-1);
                        if (isExisted("\"\"", str)) {
                            str = str.replaceAll("\"\"", "\"");
                        }
                    }
                    if (!"".equals(str)) {
                        listTemp.add(str);
                    }
                    strShowLinr+=str+"------------";
                }
                list.add((String[]) listTemp.toArray(new String[listTemp.size()]));
            }
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
        return list;
    }

    /**
     * csv文件做成<BR/>
     * 将argList写入argPath路径下的argFileName文件里。
     *
     * @param argList  要写入csv文件的数据（List<String[]>）
     * @param argPath csv文件路径
     * @param argFileName csv文件名
     * @param isNewFile 是否覆盖原有文件
     * @throws IOException
     * @throws Exception
     */
    public static void writeCsvFile(List argList, String argPath, String argFileName, boolean isNewFile)
            throws IOException, Exception {
        // 数据check
        if (argList == null || argList.size() == 0) {
            throw new Exception("没有数据");
        }
        for (int i = 0; i < argList.size(); i++) {
            if (!(argList.get(i) instanceof String[])) {
                throw new Exception("数据格式不对");
            }
        }
//        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        OutputStreamWriter writerStream = null;
        String strFullFileName = FileUtils.getApplicationPath(argPath);
        if (strFullFileName.lastIndexOf("/") == (strFullFileName.length() - 1)) {
            strFullFileName += argFileName;
        } else {
            strFullFileName += "/" + argFileName;
        }
        File file = new File(strFullFileName);
        // 文件路径check
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
//            if (isNewFile) {
//                // 覆盖原有文件
//                fileWriter = new FileWriter(file);
//            } else {
//                // 在原有文件上追加数据
//                fileWriter = new FileWriter(file, true);
//            }
            writerStream = new OutputStreamWriter(new FileOutputStream(file),"utf-8");
            bufferedWriter = new BufferedWriter(writerStream);
//            bufferedWriter = new BufferedWriter(fileWriter);

            for (int i = 0; i < argList.size(); i++) {
                String[] strTemp = (String[]) argList.get(i);
                for (int j = 0; j < strTemp.length; j++) {
                    if (isExisted("\"",strTemp[j])) {
                            strTemp[j] = strTemp[j].replaceAll("\"", "\"\"");
                                    bufferedWriter.write("=\""+strTemp[j].replaceAll(",","_")+"\"");
                } else if (isExisted(",",strTemp[j])
                        || isExisted("/n",strTemp[j])
                        || isExisted(" ",strTemp[j])
                        || isExisted("“",strTemp[j])){
                    bufferedWriter.write("=\""+strTemp[j].replaceAll(",","_")+"\"");
                } else {

                    bufferedWriter.write("=\""+strTemp[j].replaceAll(",","_")+"\"");
                }
                if (j < strTemp.length - 1) {
                    bufferedWriter.write(",");
                }
            }
            bufferedWriter.newLine();
        }
    } catch (IOException e) {
         writeErrorLog( "", "", e);
    } finally {
        try {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (writerStream != null) {
                writerStream.close();
            }
        } catch (IOException e) {
            throw e;
        }
    }
}
    /**
     * csv文件做成<BR/>
     * 将argList写入argPath路径下的argFileName文件里。
     *
     * @param argList  要写入csv文件的数据（List<String[]>）
     * @param argPath csv文件路径
     * @param argFileName csv文件名
     * @param isNewFile 是否覆盖原有文件
     * @throws IOException
     * @throws Exception
     */
    public static void writeCsvFileInfo(List<HashMap<String, Object>> argList, String argPath, String argFileName, boolean isNewFile)
            throws IOException, Exception {
        // 数据check
        if (argList == null || argList.size() == 0) {
            throw new Exception("没有数据");
        }
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        String strFullFileName = FileUtils.getApplicationPath(argPath);
        if (strFullFileName.lastIndexOf("/") == (strFullFileName.length() - 1)) {
            strFullFileName += argFileName;
        } else {
            strFullFileName += "/" + argFileName;
        }
        File file = new File(strFullFileName);
        // 文件路径check
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            if (isNewFile) {
                // 覆盖原有文件
                fileWriter = new FileWriter(file);
            } else {
                // 在原有文件上追加数据
                fileWriter = new FileWriter(file, true);
            }
            bufferedWriter = new BufferedWriter(fileWriter);
            //写标题
            HashMap<String, Object> HeadItem=argList.get(0);
            Set<String> keyList=HeadItem.keySet();
            writeCsvFileLine(keyList,bufferedWriter);
            for(HashMap<String, Object> objItem:argList){
                List<Object> rowData=new ArrayList<Object>();
                for(String key:keyList){
                    rowData.add(objItem.get(key));
                }
                bufferedWriter.newLine();
                writeCsvFileLine(rowData,bufferedWriter);
            }
        } catch (IOException e) {
             writeErrorLog( "", "", e);
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }
    /**
     * csv文件做成<BR/>
     * 将argList写入argPath路径下的argFileName文件里。
     *@param columnList  表定义格式
     * @param argList  要写入csv文件的数据（List<String[]>）
     * @param argPath csv文件路径
     * @param argFileName csv文件名
     * @throws IOException
     * @throws Exception
     */
    public static void writeCsvFileInfo(List<HashMap<String, Object>> columnList,List<HashMap<String, Object>> argList, String argPath, String argFileName)
            throws IOException, Exception {
        // 数据check
        if (argList == null || argList.size() == 0) {
            throw new Exception("没有数据");
        }
        OutputStreamWriter writerStream = null;
        BufferedWriter bufferedWriter = null;
        String strFullFileName = FileUtils.getApplicationPath(argPath);
        if (strFullFileName.lastIndexOf("/") == (strFullFileName.length() - 1)) {
            strFullFileName += argFileName;
        } else {
            strFullFileName += "/" + argFileName;
        }
        File file = new File(strFullFileName);
        // 文件路径check
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            writerStream = new OutputStreamWriter(new FileOutputStream(file),"utf-8");
            bufferedWriter = new BufferedWriter(writerStream);
            //写标题
            //HashMap<String, Object> HeadItem=argList.get(0);
            //Set<String> keyList=HeadItem.keySet();
            //writeCsvFileLine(keyList,bufferedWriter);
            List<String> keyList=new ArrayList<String>();
            List<Object> headList=new ArrayList<Object>();
            for(HashMap<String, Object> objItem:columnList){
                Object obj=objItem.get("COLUMN_COMMENT");
                if(obj==null||"".equals(obj)||"".equals(obj.toString().trim()))continue;
                headList.add(obj.toString());
                keyList.add(objItem.get("COLUMN_NAME").toString());
            }
            writeCsvFileLine(headList,bufferedWriter);
            //写数据
            for(HashMap<String, Object> objItem:argList){
                List<Object> rowData=new ArrayList<Object>();
                for(String key:keyList){
                    rowData.add(objItem.get(key));
                }
                bufferedWriter.newLine();
                writeCsvFileLine(rowData,bufferedWriter);
            }
        } catch (IOException e) {
             writeErrorLog( "", "", e);
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (writerStream != null) {
                    writerStream.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }
    public static void writeCsvFileLine(List<Object> strTemp,BufferedWriter bufferedWriter) throws IOException {
        for (int j=0;j<strTemp.size();j++) {
            Object objValue=strTemp.get(j);
            //Object objValue=strTemp.get(j);
            String strValue="";
            if(objValue==null){
                strValue="";
            }else {
                if (objValue.getClass() == String.class) {
                    strValue = objValue.toString();
                } else if (objValue.getClass() == Integer.class) {
                    strValue = objValue.toString();
                } else if (objValue.getClass() == Double.class) {
                    strValue = objValue.toString();
                } else if (objValue.getClass() == Date.class) {
                    strValue = DateUtils.formatDate((Date) objValue, "yyyy-MM-dd HH:mm:ss");
                } else if (objValue.getClass() == java.sql.Timestamp.class) {
                    strValue = DateUtils.formatDate((Date) objValue, "yyyy-MM-dd HH:mm:ss");
                } else {
                    strValue = objValue.toString();
                }
            }
            if (isExisted("\"",strValue)) {
                strValue= strValue.replaceAll("=\"", "\"\"");
                bufferedWriter.write("\""+strValue.replaceAll(",","_")+"\"");
            } else if (isExisted(",",strValue)
                    || isExisted("/n",strValue)
                    || isExisted(" ",strValue)
                    || isExisted("“",strValue)){
                bufferedWriter.write("=\""+strValue.replaceAll(",","_")+"\"");
            } else {
                bufferedWriter.write("=\""+strValue.replaceAll(",","_")+"\"");
            }
            if (j < strTemp.size() - 1) {
                bufferedWriter.write(",");
            }
        }
    }
    public static void writeCsvFileLineWithoutQuotes_noEqual(List<Object> strTemp,
                                                     BufferedWriter bufferedWriter) throws IOException {
        for (int j=0;j<strTemp.size();j++) {
            Object objValue=strTemp.get(j);
            //Object objValue=strTemp.get(j);
            String strValue="";
            if(objValue==null){
                strValue="";
            }else {
                if (objValue.getClass() == String.class) {
                    strValue = objValue.toString();
                    if (strValue.indexOf(",") != -1) {
                        strValue = strValue.replaceAll(",", "_");
                    }
                } else if (objValue.getClass() == Integer.class) {
                    strValue = objValue.toString();
                } else if (objValue.getClass() == Double.class) {
                    strValue = objValue.toString();
                } else if (objValue.getClass() == Date.class) {
                    strValue = DateUtils.formatDate((Date) objValue, "yyyy-MM-dd HH:mm:ss");
                } else {
                    strValue = objValue.toString();
                    if (strValue.indexOf(",") != -1) {
                        strValue = strValue.replaceAll(",", "_");
                    }
                }
            }
            bufferedWriter.write(strValue.replaceAll(",","_"));

            if (j < strTemp.size() - 1) {
                bufferedWriter.write(",");
            }
        }
    }
    public static void writeCsvFileLineWithoutQuotes(List<Object> strTemp,
                                                     BufferedWriter bufferedWriter) throws IOException {
        for (int j=0;j<strTemp.size();j++) {
            Object objValue=strTemp.get(j);
            //Object objValue=strTemp.get(j);
            String strValue="";
            if(objValue==null){
                strValue="";
            }else {
                if (objValue.getClass() == String.class) {
                    strValue = objValue.toString();
                    if (strValue.indexOf(",") != -1) {
                        strValue = strValue.replaceAll(",", "_");
                    }
                } else if (objValue.getClass() == Integer.class) {
                    strValue = objValue.toString();
                } else if (objValue.getClass() == Double.class) {
                    strValue = objValue.toString();
                } else if (objValue.getClass() == Date.class) {
                    strValue = DateUtils.formatDate((Date) objValue, "yyyy-MM-dd HH:mm:ss");
                } else {
                    strValue = objValue.toString();
                    if (strValue.indexOf(",") != -1) {
                        strValue = strValue.replaceAll(",", "_");
                    }
                }
            }
            bufferedWriter.write("=\""+strValue.replaceAll(",","_")+"\"");

            if (j < strTemp.size() - 1) {
                bufferedWriter.write(",");
            }
        }
    }

    public static void writeCsvFileLineForMongoDB_equal(List<String> strTemp,
                                                  BufferedWriter bufferedWriter,
                                                  boolean isHeader) throws IOException {
        if (! isHeader) bufferedWriter.newLine();
        int j = 0;
        for (String str : strTemp) {
            j++;
            bufferedWriter.write("=\""+str+"\"");
            if (j < strTemp.size() ) {
                bufferedWriter.write(",");
            }
        }
    }
    public static void writeCsvFileLineForMongoDB(List<String> strTemp,
                                                  BufferedWriter bufferedWriter,
                                                  boolean isHeader) throws IOException {
        if (! isHeader) bufferedWriter.newLine();
        int j = 0;
        for (String str : strTemp) {
            j++;
            bufferedWriter.write(str);
            if (j < strTemp.size() ) {
                bufferedWriter.write(",");
            }
        }
    }
    public static void writeCsvFileLine_noEqual(Set<String> strTemp,BufferedWriter bufferedWriter) throws IOException {
        int j=0;
        for (Object objValue:strTemp) {
            j++;
            //Object objValue=strTemp.get(j);
            String strValue="";
            if(objValue==null){
                strValue="";
            }
            strValue=objValue.toString();
            if (isExisted("\"",strValue)) {
                strValue= strValue.replaceAll("\"", "\"\"");
                bufferedWriter.write(strValue.replaceAll(",","_"));
            } else if (isExisted(",",strValue)
                    || isExisted("/n",strValue)
                    || isExisted(" ",strValue)
                    || isExisted("“",strValue)){
                bufferedWriter.write(strValue.replaceAll(",","_"));
            } else {
                bufferedWriter.write(strValue.replaceAll(",","_"));
            }
            if (j < strTemp.size() ) {
                bufferedWriter.write(",");
            }
        }
    }
    public static void writeCsvFileLine(Set<String> strTemp,BufferedWriter bufferedWriter) throws IOException {
        int j=0;
        for (Object objValue:strTemp) {
            j++;
            //Object objValue=strTemp.get(j);
            String strValue="";
            if(objValue==null){
                strValue="";
            }
            strValue=objValue.toString();
            if (isExisted("\"",strValue)) {
                strValue= strValue.replaceAll("\"", "\"\"");
                bufferedWriter.write("=\""+strValue.replaceAll(",","_")+"\"");
            } else if (isExisted(",",strValue)
                    || isExisted("/n",strValue)
                    || isExisted(" ",strValue)
                    || isExisted("“",strValue)){
                bufferedWriter.write("=\""+strValue.replaceAll(",","_")+"\"");
            } else {
                bufferedWriter.write("=\""+strValue.replaceAll(",","_")+"\"");
            }
            if (j < strTemp.size() ) {
                bufferedWriter.write(",");
            }
        }
    }
    public static void writeCsvFileLineByList(List<String> strTemp,BufferedWriter bufferedWriter) throws IOException {
        int j=0;
        for (String objValue:strTemp) {
            j++;
            //Object objValue=strTemp.get(j);
            String strValue="";
            if(objValue==null){
                strValue="";
            }
            strValue=objValue.toString();
            if (isExisted("\"",strValue)) {
                strValue= strValue.replaceAll("\"", "\"\"");
                bufferedWriter.write("=\""+strValue.replaceAll(",","_")+"\"");
            } else if (isExisted(",",strValue)
                    || isExisted("/n",strValue)
                    || isExisted(" ",strValue)
                    || isExisted("“",strValue)){
                bufferedWriter.write("=\""+strValue.replaceAll(",","_")+"\"");
            } else {
                bufferedWriter.write("=\""+strValue.replaceAll(",","_")+"\"");
            }
            if (j < strTemp.size() ) {
                bufferedWriter.write(",");
            }
        }
    }
    /**
     * csv文件做成<BR/>
     * 将argList写入argPath路径下的argFileName文件里。
     *
     * @param argList  要写入csv文件的数据（List<String[]>）
     * @param argPath csv文件绝对路径
     * @param argFileName csv文件名
     * @param isNewFile 是否覆盖原有文件
     * @throws IOException
     * @throws Exception
     */
    public static void writeQualityCsvFileInfo(List<HashMap<String, Object>> argList, String argPath, String argFileName, boolean isNewFile)
            throws IOException, Exception {
        // 数据check
        if (argList == null || argList.size() == 0) {
            throw new Exception("没有数据");
        }
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        String strFullFileName = argPath;
        if (strFullFileName.lastIndexOf("/") == (strFullFileName.length() - 1)) {
            strFullFileName += argFileName;
        } else {
            strFullFileName += "/" + argFileName;
        }
        File file = new File(strFullFileName);
        // 文件路径check
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            if (isNewFile) {
                // 覆盖原有文件
                fileWriter = new FileWriter(file);
            } else {
                // 在原有文件上追加数据
                fileWriter = new FileWriter(file, true);
            }
            bufferedWriter = new BufferedWriter(fileWriter);
            //写标题
            HashMap<String, Object> HeadItem=argList.get(0);
            Set<String> keyList=HeadItem.keySet();
//            writeCsvFileLine(keyList,bufferedWriter);

            for (int i = 0; i < argList.size(); i++) {
                HashMap<String, Object> objItem = argList.get(i);
                List<Object> rowData=new ArrayList<>();
                for(String key:keyList){
                    rowData.add(objItem.get(key));
                }
                writeCsvFileLineWithoutQuotes_noEqual(rowData,bufferedWriter);
                if (i + 1 < argList.size()) bufferedWriter.newLine();
            }
        } catch (IOException e) {
             writeErrorLog( "", "", e);
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    /**
     * csv文件做成<BR/>
     * 将argList写入argPath路径下的argFileName文件里。
     *
     * @param argList  要写入csv文件的数据（List<String[]>）
     * @param argPath csv文件绝对路径
     * @param argFileName csv文件名
     * @param isNewFile 是否覆盖原有文件
     * @throws IOException
     * @throws Exception
     */
    public static void writeQualityCsvFileInfoHasTitle(List<HashMap<String, Object>> argList, String argPath, String argFileName, boolean isNewFile)
            throws IOException, Exception {
        // 数据check
        if (argList == null || argList.size() == 0) {
            throw new Exception("没有数据");
        }
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        String strFullFileName = argPath;
        if (strFullFileName.lastIndexOf("/") == (strFullFileName.length() - 1)) {
            strFullFileName += argFileName;
        } else {
            strFullFileName += "/" + argFileName;
        }
        File file = new File(strFullFileName);
        // 文件路径check
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            if (isNewFile) {
                // 覆盖原有文件
                fileWriter = new FileWriter(file);
            } else {
                // 在原有文件上追加数据
                fileWriter = new FileWriter(file, true);
            }
            bufferedWriter = new BufferedWriter(fileWriter);
            //写标题
            HashMap<String, Object> HeadItem=argList.get(0);
            Set<String> keyList=HeadItem.keySet();
            writeCsvFileLine_noEqual(keyList,bufferedWriter);
            bufferedWriter.newLine();
            for (int i = 0; i < argList.size(); i++) {
                HashMap<String, Object> objItem = argList.get(i);
                List<Object> rowData=new ArrayList<>();
                for(String key:keyList){
                    rowData.add(objItem.get(key));
                }
                writeCsvFileLineWithoutQuotes_noEqual(rowData, bufferedWriter);
                if (i + 1 < argList.size()) bufferedWriter.newLine();
            }
        } catch (IOException e) {
             writeErrorLog( "", "", e);
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }
    public static void writeQualityCsvFileInfoHasTitle_equal(List<HashMap<String, Object>> argList, String argPath, String argFileName, boolean isNewFile)
            throws IOException, Exception {
        // 数据check
        if (argList == null || argList.size() == 0) {
            throw new Exception("没有数据");
        }
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        String strFullFileName = argPath;
        if (strFullFileName.lastIndexOf("/") == (strFullFileName.length() - 1)) {
            strFullFileName += argFileName;
        } else {
            strFullFileName += "/" + argFileName;
        }
        File file = new File(strFullFileName);
        // 文件路径check
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            if (isNewFile) {
                // 覆盖原有文件
                fileWriter = new FileWriter(file);
            } else {
                // 在原有文件上追加数据
                fileWriter = new FileWriter(file, true);
            }
            bufferedWriter = new BufferedWriter(fileWriter);
            //写标题
            HashMap<String, Object> HeadItem=argList.get(0);
            Set<String> keyList=HeadItem.keySet();
            writeCsvFileLine(keyList,bufferedWriter);
            bufferedWriter.newLine();
            for (int i = 0; i < argList.size(); i++) {
                HashMap<String, Object> objItem = argList.get(i);
                List<Object> rowData=new ArrayList<>();
                for(String key:keyList){
                    rowData.add(objItem.get(key));
                }
                writeCsvFileLineWithoutQuotes(rowData, bufferedWriter);
                if (i + 1 < argList.size()) bufferedWriter.newLine();
            }
        } catch (IOException e) {
             writeErrorLog( "", "", e);
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }
    public static void writeQualityCsvFileInfoHasTitle_equal(List<HashMap<String, Object>> argList,List<String> headOtherList,List<String> endOtherList,String argPath, String argFileName, boolean isNewFile)
            throws IOException, Exception {
        // 数据check
        if (argList == null || argList.size() == 0) {
            throw new Exception("没有数据");
        }
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        String strFullFileName = argPath;
        if (strFullFileName.lastIndexOf("/") == (strFullFileName.length() - 1)) {
            strFullFileName += argFileName;
        } else {
            strFullFileName += "/" + argFileName;
        }
        File file = new File(strFullFileName);
        // 文件路径check
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            if (isNewFile) {
                // 覆盖原有文件
                fileWriter = new FileWriter(file);
            } else {
                // 在原有文件上追加数据
                fileWriter = new FileWriter(file, true);
            }
            bufferedWriter = new BufferedWriter(fileWriter);
            //写表头其他数据
            if(headOtherList!=null&&headOtherList.size()>0){
                writeCsvFileLineByList(headOtherList,bufferedWriter); bufferedWriter.newLine();
            }
            //写标题
            HashMap<String, Object> HeadItem=argList.get(0);
            Set<String> keyList=HeadItem.keySet();
            writeCsvFileLine(keyList,bufferedWriter);
            bufferedWriter.newLine();
            for (int i = 0; i < argList.size(); i++) {
                HashMap<String, Object> objItem = argList.get(i);
                List<Object> rowData=new ArrayList<>();
                for(String key:keyList){
                    rowData.add(objItem.get(key));
                }
                writeCsvFileLineWithoutQuotes(rowData, bufferedWriter);
                if (i + 1 < argList.size()) bufferedWriter.newLine();
            }
            //写表尾其他数据
            if(endOtherList!=null&&endOtherList.size()>0){
                writeCsvFileLineByList(endOtherList,bufferedWriter);
            }
        } catch (IOException e) {
             writeErrorLog( "", "", e);
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }
    public static void writeCsvFileInfo(List<String> rowList,BufferedWriter bufferedWriter)throws Exception {
        // 数据check
        if (rowList == null || rowList.size() == 0)throw new Exception("没有数据");
        try {
            //写数据
            for(String str:rowList){
                bufferedWriter.write(str);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
    /**
     * @param argChar
     * @param argStr
     * @return
     */
    private static boolean  isExisted(String argChar, String argStr) {

        boolean blnReturnValue = false;
        if ((argStr.indexOf(argChar) >= 0)
                && (argStr.indexOf(argChar) <= argStr.length())) {
            blnReturnValue = true;
        }
        return blnReturnValue;
    }

    /**
     * 正则表达式。
     * @return 匹配csv文件里最小单位的正则表达式。
     */
    private static String getRegExp() {
        String strRegExp = "";
        strRegExp ="\"(("+ SPECIAL_CHAR_A + "*[,\\n 　])*("+ SPECIAL_CHAR_A + "*\"{2})*)*"+ SPECIAL_CHAR_A + "*\"[ 　]*,[ 　]*"
                +"|"+ SPECIAL_CHAR_B + "*[ 　]*,[ 　]*"
                        + "|\"(("+ SPECIAL_CHAR_A + "*[,\\n 　])*("+ SPECIAL_CHAR_A + "*\"{2})*)*"+ SPECIAL_CHAR_A + "*\"[ 　]*"
                + "|"+ SPECIAL_CHAR_B + "*[ 　]*";
        return strRegExp;
    }
    private static void writeErrorLog(String method,String content,Exception e){
        BaseSystemOutLog.writeLog(BaseSystemOutLog.LogType_Error, "FileUtilsCsv", method, content, e);
    }
    public static void main(String[] args) {
        try {
//            Pattern pCells = Pattern.compile("\\G(?:^|,)(?:\"([^\"]*+(?:\"\"[^\"]*+)*+)\"|([^\",]*+))" );
//            String strReplacePattern = "(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,";
//            String strReplacePattern2 = "(?sm)(\"(\"))";
            Pattern pCells = Pattern.compile("\\G(?:^|,)(?:\"([^\"]*+(?:\"\"[^\"]*+)*+)\"|([^\",]*+))" );
            String strReplacePattern = "(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,";
            String strReplacePattern2 = "(?sm)(\"(\"))";
            String strReplacePattern3 = "^'|'*$";//首尾的单引号
            String strReplacePattern4 = "^\"|\"*$";//首尾的单引号
            String strReplacePattern5 = "^(,\")|\"*$";//首尾的单引号
           // FileUtilsCsv.readCsvFile(new File("D:\\workform\\data\\quality\\FCR报表.csv"), "Unicode");
            String str = "dy-gz-t93000002_20160502_172630.mp3|1437926178904|t93000002_20160502_172630.mp3|1437902652000|45|dy-gz|呼出|015899561984|||kw1870||粤d04|6225561040268846|2015|07|26|/mnt93000002_20160502_1/t93000002_20160502_172630.mp3|46183|dy-gz-t93000002_20160502_172630.mp3|t93000002_20160502_172630.mp3|喂您好哎您好莫利斌;不好意思打扰您啊到您了这边广发银行信用卡中心的啊就是针对您七月十三号目的两万元在餐饮里面的就是这笔两万元记录审批可以帮您分摊十二期分期还款不;这个;嗯;不对;|这个属于现在这个时候回来啊;哎不怎么是吧;我原来;对那不要了啊嗯公司应该;留的;|21.8 23.72 4.69;25.94 37.06 6.29;38.28 38.64 5.56;42.06 42.32 3.85;42.94 43.72 2.56;|15.68 21.4 2.27;24.18 25.4 4.92;37.48 37.92 6.82;39 41.92 3.77;42.36 42.78 4.76;|13|1|[{\"st93000002_20160502_1|t93000002_20160502_172630|||||||||||||epp|b拒绝|b18-其他|无|粤d04|||||||||null ";
            String str2 = "客服1,无2,1412138966,account_no,2,第一分类2,第二分类 ";
            String str3 = " ,13909806494,,,,,";
//            String regex = "([^|]*)(?:||$)" ;
            String regex = "[^\\|]*" ;
            Matcher main = Pattern.compile(regex).matcher(str);
            Matcher mquote = Pattern.compile("\"\"").matcher("");
            while (main.find()) {
                String field = main.group();

//                    if (main.start(2) >= 0) {
//                        field = main.group(2);
//                } else {
//                    field = mquote.reset(main.group(1)).replaceAll("\"");
//                }
                System.out.println("Field [" + field + "]");
            }
            //System.out.println("dw kk,ll ,yioi iu , r3 \"fte l kk\"ll mm'oo n\"dw,erw\",e");
        } catch (Exception e) {
             writeErrorLog( "", "", e);
        }

    }
}
