package com.nhXJH.web.core.netty;

import com.rabbitmq.client.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * 读取RabbitMQ配置文件
 */
@Slf4j
@Configuration
public class MqConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Bean
    public Queue directQueue() {
        // 队列名字， 持久化
        return new Queue(Constants.PRESS_EXCHANGE, true);
    }


    public Connection getConn() {
        com.rabbitmq.client.ConnectionFactory factory = new com.rabbitmq.client.ConnectionFactory();
        Connection connection = null;
        try {
            factory.setHost(host);
            factory.setPort(port);
            factory.setUsername(username);
            factory.setPassword(password);
            factory.setConnectionTimeout(30000);
            connection = factory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
            log.error("获取MQ连接失败...");
        }
        return connection;
    }


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        /*channel缓存的大小*/
        connectionFactory.setChannelCacheSize(200);
        /**/
        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.addConnectionListener(new ConnectionListener() {
            @Override
            public void onCreate(org.springframework.amqp.rabbit.connection.Connection connection) {

            }
            @Override
            public void onClose(org.springframework.amqp.rabbit.connection.Connection connection) {
//                logger.info("关闭rabbitmq连接");
            }
        });
        //设置虚拟主机，默认/
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.SIMPLE);
        connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }
    /**
     * 配置spring上下文监听容器 需要延迟启动
     *
     * @return SimpleMessageListenerContainer
     */
    public SimpleMessageListenerContainer newListenerContainer(MessageListener messageListener, String[] queues) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        container.setAutoDeclare(false);
        //声明失败重复次数
        container.setDeclarationRetries(1);
        //可接受来自broker一个socket帧中的消息数目. 数值越大，消息分发速度就越快, 但无序处理的风险更高
        container.setPrefetchCount(10);
        container.addQueueNames(queues);
        container.setAcknowledgeMode(AcknowledgeMode.NONE);
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(100);
        container.setConcurrentConsumers(10);
        container.setMessageListener(messageListener);
        container.setAutoStartup(false);
        container.setExclusive(false);
         /*丢弃所有失败的消息 false丢弃 true 循环处理 或者，
         你可以抛出一个AmqpRejectAndDontRequeueException;这会阻止消息重新入列,不管defaultRequeueRejected 属性设置的是什么.*/
        container.setDefaultRequeueRejected(false);
        container.start();
        return container;
    }

    /**
     * 配置管理器
     *
     * @return RabbitAdmin
     */
    @Bean
    public RabbitAdmin rabbitAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        rabbitAdmin.setIgnoreDeclarationExceptions(true);
        return rabbitAdmin;
    }

    /**
     * 声明交互器
     *
     * @return
     */
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("exchange_pushmsg");
    }
    /**
     * 创建一个ReceivePushMsgListener，监听routingKey为“rk_recivemsg”的队列实现客户端收到消息后向此队列发送确认收到消息
     */
    @Bean
    public Object declareDirectQueue() {
        List<String> receiveQueueNames = new ArrayList<>();
        String receive = "queue_purchase";
        declare(receive, directExchange(), "purchase_ack_*");
        receiveQueueNames.add(receive);
        newListenerContainer(new ReceivePushMsgListener(), receiveQueueNames.toArray(new String[receiveQueueNames.size()]));
        return null;
    }

    private void declare(String queueName, DirectExchange exchange, String routingKey) {
        RabbitAdmin admin = rabbitAdmin();
        Queue queue = new Queue(queueName, true, false, false);
        admin.declareQueue(queue);
        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));
    }
}