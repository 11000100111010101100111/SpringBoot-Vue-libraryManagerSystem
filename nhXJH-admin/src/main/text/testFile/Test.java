package testFile;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/23
 * Time: 12:44
 **/
public class Test {

    public static final String SPLIT_CHAR = " ";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int maxBudget = Integer.parseInt(in.nextLine());//预算上限
        String priceLine = in.nextLine();

        String[] price = priceLine.split(Test.SPLIT_CHAR);

        List<Integer> prices = new ArrayList<>();
        for (String s : price) {
            prices.add(Integer.parseInt(s));
        }
        prices.sort((o1,o2)-> o1>o2?0:-1);

        int sumPrice = 0;
        for(int i:prices) {
            if(sumPrice+i <= maxBudget) {
                sumPrice += i;
            }
            else {
                break;
            }
        }


        System.out.println(sumPrice);
    }
    /**
     * 采用快速排序对价格进行排序
     * @param arr    价格列表数组
     * @param left    数组左端
     * @param right 数组右端针
     */
    public static void sortPrices(int [] arr,int left,int right) {
        int pivot = 0;
        if(left < right) {

            int key = arr[left];
            while(left < right) {
                while(left < right && arr[right] >= key) {
                    right--;
                }
                arr[left] = arr[right];
                while(left < right && arr[left] <= key) {
                    left++;
                }
                arr[right] = arr[left];
            }
            arr[left] = key;

            pivot = left;

            sortPrices(arr,left,pivot-1);
            sortPrices(arr,pivot+1,right);
        }
    }
}
