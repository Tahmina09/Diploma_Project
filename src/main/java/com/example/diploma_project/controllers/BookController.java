package com.example.diploma_project.controllers;

import com.example.diploma_project.exceptions.BookNotFoundException;
import com.example.diploma_project.exceptions.ReaderNotFoundException;
import com.example.diploma_project.models.Book;
import com.example.diploma_project.models.Reader;
import com.example.diploma_project.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер по обработки запросов о книгах
 */
@RestController
@AllArgsConstructor
public class BookController {
    private final BookService service;

    /**
     * Метод добавления книги
     * @param book книга
     * @return добавленную книгу
     */
    @PostMapping("/book/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createBook(@RequestBody Book book) {
        service.saveBook(book);
        return ResponseEntity.ok().build();
    }

    /**
     * Метод получения всех книг
     * @return список всех книг
     */
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks() {
        return new ResponseEntity<>(service.getAllBooks(), HttpStatus.OK);
    }

    /**
     * Метод редактирования книги
     * @param id идентификатор книги
     * @param book книга для редактирования
     * @return отредактированную книгу
     * @throws BookNotFoundException выбрасывает исключение
     */
    @PutMapping("/book/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody Book book) throws BookNotFoundException {
        return new ResponseEntity<>(service.updateBook(id, book), HttpStatus.OK);
    }

    /**
     * Метод удаления книги
     * @param id идентификатор книги
     * @return http запрос на успешное удаление книги
     * @throws BookNotFoundException выбрасывает исключение
     */
    @DeleteMapping("/book/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) throws BookNotFoundException {
        service.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Метод для получения книги от пользователя
     * @param id идентификатор книги
     * @return сообщение об успешном получении
     * @throws BookNotFoundException выбрасывает исключение
     */
    @PutMapping("/book/free/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> markAsFree(@PathVariable("id") Long id) throws BookNotFoundException {
        service.freeBook(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Метод для выдачи книги
     * @param id идентификатор книги
     * @param reader пользователь(получатель книги)
     * @return сообщение об успешной выдаче
     * @throws BookNotFoundException выбрасывает исключение
     */
    @PutMapping("/book/take/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> markAsTaken(@PathVariable("id") Long id, Reader reader) throws BookNotFoundException {
        service.takenBook(id, reader);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Метод получения пользователя книги
     * @param id идентификатор пользователя
     * @return пользователя
     * @throws ReaderNotFoundException выбрасывает исключение
     */
    @GetMapping("/book/{id}/owner")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Reader> getBookOwner(@PathVariable("id") Long id) throws ReaderNotFoundException {
        return new ResponseEntity<>(service.getBookOwner(id), HttpStatus.FOUND);
    }

    /**
     * Метод проверки просроченности книги
     * @param id идентификатор книги
     * @return да или нет
     * @throws BookNotFoundException выбрасывает исключение
     */
    @GetMapping("/book/overdue/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> isOverdue(@PathVariable("id") Long id) throws BookNotFoundException {
        if (!service.isBookOverdue(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Метод поиска книги по названию
     * @param title название книги
     * @return найденную книгу
     * @throws BookNotFoundException выбрасывает исключение
     */
    @GetMapping("/book/find/title")
    public ResponseEntity<Book> findBookByTitle(String title) throws BookNotFoundException {
        return new ResponseEntity<>(service.findBookByTitle(title), HttpStatus.FOUND);
    }

    /**
     * Метод поиска книг по имени автора
     * @param author имя автора
     * @return список книг автора
     * @throws BookNotFoundException выбрасывает исключение
     */
    @GetMapping("/book/find/author")
    public ResponseEntity<List<Book>> findBooksByAuthor(String author) throws BookNotFoundException {
        return new ResponseEntity<>(service.findBooksByAuthorName(author), HttpStatus.FOUND);
    }

    /**
     * Метод поиска книги по идентификатору
     * @param id идентификатор книги
     * @return найденную книгу
     */
    @GetMapping("/book/{id}")
    public ResponseEntity<Book> findById(@PathVariable("id") Long id) {
        Optional<Book> book = service.foundBook(id);
        return book.map(searchBook -> new ResponseEntity<>(searchBook, HttpStatus.FOUND))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Book()));
    }
}
