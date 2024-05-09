package com.example.diploma_project.models;

import com.example.diploma_project.enums.BookStatus;
import com.example.diploma_project.enums.BookType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * Класс представления книги
 */
@Data
@Entity
@Table(name = "Book")
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    /**
     * Идентификатор книги
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    /**
     * Название книги
     */
    @Column(nullable = false, name = "book_title")
    private String title;

    /**
     * Имя автора (на ней будет ссылка на прохождение в википедию)
     */
    @Column(nullable = false, name = "author_name")
    private String author;

    /**
     * Год издания экземпляра книги
     */
    @Column(name = "year_of_edition")
    private int edition_year;

    /**
     * Дата выдачи книги
     */
    @Column(name = "book_taken_date")
    @Temporal(TemporalType.DATE)
    private LocalDate takenDate;

    /**
     * Дата возврата книги
     */
    @Column(name = "book_give_date")
    @Temporal(TemporalType.DATE)
    private LocalDate giveDate;

    /**
     * Просрочен ли срок возрата
     */
    @Transient
    private boolean isOverdue;

    /**
     * Статус книги (занята или освобождена)
     */
    @Column(name = "book_status")
    private BookStatus status;

    /**
     * Количество экземпляров книги
     */
    @Column(name = "book_amount")
    private int amount;

    /**
     * Тип книги
     */
    @Column(name = "book_type")
    private BookType type;

    /**
     * Владелец книги
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "reader_id", nullable = false)
    private Reader reader;

    public Book(Long id, String title, String author, int edition_year, int amount, BookType type) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.edition_year = edition_year;
        this.amount = amount;
        this.type = type;
    }
}
