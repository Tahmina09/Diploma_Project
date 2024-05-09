package com.example.diploma_project.services;

import com.example.diploma_project.enums.BookStatus;
import com.example.diploma_project.exceptions.BookNotFoundException;
import com.example.diploma_project.exceptions.ReaderNotFoundException;
import com.example.diploma_project.models.Book;
import com.example.diploma_project.models.Reader;
import com.example.diploma_project.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с книгами
 */
@Service
@AllArgsConstructor
public class BookService {
    private BookRepository repository;

    /**
     * Метод получения всех книг
     * @return список книг
     */
    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    /**
     * Метод сохранение книги
     * @param book книга
     */
    @Transactional
    public void saveBook(Book book) {
        repository.save(book);
    }

    /**
     * Метод обновления книги
     * @param id идентификатор книги
     * @param book книга
     * @throws BookNotFoundException выбрасывает исключение
     */
    @Transactional
    public Book updateBook(Long id, Book book) throws BookNotFoundException {
        Optional<Book> optionalBook = foundBook(id);
        if (optionalBook.isPresent()) {
            Book updatedBook = optionalBook.get();
            updatedBook.setTitle(book.getTitle());
            updatedBook.setAuthor(book.getAuthor());
            updatedBook.setEdition_year(book.getEdition_year());
            updatedBook.setAmount(book.getAmount());
            updatedBook.setType(book.getType());
            saveBook(updatedBook);
            return updatedBook;
        }
        throw new BookNotFoundException("Книга не найдена. Введите запрос корректно или повторите попытку!");
    }

    /**
     * Метод удаления книги
     * @param id идентификатор книги
     * @throws BookNotFoundException выбрасывает исключение
     */
    @Transactional
    public void deleteBook(Long id) throws BookNotFoundException {
        Optional<Book> searchBook = foundBook(id);
        searchBook.ifPresent(book -> repository.delete(book));
        if (searchBook.isEmpty()) {
            throw new BookNotFoundException("Книга не найдена. Введите запрос корректно или повторите попытку!");
        }
    }

    /**
     * Метод для возврата книги пользователем
     * @param id идентификатор книги
     * @throws BookNotFoundException выбрасывает исключение
     */
    @Transactional
    public void freeBook(Long id) throws BookNotFoundException {
        Optional<Book> searchBook = foundBook(id);
        searchBook.ifPresent(book -> book.setReader(null));
        searchBook.ifPresent(book -> book.setGiveDate(LocalDate.now()));
        searchBook.ifPresent(book -> book.setStatus(BookStatus.FREE));
        searchBook.ifPresent(this::saveBook);
        if (searchBook.isEmpty()) {
            throw new BookNotFoundException("Книга не найдена. Введите запрос корректно или повторите попытку!");
        }
    }

    /**
     * Метод для выдачи книги
     * @param id идентификатор книги
     * @param reader пользователь(получатель книги)
     * @throws BookNotFoundException выбрасывает исключение
     */
    @Transactional
    public void takenBook(Long id, Reader reader) throws BookNotFoundException {
        Optional<Book> optionalBook = foundBook(id);
        optionalBook.ifPresent(book -> book.setReader(reader));
        optionalBook.ifPresent(book -> book.setTakenDate(LocalDate.now()));
        optionalBook.ifPresent(book -> book.setGiveDate(book.getTakenDate().plusDays(30)));
        optionalBook.ifPresent(book -> book.setStatus(BookStatus.BUSY));
        optionalBook.ifPresent(this::saveBook);
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException("Книга не найдена. Введите запрос корректно или повторите попытку!");
        }
    }

    /**
     * Метод проверки просроченности книги
     * @param id идентификатор книги
     * @return да или нет
     * @throws BookNotFoundException выбрасывает исключение
     */
    public boolean isBookOverdue(Long id) throws BookNotFoundException {
        Optional<Book> optionalBook = foundBook(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (book.getGiveDate().isAfter(book.getTakenDate().plusDays(30))){
                book.setOverdue(true);
                return true;
            }
            book.setOverdue(false);
            return false;
        }
        throw new BookNotFoundException("Книга не найдена. Введите запрос корректно или повторите попытку!");
    }

    /**
     * Метод получение пользователя книги
     * @param id идентификатор книги
     * @return пользователя
     */
    public Reader getBookOwner(Long id) throws ReaderNotFoundException {
        Optional<Book> searchBook = foundBook(id);
        return searchBook.map(Book::getReader).orElse(null);
    }

    /**
     * Метод поиска книги по названию
     * @param title название книги
     * @return книгу
     * @throws BookNotFoundException выбрасывает исключение
     */
    public Book findBookByTitle(String title) throws BookNotFoundException {
        Book searchBook = repository.findByTitle(title).orElse(null);
        if (searchBook != null){
            return searchBook;
        }
        throw new BookNotFoundException("Книга не найдена. Введите запрос корректно или повторите попытку!");
    }

    /**
     * Метод поиска книг по автору
     * @param author имя автора
     * @return список книг
     * @throws BookNotFoundException выбрасывает исключение
     */
    public List<Book> findBooksByAuthorName(String author) throws BookNotFoundException {
        List<Book> searchBook = repository.findBooksByAuthor(author);
        if (searchBook.isEmpty()){
            throw new BookNotFoundException("Книга не найдена. Введите запрос корректно или повторите попытку!");
        }
        return searchBook;
    }

    /**
     * Метод поиска книги по идентификатору
     * @param id идентификатор книги
     * @return книгу
     */
    public Optional<Book> foundBook(Long id) {
        return repository.findById(id);
    }
}
