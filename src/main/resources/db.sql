CREATE TABLE author
(
  id number(10),
  first_name varchar2(50),
  last_name varchar2(50),
  e_mail varchar2(50),
  CONSTRAINT author_pk PRIMARY KEY (id)
);
/
CREATE TABLE book
(
	id number(10),
	title varchar2(50),
	author_id number(10),
	CONSTRAINT fk_author
    FOREIGN KEY (author_id)
    REFERENCES author(id)
);
/
INSERT INTO author (id, first_name, last_name, e_mail) VALUES (1, 'Stephen', 'King', 'sking@gmail.com');
/
INSERT INTO author (id, first_name, last_name, e_mail) VALUES (2, 'Gabriel', 'García', 'ggarcia@gmail.com');
/
INSERT INTO book (id, title, author_id) VALUES (1, 'Carrie', 1);
/
INSERT INTO book (id, title, author_id) VALUES (2, 'Misery', 1);
/
INSERT INTO book (id, title, author_id) VALUES (3, 'It', 1);
/
INSERT INTO book (id, title, author_id) VALUES (4, 'One hundred years of solitude', 2);
/
INSERT INTO book (id, title, author_id) VALUES (5, 'Love in the Time of Cholera', 2);
/
INSERT INTO book (id, title, author_id) VALUES (6, 'Of Love and Other Demons', 2);
/