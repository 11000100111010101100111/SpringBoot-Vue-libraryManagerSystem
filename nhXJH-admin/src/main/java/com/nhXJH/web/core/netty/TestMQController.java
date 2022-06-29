package com.nhXJH.web.core.netty;

import com.nhXJH.common.core.controller.BaseController;
import com.nhXJH.common.core.domain.AjaxResult;
import com.rabbitmq.client.BuiltinExchangeType;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;


@RestController
public class TestMQController extends BaseController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @GetMapping("/mq")
    public AjaxResult send() throws IOException {
        SendUtil.pushMsg(
                Constants.PURCHASE_EXCHANGE,
                BuiltinExchangeType.FANOUT,
                "purchase_"+getUserId(),
                "hello");
        return AjaxResult.success("success");
    }

    @GetMapping("/mq/template")
    public AjaxResult set(){
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(
                Constants.PURCHASE_EXCHANGE,
                "purchase_"+getUserId(),
                "hello",
                correlationId);
        return AjaxResult.success("hello");
    }

}