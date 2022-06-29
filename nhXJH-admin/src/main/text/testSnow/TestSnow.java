package testSnow;

import com.nhXJH.common.config.SnowFlakeUtil;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/2
 * Time: 14:38
 **/
public class TestSnow {

    @Test
    public void test(){
       for (int i=0;i<5;i++){
           System.out.println(i+":"+ getSnowID(i));
       }
    }
    public Long getSnowID(int i){
//        try {
//            Thread.sleep(1);
//            int m = 1/i;
//            SnowFlakeUtil snowFlakeUtil = new SnowFlakeUtil();
//            return snowFlakeUtil.snowflakeId();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return getSnowID(i+1);
        SnowFlakeUtil snowFlakeUtil = new SnowFlakeUtil();
        return snowFlakeUtil.snowflakeId();
    }
}
