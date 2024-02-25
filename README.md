### Задание. Spring

**Аннотации и Java config:**
- Реализовать класс для подключения к БД
- Заменить в проекте из предыдущего задания конфигурацию `XML` на `java based` и аннотации
- Добавить профайлы `prod` и `test`, соединение с БД должно отличаться для этих профайлов

**Event Listeners:**
- Создать слушателей, которые будут логировать вызовы `CRUD` операций и результаты их выполнения
- Для этого публиковать события до вызова запроса в БД и публиковать событие после выполнения запроса
- Слушатели должны вызываться по дополнительным условиям (**condition**)

 *Применено*:
- Gradle, Lombok
- Конфигурация beans в ApplicationConfiguration.java и через аннотации @Configuration, @Bean, @Profile, @Repository, @Service
- Подключение к БД через JDBC (DriverManager), PostgresDriver
