package com.ruchir.kafka.consumer.demo;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerDemo {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ConsumerDemo.class);

        // Create properties
        String bootstrapServer = "localhost:9092";
        String groupId = "group-4";
        String topic = "top_con";

        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Create a consumer
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);

        // Subscribe consumer to the topic
        kafkaConsumer.subscribe(Collections.singleton(topic));

        // poll for new data
        Duration timeout = Duration.ofMillis(1000);
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(timeout);
            for (ConsumerRecord record : records) {
                logger.info("\nKey: " + record.key() +
                "\nValue: "+ record.value()+
                "\nPartition: " + record.partition()+
                "\nOffset" + record.offset() +
                "\n------------------------------------"
                );
            }
        }
    }
}
