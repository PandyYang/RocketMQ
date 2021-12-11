package com.pandy.mq.base.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 消息的接收者
 * 默认负载均衡
 */
public class Consumer {
    public static void main(String[] args) throws MQClientException {
        /**
         * 1.创建消费者Consumer，制定消费者组名
         * 2.指定Nameserver地址
         * 3.订阅主题Topic和Tag
         * 4.设置回调函数，处理消息
         * 5.启动消费者consumer
         */
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");

        consumer.setNamesrvAddr("192.168.174.174:9876;192.168.174.173:9876");

        consumer.subscribe("base", "Tag2");

        // 接收消息内容的方法
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            System.out.println(msgs);
            for (MessageExt msg : msgs) {
                System.out.println("msg = " + new String(msg.getBody()));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
    }
}
