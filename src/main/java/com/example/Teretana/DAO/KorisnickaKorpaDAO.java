package com.example.Teretana.DAO;

import com.example.Teretana.Model.KorisnickaKorpa;

import java.util.List;

public interface KorisnickaKorpaDAO {
    List<KorisnickaKorpa> findAll();
    List<KorisnickaKorpa> findByKorisnikId(Long id);
    List<KorisnickaKorpa> findByTerminId(Long id);
    boolean proveraKapaciteta(Long id, int kapacitetSale);
    int save(KorisnickaKorpa korisnickaKorpa);
    int update(KorisnickaKorpa korisnickaKorpa);
    int delete(Long id);
}
