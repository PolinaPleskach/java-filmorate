# java-filmorate
Template repository for Filmorate project.
## ER-диаграмма

![Схема базы данных проекта Filmorate](ER-diagram.png)

<details><summary><strong><span style="font-size: 20px;">Пояснения к таблицам БД</span></strong></summary>

### films
- Информация о фильмах

### ratings
- Информация о рейтингах фильмов

### film_genres
- Соединительная таблица для фильмов и их жанров

### genres
- Информация о жанрах фильмов

### users
- Информация о пользователях приложения

### likes
- Соединительная таблица для фильмов и пользователях, оценивших фильм

### friends
- Соединительная таблица для пользователей и их друзей (других пользователей)

### statuses
- Информация о статусе запроса "дружбы" между пользователями

<details><summary><strong><span style="font-size: 20px;">Примеры SQL-запросов для модели Film</span></strong></summary>

### 1. Добавить фильм
#### create(Film film)
```sql
INSERT INTO films(name,
                  description,
                  releaseDate,
                  duration,
                  rating_id)
VALUES ({film.getName()}, 
       {film.getDescription()}, 
       {film.getReleaseDate()}, 
       {film.getDuration()},
       {film.getRating()});
```

### 2. Обновить фильм
#### update(Film film)
```sql
UPDATE Film 
SET name = {film.getName()}, 
    description = {film.getDescription()}, 
    releaseDate = {film.getReleaseDate()}, 
    duration = {film.getDuration()}, 
    rating = {film.getRating()}, 
WHERE id = {film.getId()};
```

### 3. Найти фильм по id
#### findFilm(Long filmId)
```sql
SELECT * 
FROM films 
WHERE id = {filmId};
```

### 4. Получить список всех фильмов
#### Collection<Film> findAll()
```sql
SELECT * 
FROM films;
```

### 5. Удалить фильм
#### delete(Long filmId)
```sql
DELETE FROM films 
WHERE id = {filmId};
```

### 6. Поставить лайк
#### addLike(Film film, User user)
```sql
INSERT INTO likes(film_id, user_id) 
VALUES ({film.getId()}, {user.getId()});
```

### 7. Удалить лайк
#### deleteLike(Film film, User user)
```sql
DELETE FROM likes 
WHERE film_id = {film.getId()} AND user_id = {user.getId()};
```

### 8. Отобразить популярные фильмы
#### findPopular(Integer count)
```sql
SELECT f.* FROM films AS f  
LEFT JOIN (SELECT film_id, count(l.user_id) likes
           FROM likes AS l  
           GROUP BY l.film_id
           ORDER BY count(l.user_id) DESC 
           LIMIT {count})
AS liked_films ON f.id = liked_films.film_id  
ORDER BY liked_films.likes DESC;
```

### 9. Поиск фильмов по режиссёру
#### findDirectorFilms(Long directorId)
```sql
SELECT f.* FROM films_directors AS fd 
LEFT JOIN films AS f ON fd.film_id = f.id
WHERE fd.director_id = {directorId};
```

### 10. Получить количество лайков у фильма
#### getLikes(Long filmId)
```sql
SELECT user_id FROM likes
WHERE film_id = {filmId};
```
</details>

<details><summary><strong><span style="font-size: 20px;">Примеры SQL-запросов для модели User</span></strong></summary>

### 1. Добавить пользователя
#### create(User user)
```sql
INSERT INTO users(email, login, name, birthday)
VALUES ({user.getEmail()},
        {user.getLogin()},
        {user.getName()},
        {user.getBirthday()});
```

### 2. Обновить пользователя
#### update(User newUser)
```sql
UPDATE users
SET email = {newUser.getEmail()},
    login = {newUser.getLogin()},
    name = {newUser.getName()},
    birthday = {newUser.getBirthday()}
WHERE id = newUser.getId();
```

### 3. Найти пользователя по id
#### findUser(Long userId)
```sql
SELECT *
FROM users WHERE id = {userId};
```

### 4. Получить список пользователей
#### getUsers()
```sql
SELECT u.id, u.email, u.login, u.name, u.birthday, GROUP_CONCAT(f.friend_id) AS friends
FROM users u  
LEFT JOIN friends f ON u.id = f.user_id
LEFT JOIN statuses AS s ON f.status_id = s.id AND s.name = 'Подтверждённая'
GROUP BY u.id, u.email, u.login, u.name, u.birthday;
```

### 5. Удалить пользователя
#### delete(Long id)
```sql
DELETE FROM users
WHERE id = {id};
```

### 6. Добавить пользователя в друзья
#### addFriend(Long userId, Long friendId)
```sql
INSERT INTO friends(user_id, friend_id, status_id)
VALUES ({user_id}, {friend_id}, {status_id});
```

### 7. Удалить пользователя из друзей
#### deleteFriend(Long userId, Long friendId)
```sql
DELETE FROM friends
WHERE user_id = {userId} AND friend_id = {friendId};
```
</details>

<details><summary><strong><span style="font-size: 20px;">Примеры SQL-запросов для модели Genre</span></strong></summary>

### 1. Добавить жанр
#### create(Genre genre)
```sql
INSERT INTO genres(name)
VALUES ({genre.getName()});
```

### 2. Найти жанр по id
#### findGenre(Long genreId)
```sql
SELECT *
FROM genres
WHERE id = genreId;
```

### 3. Получить список всех жанров
#### findAll()
```sql
SELECT *
FROM genres;
```
</details>

<details><summary><strong><span style="font-size: 20px;">Примеры SQL-запросов для модели Mpa</span></strong></summary>

### 1. Добавить рейтинг
#### create(Mpa mpa)
```sql
INSERT INTO ratings(name, description)
VALUES ({mpa.getName()},
        {mpa.getDescription()});
```

### 2. Найти рейтинг по id
#### findMpa(Long mpaId)
```sql
SELECT * FROM ratings
WHERE id = {mpaId};
```

### 3. Получить список всех рейтингов
#### findAll()
```sql
SELECT *
FROM ratings;
