package com.example.Teretana.Model;

public class Izvestaj {

    private Long idTreninga;
    private String nazivTreninga;
    private String treneri;
    private int brojZakazanihTermina;
    private int ukupnaCenaTermina;

    public Izvestaj(Long idTreninga, String nazivTreninga, String treneri, int brojZakazanihTermina, int ukupnaCenaTermina) {
        this.idTreninga = idTreninga;
        this.nazivTreninga = nazivTreninga;
        this.treneri = treneri;
        this.brojZakazanihTermina = brojZakazanihTermina;
        this.ukupnaCenaTermina = ukupnaCenaTermina;
    }

    public Izvestaj(String nazivTreninga, String treneri, int brojZakazanihTermina, int ukupnaCenaTermina) {
        this.nazivTreninga = nazivTreninga;
        this.treneri = treneri;
        this.brojZakazanihTermina = brojZakazanihTermina;
        this.ukupnaCenaTermina = ukupnaCenaTermina;
    }

    public Long getIdTreninga() {
        return idTreninga;
    }

    public void setIdTreninga(Long idTreninga) {
        this.idTreninga = idTreninga;
    }

    public String getNazivTreninga() {
        return nazivTreninga;
    }

    public void setNazivTreninga(String nazivTreninga) {
        this.nazivTreninga = nazivTreninga;
    }

    public String getTreneri() {
        return treneri;
    }

    public void setTreneri(String treneri) {
        this.treneri = treneri;
    }

    public int getBrojZakazanihTermina() {
        return brojZakazanihTermina;
    }

    public void setBrojZakazanihTermina(int brojZakazanihTermina) {
        this.brojZakazanihTermina = brojZakazanihTermina;
    }

    public int getUkupnaCenaTermina() {
        return ukupnaCenaTermina;
    }

    public void setUkupnaCenaTermina(int ukupnaCenaTermina) {
        this.ukupnaCenaTermina = ukupnaCenaTermina;
    }

    @Override
    public String toString() {
        return "Izvestaj{" +
                "idTreninga=" + idTreninga +
                ", nazivTreninga='" + nazivTreninga + '\'' +
                ", treneri='" + treneri + '\'' +
                ", brojZakazanihTermina=" + brojZakazanihTermina +
                ", ukupnaCenaTermina=" + ukupnaCenaTermina +
                '}';
    }
}
