USE [5D1Equipe04]
GO 

DROP TABLE IF EXISTS Likes
GO
DROP TABLE IF EXISTS Courses
GO
DROP TABLE IF EXISTS Abonnements
GO
DROP TABLE IF EXISTS Utilisateurs
GO


-- Creation des tables
CREATE TABLE Utilisateurs (
	id INT PRIMARY KEY IDENTITY(1, 1),
	nom_utilisateur VARCHAR(255),
	nom VARCHAR(255),
	email VARCHAR(255),
	password VARCHAR(255)
)
GO

CREATE TABLE Abonnements (
	id_utilisateur_suivi INT FOREIGN KEY REFERENCES Utilisateurs(id),
	id_utilisateur_suivant INT FOREIGN KEY REFERENCES Utilisateurs(id)
)
GO

CREATE TABLE Courses (
	id INT PRIMARY KEY IDENTITY(1, 1),
	id_utilisateur INT FOREIGN KEY REFERENCES Utilisateurs(id),
	distance FLOAT,
	duree TIME,
)
GO

CREATE TABLE Likes (
	id_course INT FOREIGN KEY REFERENCES Courses(id),
	id_utilisateur INT FOREIGN KEY REFERENCES Utilisateurs(id)
)
GO




-- Insertion de donnees dans les tables
INSERT INTO Utilisateurs VALUES 
	('marc1234', 'Marc Charlebois', 'eric@gmail.com', 'password123'),
	('eric1234', 'Eric Larivee', 'eric@gmail.com', 'password123'),
	('antoine1234', 'Antoine Lauzon', 'antoine@gmail.com', 'password123'),
	('test1234', 'Hello World', 'test@gmail.com', 'password123')
GO

INSERT INTO Abonnements VALUES 
	(1, 2),
	(1, 3),
	(1, 4),
	(2, 1),
	(2, 3),
	(3, 1),
	(4, 1),
	(4, 2)
GO

INSERT INTO Courses VALUES
	(1, 5.54, '00:25:43'),
	(1, 4.32, '00:15:43'),
	(2, 5.54, '00:25:43'),
	(2, 5.54, '00:25:43'),
	(2, 5.10, '00:25:43'),
	(3, 5.54, '00:22:43'),
	(3, 5.54, '00:25:43'),
	(3, 5.54, '00:30:43'),
	(4, 5.54, '00:25:43'),
	(4, 5.54, '00:25:43'),
	(4, 3.54, '00:25:43'),
	(4, 2.01, '00:25:43')
GO

INSERT INTO Likes VALUES 
	(1, 2),
	(1, 3),
	(2, 1),
	(3, 1),
	(4, 3),
	(5, 1),
	(5, 4),
	(6, 2)
GO