package com.kafka.utils;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * Класс для получения стандартных настроек Kafka Producer и Consumer.
 */
public class DefaultProperties {

    // Чтение файла YAML
    private static final Properties data = ReadYml.readYamlFile("data");

    /**
     * Получает свойства для стандартного Producer.
     *
     * @return свойства Producer
     */
    public static Properties getProducerProperties() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, data.getProperty("host"));
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }

    /**
     * Получает свойства для стандартного Consumer.
     *
     * @param topic название топика
     * @return свойства Consumer
     */
    public static Properties getConsumerProperties(String topic) {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, data.getProperty("host"));
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, topic);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        return properties;
    }

    /**
     * Получает свойства для Avro Producer.
     *
     * @return свойства Avro Producer
     */
    public static Properties getProducerAvroProperties() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, data.getProperty("host"));
        properties.setProperty("schema.registry.url", "http://localhost:8081");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        return properties;
    }

    /**
     * Получает свойства для Avro Consumer.
     *
     * @param topic название топика
     * @return свойства Avro Consumer
     */
    public static Properties getConsumerAvroProperties(String topic) {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, data.getProperty("host"));
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, topic);
        properties.setProperty("schema.registry.url", "http://localhost:8081");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        return properties;
    }
}
