package com.example.diploma_project.controllers;

import com.example.diploma_project.exceptions.ReaderNotFoundException;
import com.example.diploma_project.models.Book;
import com.example.diploma_project.models.Reader;
import com.example.diploma_project.services.ReaderDetailsService;
import com.example.diploma_project.services.ReaderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для обработки запросов по пользователям
 */
@RestController
@AllArgsConstructor
public class ReaderController {
    private final ReaderService service;
    private final ReaderDetailsService readerDetailsService;

    /**
     * Метод регистрации пользователя
     * @param reader пользователь
     * @return сообщение об успешной регистрации
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerReader(@RequestBody Reader reader) {
        try {
            service.registerReader(reader);
            return ResponseEntity.ok("Регистрация прошла успешно");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при регистрации: " + e.getMessage());
        }
    }

    /**
     * Метод получения всех пользователей
     * @return список пользователей
     */
    @GetMapping("/readers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Reader>> getAllReaders() {
        return new ResponseEntity<>(service.getAllReaders(), HttpStatus.OK);
    }

    /**
     * Метод редактирования пользователя
     * @param id идентификатор пользователя
     * @param reader пользователь
     * @return отредактированный пользователь
     * @throws ReaderNotFoundException выбрасывает исключение
     */
    @PutMapping("/reader/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Reader> updateReader(@PathVariable("id") Long id, @RequestBody @Valid Reader reader) throws ReaderNotFoundException {
        return new ResponseEntity<>(service.updateReader(id, reader), HttpStatus.OK);
    }

    /**
     * Метод удаление пользователя
     * @param id идентификатор пользователя
     * @return сообщение об успешном удалении
     * @throws ReaderNotFoundException выбрасывает исключение
     */
    @DeleteMapping("/reader/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteReader(@PathVariable("id") Long id) throws ReaderNotFoundException {
        service.deleteReader(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Метод получения всех книг пользователя
     * @param id идентификатор пользователя
     * @return список книг пользователя
     * @throws ReaderNotFoundException выбрасывает исключение
     */
    @GetMapping("/reader/{id}/books")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Book>> getAllReaderBooks(@PathVariable("id") Long id) throws ReaderNotFoundException {
        return new ResponseEntity<>(service.getAllReaderBooks(id), HttpStatus.OK);
    }

    /**
     * Метод поиска пользователя по имени
     * @param email электронная почта пользователя
     * @return найденного пользователя
     */
    @GetMapping("/reader/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getReaderDetailsByEmail(@PathVariable String email) {
        UserDetails userDetails = readerDetailsService.loadUserByUsername(email);
        return new ResponseEntity<>("Email: " + userDetails.getUsername() + "\n"
                + "Password: " + userDetails.getPassword() + "\n"
                + "Roles: " + userDetails.getAuthorities()
                , HttpStatus.FOUND);
    }

    /**
     * Метод поиска пользователя по идентификатору
     * @param id идентификатор пользователя
     * @return найденного пользователя
     */
    @GetMapping("/reader/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Reader> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.findReader(id).orElse(null), HttpStatus.FOUND);
    }
}
