package com.jwBlog.utils.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ReadFiles {

    private static HashMap<String, HashMap<String, Float>> allTheTf = new HashMap<String, HashMap<String, Float>>();
    private static HashMap<String, HashMap<String, Integer>> allTheNormalTF = new HashMap<String, HashMap<String, Integer>>();
    private static HashMap<String, Integer> allWordsTf = new HashMap<String, Integer>(); 
    
    public static void normalTF(String oneCall,String callCode) throws IOException {
    	String[] cutWordResult=oneCall.trim().split(" ");
        HashMap<String, Integer> tfNormal = new HashMap<String, Integer>();//没有正规化
        int wordNum = cutWordResult.length;
        for (int i = 0; i < wordNum; i++) {
            if (cutWordResult[i] != " "&&!"".equals(cutWordResult[i].trim())) {
                String word=cutWordResult[i];
                if(tfNormal.containsKey(word)){
                	tfNormal.put(word, tfNormal.get(word)+1);
                }else{
                	tfNormal.put(word, 1);
                }
                if(allWordsTf.containsKey(word)){
                	allWordsTf.put(word, allWordsTf.get(word)+1);
                }else{
                	allWordsTf.put(word, 1);
                }
            }
        }
        allTheNormalTF.put(callCode, tfNormal);
        
        HashMap<String, Float> tf = new HashMap<String, Float>();//正规化
        for(Entry<String, Integer> en:tfNormal.entrySet()){
        	tf.put(en.getKey(), new Float(en.getValue())/wordNum);
        }
        allTheTf.put(callCode, tf);
    }

    public static Map<String, Integer> NormalTFOfAll(String fileName) throws IOException {
    	allTheTf.clear();allTheNormalTF.clear();allWordsTf.clear();
        BufferedReader br = new BufferedReader(
        		new InputStreamReader(new FileInputStream(fileName)));
        String line =null;
        int lineNumer=1;
        while ((line = br.readLine()) != null) {
        	ReadFiles.normalTF(line,lineNumer+"");
        	lineNumer++;
        }
        br.close();
        return allWordsTf;
    }
    

    public static Map<String, Float> idf() throws Exception {
        //公式IDF＝log((1+|D|)/|Dt|)，其中|D|表示文档总数，|Dt|表示包含关键词t的文档数量。
        Map<String, Float> idf = new HashMap<String, Float>();
        List<String> located = new ArrayList<String>();

        float Dt = 1;
        int D = allTheNormalTF.size();//文档总数
        

        for (int i = 0; i < D; i++) {
            HashMap<String, Integer> temp = allTheNormalTF.get((i+1+""));
            for (String word : temp.keySet()) {
                if (!(located.contains(word))) {
                    Dt = allWordsTf.get(word);
                    idf.put(word, log((1 + D) / Dt, 10));
                    located.add(word);
                }
            }
        }
        return idf;
    }

    public static Map<String, HashMap<String, Float>> tfidf() throws Exception {

        Map<String, Float> idf = ReadFiles.idf();
        Map<String, HashMap<String, Float>> tfidf = new HashMap<String, HashMap<String, Float>>();
        
        for (String file : allTheTf.keySet()) {
            Map<String, Float> singelFile = allTheTf.get(file);
            HashMap<String, Float> curtfIdfMap =  new HashMap<String, Float>();
            for (String word : singelFile.keySet()) {
            	curtfIdfMap.put(word, (idf.get(word)) * singelFile.get(word));
            }
            tfidf.put(file, curtfIdfMap);
        }
        return tfidf;
    }
    
    public static float log(float value, float base) {
        return (float) (Math.log(value) / Math.log(base));
    }
}