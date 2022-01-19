drop schema if exists teretana;
create schema teretana default character set utf8;
use teretana;

create table korisnici (
	id bigint auto_increment,
    korisnickoIme varchar(20) not null,
    lozinka varchar(20) not null,
    email varchar(35),
    ime varchar(20),
    prezime varchar(20),
    datumRodjenja date,
    adresa varchar(35),
    telefon varchar(20),
    datumIVremeRegistracije datetime,
    uloga varchar(15) not null,
    blokiran boolean,
    primary key(id)
);

create table tipoviTreninga(
	id bigint auto_increment,
    ime varchar(20),
    opis varchar(40),
    primary key(id)
);

create table treninzi (
	id bigint auto_increment,
    naziv varchar(20),
    treneri varchar(35),
    kratakOpis varchar(35),
    urlSlika varchar(30),
    cena int,
    vrstaTreninga varchar(11),
    nivoTreninga varchar(9),
    trajanje int,
    ocena double,
    primary key(id)
);

create table treninziTipovi(
	treningId bigint,
    tipId bigint,
    primary key(treningId, tipId),
    foreign key(treningId) references treninzi(id)
		on delete cascade,
	foreign key(tipId) references tipoviTreninga(id)
		on delete cascade
);

insert into korisnici(korisnickoIme, lozinka, email, ime, prezime, datumRodjenja, adresa, telefon, datumIVremeRegistracije, uloga, blokiran) 
	values ('Stefo', '12345', 'vlajkovic@gmail.com', 'Stefan', 'Vlajkovic', '2001-05-17', 'Novi Sad', '061062', '2022-01-08 14:20', 'ADMINISTRATOR', false);
insert into korisnici(korisnickoIme, lozinka, email, ime, prezime, datumRodjenja, adresa, telefon, datumIVremeRegistracije, uloga, blokiran) 
	values ('Nidzo', '12345', 'nikolic@gmail.com', 'Nikola', 'Nikolic', '1999-06-20', 'Novi Sad', '062063', '2022-01-09 13:25', 'CLAN', false);
insert into korisnici(korisnickoIme, lozinka, email, ime, prezime, datumRodjenja, adresa, telefon, datumIVremeRegistracije, uloga, blokiran) 
	values ('Mare', '12345', 'markovic@gmail.com', 'Marko', 'Markovic', '2000-07-23', 'Beograd', '064065', '2022-01-15 14:35', 'CLAN', false);
insert into korisnici(korisnickoIme, lozinka, email, ime, prezime, datumRodjenja, adresa, telefon, datumIVremeRegistracije, uloga, blokiran) 
	values ('Pero', '12345', 'peric@gmail.com', 'Petar', 'Peric', '1995-04-13', 'Subotica', '061064', '2022-01-19 12:09', 'ADMINISTRATOR', false);

insert into tipoviTreninga(ime, opis) values ('Joga', 'Istezanje, oslobadjanje od stresa');
insert into tipoviTreninga(ime, opis) values ('Fitness', 'Mrsavljenje, rast misica');
insert into tipoviTreninga(ime, opis) values ('Kardio', 'Povecavanje kondicije');

insert into treninzi(naziv, treneri, kratakOpis, urlSlika, cena, vrstaTreninga, nivoTreninga, trajanje, ocena) 
	values('Polimetrija', 'Mika, Pera', 'Trenazni proces', 'images/polimetrija.jpg', 500, 'POJEDINACNI', 'SREDNJI', 60, 4.2);
insert into treninzi(naziv, treneri, kratakOpis, urlSlika, cena, vrstaTreninga, nivoTreninga, trajanje, ocena) 
	values('Kruzni', 'Mika', 'Trcanje', 'images/kruzni.jpg', 350, 'POJEDINACNI', 'AMATERSKI', 60, 4.0);
insert into treninzi(naziv, treneri, kratakOpis, urlSlika, cena, vrstaTreninga, nivoTreninga, trajanje, ocena) 
	values('Snaga', 'Mika, Zika', 'Vezbe snage', 'images/snaga.jpg', 420, 'GRUPNI', 'NAPREDNI', 45, 4.3);
insert into treninzi(naziv, treneri, kratakOpis, urlSlika, cena, vrstaTreninga, nivoTreninga, trajanje, ocena) 
	values('Mrsavljenje', 'Zika', 'Skidanje kilograma', 'images/mrsavljenje.jpg', 250, 'POJEDINACNI', 'AMATERSKI', 30, 3.9);
    
insert into treninziTipovi(treningId, tipId) values (1, 2);
insert into treninziTipovi(treningId, tipId) values (1, 3);
insert into treninziTipovi(treningId, tipId) values (2, 3);
insert into treninziTipovi(treningId, tipId) values (3, 2);
insert into treninziTipovi(treningId, tipId) values (3, 3);
insert into treninziTipovi(treningId, tipId) values (4, 2);
insert into treninziTipovi(treningId, tipId) values (4, 1);

select * from korisnici;
select * from tipoviTreninga;
select * from treninzi;
select * from treninziTipovi;
