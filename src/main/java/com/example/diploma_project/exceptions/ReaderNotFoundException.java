package com.example.diploma_project.exceptions;

/**
 * Класс исключения для не найденного пользователя
 */
public class ReaderNotFoundException extends Exception {
    public ReaderNotFoundException() {
        super("Пользователь не найден! Введите запрос корректно и повторите попытку.");
    }
}
