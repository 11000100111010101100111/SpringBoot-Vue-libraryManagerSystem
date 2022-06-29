package textPo;

import com.nhXJH.web.domain.po.TemplateNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/13
 * Time: 21:17
 **/
public class TextTemplateNode {
    @Test
    public void getJson(){
        List<Long> ids = new ArrayList<>();
        ids.add(111l);
        ids.add(112l);
        ids.add(113l);
        ids.add(114l);

        TemplateNode head = new TemplateNode();
        head.setIdList(ids);
        head.setUid(111l);
        head.setMark("hello");
        head.setStatus(1);
        head.setHandelTime(new Date());

        TemplateNode next = new TemplateNode();
        next.setIdList(ids);
        next.setUid(112l);
        next.setMark("hello");
        next.setStatus(1);
        next.setHandelTime(new Date());

        TemplateNode nextNext = new TemplateNode();
        nextNext.setIdList(ids);
        nextNext.setUid(113l);
        nextNext.setMark("hello");
        nextNext.setStatus(1);
        nextNext.setHandelTime(new Date());

        TemplateNode end = new TemplateNode();
        end.setIdList(ids);
        end.setUid(114l);
        end.setMark("hello");
        end.setStatus(1);
        end.setHandelTime(new Date());

        List<TemplateNode> headNext=new ArrayList<>();
        headNext.add(next);
        head.setNext(headNext);

        List<TemplateNode> nNext=new ArrayList<>();
        headNext.add(nextNext);
        next.setNext(nNext);

        List<TemplateNode> nNNext=new ArrayList<>();
        headNext.add(end);
        nextNext.setNext(nNNext);


        System.out.println(head.toString());
    }

    @Test
    public void getCode(){
        StringBuffer str = new StringBuffer();
        //          1501829912615583744                      P A  S C J J  M G  P F I D H E E
        Long item = 1501829912615583744l;//PAS-SZ-ZMWPVYT-XUU 15 0 18 2 9 9 12 6 15 5 8 3 7 4 4
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

        System.out.println(str.toString().toUpperCase());
    }

    @Test
    public void getLong(){
        //        15 0 18 18 25 25 12 22 15 21 24 19 23 20 20
        String str = "PAS-CJ-JMGPFID-HEE".replaceAll("-","").toLowerCase();
        System.out.println(str);
        char[] ch = str.toCharArray();
        StringBuffer val = new StringBuffer();
        for (int i = 0; i < ch.length; i++) {
            val.append(ch[i]-'a');
        }
        System.out.println(val);
    }

    @Test
    public void isCode(){
        String regex = "[A-Za-z\\-]*";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher("PAS-CJ-JMGPFID-HEE");
        System.out.println( matcher.matches());

    }


}
