drop schema if exists teretana;
create schema teretana default character set utf8;
use teretana;

#MODEL PODATAKA
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

create table sale(
	id bigint auto_increment,
    oznaka varchar(15),
    kapacitet int,
    primary key(id)
);

create table termini(
	id bigint auto_increment,
    salaId bigint,
    treningId bigint, 
    datumOdrzavanja datetime,
    datumOdrzavanjaKraj datetime,
    primary key(id),
    foreign key(salaId) references sale(id)
		on delete cascade,
	foreign key(treningId) references treninzi(id)
		on delete cascade
);

create table korisnickaKorpa(
	id bigint auto_increment,
	korisnikId bigint,
    terminId bigint,
    datumRezervacije datetime,
    primary key(id),
    foreign key(korisnikId) references korisnici(id)
		on delete cascade,
	foreign key(terminId) references termini(id)
		on delete cascade
);

create table listaZelja(
	id bigint auto_increment,
    korisnikId bigint,
    treningId bigint,
    primary key(id),
	foreign key(korisnikId) references korisnici(id)
		on delete cascade,
	foreign key(treningId) references treninzi(id)
		on delete cascade
);

create table komentari(
	id bigint auto_increment,
    tekst varchar(50),
    ocena int,
    datum date,
    korisnikId bigint,
    treningId bigint,
    statusKomentara varchar(15),
    anoniman boolean,
	primary key(id),
	foreign key(korisnikId) references korisnici(id)
		on delete cascade,
	foreign key(treningId) references treninzi(id)
		on delete cascade
);

create table zahteviZaKarticu(
	id bigint auto_increment,
	korisnikId bigint,
    podnosenjeZahteva datetime,
    statusZahteva varchar(15),
    primary key(id),
	foreign key(korisnikId) references korisnici(id)
		on delete cascade
);

create table clanskeKartice(
	id bigint auto_increment,
    korisnikId bigint,
    brojBodova int,
	primary key(id),
	foreign key(korisnikId) references korisnici(id)
		on delete cascade
);

create table specijalniDatumi(
	id bigint auto_increment,
    pocetakDatuma date,
    krajDatuma date,
    popust int,
    primary key(id)
);

create table datumiTreninzi(
	datumId bigint,
    treningId bigint,
    primary key(datumId, treningId),
    foreign key(datumId) references specijalniDatumi(id)
		on delete cascade,
	foreign key(treningId) references treninzi(id)
		on delete cascade
);

#UBACIVANJE PODATAKA
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

insert into sale(oznaka, kapacitet) values ('A1', 5);
insert into sale(oznaka, kapacitet) values ('B1', 10);
insert into sale(oznaka, kapacitet) values ('C1', 15);

insert into termini(salaId, treningId, datumOdrzavanja, datumOdrzavanjaKraj) values (1, 2, '2022-02-21 12:00', '2022-02-21 13:00');
insert into termini(salaId, treningId, datumOdrzavanja, datumOdrzavanjaKraj) values (2, 3, '2022-02-22 13:00', '2022-02-22 13:45');
insert into termini(salaId, treningId, datumOdrzavanja, datumOdrzavanjaKraj) values (3, 1, '2022-02-23 12:00', '2022-02-23 13:00');
insert into termini(salaId, treningId, datumOdrzavanja, datumOdrzavanjaKraj) values (2, 1, '2022-02-24 14:00', '2022-02-24 15:00');

insert into korisnickaKorpa(korisnikId, terminId, datumRezervacije) values (2, 1, '2022-02-14 10:55');
insert into korisnickaKorpa(korisnikId, terminId, datumRezervacije) values (2, 2, '2022-02-15 10:56');
insert into korisnickaKorpa(korisnikId, terminId, datumRezervacije) values (3, 3, '2022-02-16 10:57');
insert into korisnickaKorpa(korisnikId, terminId, datumRezervacije) values (3, 4, '2022-02-17 10:58');
insert into korisnickaKorpa(korisnikId, terminId, datumRezervacije) values (3, 2, '2022-02-17 10:58');

insert into listaZelja(korisnikId, treningId) values (2, 1);
insert into listaZelja(korisnikId, treningId) values (2, 3);
insert into listaZelja(korisnikId, treningId) values (3, 4);
insert into listaZelja(korisnikId, treningId) values (3, 2);

insert into komentari(tekst, ocena, datum, korisnikId, treningId, statusKomentara, anoniman) 
	values('Jako dobar trening', 5, '2022-02-12', 2, 2, 'NA_CEKANJU', false);
insert into komentari(tekst, ocena, datum, korisnikId, treningId, statusKomentara, anoniman) 
	values('Prosecan trening', 3, '2022-02-13', 2, 3, 'NA_CEKANJU', false);
insert into komentari(tekst, ocena, datum, korisnikId, treningId, statusKomentara, anoniman) 
	values('Los trening', 1, '2022-02-14', 3, 3, 'NIJE_ODOBREN', false);
insert into komentari(tekst, ocena, datum, korisnikId, treningId, statusKomentara, anoniman) 
	values('Zadovoljan sam', 4, '2022-02-15', 3, 1, 'ODOBREN', true);
    
insert into zahteviZaKarticu(korisnikId, podnosenjeZahteva, statusZahteva) values (2, '2022-02-17 12:14', 'NA_CEKANJU'); 
insert into zahteviZaKarticu(korisnikId, podnosenjeZahteva, statusZahteva) values (3, '2022-02-14 11:26', 'NIJE_ODOBREN');
insert into zahteviZaKarticu(korisnikId, podnosenjeZahteva, statusZahteva) values (3, '2022-02-16 08:35', 'ODOBREN');

insert into clanskeKartice(korisnikId, brojBodova) values(3, 10);

insert into specijalniDatumi(pocetakDatuma, krajDatuma, popust) values('2022-02-21', '2022-02-22', 10);
insert into specijalniDatumi(pocetakDatuma, krajDatuma, popust) values('2022-02-23', '2022-02-24', 8);

insert into datumiTreninzi(datumId, treningId) values(1, 1);
insert into datumiTreninzi(datumId, treningId) values(1, 2);
insert into datumiTreninzi(datumId, treningId) values(1, 3);
insert into datumiTreninzi(datumId, treningId) values(1, 4);
insert into datumiTreninzi(datumId, treningId) values(2, 2);

select * from korisnici;
select * from tipoviTreninga;
select * from treninzi;
select * from treninziTipovi;
select * from sale;
select * from termini;
select * from korisnickaKorpa;
select * from listaZelja;
select * from komentari;
select * from zahteviZaKarticu;
select * from clanskeKartice;
select * from specijalniDatumi;
select * from datumiTreninzi;