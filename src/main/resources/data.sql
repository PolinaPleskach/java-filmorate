SET REFERENTIAL_INTEGRITY FALSE;

TRUNCATE TABLE FILMS_GENRES;

TRUNCATE TABLE FRIENDS;

TRUNCATE TABLE LIKES;

TRUNCATE TABLE FILMS RESTART IDENTITY;

TRUNCATE TABLE USERS RESTART IDENTITY;

TRUNCATE TABLE GENRES RESTART IDENTITY;

TRUNCATE TABLE RATINGS RESTART IDENTITY;

TRUNCATE TABLE STATUSES RESTART IDENTITY;

SET REFERENTIAL_INTEGRITY TRUE;

INSERT INTO RATINGS (NAME, DESCRIPTION)
VALUES ('G',      'У фильма нет возрастных ограничений.'), 										-- 1
		('PG',    'Детям рекомендуется смотреть фильм с родителями.'),							-- 2
		('PG-13', 'Детям до 13 лет просмотр не желателен.'), 									-- 3
		('R',     'Лицам до 17 лет просматривать фильм можно только в присутствии взрослого.'),  -- 4
		('NC-17', 'Лицам до 18 лет просмотр запрещён.'); 										-- 5

INSERT INTO GENRES (NAME)
VALUES ('Комедия'), 		-- 1
		('Драма'), 			-- 2
		('Мультфильм'),		-- 3
		('Триллер'),		-- 4
		('Документальный'), -- 5
		('Боевик');		    -- 6

INSERT INTO STATUSES (NAME)
VALUES ('Подтвержденная'), 		-- 1
		('Неподтвержденная'); 	-- 2

INSERT INTO FILMS (NAME , DESCRIPTION , RELEASEDATE , DURATION , RATING_ID)
VALUES ('Однажды в сказке', 'Серия 1', '2010-10-02', 43 , 1),
		('Блондинка в законе', 'Часть 1', '2005-11-02', 120 , 2),
		('Гарри Поттер и Тайная комната', 'История про мальчика, который выжил.', '2000-11-02', 123 , 3),
		('Пурпурные сердца', 'История любви', '2014-09-08', 145 , 4);

INSERT INTO USERS (EMAIL , LOGIN , NAME , BIRTHDAY)
VALUES ('plskwtzkk@yandex.ru', 'vasilek', 'Polina', '2005-04-06'), 	-- 1
		('zubenkov@yandex.ru', 'Nikita123', 'Nikita', '2003-01-20'), 			-- 2
		('vasilek@yandex.ru', 'cat', 'kotik', '2023-11-01'); 	-- 3

INSERT INTO LIKES (FILM_ID, USER_ID)
VALUES (1, 1), (1, 3),
		(2, 1), (2, 2), (2, 3),
		(3, 2),
		(4, 1), (4, 2), (4, 3);

INSERT INTO FRIENDS (USER_ID, FRIEND_ID, STATUS_ID)
VALUES (1, 2, 1), (1, 3, 1),
		(2, 1, 2),
		(3, 1, 2), (3, 2, 1);

INSERT INTO FILMS_GENRES (FILM_ID , GENRE_ID)
VALUES (1, 2), (1, 4), (1, 6),
		(2, 2), (2, 4), (2, 6),
		(3, 1), (3, 2), (3, 4),
		(4, 1), (4, 3), (4, 4), (4, 6);
