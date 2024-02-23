## Задание. Spring

**XML конфигурация:**
- Создать `application.properties` файл для подключения к БД
- Реализовать класс для подключения к БД
- Настроить `XML конфигурацию` так, чтобы репозитории получили соединение
- В методе main получить bean классов `UserService` и `CompanyService`
- Вызвать методы `findUserById` и `findCompanyById`

**Аннотации и Java config:**
- Заменить в проекте из предыдущего задания конфигурацию `XML` на `java based` и аннотации
- Добавить профайлы `prod` и `test`, соединение с БД должно отличаться для этих профайлов

**Event Listeners:**
- Создать слушателей, которые будут логировать вызовы `CRUD` операций и результаты их выполнения
- Для этого публиковать события до вызова запроса в БД и публиковать событие после выполнения запроса
- Слушатели должны вызываться по дополнительным условиям (**condition**)
- Также при желании можно добавить роли пользователей и их права на выполнение операций

 *Применено*:
- Gradle, Lombok
- Конфигурация beans в ApplicationConfiguration.java и через аннотации @Configuration, @Bean, @Profile, @Repository, @Service
- Подключение к БД через JDBC (DriverManager), PostgresDriver