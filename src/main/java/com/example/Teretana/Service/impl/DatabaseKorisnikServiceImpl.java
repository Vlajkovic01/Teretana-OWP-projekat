package com.example.Teretana.Service.impl;

import com.example.Teretana.DAO.KorisnikDAO;
import com.example.Teretana.Model.Korisnik;
import com.example.Teretana.Service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseKorisnikServiceImpl implements KorisnikService {


    @Autowired
    private KorisnikDAO korisnikDAO;

    @Override
    public Korisnik findOne(Long id) {
        return korisnikDAO.findOne(id);
    }

    @Override
    public Korisnik findOne(String korisnickoIme) {
        return korisnikDAO.findOne(korisnickoIme);
    }

    @Override
    public Korisnik findOne(String korisnickoIme, String lozinka) {
        return korisnikDAO.findOne(korisnickoIme, lozinka);
    }

    @Override
    public List<Korisnik> findAll() {
        return korisnikDAO.findAll();
    }

    @Override
    public Korisnik save(Korisnik korisnik) {
        korisnikDAO.save(korisnik);
        return korisnik;
    }

    @Override
    public Korisnik update(Korisnik korisnik) {
        korisnikDAO.update(korisnik);
        return korisnik;
    }

    @Override
    public Korisnik delete(Long id) {
        Korisnik korisnik = korisnikDAO.findOne(id);
        korisnikDAO.delete(id);
        return korisnik;
    }
}
