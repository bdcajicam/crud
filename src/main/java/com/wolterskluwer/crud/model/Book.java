package com.wolterskluwer.crud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @Column(name = "ID")
    private Integer id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    //If you want to hide the whole data of the author uncomment the next line
    //@JsonIgnore
    private Author author;

    @Column(name = "AUTHOR_ID")
    private Integer authorId;

}
