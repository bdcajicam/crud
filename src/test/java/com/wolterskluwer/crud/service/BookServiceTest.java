package com.wolterskluwer.crud.service;

import com.wolterskluwer.crud.model.Book;
import com.wolterskluwer.crud.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private static final Integer BOOK_ID = 1;
    private static final Integer AUTHOR_ID = 1;
    private static final Book book = new Book();
    private static final List<Book> books = Collections.singletonList(book);

    @BeforeEach
    void setUp() {
        book.setId(BOOK_ID);
    }

    @Test
    void bookAlreadyExists() {
        when(bookRepository.existsById(anyInt())).thenReturn(true);
        assertTrue(bookService.createBook(book).isEmpty());
    }

    @Test
    void bookIsCreated() {
        when(bookRepository.existsById(anyInt())).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        Optional<Book> optionalBook = bookService.createBook(book);
        assertTrue(optionalBook.isPresent());
        assertEquals(book, optionalBook.get());
    }

    @Test
    void bookByIdNotFound() {
        when(bookRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertTrue(bookService.getBookById(BOOK_ID).isEmpty());
    }

    @Test
    void bookByIdFound() {
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));
        Optional<Book> optionalBook = bookService.getBookById(BOOK_ID);
        assertTrue(optionalBook.isPresent());
        assertEquals(book, optionalBook.get());
    }

    @Test
    void getsAllBooks() {
        when(bookRepository.findAll()).thenReturn(books);
        assertFalse(bookService.getAllBooks().isEmpty());
    }

    @Test
    void getsBooksByAuthorSuccessfully() {
        when(bookRepository.findByAuthorId(anyInt())).thenReturn(books);
        assertFalse(bookService.getBooksByAuthorId(AUTHOR_ID).isEmpty());
    }

    @Test
    void bookDoesNotExistWhileUpdatingBook() {
        Optional<Book> optionalBook = bookService.updateBook(book);
        assertTrue(optionalBook.isEmpty());
    }

    @Test
    void bookUpdateSuccessful() {
        when(bookRepository.existsById(anyInt())).thenReturn(true);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        Optional<Book> optionalBook = bookService.updateBook(book);
        assertTrue(optionalBook.isPresent());
        assertEquals(book, optionalBook.get());
    }

    @Test
    void bookDeletionSuccessful() {
        bookService.deleteBook(BOOK_ID);
        verify(bookRepository, times(1)).deleteById(anyInt());
    }

}