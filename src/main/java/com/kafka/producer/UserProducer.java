package com.kafka.producer;

import com.kafka.model.User;
import com.kafka.utils.DefaultProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * Класс, ответственный за отправку сообщений в указанный топик Kafka.
 */
public class UserProducer {

    /**
     * Отправляет сообщение в указанный топик Kafka.
     *
     * @param topic   название топика Kafka
     * @param message сообщение для отправки
     */
    public static void sendTopicMessage(String topic, User message) {
        // Генерируем случайный ключ для сообщения
        String generatedKey = String.valueOf(Math.random());

        // Создаем новый Producer с указанными свойствами
        Producer<String, String> producer = new KafkaProducer<>(DefaultProperties.getProducerProperties());

        try {
            // Создаем запись ProducerRecord и отправляем сообщение
            producer.send(new ProducerRecord<>(topic, generatedKey, message.toString()));
            System.out.println("Сообщение отправлено в топик: " + message.toString());
        } finally {
            // Закрываем Producer для освобождения ресурсов
            producer.close();
        }
    }
}
