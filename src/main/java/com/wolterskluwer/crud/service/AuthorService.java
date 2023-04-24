package com.wolterskluwer.crud.service;

import com.wolterskluwer.crud.model.Author;
import com.wolterskluwer.crud.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Optional<Author> createAuthor(Author author) {
        if (authorRepository.existsById(author.getId()))
            return Optional.empty();
        return Optional.of(authorRepository.save(author));
    }

    public Optional<Author> getAuthorById(Integer id) {
        return authorRepository.findById(id);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> updateEmail(Integer id, String email) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isEmpty())
            return optionalAuthor;
        Author author = optionalAuthor.get();
        author.setEmail(email);
        return Optional.of(authorRepository.save(author));
    }
    public Optional<Author> updateAuthor(Author author) {
        if (!authorRepository.existsById(author.getId()))
            return Optional.empty();
        return Optional.of(authorRepository.save(author));
    }

    public void deleteAuthor(Integer id) {
        authorRepository.deleteById(id);
    }

}
