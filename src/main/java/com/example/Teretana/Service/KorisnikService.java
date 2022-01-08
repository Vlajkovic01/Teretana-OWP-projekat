package com.example.Teretana.Service;

import com.example.Teretana.Model.Korisnik;

import java.util.List;

public interface KorisnikService {

    Korisnik findOne(Long id);
    Korisnik findOne(String korisnickoIme);
    Korisnik findOne(String korisnickoIme, String lozinka);
    List<Korisnik> findAll();
    Korisnik save(Korisnik korisnik);
    Korisnik update(Korisnik korisnik);
    Korisnik delete(Long id);
}
