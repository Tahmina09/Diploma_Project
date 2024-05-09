Целью данного технического задания является разработка веб-сервиса для цифрового учета книг и пользователей в библиотеке. Веб-сервис должен обеспечивать функционал регистрации читателей, выдачи книг, возврата книг и управления данными о пользователях и книгах. Также должна быть реализована возможность сортировки и поиска пользователей и книг.

--------------------------------------------------------------
## Функциональные требования

Веб-сервис должен предоставлять следующие функции:

- **Регистрация читателей**: Библиотекари должны иметь возможность зарегистрировать новых читателей, включая ввод их персональных данных (имя, фамилия, адрес электронной почты, контактный номер и т. д.).

- **Выдача книг**: Библиотекари должны иметь возможность выдавать книги зарегистрированным читателям. При выдаче книги должны фиксироваться дата выдачи и срок возврата.

- **Возврат книг**: Библиотекари должны иметь возможность освобождать книги, которые возвращаются читателями. При возврате книги должна фиксироваться дата возврата.

- **Управление данными о пользователях и книгах**: Библиотекари должны иметь возможность просматривать, редактировать и удалять данные о пользователях и книгах.

- **Сортировка**: Пользователям должна быть предоставлена возможность сортировать список пользователей и книг.

- **Поиск пользователей и книг**: Пользователям должна быть предоставлена возможность выполнять поиск пользователей и книг по различным критериям.

## Нефункциональные требования

- **Безопасность**: Веб-сервис должен обеспечивать безопасность передачи данных и аутентификацию пользователей (библиотекарей). Доступ к функционалу управления данными должен быть защищен аутентификацией и авторизацией.

- **Производительность**: Веб-сервис должен быть способен обрабатывать одновременные запросы от нескольких библиотекарей и читателей с минимальными задержками.

## Технологический стек

Рекомендуемый технологический стек для разработки веб-сервиса:

- Язык программирования: *Java*

- Фреймворк: *Spring Boot*

- СУБД: *H2*

- Инструменты сборки и управления зависимостями: *Maven* 