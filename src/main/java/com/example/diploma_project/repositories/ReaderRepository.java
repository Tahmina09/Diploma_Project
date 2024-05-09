package com.example.diploma_project.repositories;

import com.example.diploma_project.models.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Интерфейс репозитория для управления учетной записи пользователя
 */
@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {
    /**
     * Метод поиска пользователя по идентификатору
     * @param id идентификатор пользователя
     * @return пользователя
     */
    @Override
    Optional<Reader> findById(Long id);

    /**
     * Метод поиска пользователя по имени
     * @param username имя пользователя
     * @return пользователя
     */
    Optional<Reader> findByUsername(String username);

    /**
     * Метод поиска пользователя по электронной почте
     * @param email электронная почта
     * @return пользователя
     */
    Optional<Reader> findByEmail(String email);
}
