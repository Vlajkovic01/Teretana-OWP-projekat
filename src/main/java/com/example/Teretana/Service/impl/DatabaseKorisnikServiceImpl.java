package com.example.Teretana.Service.impl;

import com.example.Teretana.DAO.KorisnikDAO;
import com.example.Teretana.Model.Korisnik;
import com.example.Teretana.Service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
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
    public Korisnik findOneByEmail(String email) {
        return korisnikDAO.findOneByEmail(email);
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

    @Override
    public String validacija(String korIme, String lozinka, String email, String ime, String prezime, String datumRodjenja, String adresa, String telefon) {
        String poruka = " Ispravi gresku: ";
        boolean postojiGreska = false;

        Korisnik kIme = findOne(korIme);
        Korisnik kEmail = findOneByEmail(email);
        LocalDate datum = LocalDate.parse(datumRodjenja);

        if (kIme != null) {
            poruka += "-Korisnik sa tim korisnickim imenom vec postoji.\n";
            postojiGreska = true;
        }

        if (kEmail != null) {
            poruka += "-Korisnik sa tim emailom vec postoji.\n";
            postojiGreska = true;
        }

        if (korIme.length() > 20 || korIme.length() < 3 ) {
            poruka += "-Korisnicko ime ne moze biti duze od 20 i manje od 3 karaktera.\n";
            postojiGreska = true;
        }

        if (lozinka.length() > 20 || lozinka.length() < 3) {
            poruka += "-Lozinka ne moze biti duza od 20 i manja od 3 karaktera.\n";
            postojiGreska = true;
        }

        if (email.length() > 35 || email.length() < 13) {
            poruka += "-Email ne moze biti duzi od 35 i manji od 13 karaktera.\n";
            postojiGreska = true;
        }

        if (ime.length() > 20 || ime.length() < 3) {
            poruka += "-Ime ne moze biti duze od 20 i manje od 3 karaktera.\n";
            postojiGreska = true;
        }

        if (prezime.length() > 20 || prezime.length() < 3) {
            poruka += "-Prezime ne moze biti duze od 20 i manje od 3 karaktera.\n";
            postojiGreska = true;
        }

        if (datum.isBefore(LocalDate.of(1900,1,1)) || datum.isAfter(LocalDate.now())) {
            poruka += "-Datum rodjenja moze biti izmedju 1900-01-01 i trenutnog datuma.\n";
            postojiGreska = true;
        }

        if (adresa.length() > 35 || adresa.length() < 3) {
            poruka += "-Adresa ne moze biti duza od 35 i manja od 3 karaktera.\n";
            postojiGreska = true;
        }

        if (telefon.length() > 20 || telefon.length() < 3) {
            poruka += "-Telefon ne moze biti duzi od 20 i manji od 3 karaktera.\n";
            postojiGreska = true;
        }

        if (postojiGreska) {
            return  poruka;
        }

        return null;
    }
}
