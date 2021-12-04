package com.pandy.mq.base.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 发送单向消息
 */
public class OneWayProducer {
    public static void main(String[] args) throws Exception {
        /**
         * 创建生产者步骤
         * 1.创建消息生产者producer，并制定生产者组名
         * 2.指定Nameserver地址
         * 3.启动producer
         * 4.创建消息对象，指定主题Topic、Tag和消息体
         * 5.发送消息
         * 6.关闭生产者producer
         */
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("192.168.174.174:9876;192.168.174.173:9876;");
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message msg = new Message("base", "Tag3", ("Hello World, 单项消息" + i ).getBytes(StandardCharsets.UTF_8));
            producer.sendOneway(msg);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
