DROP TABLE IF EXISTS AUTHOR;

CREATE TABLE AUTHOR (
    id int PRIMARY KEY,
    first_name VARCHAR(250),
    last_name VARCHAR(250),
    e_mail VARCHAR(250)
);

DROP TABLE IF EXISTS BOOK;

CREATE TABLE BOOK (
    id int PRIMARY KEY,
    title VARCHAR(250),
    author_id VARCHAR(250),
    foreign key (author_id) references AUTHOR(id)
);