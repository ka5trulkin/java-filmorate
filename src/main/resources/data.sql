-- Add mpa
MERGE INTO MPA KEY(ID) VALUES(1, 'G');

MERGE INTO MPA KEY(ID) VALUES(2, 'PG');

MERGE INTO MPA KEY(ID) VALUES(3, 'PG-13');

MERGE INTO MPA KEY(ID) VALUES(4, 'R');

MERGE INTO MPA KEY(ID) VALUES(5, 'NC-17');

-- Add genre
MERGE INTO GENRE KEY(ID) VALUES(1, 'Комедия');

MERGE INTO GENRE KEY(ID) VALUES(2, 'Драма');

MERGE INTO GENRE KEY(ID) VALUES(3, 'Мультфильм');

MERGE INTO GENRE KEY(ID) VALUES(4, 'Триллер');

MERGE INTO GENRE KEY(ID) VALUES(5, 'Документальный');

MERGE INTO GENRE KEY(ID) VALUES(6, 'Боевик');