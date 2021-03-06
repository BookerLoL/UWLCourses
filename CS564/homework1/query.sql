--query 1 for homework 1
SELECT name FROM actors NATURAL JOIN movies_actors NATURAL JOIN movies WHERE title LIKE '%Star Wars%' GROUP BY actor_id;

-- query 2 for homework 1
SELECT title FROM movies NATURAL JOIN movies_actors NATURAL JOIN actors WHERE name LIKE '%Marx' GROUP BY movie_id;