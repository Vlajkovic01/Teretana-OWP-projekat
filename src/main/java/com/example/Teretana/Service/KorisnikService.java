package com.example.Teretana.Service;

import com.example.Teretana.Model.Korisnik;

import java.util.List;

public interface KorisnikService {

    Korisnik findOne(Long id);
    Korisnik findOne(String korisnickoIme);
    Korisnik findOne(String korisnickoIme, String lozinka);
    Korisnik findOneByEmail(String email);
    List<Korisnik> findAll();
    String validacija(String korIme, String lozinka, String email, String ime, String prezime, String datumRodjenja, String adresa, String telefon);
    Korisnik save(Korisnik korisnik);
    Korisnik update(Korisnik korisnik);
    Korisnik delete(Long id);
}
