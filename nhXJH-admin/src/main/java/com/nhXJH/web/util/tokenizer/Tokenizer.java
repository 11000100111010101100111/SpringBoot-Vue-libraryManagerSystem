package com.nhXJH.web.util.tokenizer;

import com.hankcs.hanlp.restful.HanLPClient;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.nhXJH.common.utils.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.*;

/*
 * 文件格式：已分词的中文文本，每个词语空格分割，每行一个段落。
 * 这个类适合读取每行数量较少的文本，比如分好段落的文本，一个段落一行存储。
 * 读取一行，步长为1，返回词组。不会跨段落生成词组。
 * 3种模式：
 * 1 读到文件末尾，结束
 * 2 读到文件末尾，从头再来
 * 3 一行循环多次，浏览到文本末尾就完了
 */
public class Tokenizer {
	private static String tokenizerUrl="https://hanlp.hankcs.com/api";

	static final int normalMode = 0;//浏览到文本末尾就完了
	static final int againMode = 1;//浏览到文本末尾，从头再来
	static final int paraAgainMode = 2 ;//一行循环多次，浏览到文本末尾就完了
	private int currentMode = 0;
	
	private RandomAccessFile raf= null;
	private File file;
	private ArrayList<String> paraWords = null;
	
	private StringTokenizer tokenizer;
	private int currentPara = -1;
	private int paraPos = 0;
	private int paraIter = 0;
	private int paraIters = 1;
	public  Tokenizer(String fileName) throws IOException
	{
		file=new File(fileName);
		raf = new RandomAccessFile(file,"r") ;  
		paraWords = new ArrayList<String>();
	}	
	
	public void setMode(int m)
	{
		currentMode = m;
	}
	
	public void setParaIters(int iters)
	{
		paraIters = iters;
		setMode(paraAgainMode);
	}
	
	public int paraIndex()
	{
		return currentPara;
	}
	private boolean readPara() throws IOException
	{		
		String line = raf.readLine();
		if(line == null)//到文件末尾了
		{
			if(currentMode == normalMode || currentMode == paraAgainMode)
			{
				return false;
			}
			else 
			{		
				System.out.println("文件太大可能不支持");
				raf.seek(0); 
				currentPara = -1;
				return readPara();
			}			
		}
		paraWords.clear();	
		line = new String(line.getBytes("iso8859-1"),"utf-8"); 
		tokenizer= new StringTokenizer(line," ");
		while(tokenizer.hasMoreTokens())
		{
			paraWords.add(tokenizer.nextToken());
		}	
		currentPara++;
		paraPos = 0;
		return true;	
	}
	
	public String[] getNextWords(int count) throws IOException
	{
		if(paraPos+count >= paraWords.size())//到了段落末尾
		{
			if(currentMode == paraAgainMode && paraIter< paraIters)//段落从头再来
			{
				paraPos = 0;
				paraIter++;
				return getNextWords(count);
			}
			else 
			{
				paraIter =0;
				if(readPara())//读取新的段落
				return getNextWords(count);
				else return null;
			}
		}
		String[] words = new String[count];
		for(int i=0;i<count;i++)
		{
			words[i] = paraWords.get(paraPos+i);
			
		}
		paraPos++;
		return words;
	}

	public synchronized static List<String> getTokenizerValue(String str){
		Set<String> tokenizerValue = new HashSet<>();
		List<String> arr = new ArrayList<>();
		if (null == str || "".equals(str.trim())){
			return arr;
		}

		HanLPClient HanLP = new HanLPClient( Tokenizer.tokenizerUrl, null, "zh",3000);
		Map<String, List> result = new HashMap<>();

		try {
			result = HanLP.parse(str);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (result.size()<=0){
			return arr;
		}
		List<List<List<Object>>> valList = result.get("ner/pku");
		for (List<List<Object>> s : valList) {
			if ( null == s ){
				return arr;
			}
			for (List<Object> strings : s) {
				if (null == strings ){
					return arr;
				}
				if ( null!=strings.get(0) && !"".equals(strings.get(0).toString().trim())) {
					tokenizerValue.add(strings.get(0).toString());
				}
			}
		}
		arr.addAll(tokenizerValue);
		return arr;
	}
 
}