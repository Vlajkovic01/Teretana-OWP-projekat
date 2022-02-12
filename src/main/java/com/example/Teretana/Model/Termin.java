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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime*result + ((id == null) ? 0 : id.hashCode());
        return 31 + id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
       Termin other = (Termin) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
