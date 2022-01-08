drop schema if exists teretatana;
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
    adresa varchar(20),
    telefon varchar(20),
    datumIVremeRegistracije datetime,
    uloga varchar(15) not null,
    blokiran boolean,
    primary key(id)
);

insert into korisnici(korisnickoIme, lozinka, email, ime, prezime, datumRodjenja, adresa, telefon, datumIVremeRegistracije, uloga, blokiran) values ('Stefo', '12345', 'vlajkovic@gmail.com', 'Stefan', 'Vlajkovic', '2001-05-17', 'Novi Sad', '061062', '2022-01-08 14:20', 'ADMINISTRATOR', false);
insert into korisnici(korisnickoIme, lozinka, email, ime, prezime, datumRodjenja, adresa, telefon, datumIVremeRegistracije, uloga, blokiran) values ('Nidzo', '12345', 'nikolic@gmail.com', 'Nikola', 'Nikolic', '1999-06-20', 'Novi Sad', '062063', '2022-01-09 13:25', 'CLAN', false);

select * from korisnici;