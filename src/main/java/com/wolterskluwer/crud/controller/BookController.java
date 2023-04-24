package com.wolterskluwer.crud.controller;

import com.wolterskluwer.crud.model.Author;
import com.wolterskluwer.crud.model.Book;
import com.wolterskluwer.crud.service.AuthorService;
import com.wolterskluwer.crud.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/book")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Optional<Author> optionalAuthor = authorService.getAuthorById(book.getAuthorId());
        if (optionalAuthor.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Author with id %d doesn't exist", book.getAuthorId()));
        Optional<Book> optionalBook = bookService.createBook(book);
        if (optionalBook.isEmpty())
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Book with id %d already exist", book.getId()));
        return new ResponseEntity<>(optionalBook.get(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
        Optional<Book> optionalBook = bookService.getBookById(id);
        if (optionalBook.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Book with id %d doesn't exist", id));
        return new ResponseEntity<>(optionalBook.get(), HttpStatus.OK);
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Book>> getBooksByAuthorId(@PathVariable Integer authorId) {
        return new ResponseEntity<>(bookService.getBooksByAuthorId(authorId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        Optional<Author> optionalAuthor = authorService.getAuthorById(book.getAuthorId());
        if (optionalAuthor.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Author with id %d doesn't exist", book.getAuthorId()));
        Optional<Book> optionalBook = bookService.updateBook(book);
        if (optionalBook.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Book with id %d doesn't exist", book.getId()));
        return new ResponseEntity<>(optionalBook.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
