package com.wolterskluwer.crud.controller;

import com.wolterskluwer.crud.model.Author;
import com.wolterskluwer.crud.service.AuthorService;
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
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {

    @Mock
    private AuthorService authorService;
    private AuthorController authorController;

    private static final Author author = new Author();
    private static final List<Author> authors = Collections.singletonList(author);

    private static final Integer ID = 1;
    private static final String EMAIL = "example@wolterskluwer.com";

    @BeforeEach
    void setUp() {
        authorController = new AuthorController(authorService);
    }

    @Test
    void authorAlreadyExists() {
        when(authorService.createAuthor(any(Author.class))).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> authorController.createAuthor(author));
    }

    @Test
    void authorCreated() {
        when(authorService.createAuthor(any(Author.class))).thenReturn(Optional.of(author));
        ResponseEntity<Author> responseEntity = authorController.createAuthor(author);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(author, responseEntity.getBody());
    }

    @Test
    void getsAllAuthors() {
        when(authorService.getAllAuthors()).thenReturn(authors);
        ResponseEntity<List<Author>> responseEntity = authorController.getAllAuthors();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(authors, responseEntity.getBody());
    }

    @Test
    void authorByIdNotFound() {
        when(authorService.getAuthorById(anyInt())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> authorController.getAuthorById(ID));
    }

    @Test
    void authorFoundById() {
        when(authorService.getAuthorById(anyInt())).thenReturn(Optional.of(author));
        ResponseEntity<Author> responseEntity = authorController.getAuthorById(ID);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(author, responseEntity.getBody());
    }

    @Test
    void authorDoesNotExistWhileUpdatingEmail() {
        when(authorService.updateEmail(anyInt(), anyString())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> authorController.updateEmail(ID, EMAIL));
    }

    @Test
    void emailUpdateSuccessful() {
        author.setEmail(EMAIL);
        when(authorService.updateEmail(anyInt(), anyString())).thenReturn(Optional.of(author));
        ResponseEntity<Author> responseEntity = authorController.updateEmail(ID, EMAIL);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(EMAIL, Objects.requireNonNull(responseEntity.getBody()).getEmail());
    }

    @Test
    void authorDoesNotExistWhileUpdatingAuthor() {
        when(authorService.updateAuthor(any(Author.class))).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> authorController.updateAuthor(author));
    }

    @Test
    void authorUpdateSuccessful() {
        when(authorService.updateAuthor(any(Author.class))).thenReturn(Optional.of(author));
        ResponseEntity<Author> responseEntity = authorController.updateAuthor(author);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(author, responseEntity.getBody());
    }

    @Test
    void authorDeletionSuccessful() {
        authorController.deleteAuthor(ID);
        verify(authorService, times(1)).deleteAuthor(anyInt());
    }

}