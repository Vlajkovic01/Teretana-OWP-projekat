package com.example.Teretana.Model;

public class Zelja {

    private Long id;
    private Korisnik korisnik;
    private Trening trening;

    public Zelja() {

    }

    public Zelja(Long id, Korisnik korisnik, Trening trening) {
        this.id = id;
        this.korisnik = korisnik;
        this.trening = trening;
    }

    public Zelja(Korisnik korisnik, Trening trening) {
        this.korisnik = korisnik;
        this.trening = trening;
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

    public Trening getTrening() {
        return trening;
    }

    public void setTrening(Trening trening) {
        this.trening = trening;
    }

    @Override
    public String toString() {
        return "Zelja{" +
                "id=" + id +
                ", korisnik=" + korisnik +
                ", trening=" + trening +
                '}';
    }
}
