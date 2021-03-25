use Muzica
go 

------- Spectacol -------

--testare Spectacol 
create function testSpectacol(
@nume_spectacol varchar(30)
)
returns int
as
begin
	--daca numele dat este invalid
	if @nume_spectacol=''
		return 2
	declare @numeCautat varchar(30)
	set @numeCautat=''
	select @numeCautat=s.nume_spectacol
		from Spectacol as s 
		where s.nume_spectacol=@nume_spectacol
	--daca s-a gasit un spectacol cu numele dat
	if @numeCautat !=''
		return 1
	--altfel
	return 0
end
go

drop function dbo.testSpectacol
--Select dbo.testSpectacol('') as rezTestSpec

--procedura CRUD pentru Spectacol
create procedure CRUDspectacol
@nume_spectacol varchar(30)
as
begin
	declare @rezultat_testSpectacol int
	set @rezultat_testSpectacol=dbo.testSpectacol(@nume_spectacol)
	--verific daca am vreuna dintre erori
	if @rezultat_testSpectacol = 1
		print 'Acest nume exista deja in tabela Spectacol'
	if @rezultat_testSpectacol = 2
	    print 'Numele dat ca parametru nu poate fi vid!'
	--cazul bun (pot face crud)
	if @rezultat_testSpectacol = 0
	begin
		--C = create
		print 'ADAUGARE'
		insert into Spectacol(nume_spectacol) values (@nume_spectacol)
		print 'ADAUGARE cu succes'

		--R = read
		print 'CITIRE'
		select * from Spectacol
		print 'CITIRE cu succes'

		--U = update
		print 'ACTUALIZARE'
		update Spectacol
			set nume_spectacol=@nume_spectacol 
			where nume_spectacol=@nume_spectacol
		print 'ACTUALIZARE cu succes'

		--D = delete
		print 'STERGERE'
		delete from Spectacol
			where nume_spectacol=@nume_spectacol
		print 'STERGERE cu succes'
	end
end
go

------- Spectacol_al_Artistului -------

--testare Spectacol_al_Artistului
create function testSpectacol_al_Artistului(
@nume_artist varchar(30),
@nume_spectacol varchar(30)
)
returns int 
as
begin 
	--daca numele artistului este invalid
	if @nume_artist = '' 
		return 2
	--daca numele spectacolului este invalid
	if @nume_spectacol = '' 
		return 3

	declare @nume_artist_cautat varchar(30)
	set @nume_artist_cautat=''
	declare @nume_spectacol_cautat varchar(30)
	set @nume_spectacol_cautat=''
	select @nume_artist_cautat=saa.nume_artist, @nume_spectacol_cautat=saa.nume_spectacol
		from Spectacol_al_Artistului as saa
		where saa.nume_artist=@nume_artist and saa.nume_spectacol=@nume_spectacol
	
	--daca s-a gasit o potrivire in tabela
	if @nume_artist_cautat !='' and @nume_spectacol_cautat !=''
		return 1
	--altfel - cazul bun
		return 0
end
go

--procedura CRUD pentru Spectacol_al_Artistului
create procedure CRUDspectacol_al_Artistului
@nume_artist varchar(30),
@nume_spectacol varchar(30)
as
begin 
	declare @rezultat_testsaa int 
	set @rezultat_testsaa = dbo.testSpectacol_al_Artistului(@nume_artist,@nume_spectacol)
	--verific daca am vreuna dintre erori
	if @rezultat_testsaa = 1
		print 'Acest spectacol al artistului exista deja in tabela!'
	if @rezultat_testsaa = 2
		print 'Numele artistului dat ca parametru nu poate fi vid'
	if @rezultat_testsaa = 3
		print 'Numele spectacolului dat ca parametru nu poate fi vid'
	--cazul bun (pot face crud)
	if @rezultat_testsaa = 0
	begin
		--C = create
		print 'ADAUGARE'
		insert into Spectacol_al_Artistului(nume_artist,nume_spectacol) values (@nume_artist,@nume_spectacol)
		print 'ADAUGARE cu succes'

		--R = read
		print 'CITIRE'
		select * from Spectacol_al_Artistului
		print 'CITIRE cu succes'

		--U = update
		print 'ACTUALIZARE'
		update Spectacol_al_Artistului
			set nume_artist=@nume_artist ,nume_spectacol=@nume_spectacol
			where nume_artist=@nume_artist and nume_spectacol=@nume_spectacol
		print 'ACTUALIZARE cu succes'

		--D = delete
		print 'STERGERE'
		delete from Spectacol_al_Artistului
			where nume_artist=@nume_artist and nume_spectacol=@nume_spectacol
		print 'STERGERE cu succes'
	end
end
go


------- Artist -------

--testare Artist
create function testArtist(
@nume varchar(30),
@nationalitate varchar(30),
@varsta int,
@link_profil_online varchar(250),
@nume_spectacol varchar(30)
)
returns int
as
begin 
	--daca numele este invalid
	if @nume = ''
		return 2
	--daca nationalitatea este invalida 
	if @nationalitate = ''
		return 3
	--daca varsta este imposibila
	if @varsta<1 or @varsta>110
		return 4
	--daca linkul profilului online este invalid
	if @link_profil_online = ''
		return 5
	--daca nume sprctacol este invalid
	if @nume_spectacol=''
		return 6

	declare @nume_cautat varchar(30)
	set @nume_cautat = ''
	select @nume_cautat = a.nume 
		from Artist as a
		where a.nume = @nume
	--daca s-a gasit un artist cu numele cautat
	if @nume_cautat != ''
		return 1
	return 0
end
go

--procedura CRUD pentru Artist
create procedure CRUDartist
@nume varchar(30),
@nationalitate varchar(30),
@varsta int,
@link_profil_online varchar(250),
@nume_spectacol varchar(30)
as
begin
	declare @rezultat_testArtist int 
	set @rezultat_testArtist = dbo.testArtist(@nume, @nationalitate, @varsta,@link_profil_online,@nume_spectacol)
	--verific erorile
	if @rezultat_testArtist = 1 
		print 'Acest artist (cu numele dat) exista deja in tabela!'
	if @rezultat_testArtist = 2
		print 'Numele artistului nu poate fi vid'
	if @rezultat_testArtist = 3
		print 'Nationalitatea artistului nu poate fi vida'
	if @rezultat_testArtist = 4
		print 'Varsta artistului trebuie sa se incadreze in intervalul (1,110)'
	if @rezultat_testArtist = 5
		print 'Linkul artistului nu poate fi vid'
	if @rezultat_testArtist = 6
	    print 'Numele spectacolului nu poate fi vid'

	if @rezultat_testArtist = 0
	begin 
		--C = create
		print 'ADAUGARE'
		insert into Artist(nume,nationalitatea,varsta,link_profil_online,nume_spectacol) values (@nume,@nationalitate,@varsta,@link_profil_online,@nume_spectacol)
		print 'ADAUGARE cu succes'

		--R = read
		print 'CITIRE'
		select * from Artist
		print 'CITIRE cu succes'

		--U = update
		print 'ACTUALIZARE'
		update Artist
			set nume=@nume, nationalitatea=@nationalitate, varsta=@varsta, link_profil_online=@link_profil_online, nume_spectacol=@nume_spectacol
			where nume=@nume
		print 'ACTUALIZARE cu succes'

		--D = delete
		print 'STERGERE'
		delete from Artist
			where nume=@nume
		print 'STERGERE cu succes'
	end
end
go


Insert into Spectacol_al_Artistului(nume_artist,nume_spectacol) values ('Artist1', 'Spectacol1')
delete from Spectacol_al_Artistului where nume_artist='Artist1' and nume_spectacol='Spectacol1'
exec CRUDspectacol 'Spectacol1'
exec CRUDspectacol_al_Artistului 'Artist1', 'Spectacol1'
Insert into Profil_Online(link_profil_online, nr_urmaritori) values ('RA_priku', 50000)
delete from Profil_Online where link_profil_online='RA_priku'
exec CRUDartist 'Artist1', 'Romania', 37, 'RA_priku', 'Spectacol1'

go
--VIEWS
create view view_spectacol as
select s.nume_spectacol 
from Spectacol s
inner join Spectacol_al_Artistului SaA on s.nume_spectacol=SaA.nume_spectacol
where s.nume_spectacol > 'b'
go

create view view_artist as
select a.nume, a.nationalitatea, a.varsta, a.link_profil_online, a.nume_spectacol
from Artist a
inner join Spectacol_al_Artistului SaA on a.nume_spectacol=SaA.nume_spectacol and a.nume=SaA.nume_artist
where a.varsta>30
go

create nonclustered index index_spectacol on Spectacol(nume_spectacol)
create nonclustered index index_artist_nume on Artist(nume)
create nonclustered index index_artist_nationalitatea on Artist(nationalitatea)

select * from view_spectacol order by nume_spectacol
select * from view_artist