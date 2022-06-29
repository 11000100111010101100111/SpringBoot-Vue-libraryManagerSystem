package com.nhXJH.web.util.file;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/12
 * Time: 20:45
 **/
public class CountElement {
    public static void main(String[] args) {
        //System.out.println(CountElement("Fe4(SO2)2"));
        show(splitStr(CountElement("Fe4(SO2)2")));
    }
    public static String CountElement(String data)
    {
        StringBuilder ans = new StringBuilder();//进行字符串拼接
        Map<String, Integer> element = new HashMap<String, Integer>();//记录元素和其对应的次数
        List<String> lst = new ArrayList<>();//按顺序记录元素
        List<Integer> lstNum = new ArrayList<>();//按顺序记录每次元素在此刻出现的次数
        Stack<Integer> s = new Stack<Integer>();//存储括号开始的位置
        for(int i = 0; i < data.length(); ++i){
            if(data.charAt(i)>='A'&& data.charAt(i) <= 'Z'){
                int tmp = i;
                ++i;
                while(i < data.length() &&(data.charAt(i)<='z'&& data.charAt(i) >='a')){
                    ++i;
                }
                String str = data.substring(tmp, i);
                if(!element.containsKey(str)){
                    element.put(str, 0);
                }
                lst.add(str);
                if (i < data.length() && (data.charAt(i)>= '0' && data.charAt(i) <= '9')){
                    int sum = 0;
                    while (i < data.length() && (data.charAt(i) >= '0' && data.charAt(i) <= '9')){
                        sum = sum * 10 + data.charAt(i) - '0';
                        ++i;
                    }
                    element.put(str,element.get(str) + sum);
                    lstNum.add(sum);
                }
                else{
                    element.put(str,element.get(str) + 1);
                    lstNum.add(1);
                }
                --i;//回档，不然会跳过一个字符
            }
            else if(data.charAt(i) == '('){//记录下括号开始的元素下标
                s.push(lst.size());
            }
            else if(data.charAt(i)  == ')'){//表示一个括号结束了可以进行计算了
                ++i;
                int sum;
                if (i < data.length() && (data.charAt(i) >= '0' && data.charAt(i) <= '9')){
                    sum = 0;
                    while (i < data.length() && (data.charAt(i) >= '0' && data.charAt(i) <= '9'))
                    {
                        sum = sum * 10 + data.charAt(i)- '0';
                        ++i;
                    }
                }
                else{
                    continue;
                }
                for(int j = s.pop(); j < lst.size(); ++j){
                    element.put(lst.get(j),sum * lstNum.get(j));
                    Integer temp = lstNum.get(j);
                    lstNum.set(j, temp + sum * lstNum.get(j));
                }
                --i;//回档，不然会跳过一个字符
            }
        }
        for(int i = 0; i < lst.size(); ++i){
            if(element.containsKey(lst.get(i))){//元素拼接一次就够了，其他地方出现的相同元素就不管它了。
                ans.append(lst.get(i) + element.get(lst.get(i)));
                element.remove(lst.get(i));
            }
        }
        return ans.toString();
    }

    public static String[] splitStr(String str){
        StringBuilder sb = new StringBuilder(str);
        int addCount = 0;
        for (int i=0;i<str.length();i++){
            if(str.substring(i,i+1).matches("[0-9]") && i+1<str.length() && str.substring(i+1,i+2).matches("[A-Z]")){
                sb.insert(i+addCount+1,",");
                addCount++;
            }
        }
        return sb.toString().split(",");
    }
    public static String[] sum(String str){
        StringBuilder sb = new StringBuilder(str);
        for (int i=0;i<str.length();i++){
            if(str.substring(i,i+1).matches("[0-9]")){
                sb.insert(i,",");
            }
        }
        return sb.toString().split(",");
    }
    public static void show(String []arr){
        for (String s : arr) {
            String []val = sum(s);
            System.out.println(val[0]+":"+val[1]);
        }
    }
}
