package com.example.diploma_project.services;

import com.example.diploma_project.models.Reader;
import com.example.diploma_project.repositories.ReaderRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ReaderDetailsService implements UserDetailsService {
    private final ReaderRepository repository;

    /**
          * Метод поиска пользователя по имени
          * @param email электронная почта пользователя
          * @return пользователя
          * @throws UsernameNotFoundException выбрасывает исключение
     * */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Reader reader = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(reader.getEmail())
                .password(reader.getPassword())
                .roles("USER")
                .build();
    }
}
