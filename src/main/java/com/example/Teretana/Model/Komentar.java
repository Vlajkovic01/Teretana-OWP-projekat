package com.example.Teretana.Model;

import java.time.LocalDate;

public class Komentar {

    private Long id;
    private String tekst;
    private int ocena;
    private LocalDate datum;
    private Korisnik autor;
    private Trening trening;
    private StatusKomentara status;
    private boolean anoniman;

    public Komentar() {

    }

    public Komentar(String tekst, int ocena, LocalDate datum, Korisnik autor, Trening trening, StatusKomentara status, boolean anoniman) {
        this.tekst = tekst;
        this.ocena = ocena;
        this.datum = datum;
        this.autor = autor;
        this.trening = trening;
        this.status = status;
        this.anoniman = anoniman;
    }

    public Komentar(Long id, String tekst, int ocena, LocalDate datum, Korisnik autor, Trening trening, StatusKomentara status, boolean anoniman) {
        this.id = id;
        this.tekst = tekst;
        this.ocena = ocena;
        this.datum = datum;
        this.autor = autor;
        this.trening = trening;
        this.status = status;
        this.anoniman = anoniman;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public Korisnik getAutor() {
        return autor;
    }

    public void setAutor(Korisnik autor) {
        this.autor = autor;
    }

    public Trening getTrening() {
        return trening;
    }

    public void setTrening(Trening trening) {
        this.trening = trening;
    }

    public StatusKomentara getStatus() {
        return status;
    }

    public void setStatus(StatusKomentara status) {
        this.status = status;
    }

    public boolean isAnoniman() {
        return anoniman;
    }

    public void setAnoniman(boolean anoniman) {
        this.anoniman = anoniman;
    }

    @Override
    public String toString() {
        return "Komentar{" +
                "id=" + id +
                ", tekst='" + tekst + '\'' +
                ", ocena=" + ocena +
                ", datum=" + datum +
                ", autor=" + autor +
                ", trening=" + trening +
                ", status=" + status +
                ", anoniman=" + anoniman +
                '}';
    }
}
