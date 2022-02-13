package com.example.Teretana.Service;

import com.example.Teretana.Model.KorisnickaKorpa;

import java.time.LocalDateTime;
import java.util.List;

public interface KorisnickaKorpaService {
    List<KorisnickaKorpa> findAll();
    KorisnickaKorpa findOne(Long id);
    List<KorisnickaKorpa> findByKorisnikId(Long id);
    List<KorisnickaKorpa> findByTerminId(Long id);
    boolean proveraKapaciteta(Long id, int kapacitetSale);
    boolean proveraVremena(Long idKorisnika, LocalDateTime noviTerminPocetak, LocalDateTime noviTerminKraj);
    int ukupnaCenaRezervacija(Long idKorisnika);
    int save(KorisnickaKorpa korisnickaKorpa);
    int update(KorisnickaKorpa korisnickaKorpa);
    int delete(Long id);
}
