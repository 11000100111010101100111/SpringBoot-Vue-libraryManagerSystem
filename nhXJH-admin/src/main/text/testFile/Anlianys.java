package testFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hankcs.hanlp.restful.HanLPClient;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.nhXJH.common.utils.StringUtils;
import com.nhXJH.web.util.TemplateUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/17
 * Time: 15:37
 **/
public class Anlianys {
    @Test
    public void fun(){
        JiebaSegmenter segmenter = new JiebaSegmenter();
//        String[] sentences =
//                new String[] {"这是一个伸手不见五指的黑夜。我叫孙悟空，我爱北京，我爱Python和C++。", "我不喜欢日本和服。", "雷猴回归人间。",
//                        "工信处女干事每月经过下属科室都要亲口交代24口交换机等技术性器件的安装工作", "结果婚的和尚未结过婚的"};
//        for (String sentence : sentences) {
//            System.out.println(segmenter.process(sentence, JiebaSegmenter.SegMode.INDEX).toString());
//        }
        String item = "  南昌大学图书馆，于1993年5月南昌大学成立时，由原江西大学图书馆和原江西工业大学图书馆合并而成，是学校的文献信息中心，是为教学和科学研究服务的学术性机构，是学校信息化和社会信息化的重要基地。并成为中国高等教育文献保障系统 (CALIS) 成员馆、 CALIS 江西省高校文献信息服务中心、教育部科技查新工作站（ L25 ）、江西省医学科技情报中心。";
//        List<String> str = segmenter.sentenceProcess(item);
//
//        List<String> res = new ArrayList<>();
//        for (String s : str) {
//            if ( s.length()>1 && !StringUtils.hasSpecialChar(s)){
//                res.add(s);
//                System.out.println(s);
//            }
////            System.out.println(s+":"+(StringUtils.hasSpecialChar(s)?"":"不")+"包含特殊字符");
//        }
        HanLPClient HanLP = new HanLPClient("https://hanlp.hankcs.com/api", null, "zh",3000);
        Map<String, List> resss = new HashMap<>();
        try {
            resss = HanLP.parse(item);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<List<List<Object>>> valList = resss.get("ner/pku");
        for (List<List<Object>> s : valList) {
            for (List<Object> strings : s) {
                System.out.println(strings.get(0));
            }
        }
//        System.out.println(valList.toString());
//        Iterator<Map.Entry<String,List>> entries = resss.entrySet().iterator();
//        while (entries.hasNext()){
//            Map.Entry<String,List> entry = entries.next();
//            List l = entry.getValue();
//            String val = entry.getKey();
////            for (Object o : l) {
//                System.out.println("["+val+":"+l.toString()+"]");
////            }
//        }
    }
    @Test
    public void readJSON(){
        System.out.println(StringUtils.replaceBlank(TemplateUtils.anlyzeJSON(new File("D:\\桌面\\data.json")) ));
        System.out.println();
        System.out.println(StringUtils.replaceBlank(TemplateUtils.anlyzeJSON(new File("D:\\桌面\\data2.json")) ));
        System.out.println();
        System.out.println(StringUtils.replaceBlank(TemplateUtils.anlyzeJSON(new File("D:\\桌面\\data3.json")) ));
        System.out.println();
        System.out.println(StringUtils.replaceBlank(TemplateUtils.anlyzeJSON(new File("D:\\桌面\\data4.json")) ));
        System.out.println();
        System.out.println(StringUtils.replaceBlank(TemplateUtils.anlyzeJSON(new File("D:\\桌面\\新建文本文档.txt")) ));
        System.out.println();
        System.out.println(TemplateUtils.JsonFileParseTemplate(new File("D:\\桌面\\新建文本文档.txt")));
        System.out.println();
        System.out.println(TemplateUtils.JsonFileParseTemplate(new File("D:\\桌面\\data4.json")));
        System.out.println();
        System.out.println(TemplateUtils.JsonFileParseTemplate(new File("D:\\桌面\\data3.json")));
        System.out.println();
        System.out.println(TemplateUtils.JsonFileParseTemplate(new File("D:\\桌面\\data2.json")));
        System.out.println();
        System.out.println(TemplateUtils.JsonFileParseTemplate(new File("D:\\桌面\\data.json")));
        System.out.println();
    }
}
