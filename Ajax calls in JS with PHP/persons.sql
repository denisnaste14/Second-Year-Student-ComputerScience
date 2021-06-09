use lab_ajax;

create table Person(
	id int auto_increment primary key,
    nume varchar(30),
    prenume varchar(30),
    telefon varchar(30),
    email varchar(30)
);

insert into Person(nume,prenume,telefon,email) values('Naste','Denis','0754000222','denisnaste@yahoo.com');
insert into Person(nume,prenume,telefon,email) values('Naste','Marian','0754999222','marian_naste@yahoo.com');
insert into Person(nume,prenume,telefon,email) values('Popescu','Ion','0744120357','ionIon@yahoo.com');
insert into Person(nume,prenume,telefon,email) values('Messi','Leo','0754123321','leomessi@yahoo.com');
insert into Person(nume,prenume,telefon,email) values('Mane','Sadio','0754123789','mane_sadio@yahoo.com');
insert into Person(nume,prenume,telefon,email) values('Lewandowski','Robert','0369111222','rlew@yahoo.com');
insert into Person(nume,prenume,telefon,email) values('Mane','Sadio','0754123789','mane_sadio@yahoo.com');
insert into Person(nume,prenume,telefon,email) values('Mbappe','Kylian','0742374632','mbappe@yahoo.com');
insert into Person(nume,prenume,telefon,email) values('Ronaldo','Cristiano','0754111111','cristi@yahoo.com');
insert into Person(nume,prenume,telefon,email) values('Hazard','Eden','0722123222','hazard@yahoo.com');
insert into Person(nume,prenume,telefon,email) values('Firmino','Robert','0754123956','firmino.robert@yahoo.com');
insert into Person(nume,prenume,telefon,email) values('Mane','Sadio','0754123789','mane_sadio@yahoo.com');
insert into Person(nume,prenume,telefon,email) values('Silva','Bernardo','0745151515','bsilva@yahoo.com');
insert into Person(nume,prenume,telefon,email) values('Benzema','Karim','0732788455','karimBenzema@yahoo.com');
insert into Person(nume,prenume,telefon,email) values('Oblak','Jan','0744888888','oblak@yahoo.com');

select * from Person;