package com.example.Teretana.DAO;

import com.example.Teretana.Model.Korisnik;

import java.util.List;

public interface KorisnikDAO {

    Korisnik findOne(Long id);
    Korisnik findOne(String korisnickoIme);
    Korisnik findOne(String korisnickoIme, String lozinka);
    Korisnik findOneByEmail(String email);
    List<Korisnik> findAll();
    int save(Korisnik korisnik);
    int update(Korisnik korisnik);
    int delete(Long id);

}
