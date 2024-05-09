package com.example.diploma_project.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(name = "Role")
@Data
@RequiredArgsConstructor
public class Role implements GrantedAuthority {
    /**
     * Идентификатор роли
     */
    @Id
    private final Long id;

    /**
     * Наименование роли
     */
    private final String name;

    /**
     * Коллекция пользователей
     */
    @Transient
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Reader> readers;

    /**
     * Метод получения роли пользователя
     * @return наименование роли
     */
    @Override
    public String getAuthority() {
        return getName();
    }
}
