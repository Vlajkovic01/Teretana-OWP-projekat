package com.example.Teretana.Service.impl;

import com.example.Teretana.DAO.KorisnickaKorpaDAO;
import com.example.Teretana.Model.KorisnickaKorpa;
import com.example.Teretana.Service.KorisnickaKorpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DatabaseKorisnickaKorpaServiceImpl implements KorisnickaKorpaService {

    @Autowired
    private KorisnickaKorpaDAO korisnickaKorpaDAO;

    @Override
    public List<KorisnickaKorpa> findAll() {
        return korisnickaKorpaDAO.findAll();
    }

    @Override
    public KorisnickaKorpa findOne(Long id) {
        return korisnickaKorpaDAO.findOne(id);
    }

    @Override
    public List<KorisnickaKorpa> findByKorisnikId(Long id) {
        return korisnickaKorpaDAO.findByKorisnikId(id);
    }

    @Override
    public List<KorisnickaKorpa> findByTerminId(Long id) {
        return korisnickaKorpaDAO.findByTerminId(id);
    }

    @Override
    public boolean proveraKapaciteta(Long id, int kapacitetSale) {
        return korisnickaKorpaDAO.proveraKapaciteta(id, kapacitetSale);
    }

    @Override
    public boolean proveraVremena(Long idKorisnika, LocalDateTime noviTerminPocetak, LocalDateTime noviTerminKraj) {
        return korisnickaKorpaDAO.proveraVremena(idKorisnika, noviTerminPocetak, noviTerminKraj);
    }

    @Override
    public int ukupnaCenaRezervacija(Long idKorisnika) {
        return korisnickaKorpaDAO.ukupnaCenaRezervacija(idKorisnika);
    }

    @Override
    public int save(KorisnickaKorpa korisnickaKorpa) {
        return korisnickaKorpaDAO.save(korisnickaKorpa);
    }

    @Override
    public int update(KorisnickaKorpa korisnickaKorpa) {
        return korisnickaKorpaDAO.update(korisnickaKorpa);
    }

    @Override
    public int delete(Long id) {
        return korisnickaKorpaDAO.delete(id);
    }
}
