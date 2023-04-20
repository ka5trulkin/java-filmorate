CREATE TABLE IF NOT EXISTS FILM_DB (
	ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	NAME CHARACTER VARYING(70) NOT NULL,
	DESCRIPTION CHARACTER VARYING(255) NOT NULL,
	RELEASE_DATE DATE NOT NULL,
	DURATION INTEGER NOT NULL,
	RATE INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS MPA (
	ID SMALLINT NOT NULL PRIMARY KEY,
	NAME CHARACTER VARYING(8) NOT NULL
);

CREATE TABLE IF NOT EXISTS GENRE (
	ID SMALLINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	NAME CHARACTER VARYING(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS FILM_GENRE (
	FILM_ID BIGINT NOT NULL REFERENCES FILM_DB(ID) ON DELETE CASCADE ON UPDATE CASCADE,
	GENRE_ID SMALLINT NOT NULL REFERENCES GENRE(ID) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT FILM_GENRE_PK PRIMARY KEY (FILM_ID,GENRE_ID)
);

CREATE TABLE IF NOT EXISTS FILM_MPA (
	FILM_ID BIGINT NOT NULL REFERENCES FILM_DB(ID) ON DELETE CASCADE ON UPDATE CASCADE,
	MPA_ID SMALLINT NOT NULL REFERENCES MPA(ID) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT FILM_MPA_PK PRIMARY KEY (FILM_ID,MPA_ID)
);

CREATE TABLE IF NOT EXISTS USER_DB (
	ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	NAME CHARACTER VARYING(70) NOT NULL,
	EMAIL CHARACTER VARYING(70) NOT NULL,
	LOGIN CHARACTER VARYING(70) NOT NULL,
	BIRTHDAY DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS FRIENDS (
	FRIEND_ONE BIGINT NOT NULL REFERENCES USER_DB(ID) ON DELETE CASCADE ON UPDATE CASCADE,
	FRIEND_TWO BIGINT NOT NULL REFERENCES USER_DB(ID) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT FRIENDS_PK PRIMARY KEY (FRIEND_TWO,FRIEND_ONE)
);

CREATE TABLE IF NOT EXISTS LIKES (
	FILM_ID BIGINT NOT NULL REFERENCES FILM_DB(ID) ON DELETE CASCADE ON UPDATE CASCADE,
	USER_ID BIGINT NOT NULL REFERENCES USER_DB(ID) ON DELETE CASCADE ON UPDATE CASCADE,
	LIKED BOOLEAN DEFAULT TRUE NOT NULL,
	CONSTRAINT LIKES_PK PRIMARY KEY (FILM_ID,USER_ID)
);