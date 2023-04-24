package com.wolterskluwer.crud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "E_MAIL")
    private String email;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Book> books;

}
