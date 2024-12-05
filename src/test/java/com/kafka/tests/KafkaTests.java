package com.kafka.tests;

import com.kafka.consumer.UserAvroConsumer;
import com.kafka.consumer.UserConsumer;
import com.kafka.model.User;
import com.kafka.producer.UserAvroProducer;
import com.kafka.producer.UserProducer;
import com.kafka.utils.ReadYml;
import modelAvro.user.UserAvro;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Используем один экземпляр класса для всех тестов
@Tag("integration") // Тег для обозначения интеграционных тестов
@DisplayName("Тестирование функциональности Kafka") // Общее описание для класса
public class KafkaTests {

    private User user;
    private UserAvro avroMessage;

    @BeforeEach
    public void setUp() {
        // Читаем YAML-файл с тестовыми данными
        Properties data = ReadYml.readYamlFile("data");

        // Инициализация объектов для тестов
        user = User.builder()
                .name(data.getProperty("name"))
                .email(data.getProperty("email"))
                .age(1)
                .build();

        avroMessage = UserAvro.newBuilder()
                .setName("ivan")
                .setEmail("ivan@gmail.com")
                .setAge("31")
                .build();
    }

    @Test
    @DisplayName("Отправка сообщения в Kafka топик с объектом User")
    public void shouldSendMessageToTopicSuccessfully() {
        // Отправляем сообщение в топик "topic_user"
        UserProducer.sendTopicMessage("topic_user", user);

        // Потребляем сообщения из топика и проверяем последнее сообщение
        List<String> messages = UserConsumer.getTopicMessages("topic_user");
        assertThat(messages.getLast(), is("{\"name\":\"ivan\",\"email\":\"ivan@gmail.com\",\"age\":1}"));
    }

    @Test
    @DisplayName("Отправка сообщения в Kafka топик с Avro-сообщением")
    public void shouldSendAvroMessageToTopicSuccessfully() {
        // Отправляем Avro-сообщение в топик "topic_user"
        UserAvroProducer.sendTopicMessage("topic_user", avroMessage);

        // Потребляем сообщения из топика и проверяем последнее сообщение
        List<String> messages = UserAvroConsumer.getTopicMessages("topic_user");
        assertThat(messages.getLast(), is("{\"name\": \"ivan\", \"email\": \"ivan@gmail.com\", \"age\": \"31\"}"));
    }
}
