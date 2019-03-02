package com.allblue.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Description: ...
 * @Author: Xone
 * @Date: 2019/2/1 15:16
 **/
@Component
public class TestConsumer {
    @KafkaListener(topics = "photo")
    public void listen (ConsumerRecord<?, ?> record) throws Exception {
        System.out.printf("topic = %s, offset = %d, value = %s \n", record.topic(), record.offset(), record.value());
    }
}
