package com.example.diploma_project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "Reader")
@AllArgsConstructor
@NoArgsConstructor
public class Reader implements UserDetails {
    /**
     * Идентификатор читателя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reader_id")
    private Long Id;

    /**
     * Имя читателя
     */
    @Column(name = "reader_name", nullable = false, length = 150)
    private String username;

    /**
     * Номер телефона читателя
     */
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    /**
     * Адрес электронной почты
     */
    @Column(name = "reader_email")
    private String email;

    /**
     * Пароль для сайта
     */
    @Column(name = "password", nullable = false)
    @Size(min = 8, message = "Не менее 8 символов")
    private String password;

    /**
     * Роль читателя (User или Admin)
     */
    @Column(name = "role")
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Role> roles;

    /**
     * Лист книг читателя
     */
    @OneToMany(mappedBy = "reader", fetch = FetchType.LAZY)
    private List<Book> books;

    public Reader(Long id, String username, String phoneNumber) {
        Id = id;
        this.username = username;
        this.phoneNumber = phoneNumber;
    }

    public Reader(String email, String password, Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
