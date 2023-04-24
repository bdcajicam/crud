package com.wolterskluwer.crud.service;

import com.wolterskluwer.crud.model.Author;
import com.wolterskluwer.crud.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;
    private AuthorService authorService;
    
    private static final Author author = new Author();
    private static final List<Author> authors = Collections.singletonList(author);
    private static final Integer ID = 1;
    private static final String EMAIL = "example@wolterskluwer.com";

    @BeforeEach
    void setUp() {
        author.setId(ID);
        authorService = new AuthorService(authorRepository);
    }

    @Test
    void authorAlreadyExists() {
        when(authorRepository.existsById(anyInt())).thenReturn(true);
        assertTrue(authorService.createAuthor(author).isEmpty());
    }

    @Test
    void authorCreated() {
        when(authorRepository.existsById(anyInt())).thenReturn(false);
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        Optional<Author> optionalAuthor = authorService.createAuthor(author);
        assertTrue(optionalAuthor.isPresent());
        assertEquals(author, optionalAuthor.get());
    }

    @Test
    void authorByIdNotFound() {
        when(authorRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertTrue(authorService.getAuthorById(ID).isEmpty());
    }

    @Test
    void authorByIdFound() {
        when(authorRepository.findById(anyInt())).thenReturn(Optional.of(author));
        Optional<Author> optionalAuthor = authorService.getAuthorById(1);
        assertTrue(optionalAuthor.isPresent());
        assertEquals(author, optionalAuthor.get());
    }

    @Test
    void getsAllAuthors() {
        when(authorRepository.findAll()).thenReturn(authors);
        assertFalse(authorService.getAllAuthors().isEmpty());
    }

    @Test
    void authorDoesNotExistWhileUpdatingEmail() {
        Optional<Author> optionalAuthor = authorService.updateEmail(ID, EMAIL);
        assertTrue(optionalAuthor.isEmpty());
    }

    @Test
    void emailUpdateSuccessful() {
        when(authorRepository.findById(anyInt())).thenReturn(Optional.of(author));
        author.setEmail(EMAIL);
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        Optional<Author> optionalAuthor = authorService.updateEmail(ID, EMAIL);
        assertTrue(optionalAuthor.isPresent());
        assertEquals(EMAIL, optionalAuthor.get().getEmail());
    }

    @Test
    void authorDoesNotExistWhileUpdatingAuthor() {
        Optional<Author> optionalAuthor = authorService.updateAuthor(author);
        assertTrue(optionalAuthor.isEmpty());
    }

    @Test
    void authorUpdateSuccessful() {
        when(authorRepository.existsById(anyInt())).thenReturn(true);
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        Optional<Author> optionalAuthor = authorService.updateAuthor(author);
        assertTrue(optionalAuthor.isPresent());
        assertEquals(author, optionalAuthor.get());
    }

    @Test
    void authorDeletionSuccessful() {
       authorService.deleteAuthor(ID);
       verify(authorRepository, times(1)).deleteById(anyInt());
    }

}