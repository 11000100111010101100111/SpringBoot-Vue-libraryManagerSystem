package com.nhXJH.web.core.config;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/23
 * Time: 17:16
 **/
public class Test {
    private final String FIRST = "一等奖";
    private final String SECOND = "二等奖";
    private final String THIRD = "三等奖";
    public Set<Integer> getLuckDraw(){
        Set<Integer> luckBoys = new HashSet<>();
        while (luckBoys.size()<7){
            Random luckNumber = new Random();
            luckBoys.add(luckNumber.nextInt(1)+999);
        }
        return luckBoys;
    }
    public Map<String,Integer[]> getRank(Set<Integer> luckDraw){
        Map<String,Integer[]> rank = new HashMap<>();
        int rankIndex = 0;
        Integer [] nums = new Integer[luckDraw.size()];
        for (Integer luck : luckDraw) {
            nums[rankIndex++] = luck;
        }
        rank.put(FIRST,new Integer[]{nums[0]});
        rank.put(SECOND,new Integer[]{nums[1],nums[2]});
        rank.put(THIRD,new Integer[]{nums[3],nums[4],nums[5],nums[6]});
        return rank;
    }
    public static void main(String[] args) {
        Test test = new Test();
        test.getRank(test.getLuckDraw());
    }

    public int[] sort(int [] arr){
        for (int i = 0;i<arr.length;i++){
            for (int j = 0;j<arr.length - i;j++){
                if(arr[i] > arr[j]){
                    arr[i] = arr[j] + arr[i];
                    arr[j] = arr[i] - arr[j];
                    arr[i] = arr[i] - arr[j];
                }
            }
        }
        return arr;
    }
}
