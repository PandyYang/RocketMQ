package com.pandy.mq.base.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * rocktmq集群
 * 发送同步消息
 */
public class SyncProducer {
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
            Message msg = new Message("base", "Tag1", ("Hello World" + i ).getBytes(StandardCharsets.UTF_8));
            SendResult result = producer.send(msg);
            SendStatus sendStatus = result.getSendStatus();
            String msgId = result.getMsgId();
            int queueId = result.getMessageQueue().getQueueId();
            System.out.println("消息发送状态 = " + result + "消息id" + msgId + "队列Id" + queueId);
            TimeUnit.SECONDS.sleep(1);
        }
        producer.shutdown();
    }
}
