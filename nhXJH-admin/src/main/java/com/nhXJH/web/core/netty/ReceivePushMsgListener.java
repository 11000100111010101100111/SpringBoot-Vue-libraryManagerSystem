package com.nhXJH.web.core.netty;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/8
 * Time: 14:58
 **/
public class ReceivePushMsgListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

    }
}
