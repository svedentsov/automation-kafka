package com.kafka.producer;

import com.kafka.utils.DefaultProperties;
import modelAvro.user.UserAvro;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * Класс, ответственный за отправку Avro-сообщений в указанный топик Kafka.
 */
public class UserAvroProducer {

    /**
     * Отправляет Avro-сообщение в указанный топик Kafka.
     *
     * @param topic   название топика Kafka
     * @param message Avro-сообщение для отправки
     */
    public static void sendTopicMessage(String topic, UserAvro message) {
        // Генерируем случайный ключ для сообщения
        String generatedKey = String.valueOf(Math.random());

        // Создаем новый Avro Producer с указанными свойствами
        Producer<String, UserAvro> producer = new KafkaProducer<>(DefaultProperties.getProducerAvroProperties());

        try {
            // Создаем запись ProducerRecord и отправляем Avro-сообщение
            producer.send(new ProducerRecord<>(topic, generatedKey, message));
            System.out.println("Avro-сообщение отправлено в топик: " + message.toString());
        } finally {
            // Закрываем Producer для освобождения ресурсов
            producer.close();
        }
    }
}
