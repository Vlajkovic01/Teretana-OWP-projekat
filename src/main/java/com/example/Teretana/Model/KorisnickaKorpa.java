package com.example.Teretana.Model;

import java.time.LocalDateTime;

public class KorisnickaKorpa {

    private Long id;
    private Korisnik korisnik;
    private Termin termin;
    private LocalDateTime datumRezervacije;

    public KorisnickaKorpa() {

    }

    public KorisnickaKorpa(Long id, Korisnik korisnik, Termin termin, LocalDateTime datumRezervacije) {
        this.id = id;
        this.korisnik = korisnik;
        this.termin = termin;
        this.datumRezervacije = datumRezervacije;
    }

    public KorisnickaKorpa(Korisnik korisnik, Termin termin, LocalDateTime datumRezervacije) {
        this.korisnik = korisnik;
        this.termin = termin;
        this.datumRezervacije = datumRezervacije;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Termin getTermin() {
        return termin;
    }

    public void setTermin(Termin termin) {
        this.termin = termin;
    }

    public LocalDateTime getDatumRezervacije() {
        return datumRezervacije;
    }

    public void setDatumRezervacije(LocalDateTime datumRezervacije) {
        this.datumRezervacije = datumRezervacije;
    }

    @Override
    public String toString() {
        return "KorisnickaKorpa{" +
                "id=" + id +
                ", korisnik=" + korisnik +
                ", termin=" + termin +
                ", datumRezervacije=" + datumRezervacije +
                '}';
    }
}
