package com.kafka.consumer;

import com.kafka.utils.DefaultProperties;
import modelAvro.user.UserAvro;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс, ответственный за потребление Avro-сообщений из указанного топика Kafka.
 */
public class UserAvroConsumer {

    /**
     * Получает список Avro-сообщений из указанного топика Kafka.
     *
     * @param topic название топика Kafka для потребления
     * @return список Avro-сообщений, потребленных из топика
     */
    public static List<String> getTopicMessages(String topic) {
        List<String> messages = new ArrayList<>();

        // Создаем новый Consumer с использованием свойств для потребителя
        Consumer<String, UserAvro> consumer = new KafkaConsumer<>(DefaultProperties.getConsumerAvroProperties(topic));

        // Подписываемся на указанный топик
        consumer.subscribe(Collections.singletonList(topic));

        try {
            // Ожидаем получения Avro-записей в течение 10 секунд
            ConsumerRecords<String, UserAvro> records = consumer.poll(Duration.ofSeconds(10));

            // Обрабатываем полученные записи
            records.forEach(record -> {
                Object userAvro = record.value();
                String messageValue = userAvro.toString();
                messages.add(messageValue); // Добавляем сообщение в список
                System.out.println("Получено Avro-сообщение из топика: " + messageValue);
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
