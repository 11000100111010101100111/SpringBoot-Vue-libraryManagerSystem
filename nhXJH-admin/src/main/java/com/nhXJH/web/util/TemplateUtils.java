package com.nhXJH.web.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.nhXJH.common.utils.StringUtils;
import com.nhXJH.web.domain.param.purchase.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/5
 * Time: 13:42
 **/
public class TemplateUtils {
    public static final String EMPTY_OF_DEFAULT_TEMPLATE = "{\"name\": \"新增流程模板\",\"nodeList\": [],\"lineList\": []}" ;
    public static final String UNKNOW_TEMPLATE = "{\"name\": \"未知流程模板\",\"nodeList\": [],\"lineList\": []}" ;


    public static TemplateParam JsonParseTemplate(String template){
        System.out.println(template);
        try {

            TemplateParam param = JSONObject.parseObject(template,TemplateParam.class);
            return param;
        }catch (Exception e){
            return null;
        }
    }

    public static TemplateParam JsonFileParseTemplate(File file){
        String template = StringUtils.replaceBlank(TemplateUtils.anlyzeJSON(file));
        TemplateParam param = TemplateUtils.JsonParseTemplate(template);
        return param;
    }
    public static String anlyzeJSON(File file) {
        FileReader fileReader = null;
        StringBuffer result = new StringBuffer();
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                result.append(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
        }
        return result.toString();
    }
    public static File getFile(MultipartFile multipartFile){
        //文件上传前的名称
        String fileName = multipartFile.getOriginalFilename();
        File file = new File(fileName);
        OutputStream out = null;
        try{
            //获取文件流，以文件流的方式输出到新文件
            out = new FileOutputStream(file);
            byte[] ss = multipartFile.getBytes();
            for(int i = 0; i < ss.length; i++){
                out.write(ss[i]);
            }
            return file;
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }finally {
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static NodeList createNodeList(String templateJson){
        TemplateParam param = TemplateUtils.JsonParseTemplate(templateJson);
        return TemplateUtils.createNodeList(param);
    }

    public static NodeList createNodeList(TemplateParam param){
        NodeList head = new NodeList();
        List<PurchaserLine> lines = param.getLineList();
        List<PurchaseNode> nodes = param.getNodeList();
        if (null == lines || lines.size()<1){
            return head;
        }

        // @TODO 生成流程双向链表
        return null;
    }

    /** 获取审批流第一个结点*/
    public static PurchaseNode getFirstNode(TemplateParam param){
        List<String> fromList = new ArrayList<>();
        List<String> toList = new ArrayList<>();

        List<PurchaseNode> nodes = param.getNodeList();
        List<PurchaserLine> lines = param.getLineList();
        if (null == lines || lines.size()<1){
            return null;
        }

        String star = lines.get(0).getFrom();

        for (PurchaserLine line : lines) {
            fromList.add(line.getFrom());
            toList.add(line.getTo());
        }
        for (String s : fromList) {
            if (!toList.contains(s)){
                star = s;
                break;
            }
        }
        for (PurchaseNode node : nodes) {
            if (star.equals(node.getId())){
                return node;
            }
        }

        return null;
    }

    /** 根据当前结点id获取当前结点子节点结点*/
    public static List<NextNode> getNextNode(TemplateParam param,String curNodeId){

        List<NextNode> nextNodeIdList = new ArrayList<>();
        List<PurchaseNode> nodes = param.getNodeList();
        List<PurchaserLine> lines = param.getLineList();
        if (null == lines || lines.size()<1){
            return null;
        }

        for (PurchaserLine line : lines) {
            if (curNodeId.equals(line.getFrom()) ){
                PurchaseNode curNode = getCurNode(param, line.getTo());
                assert curNode != null;
                //判断是否最后节点
                if ("over".equals(curNode.getType())){
                    nextNodeIdList.add( new NextNode(line.getLabel(),curNode,true ) );
                    break;
                }
                nextNodeIdList.add( new NextNode(line.getLabel(),curNode,false ) );
            }
        }
        return nextNodeIdList;
    }
    /** 根据当前结点id获取当前结点子节点结点*/
    public static List<NextNode> getNextNode(TemplateParam param,String curNodeId,String handleType){

        List<NextNode> nextNodeIdList = new ArrayList<>();
        List<PurchaseNode> nodes = param.getNodeList();
        List<PurchaserLine> lines = param.getLineList();
        if (null == lines || lines.size()<1){
            return null;
        }

        for (PurchaserLine line : lines) {
            if (curNodeId.equals(line.getFrom()) && handleType.equals(line.getAudioType())){
                PurchaseNode curNode = getCurNode(param, line.getTo());
                assert curNode != null;
                //判断是否最后节点
                if ("over".equals(curNode.getType())){
                    nextNodeIdList.add( new NextNode(line.getLabel(),curNode,true ) );
                    break;
                }
                nextNodeIdList.add( new NextNode(line.getLabel(),curNode,false ) );
            }
        }
        return nextNodeIdList;
    }

    /** 根据当前结点id获取当前结点*/
    public static PurchaseNode getCurNode(TemplateParam param,String curNodeId){
        List<PurchaseNode> nodes = param.getNodeList();
        if (null == nodes || nodes.size()<1){
            return null;
        }
        for (PurchaseNode node : nodes) {
            if (curNodeId.equals(node.getId())){
                return node;
            }
        }
        return null;
    }
}
