package com.example.Teretana.Model;

public class ClanskaKartica {

    private Long id;
    private Korisnik korisnik;
    private double popust;
    private int brojPoena;

    public ClanskaKartica() {

    }

    public ClanskaKartica(Korisnik korisnik, double popust, int brojPoena) {
        this.korisnik = korisnik;
        this.popust = popust;
        this.brojPoena = brojPoena;
    }

    public ClanskaKartica(Long id, Korisnik korisnik, double popust, int brojPoena) {
        this.id = id;
        this.korisnik = korisnik;
        this.popust = popust;
        this.brojPoena = brojPoena;
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

    public double getPopust() {
        return popust;
    }

    public void setPopust(double popust) {
        this.popust = popust;
    }

    public int getBrojPoena() {
        return brojPoena;
    }

    public void setBrojPoena(int brojPoena) {
        this.brojPoena = brojPoena;
    }

    @Override
    public String toString() {
        return "ClanskaKartica{" +
                "id=" + id +
                ", korisnik=" + korisnik +
                ", popust=" + popust +
                ", brojPoena=" + brojPoena +
                '}';
    }
}
