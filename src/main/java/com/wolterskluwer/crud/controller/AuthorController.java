package com.wolterskluwer.crud.controller;

import com.wolterskluwer.crud.model.Author;
import com.wolterskluwer.crud.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/author")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Optional<Author> optionalAuthor = authorService.createAuthor(author);
        if (optionalAuthor.isEmpty())
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Author with id %d already exist", author.getId()));
        return new ResponseEntity<>(optionalAuthor.get(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        return new ResponseEntity<>(authorService.getAllAuthors(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Integer id) {
        Optional<Author> author = authorService.getAuthorById(id);
        if (author.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Author with id %d not found", id));
        return new ResponseEntity<>(author.get(), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/{email}")
    public ResponseEntity<Author> updateEmail(@PathVariable Integer id, @PathVariable String email) {
        Optional<Author> author = authorService.updateEmail(id, email);
        if (author.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Author with id %d not found", id));
        return new ResponseEntity<>(author.get(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Author> updateAuthor(@RequestBody Author newAuthor) {
        Optional<Author> author = authorService.updateAuthor(newAuthor);
        if (author.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Author with id %d not found", newAuthor.getId()));
        return new ResponseEntity<>(author.get(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Integer id) {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
