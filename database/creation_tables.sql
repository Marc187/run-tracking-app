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
		FOREIGN KEY REFERENCES utilisateurs(id),
		-- ON DELETE CASCADE,
	img varbinary(max),
    timeStamps VARCHAR(255),
    avgSpeedInKMH Float,
    distanceInMeters Int,
    timeInMillis BIGINT,
    caloriesBurned Int
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
	('test1234', 'Hello World', 'test@gmail.com', 'password123'),
	('e1234567', 'test', 'e1234567@site.com', '$2a$10$MZK.TGjL2tFtH5O9dMG04eEKY439bacpikpj2DedO.H80kFHGOAEK')
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

	id INT PRIMARY KEY IDENTITY(1, 1),
	id_utilisateur INT 
		FOREIGN KEY REFERENCES utilisateurs(id),
		-- ON DELETE CASCADE,
	img varbinary(max),
    timeStamps VARCHAR(255),
    avgSpeedInKMH Float,
    distanceInMeters Int,
    timeInMillis BIGINT,
    caloriesBurned Int
INSERT INTO courses VALUES
	(5, '[B@2412dde', '2002-9-10', 5.8, 170, 5804, 123)
	(5, '[B@2412dde', '2002-9-5', 5.8, 75, 0175, 65)
	(5, '[B@2412dde', '2002-9-18', 5.8, 16, 9451, 53)
	(5, '[B@2412dde', '2002-10-21', 5.8, 380, 5804, 123)
	(5, '[B@2412dde', '2002-10-18', 5.8, 83, 0175, 65)
	(5, '[B@2412dde', '2002-10-28', 5.8, 184, 9451, 53)
	(5, '[B@2412dde', '2002-11-10', 5.8, 568, 8175, 123)
	(5, '[B@2412dde', '2002-11-5', 5.8, 185, 01517, 65)
	(5, '[B@2412dde', '2002-11-18', 5.8, 12, 10578, 53)
	(5, '[B@2412dde', '2002-12-21', 5.8, 601, 1851, 123)
	(5, '[B@2412dde', '2002-12-18', 5.8, 157, 50185, 65)
	(5, '[B@2412dde', '2002-12-28', 5.8, 105, 19575, 53)
GO

/*
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
*/