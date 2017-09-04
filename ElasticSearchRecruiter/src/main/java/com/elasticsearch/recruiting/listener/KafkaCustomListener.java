package com.elasticsearch.recruiting.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Martha on 8/23/2017.
 */
public class KafkaCustomListener {
    public CountDownLatch countDownLatch = new CountDownLatch(3);

    @KafkaListener(id = "id0", topicPartitions = { @TopicPartition(topic = "SpringKafkaTopic", partitions = { "0" }) })
    public void listenPartition0(ConsumerRecord<?, ?> record) {
        System.out.println("Listener Id0, Thread ID: " + Thread.currentThread().getId());
        System.out.println("Received: " + record);
        countDownLatch.countDown();
    }
}
