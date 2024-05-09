package com.example.diploma_project.services;

import com.example.diploma_project.config.WebSecurityConfig;
import com.example.diploma_project.exceptions.ReaderNotFoundException;
import com.example.diploma_project.models.Book;
import com.example.diploma_project.models.Reader;
import com.example.diploma_project.models.Role;
import com.example.diploma_project.repositories.ReaderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для регистрации пользователя и работы с учетной записью пользователя
 */
@Service
@AllArgsConstructor
public class ReaderService {
    @Autowired
    private final ReaderRepository repository;

    @Autowired
    private final WebSecurityConfig config;


    /**
     * Метод получение списка всех пользователей
     * @return список всех пользователей
     */
    public List<Reader> getAllReaders() {
        return repository.findAll();
    }

    /**
     * Метод сохранения пользователя
     * @param reader пользователь
     */
    @Transactional
    public Reader save(Reader reader) {
        return repository.save(reader);
    }

    /**
     * Метод обновление параметров пользователя
     * @param id идентификатор пользователя
     * @param reader пользователь
     * @throws ReaderNotFoundException выбрасывает исключение
     */
    @Transactional
    public Reader updateReader(Long id, Reader reader) throws ReaderNotFoundException {
        Reader searchReader = findReader(id).orElseThrow(ReaderNotFoundException::new);
        searchReader.setUsername(reader.getUsername());
        searchReader.setPhoneNumber(reader.getPhoneNumber());
        save(searchReader);
        return searchReader;
    }

    /**
     * Метод удаления пользователя
     * @param id идентификатор пользователя
     * @throws ReaderNotFoundException выбрасывает исключение
     */
    @Transactional
    public void deleteReader(Long id) throws ReaderNotFoundException {
        Reader searchReader = findReader(id)
                .orElseThrow(ReaderNotFoundException::new);
        repository.deleteById(id);
    }

    /**
     * Метод получения всех книг пользователя
     * @param id идентификатор пользователя
     * @return список книг
     * @throws ReaderNotFoundException выбрасывает исключение
     */
    public List<Book> getAllReaderBooks(Long id) throws ReaderNotFoundException {
        Reader searchReader = findReader(id).orElseThrow(ReaderNotFoundException::new);
        return searchReader.getBooks();
    }

    /**
     * Метод поиска пользователя по идентификатору
     * @param id идентификатор
     * @return пользователя
     */
    public Optional<Reader> findReader(Long id) {
        return repository.findById(id);
    }

    /**
     * Метод регистрации пользователя
     * @param reader пользователь
     * @throws Exception выбрасывает исключение
     */
    @Transactional
    public void registerReader(Reader reader) throws Exception {
        if (!repository.findByEmail(reader.getEmail()).isPresent()) {
            String encodedPassword = config.passwordEncoder().encode(reader.getPassword());
            Reader newReader = new Reader();
            newReader.setUsername(reader.getUsername());
            newReader.setPhoneNumber(reader.getPhoneNumber());
            newReader.setEmail(reader.getEmail());
            newReader.setPassword(encodedPassword);
            newReader.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
            repository.save(reader);
        } else {
            throw new Exception("Пользователь с таким email уже существует");
        }
    }
}
