package com.nhXJH.common.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("snowFlakeUtil")
public class SnowFlakeUtil {
	private long workerId=0L;
	private long datacenterId=1L;
	private Snowflake snowflake= IdUtil.createSnowflake(workerId, datacenterId);
	@PostConstruct
	public void init(){
		try{
			workerId= NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
		}catch(Exception e){
			workerId=NetUtil.getLocalhostStr().hashCode();
		}
		
	}
	
	public synchronized long snowflakeId(long workerId,long datacenterId){
		Snowflake snowflake=IdUtil.createSnowflake(workerId, datacenterId);
		return snowflake.nextId();
	}
	public synchronized long snowflakeId(){
		return snowflake.nextId();
	}
}