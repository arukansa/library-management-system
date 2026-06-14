# Library Management System

## Описание (Description)
Это консольное приложение на Java (Core) для управления библиотекой. Данные хранятся в памяти приложения с помощью коллекций Java.

**Автор (Author):** Aru Seitkamal

## Функционал
- Добавление книг и читателей
- Выдача и возврат книг
- Поиск книг по автору или названию
- Аналитика: свободные книги, список должников (читателей с книгами), Топ-3 читателя.

## Структура проекта (Project Structure)
- `src/model/` — Доменные сущности (Book, Reader, BorrowRecord)
- `src/service/` — Бизнес-логика (LibraryService)
- `src/main/` — Консольный интерфейс, точка входа (Main)

## Как запустить (How to run)

1. Перейдите в корневую директорию проекта.
2. Скомпилируйте исходный код:
   ```bash
   javac -d bin src/model/*.java src/service/*.java src/main/*.java
   ```
3. Запустите приложение:
   ```bash
   java -cp bin main.Main
   ```
