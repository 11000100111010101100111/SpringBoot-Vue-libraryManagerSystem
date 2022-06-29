package com.nhXJH.web.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/11
 * Time: 13:15
 **/
public class CodeUtil {
    public static String toCode(Long item){
        StringBuffer str = new StringBuffer();
        char[] vl = item.toString().toCharArray();
        for (int i = 0; i < vl.length; i++) {
            if ("0".equals(""+vl[i])){
                str.append("A");
                continue;
            }
            boolean flage = i+1<vl.length-1;
            int sum = 0;
            if (flage){
                sum = (Integer.parseInt(vl[i]+"") * 10 + Integer.parseInt(vl[i + 1]+""));
                flage = sum<26;
            }
            char append = (char)( ( flage? sum : Integer.parseInt(vl[i] +"") )+'A');
            str.append( append ).append(i!=0&&i%3==0&&i!=vl.length-1?"-":"");
            i += flage?1:0;
        }
        return str.toString();
    }

    public static Long getKey(String code){
        String str = code.replaceAll("-","").toLowerCase();
        char[] ch = str.toCharArray();
        StringBuffer val = new StringBuffer();
        for (int i = 0; i < ch.length; i++) {
            val.append(ch[i]-'a');
        }
        try {
            return Long.parseLong(val.toString());
        }catch (NumberFormatException e){
            return -1l;
        }

    }

    public static boolean isCode(String code){
        String regex = "[A-Za-z\\-]*";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(code);
        return matcher.matches();
    }
}
