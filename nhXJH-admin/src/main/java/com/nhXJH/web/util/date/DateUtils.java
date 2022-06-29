package com.nhXJH.web.util.date;

import com.nhXJH.common.utils.StringUtils;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/15
 * Time: 14:52
 **/
public class DateUtils {
    private static String DAY="天";
    private static String HOUR="小时";
    private static String MIN="分钟";
    private static String SEC="秒";
    public static String subtractionDate(Date begin, Date end){
        if (null != begin && null != end){
            Long beginTime = begin.getTime();
            Long endTime = end.getTime();
            Long total = endTime-beginTime;
            StringBuffer time = new StringBuffer();
            Long day = total/(24 * 60 * 60 * 1000);
            time.append(day>0?day:StringUtils.EMPTY).append(day>0?DateUtils.DAY:StringUtils.EMPTY);

            Long hour = (day)% 24;
            time.append(hour>0?hour:StringUtils.EMPTY).append(hour>0?DateUtils.HOUR:StringUtils.EMPTY);

            Long min = (total/  1000) % 60;
            time.append(min>0?min:StringUtils.EMPTY).append(min>0?DateUtils.MIN:StringUtils.EMPTY);

            Long sec = (total/(60 * 1000)) % 60;
            time.append(sec>0?sec:StringUtils.EMPTY).append(sec>0?DateUtils.SEC:StringUtils.EMPTY);

            Long other = total% 1000;
            time.append(other>0?other:StringUtils.EMPTY);

            return time.toString();
        }
        return StringUtils.EMPTY;
    }
}
