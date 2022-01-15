package com.example.Teretana.Model;

public class TipTreninga {

    private Long id;
    private String ime;
    private String opis;

    public TipTreninga() {

    }

    public TipTreninga(String ime, String opis) {
        this.ime = ime;
        this.opis = opis;
    }

    public TipTreninga(Long id, String ime, String opis) {
        this.id = id;
        this.ime = ime;
        this.opis = opis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime*result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TipTreninga other = (TipTreninga) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Ime='" + ime + '\'' +
                ", Opis='" + opis + '\'';
    }
}
