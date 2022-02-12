package com.example.Teretana.Service;

import com.example.Teretana.Model.KorisnickaKorpa;

import java.util.List;

public interface KorisnickaKorpaService {
    List<KorisnickaKorpa> findAll();
    List<KorisnickaKorpa> findByKorisnikId(Long id);
    List<KorisnickaKorpa> findByTerminId(Long id);
    int save(KorisnickaKorpa korisnickaKorpa);
    int update(KorisnickaKorpa korisnickaKorpa);
    int delete(Long id);
}
