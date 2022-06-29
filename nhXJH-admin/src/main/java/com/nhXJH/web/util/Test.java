package com.nhXJH.web.util;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.T;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/2
 * Time: 17:08
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    final int COMPANY_STUFF_NUMBER= 1000;//公司人数
    final int FRIST_LUCKLY_NUMBER=1;//一等奖人数
    final int SECOND_LUCKLY_NUMBER=2;//二等奖人数
    final int THRIDER_LUCKLY_NUMBER=4;//三等奖人数
    public static final String FRIST_LUCKLY="一等奖";//一等奖人数
    public static final String SECOND_LUCKLY="二等奖";//二等奖人数
    public static final String THRIDER_LUCKLY="三等奖";//三等奖人数
    private Map<String, Integer> jackpot;//奖池
    private List<Integer> stuffNumber;//员工工号集合
    public Test(List<Integer> stuffNumber){
        this.jackpot = new HashMap<>();

        jackpot.put(Test.FRIST_LUCKLY,Test.getLuck(new Random().nextInt(this.COMPANY_STUFF_NUMBER),stuffNumber));
        jackpot.put(Test.SECOND_LUCKLY+"1",Test.getLuck(new Random().nextInt(this.COMPANY_STUFF_NUMBER-1),stuffNumber));
        jackpot.put(Test.SECOND_LUCKLY+"2",Test.getLuck(new Random().nextInt(this.COMPANY_STUFF_NUMBER-2),stuffNumber));
        jackpot.put(Test.THRIDER_LUCKLY+"1",Test.getLuck(new Random().nextInt(this.COMPANY_STUFF_NUMBER-3),stuffNumber));
        jackpot.put(Test.THRIDER_LUCKLY+"2",Test.getLuck(new Random().nextInt(this.COMPANY_STUFF_NUMBER-4),stuffNumber));
        jackpot.put(Test.THRIDER_LUCKLY+"3",Test.getLuck(new Random().nextInt(this.COMPANY_STUFF_NUMBER-5),stuffNumber));
        jackpot.put(Test.THRIDER_LUCKLY+"4",Test.getLuck(new Random().nextInt(this.COMPANY_STUFF_NUMBER-6),stuffNumber));
    }
    public static Integer getLuck(int index,List<Integer> stuffNumber){
        Integer no = stuffNumber.get(index);
        stuffNumber.remove(index);
        return no;
    }
    public static void main(String[] args) {
        List<Integer> stuffNumber = new ArrayList<>();
        for (int i = 1; i < 1001; i++) {
            stuffNumber.add(i);
        }
        Test test = new Test(stuffNumber);

        System.out.println(test.getJackpot());
    }
}

