package com.example.Teretana.Model;

import java.util.ArrayList;
import java.util.List;

public class Trening {

    private Long id;
    private String naziv;
    private String treneri;
    private String kratakOpis;
    private String urlSlika;
    private List<TipTreninga> tipTreninga = new ArrayList<>();
    private int cena;
    private VrstaTreninga vrstaTreninga;
    private NivoTreninga nivoTreninga;
    private int trajanje;
    private double ocena;

    public Trening() {

    }

    public Trening(String naziv, String treneri, String kratakOpis, String urlSlika, List<TipTreninga> tipTreninga, int cena, VrstaTreninga vrstaTreninga, NivoTreninga nivoTreninga, int trajanje, double ocena) {
        this.naziv = naziv;
        this.treneri = treneri;
        this.kratakOpis = kratakOpis;
        this.urlSlika = urlSlika;
        this.tipTreninga = tipTreninga;
        this.cena = cena;
        this.vrstaTreninga = vrstaTreninga;
        this.nivoTreninga = nivoTreninga;
        this.trajanje = trajanje;
        this.ocena = ocena;
    }

    public Trening(Long id, String naziv, String treneri, String kratakOpis, String urlSlika, List<TipTreninga> tipTreninga, int cena, VrstaTreninga vrstaTreninga, NivoTreninga nivoTreninga, int trajanje, double ocena) {
        this.id = id;
        this.naziv = naziv;
        this.treneri = treneri;
        this.kratakOpis = kratakOpis;
        this.urlSlika = urlSlika;
        this.tipTreninga = tipTreninga;
        this.cena = cena;
        this.vrstaTreninga = vrstaTreninga;
        this.nivoTreninga = nivoTreninga;
        this.trajanje = trajanje;
        this.ocena = ocena;
    }

    public Trening(Long id, String naziv, String treneri, String kratakOpis, String urlSlika, int cena, VrstaTreninga vrstaTreninga, NivoTreninga nivoTreninga, int trajanje, double ocena) {
        this.id = id;
        this.naziv = naziv;
        this.treneri = treneri;
        this.kratakOpis = kratakOpis;
        this.urlSlika = urlSlika;
        this.cena = cena;
        this.vrstaTreninga = vrstaTreninga;
        this.nivoTreninga = nivoTreninga;
        this.trajanje = trajanje;
        this.ocena = ocena;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getTreneri() {
        return treneri;
    }

    public void setTreneri(String treneri) {
        this.treneri = treneri;
    }

    public String getKratakOpis() {
        return kratakOpis;
    }

    public void setKratakOpis(String kratakOpis) {
        this.kratakOpis = kratakOpis;
    }

    public String getUrlSlika() {
        return urlSlika;
    }

    public void setUrlSlika(String urlSlika) {
        this.urlSlika = urlSlika;
    }

    public List<TipTreninga> getTipTreninga() {
        return tipTreninga;
    }

    public void setTipTreninga(List<TipTreninga> tipTreninga) {
        this.tipTreninga = tipTreninga;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public VrstaTreninga getVrstaTreninga() {
        return vrstaTreninga;
    }

    public void setVrstaTreninga(VrstaTreninga vrstaTreninga) {
        this.vrstaTreninga = vrstaTreninga;
    }

    public NivoTreninga getNivoTreninga() {
        return nivoTreninga;
    }

    public void setNivoTreninga(NivoTreninga nivoTreninga) {
        this.nivoTreninga = nivoTreninga;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public double getOcena() {
        return ocena;
    }

    public void setOcena(double ocena) {
        this.ocena = ocena;
    }

    @Override
    public String toString() {
        return "Trening{" +
                "id=" + id +
                ", naziv='" + naziv + '\'' +
                ", treneri='" + treneri + '\'' +
                ", kratakOpis='" + kratakOpis + '\'' +
                ", urlSlika='" + urlSlika + '\'' +
                ", tipTreninga=" + tipTreninga +
                ", cena=" + cena +
                ", vrstaTreninga=" + vrstaTreninga +
                ", nivoTreninga=" + nivoTreninga +
                ", trajanje=" + trajanje +
                ", ocena=" + ocena +
                '}';
    }
}
