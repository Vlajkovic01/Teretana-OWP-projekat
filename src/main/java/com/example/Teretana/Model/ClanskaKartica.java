package com.example.Teretana.Model;

public class ClanskaKartica {

    private Long id;
    private Korisnik korisnik;
    private int brojBodova;

    public ClanskaKartica() {

    }

    public ClanskaKartica(Korisnik korisnik, int brojBodova) {
        this.korisnik = korisnik;
        this.brojBodova = brojBodova;
    }

    public ClanskaKartica(Long id, Korisnik korisnik, int brojBodova) {
        this.id = id;
        this.korisnik = korisnik;
        this.brojBodova = brojBodova;
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

    public int getBrojBodova() {
        return brojBodova;
    }

    public void setBrojBodova(int brojBodova) {
        this.brojBodova = brojBodova;
    }

    @Override
    public String toString() {
        return "ClanskaKartica{" +
                "id=" + id +
                ", korisnik=" + korisnik +
                ", brojBodova=" + brojBodova +
                '}';
    }
}
