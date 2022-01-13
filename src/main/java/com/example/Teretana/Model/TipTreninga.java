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
    public String toString() {
        return "TipTreninga{" +
                "id=" + id +
                ", ime='" + ime + '\'' +
                ", opis='" + opis + '\'' +
                '}';
    }
}
