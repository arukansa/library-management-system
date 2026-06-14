package service;

import model.Book;
import model.Reader;
import model.BorrowRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * @author Aru Seitkamal
 */
public class LibraryService {
    private ArrayList<Book> booksList;
    private ArrayList<Reader> readersList;
    private ArrayList<BorrowRecord> borrowRecordsList;

    private HashMap<Integer, Book> booksMap;
    private HashMap<Integer, Reader> readersMap;

    public LibraryService() {
        booksList = new ArrayList<>();
        readersList = new ArrayList<>();
        borrowRecordsList = new ArrayList<>();
        booksMap = new HashMap<>();
        readersMap = new HashMap<>();
    }

    public void addBook(Book book) {
        booksList.add(book);
        booksMap.put(book.getId(), book);
    }

    public void addReader(Reader reader) {
        readersList.add(reader);
        readersMap.put(reader.getId(), reader);
    }

    public void borrowBook(int readerId, int bookId) {
        if (!readersMap.containsKey(readerId)) {
            System.out.println("Ошибка: Читатель с таким ID не найден (Aru Seitkamal - Admin).");
            return;
        }
        if (!booksMap.containsKey(bookId)) {
            System.out.println("Ошибка: Книга с таким ID не найдена.");
            return;
        }

        Book book = booksMap.get(bookId);
        if (!book.isAvailable()) {
            System.out.println("Ошибка: Книга сейчас недоступна (уже выдана).");
            return;
        }

        book.setAvailable(false);
        BorrowRecord record = new BorrowRecord(bookId, readerId, LocalDate.now().toString(), null);
        borrowRecordsList.add(record);
        System.out.println("Книга успешно выдана читателю.");
    }

    public void returnBook(int readerId, int bookId) {
        for (BorrowRecord record : borrowRecordsList) {
            if (record.getReaderId() == readerId && record.getBookId() == bookId && record.getReturnDate() == null) {
                record.setReturnDate(LocalDate.now().toString());
                Book book = booksMap.get(bookId);
                if (book != null) {
                    book.setAvailable(true);
                }
                System.out.println("Книга успешно возвращена.");
                return;
            }
        }
        System.out.println("Ошибка: Нет активной записи о выдаче этой книги данному читателю.");
    }

    public List<Book> getReaderBooks(int readerId) {
        List<Book> readerBooks = new ArrayList<>();
        for (BorrowRecord record : borrowRecordsList) {
            if (record.getReaderId() == readerId && record.getReturnDate() == null) {
                Book book = booksMap.get(record.getBookId());
                if (book != null) {
                    readerBooks.add(book);
                }
            }
        }
        return readerBooks;
    }

    // Аналитика и поиск
    public List<Book> searchBooks(String query) {
        String lowerCaseQuery = query.toLowerCase();
        return booksList.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(lowerCaseQuery) || 
                             b.getAuthor().toLowerCase().contains(lowerCaseQuery))
                .collect(Collectors.toList());
    }

    public List<Book> getAvailableBooks() {
        return booksList.stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }

    public List<Reader> getReadersWithBooks() {
        List<Integer> readerIdsWithBooks = borrowRecordsList.stream()
                .filter(r -> r.getReturnDate() == null)
                .map(BorrowRecord::getReaderId)
                .distinct()
                .collect(Collectors.toList());
        
        return readerIdsWithBooks.stream()
                .map(readersMap::get)
                .collect(Collectors.toList());
    }

    public List<Reader> getTop3Readers() {
        Map<Integer, Long> readerBorrowCounts = borrowRecordsList.stream()
                .collect(Collectors.groupingBy(BorrowRecord::getReaderId, Collectors.counting()));

        return readerBorrowCounts.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(3)
                .map(e -> readersMap.get(e.getKey()))
                .collect(Collectors.toList());
    }
}
