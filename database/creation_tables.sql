USE [5D1Equipe04]
GO 

DROP TABLE IF EXISTS likes
GO
DROP TABLE IF EXISTS courses
GO
DROP TABLE IF EXISTS abonnements
GO
DROP TABLE IF EXISTS utilisateurs
GO


-- Creation des tables
CREATE TABLE utilisateurs (
	id INT PRIMARY KEY IDENTITY(1, 1),
	nom_utilisateur VARCHAR(255),
	nom VARCHAR(255),
	email VARCHAR(255),
	password VARCHAR(255)
)
GO

CREATE TABLE abonnements (
	id_utilisateur_suivi INT 
		FOREIGN KEY REFERENCES utilisateurs(id),
	id_utilisateur_suivant INT 
		FOREIGN KEY REFERENCES utilisateurs(id)
)
GO

CREATE TABLE courses (
	id INT PRIMARY KEY IDENTITY(1, 1),
	id_utilisateur INT 
		FOREIGN KEY REFERENCES utilisateurs(id)
		ON DELETE CASCADE,
	distance FLOAT,
	duree TIME,
	date DATE
)
GO

CREATE TABLE likes (
	id_course INT 
		FOREIGN KEY REFERENCES courses(id),
		-- ON DELETE CASCADE,
	id_utilisateur INT 
		FOREIGN KEY REFERENCES utilisateurs(id),
		-- ON DELETE CASCADE
)
GO




-- Insertion de donnees dans les tables
INSERT INTO utilisateurs VALUES 
	('marc1234', 'Marc Charlebois', 'eric@gmail.com', 'password123'),
	('eric1234', 'Eric Larivee', 'eric@gmail.com', 'password123'),
	('antoine1234', 'Antoine Lauzon', 'antoine@gmail.com', 'password123'),
	('test1234', 'Hello World', 'test@gmail.com', 'password123')
GO

INSERT INTO abonnements VALUES 
	(1, 2),
	(1, 3),
	(1, 4),
	(2, 1),
	(2, 3),
	(3, 1),
	(4, 1),
	(4, 2)
GO

INSERT INTO courses VALUES
	(1, 5.54, '00:25:43', '2022-01-24'),
	(1, 4.32, '00:15:43', '2022-02-25'),
	(2, 5.54, '00:25:43', '2022-03-15'),
	(2, 5.54, '00:25:43', '2022-02-23'),
	(2, 5.10, '00:25:43', '2022-03-05'),
	(3, 5.54, '00:22:43', '2022-02-23'),
	(3, 5.54, '00:25:43', '2022-02-15'),
	(3, 5.54, '00:30:43', '2022-03-27'),
	(4, 5.54, '00:25:43', '2022-04-19'),
	(4, 5.54, '00:25:43', '2022-06-25'),
	(4, 3.54, '00:25:43', '2022-05-15'),
	(4, 2.01, '00:25:43', '2022-05-05')
GO

INSERT INTO likes VALUES 
	(1, 2),
	(1, 3),
	(2, 1),
	(3, 1),
	(4, 3),
	(5, 1),
	(5, 4),
	(6, 2)
GO