package com.example.diploma_project.repositories;

import com.example.diploma_project.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для работы с книгами
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    /**
     * Метод поиска книги по идентификатору
     * @param id идентификатор книги
     * @return книгу
     */
    @Override
    Optional<Book> findById(Long id);

    /**
     * Метод поиска книги по названию
     * @param title название книги
     * @return книгу
     */
    Optional<Book> findByTitle(String title);

    /**
     * Метод поиска книг по автору
     * @param author имя автора
     * @return список книг
     */
    List<Book> findBooksByAuthor(String author);
}
