package com.example.Teretana.Model;

import java.time.LocalDateTime;

public class Termin {

    private Long id;
    private Sala sala;
    private Trening trening;
    private LocalDateTime datumOdrzavanja;

    public Termin() {

    }

    public Termin(Sala sala, Trening trening, LocalDateTime datumOdrzavanja) {
        this.sala = sala;
        this.trening = trening;
        this.datumOdrzavanja = datumOdrzavanja;
    }

    public Termin(Long id, Sala sala, Trening trening, LocalDateTime datumOdrzavanja) {
        this.id = id;
        this.sala = sala;
        this.trening = trening;
        this.datumOdrzavanja = datumOdrzavanja;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Trening getTrening() {
        return trening;
    }

    public void setTrening(Trening trening) {
        this.trening = trening;
    }

    public LocalDateTime getDatumOdrzavanja() {
        return datumOdrzavanja;
    }

    public void setDatumOdrzavanja(LocalDateTime datumOdrzavanja) {
        this.datumOdrzavanja = datumOdrzavanja;
    }

    @Override
    public String toString() {
        return "Termin{" +
                "id=" + id +
                ", sala=" + sala +
                ", trening=" + trening +
                ", datumOdrzavanja=" + datumOdrzavanja +
                '}';
    }
}
