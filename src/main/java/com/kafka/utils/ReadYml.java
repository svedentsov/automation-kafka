package com.kafka.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Класс для чтения YAML-файлов и преобразования их в свойства Java.
 */
public class ReadYml {

    /**
     * Читает YAML-файл и возвращает его содержимое как объект Properties.
     *
     * @param fileName имя YAML-файла (без расширения)
     * @return объект Properties с данными из файла
     */
    public static Properties readYamlFile(String fileName) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Properties properties = null;
        try {
            properties = mapper.readValue(new File("src/main/resources/data/" + fileName + ".yml"), Properties.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
