CREATE DATABASE "Muzica"
go 
USE "Muzica"

CREATE TABLE "Profil_Online"(
	link_profil_online varchar(250) PRIMARY KEY NOT NULL,
	nr_urmaritori int
);

CREATE TABLE "Spectacol_al_Artistului"(
	nume_artist varchar(30) NOT NULL UNIQUE,
	nume_spectacol varchar(30) NOT NULL UNIQUE,
	CONSTRAINT PK_Spectacol_al_Artistului PRIMARY KEY(nume_artist,nume_spectacol)
);

CREATE TABLE "Spectacol"(
	nume_spectacol varchar(30) PRIMARY KEY NOT NULL,
	CONSTRAINT FK_Spectacol_Spectacol_al_Artistului FOREIGN KEY(nume_spectacol) REFERENCES Spectacol_al_Artistului(nume_spectacol)
);

CREATE TABLE "Casa_de_Discuri"(
	nume varchar(30) NOT NULL PRIMARY KEY,
	nr_detinatori int
);

CREATE TABLE "Platforma"(
	nume varchar(30) PRIMARY KEY NOT NULL,
	incarcari_zilnice int
);

CREATE TABLE "Melodie"(
	Mid int IDENTITY UNIQUE,
	nume varchar(30), 
	artist varchar(30) NOT NULL,
	nume_album varchar(30) NOT NULL UNIQUE,
	subgen_muzical varchar(30) NOT NULL UNIQUE,
	platforma_online varchar(30) NOT NULL,
	CONSTRAINT PK_Melodie PRIMARY KEY(Mid, nume_album, artist, subgen_muzical),
	CONSTRAINT FK_Melodie_Platforma_incarcarii FOREIGN KEY(platforma_online) REFERENCES Platforma(nume)
);

CREATE TABLE "Artist"(
	nume varchar(30) PRIMARY KEY NOT NULL,
	nationalitatea varchar(30),
	varsta int, 
	link_profil_online varchar(250),
	nume_spectacol varchar(30),
	id_melodie int,
	CONSTRAINT FK_Artist_Profil FOREIGN KEY(link_profil_online) REFERENCES Profil_Online(link_profil_online),
	CONSTRAINT FK_Artist_Spectacol_al_Artistului FOREIGN KEY(nume) REFERENCES Spectacol_al_Artistului(nume_artist),
	CONSTRAINT FK_Artist_Melodie FOREIGN KEY(id_melodie) REFERENCES Melodie(Mid)
);

CREATE TABLE "Album"(
	Aid int IDENTITY PRIMARY KEY,
	nume varchar(30),
	casa_discuri varchar(30) NOT NULL,
	CONSTRAINT FK_Album_Casa_de_Discuri FOREIGN KEY(casa_discuri) REFERENCES Casa_de_Discuri(nume),
	CONSTRAINT FK_Album_Melodie FOREIGN KEY(nume) REFERENCES Melodie(nume_album)
); 

CREATE TABLE "Subgen_Muzical"(
	SGid int IDENTITY,
	nume_gen varchar(30) NOT NULL,
	nume varchar(30) UNIQUE, 
	CONSTRAINT PK_Subgen_Muzical PRIMARY KEY(SGid, nume),
	CONSTRAINT FK_Subgen_Melodie FOREIGN KEY(nume) REFERENCES Melodie(subgen_muzical)
);

CREATE TABLE "Gen_Muzical"(
	Gid int PRIMARY KEY IDENTITY,
	nume varchar(30),
	nume_subgen varchar(30) FOREIGN KEY REFERENCES Subgen_Muzical(nume)
);

INSERT INTO "Profil_Online"(link_profil_online,nr_urmaritori) VALUES('link_RA_raresh',90000);
INSERT INTO "Platforma"(nume,incarcari_zilnice) VALUES('Bandcamp',300000);
INSERT INTO "Casa_de_Discuri"(nume,nr_detinatori) VALUES('[a:rpia:r]',30);
INSERT INTO "Melodie"(nume,artist,nume_album,subgen_muzical,platforma_online) VALUES('Vivaltu', 'Raresh', '[a:rpia:r] 13','Minimal','Bandcamp');
INSERT INTO "Subgen_Muzical"(nume_gen,nume) VALUES ('Techno', 'Minimal');
INSERT INTO "Gen_Muzical"(nume,nume_subgen) Values ('Techno','Minimal');
INSERT INTO "Spectacol_al_Artistului"(Nume_artist,nume_spectacol) VALUES ('Raresh','Mioritmic');
INSERT INTO "Spectacol"(nume_spectacol) VALUES('Mioritmic');
INSERT INTO "Artist" (nume,nationalitatea,varsta,link_profil_online,nume_spectacol) VALUES('Raresh','Romania',36,'link_RA_raresh','Mioritmic');
INSERT INTO "Album"(nume,casa_discuri) VALUES('[a:rpia:r] 13','[a:rpia:r]');

INSERT INTO "Profil_Online"(link_profil_online,nr_urmaritori) VALUES('link_RA_Solomun',90000);
INSERT INTO "Casa_de_Discuri"(nume,nr_detinatori) VALUES('Diynamic',40);
INSERT INTO "Melodie"(nume,artist,nume_album,subgen_muzical,platforma_online) VALUES('Somebody"s Story', 'Solomun', 'Compost Black','Techno','Bandcamp');
INSERT INTO "Subgen_Muzical"(nume_gen,nume) VALUES ('Techno', 'Techno');
INSERT INTO "Gen_Muzical"(nume,nume_subgen) Values ('Techno','Techno');
INSERT INTO "Spectacol_al_Artistului"(Nume_artist,nume_spectacol) VALUES ('Solomun','Tomorrowland');
INSERT INTO "Spectacol"(nume_spectacol) VALUES('Tomorrowland');
INSERT INTO "Artist" (nume,nationalitatea,varsta,link_profil_online,nume_spectacol) VALUES('Solomun','Germania',44,'link_RA_Solomun','Tomorrowland');
INSERT INTO "Album"(nume,casa_discuri) VALUES('Compost Black','Diynamic');

INSERT INTO "Profil_Online"(link_profil_online,nr_urmaritori) VALUES('link_RA_eastenddubs',100000);
INSERT INTO "Platforma"(nume,incarcari_zilnice) VALUES('Soundcloud',300000);
INSERT INTO "Casa_de_Discuri"(nume,nr_detinatori) VALUES('Eastenderz',20);
INSERT INTO "Melodie"(nume,artist,nume_album,subgen_muzical,platforma_online) VALUES('Do it right', 'East End Dubs', 'bRave','Tech House','Soundcloud');
INSERT INTO "Subgen_Muzical"(nume_gen,nume) VALUES ('Techno', 'Tech House');
INSERT INTO "Gen_Muzical"(nume,nume_subgen) Values ('Techno','Tech House');
INSERT INTO "Spectacol_al_Artistului"(Nume_artist,nume_spectacol) VALUES ('East End Dubs','Mint Warehouse');
INSERT INTO "Spectacol"(nume_spectacol) VALUES('Mint Warehouse');
INSERT INTO "Artist" (nume,nationalitatea,varsta,link_profil_online,nume_spectacol) VALUES('East End Dubs','Anglia',37,'link_RA_eastenddubs','Mint Warehouse');
INSERT INTO "Album"(nume,casa_discuri) VALUES('bRave','Eastenderz');

INSERT INTO "Profil_Online"(link_profil_online,nr_urmaritori) VALUES('Wikipedia',6000000);
INSERT INTO "Platforma"(nume,incarcari_zilnice) VALUES('Youtube',30000000);
INSERT INTO "Casa_de_Discuri"(nume,nr_detinatori) VALUES('Spinnin Records',30);
INSERT INTO "Melodie"(nume,artist,nume_album,subgen_muzical,platforma_online) VALUES('Animals', 'Martin Garrix', 'Gold Skies','EDM','Youtube');
INSERT INTO "Subgen_Muzical"(nume_gen,nume) VALUES ('Electro', 'EDM');
INSERT INTO "Gen_Muzical"(nume,nume_subgen) Values ('Electro','EDM');
INSERT INTO "Spectacol_al_Artistului"(Nume_artist,nume_spectacol) VALUES ('Martin Garrix','Ultra');
INSERT INTO "Spectacol"(nume_spectacol) VALUES('Ultra');
INSERT INTO "Artist" (nume,nationalitatea,varsta,link_profil_online,nume_spectacol) VALUES('Martin Garrix','Olanda',24,'Wikipedia','Ultra');
INSERT INTO "Album"(nume,casa_discuri) VALUES('Gold Skies','Spinnin Records');

INSERT INTO "Profil_Online"(link_profil_online,nr_urmaritori) VALUES('Facebook',6000000);
INSERT INTO "Casa_de_Discuri"(nume,nr_detinatori) VALUES('Iboga Records',30);
INSERT INTO "Melodie"(nume,artist,nume_album,subgen_muzical,platforma_online) VALUES('Adhana', 'Vini vici', 'A state of trance classics','Psytrance','Youtube');
INSERT INTO "Subgen_Muzical"(nume_gen,nume) VALUES ('Trance', 'Psytrance');
INSERT INTO "Gen_Muzical"(nume,nume_subgen) Values ('Trance','Psytrance');
INSERT INTO "Spectacol_al_Artistului"(Nume_artist,nume_spectacol) VALUES ('Vini vici','Creamfields');
INSERT INTO "Spectacol"(nume_spectacol) VALUES('Creamfields');
INSERT INTO "Artist" (nume,nationalitatea,varsta,link_profil_online,nume_spectacol) VALUES('Vini vici','Israel',24,'Facebook','Creamfields');
INSERT INTO "Album"(nume,casa_discuri) VALUES('A state of trance classics','Iboga Records');

INSERT INTO "Casa_de_Discuri"(nume,nr_detinatori) VALUES('Armada',10);
INSERT INTO "Melodie"(nume,artist,nume_album,subgen_muzical,platforma_online) VALUES('Something Real', 'Armin van Buuren', 'Balance','Progressive Trance','Youtube');
INSERT INTO "Subgen_Muzical"(nume_gen,nume) VALUES ('Trance', 'Progressive Trance');
INSERT INTO "Gen_Muzical"(nume,nume_subgen) Values ('Trance','Progressive Trance');
INSERT INTO "Spectacol_al_Artistului"(Nume_artist,nume_spectacol) VALUES ('Armin van Buuren','Untold');
INSERT INTO "Spectacol"(nume_spectacol) VALUES('Untold');
INSERT INTO "Artist" (nume,nationalitatea,varsta,link_profil_online,nume_spectacol) VALUES('Armin van Buuren','Olanda',43,'Wikipedia','Untold');
INSERT INTO "Album"(nume,casa_discuri) VALUES('Balance','Armada');


INSERT INTO "Casa_de_Discuri"(nume,nr_detinatori) VALUES('Epic',30);
INSERT INTO "Melodie"(nume,artist,nume_album,subgen_muzical,platforma_online) VALUES('Slidin', '21 Savage', 'Savage Mode 2','Trap','Youtube');
INSERT INTO "Subgen_Muzical"(nume_gen,nume) VALUES ('Hip hop', 'Trap');
INSERT INTO "Gen_Muzical"(nume,nume_subgen) Values ('Hip hop','Trap');
INSERT INTO "Spectacol_al_Artistului"(Nume_artist,nume_spectacol) VALUES ('21 Savage','Rolling Loud');
INSERT INTO "Spectacol"(nume_spectacol) VALUES('Rolling Loud');
INSERT INTO "Artist" (nume,nationalitatea,varsta,link_profil_online,nume_spectacol) VALUES('21 Savage','SUA',36,'Wikipedia','Rolling Loud');
INSERT INTO "Album"(nume,casa_discuri) VALUES('Savage Mode 2','Epic');

INSERT INTO "Profil_Online"(link_profil_online,nr_urmaritori) VALUES('link1',10);
INSERT INTO "Profil_Online"(link_profil_online,nr_urmaritori) VALUES('link2',2000);
INSERT INTO "Profil_Online"(link_profil_online,nr_urmaritori) VALUES('link3',30);
INSERT INTO "Profil_Online"(link_profil_online,nr_urmaritori) VALUES('link4',4);

INSERT INTO "Platforma"(nume,incarcari_zilnice) VALUES('Deezer',24000);
INSERT INTO "Platforma"(nume,incarcari_zilnice) VALUES('Spotify',7800000);
INSERT INTO "Platforma"(nume,incarcari_zilnice) VALUES('AppleMusic',900000);
INSERT INTO "Platforma"(nume,incarcari_zilnice) VALUES('YoutubeMusic',6000000);

INSERT INTO "Casa_de_Discuri"(nume,nr_detinatori) VALUES('Atipic',12);
INSERT INTO "Casa_de_Discuri"(nume,nr_detinatori) VALUES('RORA',33);
INSERT INTO "Casa_de_Discuri"(nume,nr_detinatori) VALUES('Amphia',50);
INSERT INTO "Casa_de_Discuri"(nume,nr_detinatori) VALUES('Meander',110);
INSERT INTO "Casa_de_Discuri"(nume,nr_detinatori) VALUES('Arupa Music',14);
INSERT INTO "Casa_de_Discuri"(nume,nr_detinatori) VALUES('aaa',60);
INSERT INTO "Casa_de_Discuri"(nume,nr_detinatori) VALUES('bbb',60);

UPDATE Casa_de_Discuri
SET nume='aa', nr_detinatori='70'
Where Casa_de_Discuri.nume like 'bbb'

Select *
From Casa_de_Discuri 

DELETE FROM "Casa_de_Discuri"
WHERE nume in ('aaa','bbb','aa');

/*delete from "Gen_Muzical";
delete from "Subgen_Muzical";
delete from "Melodie";
delete from "Artist";
delete from "Spectacol_al_Artistului";
delete from "Spectacol";
delete from "Profil_Online";
delete from "Platforma";
delete from "Album";
delete from "Casa_de_Discuri";*/

--Fac un dublu join, intre artist si spectacolul sau, unde numele si spectacolul este acelasi, dupa care un join intre rezultat si profil online unde linkul este acelasi
--unde varsta artisului este minim 30 si nr de urmaritori al profilului depaseste 100000
SELECT *
From ((Artist a inner join Spectacol_al_Artistului s on a.nume like s.nume_artist and a.nume_spectacol=s.nume_spectacol) 
inner join Profil_Online p on a.link_profil_online like p.link_profil_online)
where a.varsta>=30 and p.nr_urmaritori>=100000;

--dublu join melodie.subgen=gen.subgen dupa care rezultatul subgen.nume_gen=gen.nume
--le iau doar pe cele cu genul techno sau electro din rezultat
SELECT DISTINCT m.subgen_muzical, g.nume
FROM ((Melodie m left outer join Gen_Muzical g on m.subgen_muzical like g.nume_subgen)
left outer join Subgen_Muzical sg on sg.nume_gen = g.nume)
WHERE g.nume in ('Techno', 'Electro');

--dublu join pe acelasi tabel
--le iau doar pe cele cu numele dat sau cu valori intre 30 si 100
SELECT c1.nume, c1.nr_detinatori
FROM ((Casa_de_Discuri c1 left outer join Casa_de_Discuri c2 on c1.nume=c2.nume) inner join Casa_de_Discuri c3 on c1.nr_detinatori =c3.nr_detinatori)
Group by c1.nume, c1.nr_detinatori
Having c1.nume in ('Atipic', 'Playedby', 'Amphia', 'RORA') OR (c1.nr_detinatori > 30 AND c1.nr_detinatori<100);

--tabele cu relatie m-n
--doblu join
--le grupez dupa nationalitate(altfel as fi avut 2 Olanda) si le aleg doar pe cele care au in nume litera o
SELECT a.nationalitatea
FROM ((Artist a right outer join Spectacol_al_Artistului sa on a.nume_spectacol like sa.nume_spectacol)  
inner join Spectacol s on sa.nume_spectacol=s.nume_spectacol)
GROUP BY a.nationalitatea
Having a.nationalitatea like '%o%';

--tabele cu relatie m-n
--doblu join
--le aleg doar pe acelea care nu am numele artistului null si numelele spectacolului sa inceapa in cu cel putin litera N (din punct de vedere lexicografic)
SELECT a.nationalitatea,a.nume_spectacol
FROM ((Artist a right outer join Spectacol_al_Artistului sa on a.nume like sa.nume_artist)  
left outer join Spectacol s on sa.nume_spectacol=s.nume_spectacol)
WHERE a.nume IS NOT NULL AND a.nume_spectacol > 'N'

--3 joinuri pe tabele
--aleg doare acele linii care respecta egalitatile din joinuri si care respecta clauzele din Where 
SELECT a.nume,po.link_profil_online,po.nr_urmaritori,m.nume,p.nume,p.incarcari_zilnice
FROM (((Artist a right outer join Profil_Online po on a.link_profil_online like po.link_profil_online) 
left outer join Melodie m on a.nume like m.artist)
left outer join Platforma p on m.platforma_online like p.nume)
WHERE po.nr_urmaritori > 10000 AND p.incarcari_zilnice>30000

--join la 3 tabelele 
--le aleg din toate tabelele doar pe acelea care au subgenul de muzica la fel
--le grupez in functie de numele genului si le aleg doar pe acelea cu numele Trance sau Techno 
SELECT gm.nume AS 'Nume Gen Muzical'
FROM ((Gen_Muzical gm inner join Subgen_Muzical sg on gm.nume_subgen=sg.nume) 
inner join Melodie m on m.subgen_muzical=sg.nume)
GROUP BY gm.nume
Having gm.nume in ('Trance', 'Techno');

--selecteaza din 2 tabele, tot continutul celei din dreapta 
--le alege doar pe acelaea cu nr. de detinatori ai labelului mai mare decat 15
SELECT *
FROM Album a right outer join Casa_de_Discuri c on a.casa_discuri like c.nume
WHERE c.nr_detinatori>15 

--selectez din tabelul Melodie acele elemente care au subgenul muzical care sa inceapa cu t sau nume incepe cel putin cu litera I in ordine lexicografica
SELECT *
FROM Melodie 
WHERE Melodie.subgen_muzical like 't%' OR Melodie.artist>'I';

--selectez din tabelul Artisti doar acei artisti cu varsta mai mare de 30 de ani 
SELECT *
FROM Artist
wHERE Artist.varsta>30;



--laboratorul 3 
--versionarea bazei de date 

--creare tabelea de versiuni 
CREATE TABLE "Versiune"(
	versiunea int  
);
INSERT INTO Versiune(versiunea) values(0);
Drop table Versiune

--procedura care schimba tipul coloanei "nr_urmaritori"
CREATE PROCEDURE procedure1
AS
BEGIN 
	ALTER TABLE Profil_Online
	ALTER COLUMN nr_urmaritori varchar(5)

	UPDATE Versiune
	SET versiunea=1;
END

CREATE PROCEDURE undo1
AS
BEGIN
	ALTER TABLE Profil_Online
	ALTER COLUMN nr_urmaritori int

	UPDATE Versiune
	SET versiunea=0;
END

--procedura care adauga numarului de detinatori valoarea implicita 150 
CREATE PROCEDURE procedure2
AS
BEGIN
	ALTER TABLE Casa_de_Discuri
	ADD CONSTRAINT Df_detinatori DEFAULT 150 FOR nr_detinatori

	UPDATE Versiune
	SET versiunea=2;
END

CREATE PROCEDURE undo2
AS
BEGIN
	ALTER TABLE Casa_de_Discuri
	DROP CONSTRAINT Df_detinatori

	UPDATE Versiune
	SET versiunea=1;
END

--procedura care creaza o tabela 
CREATE PROCEDURE procedure3
AS
BEGIN
	CREATE TABLE Tabela_proc3(
		idproc3 int IDENTITY PRIMARY KEY,
		descriere varchar(30),
		link_profil_online varchar(250) 
	)

	UPDATE Versiune
	SET versiunea=3;
END

CREATE PROCEDURE undo3
AS
BEGIN
	DROP TABLE Tabela_proc3;

	UPDATE Versiune
	SET versiunea=2;
END

--procedura care adauga un camp nou intr-o tabela 
CREATE PROCEDURE procedure4
AS
BEGIN
	ALTER TABLE Profil_Online
	ADD nr_artisti int

	UPDATE Versiune
	SET versiunea=4;
END

CREATE PROCEDURE undo4
AS
BEGIN
	ALTER TABLE Profil_Online
	DROP COLUMN nr_artisti 

	UPDATE Versiune
	SET versiunea=3;
END

--procedura care sterge o cheie straina dintr-o tabela existenta 
CREATE PROCEDURE procedure5 
AS
BEGIN
	ALTER TABLE Tabela_proc3
	ADD CONSTRAINT FK_Tabela_Profil FOREIGN KEY(link_profil_online) REFERENCES Profil_Online(link_profil_online);

	UPDATE Versiune
	SET versiunea=5;
END

CREATE PROCEDURE undo5
AS
BEGIN
	ALTER TABLE Tabela_proc3
	DROP CONSTRAINT FK_Tabela_Profil

	UPDATE Versiune
	SET versiunea=4;
END 

CREATE PROCEDURE Main
@input varchar(10)
AS
BEGIN
	IF @input NOT IN ('0','1','2','3','4','5')
	BEGIN
		PRINT 'Versiune invalida!'
		return
	END
	
	--declar o variabil care va reprezenta versiunea dorita, data ca input 
	DECLARE @versiune INT
	SET @versiune = CONVERT(INT,@input)

	--declar o variabila in care voi avea versiunea curenta a bazei de date 
	DECLARE @versiuneacurenta INT
	SET @versiuneacurenta= (SELECT versiunea FROM Versiune)
	
	--declar o variabila in care voi forma numele procedurii pe care vreau sa o execut
	DECLARE @deExecutat varchar(10)
	WHILE @versiune < @versiuneacurenta
	BEGIN
		SET @deExecutat=CONCAT('undo', @versiuneacurenta)
		print 'Execut ' + @deExecutat
		EXECUTE @deExecutat
		SET @versiuneacurenta=(SELECT versiunea FROM Versiune)
	END

	WHILE @versiune > @versiuneacurenta
	BEGIN
		SET @deExecutat=CONCAT('procedure', @versiuneacurenta+1)
		print 'Execut ' + @deExecutat
		EXECUTE @deExecutat
		SET @versiuneacurenta=(SELECT versiunea FROM Versiune)
	END
END

drop procedure Main
exec rev_procedure3
exec Main 0

go
use Muzica
go

INSERT INTO "Platforma"(nume,incarcari_zilnice) VALUES('incercam',65583);
INSERT INTO "Melodie"(nume,artist,nume_album,subgen_muzical,platforma_online) VALUES('j', 'j', 'j','j','incercam');

