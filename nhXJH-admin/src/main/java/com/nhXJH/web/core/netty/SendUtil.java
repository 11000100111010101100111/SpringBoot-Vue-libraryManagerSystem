package com.nhXJH.web.core.netty;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ Channel消息Stomp推送工具类
 */
@Slf4j
@Component
public class SendUtil {

    private static SendUtil sendUtil;
    private MqConfig config;

    public SendUtil(MqConfig config) {
        this.config = config;
    }

    @PostConstruct
    public void init(){
        sendUtil = this;
    }


    /**
     *  推送stomp数据 默认消息持久化
     * @param exchange 交换机
     * @param type  交换机类型 如 : BuiltinExchangeType.DIRECT
     * @param routerKey 路由key
     * @param object 数据
     */
    public static boolean pushMsg(String exchange, BuiltinExchangeType type, String routerKey, Object object) {
        Connection connection = sendUtil.config.getConn();
        Channel channel = null;
        try {
            assert connection != null;
            channel = connection.createChannel();
            channel.exchangeDeclare(exchange, type, true);
            channel.basicPublish(exchange, routerKey,null, object.toString().getBytes());
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(channel != null){
                try {
                    channel.close();
                } catch (IOException | TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

}