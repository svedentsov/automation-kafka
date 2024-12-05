package com.kafka.consumer;

import com.kafka.utils.DefaultProperties;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс, ответственный за потребление сообщений из указанного топика Kafka.
 */
public class UserConsumer {

    /**
     * Получает список сообщений из указанного топика Kafka.
     *
     * @param topic название топика Kafka для потребления
     * @return список сообщений, потребленных из топика
     */
    public static List<String> getTopicMessages(String topic) {
        List<String> messages = new ArrayList<>();

        // Создаем новый Consumer с использованием свойств для потребителя
        Consumer<String, String> consumer = new KafkaConsumer<>(DefaultProperties.getConsumerProperties(topic));

        // Подписываемся на указанный топик
        consumer.subscribe(Collections.singletonList(topic));

        try {
            // Ожидаем получения записей в течение 10 секунд
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));

            // Обрабатываем полученные записи
            records.forEach(record -> {
                String messageValue = record.value();
                messages.add(messageValue); // Добавляем сообщение в список
                System.out.println("Получено сообщение из топика: " + messageValue);
            });

            // Асинхронно фиксируем смещения для успешных сообщений
            consumer.commitAsync();

        } catch (Exception e) {
            System.err.println("Ошибка при потреблении сообщений из топика: " + e.getMessage());
        } finally {
            // Закрываем Consumer для освобождения ресурсов
            consumer.close();
        }

        // Возвращаем список полученных сообщений
        return messages;
    }
}
