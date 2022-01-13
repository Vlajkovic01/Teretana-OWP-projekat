package com.example.Teretana.Model;

public class KorisnickaKorpa {

    private Korisnik korisnik;
    private Termin termin;

    public KorisnickaKorpa() {

    }

    public KorisnickaKorpa(Korisnik korisnik, Termin termin) {
        this.korisnik = korisnik;
        this.termin = termin;
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

    @Override
    public String toString() {
        return "KorisnickaKorpa{" +
                "korisnik=" + korisnik +
                ", termin=" + termin +
                '}';
    }
}
