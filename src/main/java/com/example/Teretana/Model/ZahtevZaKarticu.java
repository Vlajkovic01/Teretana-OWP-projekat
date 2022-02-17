package com.example.Teretana.Model;

import java.time.LocalDateTime;

public class ZahtevZaKarticu {

    private Long id;
    private Korisnik korisnik;
    private LocalDateTime podnosenjeZahteva;
    private StatusKomentaraIZahtevaKartice status;

    public ZahtevZaKarticu(Long id, Korisnik korisnik, LocalDateTime podnosenjeZahteva, StatusKomentaraIZahtevaKartice status) {
        this.id = id;
        this.korisnik = korisnik;
        this.podnosenjeZahteva = podnosenjeZahteva;
        this.status = status;
    }

    public ZahtevZaKarticu(Korisnik korisnik, LocalDateTime podnosenjeZahteva, StatusKomentaraIZahtevaKartice status) {
        this.korisnik = korisnik;
        this.podnosenjeZahteva = podnosenjeZahteva;
        this.status = status;
    }

    public ZahtevZaKarticu(Long id, Korisnik korisnik, LocalDateTime podnosenjeZahteva) {
        this.id = id;
        this.korisnik = korisnik;
        this.podnosenjeZahteva = podnosenjeZahteva;
    }

    public ZahtevZaKarticu(Korisnik korisnik, LocalDateTime podnosenjeZahteva) {
        this.korisnik = korisnik;
        this.podnosenjeZahteva = podnosenjeZahteva;
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

    public LocalDateTime getPodnosenjeZahteva() {
        return podnosenjeZahteva;
    }

    public void setPodnosenjeZahteva(LocalDateTime podnosenjeZahteva) {
        this.podnosenjeZahteva = podnosenjeZahteva;
    }

    public StatusKomentaraIZahtevaKartice getStatus() {
        return status;
    }

    public void setStatus(StatusKomentaraIZahtevaKartice status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ZahtevZaKarticu{" +
                "id=" + id +
                ", korisnik=" + korisnik +
                ", podnosenjeZahteva=" + podnosenjeZahteva +
                ", status=" + status +
                '}';
    }
}
