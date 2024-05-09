package com.example.diploma_project.exceptions;

/**
 * Класс исключения для не найденной книги
 */
public class BookNotFoundException extends Exception{
    public BookNotFoundException(String message) {
        super(message);
    }
}
