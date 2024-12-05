package com.kafka.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс, описывающий пользователя системы.
 * Используется для хранения базовой информации о пользователе.
 */
@Builder
@Getter
@Setter
public class User {

    /**
     * Полное имя пользователя. По умолчанию установлено значение "Иван".
     */
    @Builder.Default
    private String name = "Иван";

    /**
     * Электронная почта пользователя. По умолчанию установлено значение "ivan@mail.com".
     */
    @Builder.Default
    private String email = "ivan@mail.com";

    /**
     * Возраст пользователя. По умолчанию установлен возраст 30 лет.
     */
    @Builder.Default
    private Integer age = 30;

    /**
     * Переопределенный метод toString для удобного представления объекта в виде строки.
     * Строка содержит все поля объекта в формате JSON.
     *
     * @return строковое представление пользователя в формате JSON.
     */
    @Override
    public String toString() {
        return "{" +
                "\"name\":\"" + name + "\"," +
                "\"email\":\"" + email + "\"," +
                "\"age\":" + age +
                "}";
    }
}
