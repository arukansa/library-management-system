package main;

import model.Book;
import model.Reader;
import service.LibraryService;

import java.util.List;
import java.util.Scanner;

/**
 * @author Aru Seitkamal
 */
public class Main {
    public static void main(String[] args) {
        LibraryService libraryService = new LibraryService();
        Scanner scanner = new Scanner(System.in);

        // Pre-loaded info by Aru Seitkamal
        libraryService.addBook(new Book(1, "The Great Gatsby", "F. Scott Fitzgerald", 1925, true));
        libraryService.addReader(new Reader(1, "Aru Seitkamal", "123-456-7890"));

        while (true) {
            System.out.println("\n--- Library Management System (by Aru Seitkamal) ---");
            System.out.println("1. Добавить книгу");
            System.out.println("2. Добавить читателя");
            System.out.println("3. Выдать книгу");
            System.out.println("4. Принять возврат");
            System.out.println("5. Книги у читателя");
            System.out.println("6. Найти книгу (по автору или названию)");
            System.out.println("7. Все свободные книги");
            System.out.println("8. Читатели с книгами на руках (Аналитика)");
            System.out.println("9. Топ-3 читателя (Аналитика)");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка ввода! Введите число.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("ID книги: ");
                    int bookId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Название: ");
                    String title = scanner.nextLine();
                    System.out.print("Автор: ");
                    String author = scanner.nextLine();
                    System.out.print("Год издания: ");
                    int year = Integer.parseInt(scanner.nextLine());
                    libraryService.addBook(new Book(bookId, title, author, year, true));
                    System.out.println("Книга успешно добавлена.");
                    break;

                case 2:
                    System.out.print("ID читателя: ");
                    int readerId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Имя: ");
                    String name = scanner.nextLine();
                    System.out.print("Телефон: ");
                    String phone = scanner.nextLine();
                    libraryService.addReader(new Reader(readerId, name, phone));
                    System.out.println("Читатель успешно добавлен.");
                    break;

                case 3:
                    System.out.print("ID читателя: ");
                    readerId = Integer.parseInt(scanner.nextLine());
                    System.out.print("ID книги: ");
                    bookId = Integer.parseInt(scanner.nextLine());
                    libraryService.borrowBook(readerId, bookId);
                    break;

                case 4:
                    System.out.print("ID читателя: ");
                    readerId = Integer.parseInt(scanner.nextLine());
                    System.out.print("ID книги: ");
                    bookId = Integer.parseInt(scanner.nextLine());
                    libraryService.returnBook(readerId, bookId);
                    break;

                case 5:
                    System.out.print("ID читателя: ");
                    readerId = Integer.parseInt(scanner.nextLine());
                    List<Book> readerBooks = libraryService.getReaderBooks(readerId);
                    if (readerBooks.isEmpty()) {
                        System.out.println("У читателя нет взятых книг.");
                    } else {
                        System.out.println("Книги у читателя:");
                        for (Book b : readerBooks) {
                            System.out.println(b);
                        }
                    }
                    break;

                case 6:
                    System.out.print("Введите название или автора: ");
                    String query = scanner.nextLine();
                    List<Book> foundBooks = libraryService.searchBooks(query);
                    if (foundBooks.isEmpty()) {
                        System.out.println("Книги не найдены.");
                    } else {
                        System.out.println("Найденные книги:");
                        for (Book b : foundBooks) {
                            System.out.println(b);
                        }
                    }
                    break;

                case 7:
                    List<Book> availableBooks = libraryService.getAvailableBooks();
                    if (availableBooks.isEmpty()) {
                        System.out.println("Свободных книг нет.");
                    } else {
                        System.out.println("Свободные книги:");
                        for (Book b : availableBooks) {
                            System.out.println(b);
                        }
                    }
                    break;
                case 8:
                    List<Reader> readersWithBooks = libraryService.getReadersWithBooks();
                    if (readersWithBooks.isEmpty()) {
                        System.out.println("Нет читателей с книгами на руках.");
                    } else {
                        System.out.println("Читатели с книгами:");
                        for (Reader r : readersWithBooks) {
                            System.out.println(r);
                        }
                    }
                    break;
                case 9:
                    List<Reader> topReaders = libraryService.getTop3Readers();
                    if (topReaders.isEmpty()) {
                        System.out.println("Нет истории выдачи книг.");
                    } else {
                        System.out.println("Топ-3 читателя:");
                        for (Reader r : topReaders) {
                            System.out.println(r);
                        }
                    }
                    break;
                case 0:
                    System.out.println("Выход из программы. До свидания, Aru Seitkamal!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Неверный выбор. Попробуйте еще раз.");
            }
        }
    }
}
