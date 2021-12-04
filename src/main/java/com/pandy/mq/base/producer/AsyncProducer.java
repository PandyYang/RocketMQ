package com.pandy.mq.base.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 发送异步消息
 */
public class AsyncProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("group1");

        producer.setNamesrvAddr("192.168.174.174:9876;192.168.174.173:9876;");

        producer.start();

        for (int i = 0; i < 10; i++) {
            Message msg = new Message("base", "Tag2", ("Hello World" + i ).getBytes(StandardCharsets.UTF_8));

            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送成功" + sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println("发送失败：" + throwable);
                }
            });

            TimeUnit.SECONDS.sleep(1);
        }
        producer.shutdown();
    }
}
