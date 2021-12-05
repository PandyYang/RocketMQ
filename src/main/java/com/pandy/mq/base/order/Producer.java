package com.pandy.mq.base.order;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class Producer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("192.168.174.174:9876;192.168.174.173:9876;");
        producer.start();

        List<OrderStep> orderSteps = OrderStep.buildOrders();
        int i = 0;
        for (OrderStep orderStep : orderSteps) {
            String body = orderSteps + "";
            Message msg = new Message("OrderTopic", "Order", "i" + i++, body.getBytes(StandardCharsets.UTF_8));
            // 发送消息至 消息队列的选择器
            SendResult send = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    // 同一个订单 根据发送的订单id
                    long orderId = (long) arg;
                    long index = orderId % mqs.size();
                    return mqs.get((int) index);
                }
            }, orderStep.getOrderId());
            System.out.println("发送结果:" + send);
        }
        producer.shutdown();
    }
}
