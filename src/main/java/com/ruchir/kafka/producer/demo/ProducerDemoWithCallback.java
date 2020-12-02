package com.ruchir.kafka.producer.demo;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerDemoWithCallback {
    public static void main(String[] args) {

        // Creating logger for class
        final Logger logger = LoggerFactory.getLogger(ProducerDemoWithCallback.class);

        // Create producer properties
        Properties properties = new Properties();
        String bootstrapServer = "localhost:9092";
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create the producer
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);

        // Create producer record
        String topic = "top_con";
        String value = "Right from java producer";
        ProducerRecord<String, String> record;

        // Send data - asynchronous
        for (int i = 0; i < 10; i++) {
            record = new ProducerRecord<String, String>(topic, value + " " + i);
            kafkaProducer.send(record, new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    // executes everytime a record is successfully sent or an exception is thrown
                    if (exception != null) {
                        logger.info(exception.getMessage());
                    } else {
                        logger.info("Received new Metadata." + "\n" + "Topic: " + metadata.topic() + "\n"
                                + "Partition: " + metadata.partition() + "\n" + "Offset: " + metadata.offset() + "\n"
                                + "Timestamp: " + metadata.timestamp() + "\n" + "-----------------------");
                    }

                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // Flush data
        kafkaProducer.flush();

        // Close producer
        kafkaProducer.close();
    }
}
