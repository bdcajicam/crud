package com.wolterskluwer.crud.controller;

import com.wolterskluwer.crud.model.Author;
import com.wolterskluwer.crud.model.Book;
import com.wolterskluwer.crud.service.AuthorService;
import com.wolterskluwer.crud.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;
    @Mock
    private AuthorService authorService;
    private BookController bookController;

    private static final Integer BOOK_ID = 1;
    private static final Integer AUTHOR_ID = 1;

    private static final Book book = new Book();
    private static final List<Book> books = Collections.singletonList(book);

    private static final Author author = new Author();

    @BeforeEach
    void setUp() {
        bookController = new BookController(bookService, authorService);
    }

    @Test
    void authorDoesNotExistsWhileCreatingBook() {
        book.setAuthorId(AUTHOR_ID);
        when(authorService.getAuthorById(anyInt())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> bookController.createBook(book));
    }

    @Test
    void bookAlreadyExistsWhileCreatingBook() {
        book.setId(BOOK_ID);
        book.setAuthorId(AUTHOR_ID);
        when(authorService.getAuthorById(anyInt())).thenReturn(Optional.of(author));
        when(bookService.createBook(book)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> bookController.createBook(book));
    }

    @Test
    void bookIsCreated() {
        book.setId(BOOK_ID);
        book.setAuthorId(AUTHOR_ID);
        when(authorService.getAuthorById(anyInt())).thenReturn(Optional.of(author));
        when(bookService.createBook(book)).thenReturn(Optional.of(book));
        ResponseEntity<Book> responseEntity = bookController.createBook(book);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(book, responseEntity.getBody());
    }

    @Test
    void getsAllBooks() {
        when(bookService.getAllBooks()).thenReturn(books);
        ResponseEntity<List<Book>> responseEntity = bookController.getAllBooks();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(books, responseEntity.getBody());
    }

    @Test
    void bookByIdNotFound() {
        when(bookService.getBookById(anyInt())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> bookController.getBookById(BOOK_ID));
    }

    @Test
    void bookByIdFound() {
        when(bookService.getBookById(anyInt())).thenReturn(Optional.of(book));
        ResponseEntity<Book> responseEntity = bookController.getBookById(BOOK_ID);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(book, responseEntity.getBody());
    }

    @Test
    void getsBooksByAuthorSuccessfully() {
        when(bookService.getBooksByAuthorId(anyInt())).thenReturn(books);
        ResponseEntity<List<Book>> responseEntity = bookController.getBooksByAuthorId(AUTHOR_ID);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(books, responseEntity.getBody());
    }

    @Test
    void authorDoesNotExistWhileUpdatingBook() {
        book.setAuthorId(AUTHOR_ID);
        when(authorService.getAuthorById(anyInt())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> bookController.updateBook(book));
    }

    @Test
    void bookDoesNotExistWhileUpdatingBook() {
        book.setAuthorId(AUTHOR_ID);
        when(authorService.getAuthorById(anyInt())).thenReturn(Optional.of(author));
        when(bookService.updateBook(any(Book.class))).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> bookController.updateBook(book));
    }

    @Test
    void bookUpdateSuccessful() {
        book.setAuthorId(AUTHOR_ID);
        when(authorService.getAuthorById(anyInt())).thenReturn(Optional.of(author));
        when(bookService.updateBook(any(Book.class))).thenReturn(Optional.of(book));
        ResponseEntity<Book> responseEntity = bookController.updateBook(book);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(book, responseEntity.getBody());
    }

    @Test
    void bookDeletionSuccessful() {
        bookController.deleteBook(BOOK_ID);
        verify(bookService, times(1)).deleteBook(anyInt());
    }

}