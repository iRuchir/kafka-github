package com.ruchir.kafka.producer.demo;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class ProducerDemo {
    public static void main(String[] args) {

        // Create producer properties
        Properties properties = new Properties();
        String bootstrapServer = "localhost:9092";
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        
        // Create the producer
        KafkaProducer<String,String> kafkaProducer = new KafkaProducer<String,String>(properties);

        // Create producer record
        String topic = "top_con";
        String value = "Right from java producer";
        ProducerRecord<String, String> record = new ProducerRecord<String,String>(topic, value);

        // Send data - asynchronous
        kafkaProducer.send(record);

        // Flush data
        kafkaProducer.flush();

        // Close producer
        kafkaProducer.close();
    }
}
