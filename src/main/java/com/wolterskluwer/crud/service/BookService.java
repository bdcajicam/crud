package com.wolterskluwer.crud.service;

import com.wolterskluwer.crud.model.Book;
import com.wolterskluwer.crud.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<Book> createBook(Book book) {
        if (bookRepository.existsById(book.getId()))
            return Optional.empty();
        return Optional.of(bookRepository.save(book));
    }

    public Optional<Book> getBookById(Integer id) {
        return bookRepository.findById(id);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByAuthorId(Integer authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    public Optional<Book> updateBook(Book book) {
        if (!bookRepository.existsById(book.getId()))
            return Optional.empty();
        return Optional.of(bookRepository.save(book));
    }

    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }

}
