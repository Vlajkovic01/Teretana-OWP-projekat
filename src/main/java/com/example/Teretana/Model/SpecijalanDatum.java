package com.example.Teretana.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SpecijalanDatum {

    private Long id;
    private LocalDate pocetakDatuma;
    private LocalDate krajDatuma;
    private int popust;
    private List<Trening> treninzi = new ArrayList<>();

    public SpecijalanDatum() {

    }

    public SpecijalanDatum(Long id, LocalDate pocetakDatuma, LocalDate krajDatuma, int popust) {
        this.id = id;
        this.pocetakDatuma = pocetakDatuma;
        this.krajDatuma = krajDatuma;
        this.popust = popust;
    }

    public SpecijalanDatum(LocalDate pocetakDatuma, LocalDate krajDatuma, int popust) {
        this.pocetakDatuma = pocetakDatuma;
        this.krajDatuma = krajDatuma;
        this.popust = popust;
    }

    public SpecijalanDatum(Long id, LocalDate pocetakDatuma, LocalDate krajDatuma, int popust, List<Trening> treninzi) {
        this.id = id;
        this.pocetakDatuma = pocetakDatuma;
        this.krajDatuma = krajDatuma;
        this.popust = popust;
        this.treninzi = treninzi;
    }

    public SpecijalanDatum(LocalDate pocetakDatuma, LocalDate krajDatuma, int popust, List<Trening> treninzi) {
        this.pocetakDatuma = pocetakDatuma;
        this.krajDatuma = krajDatuma;
        this.popust = popust;
        this.treninzi = treninzi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPocetakDatuma() {
        return pocetakDatuma;
    }

    public void setPocetakDatuma(LocalDate pocetakDatuma) {
        this.pocetakDatuma = pocetakDatuma;
    }

    public LocalDate getKrajDatuma() {
        return krajDatuma;
    }

    public void setKrajDatuma(LocalDate krajDatuma) {
        this.krajDatuma = krajDatuma;
    }

    public int getPopust() {
        return popust;
    }

    public void setPopust(int popust) {
        this.popust = popust;
    }

    public List<Trening> getTreninzi() {
        return treninzi;
    }

    public void setTreninzi(List<Trening> treninzi) {
        this.treninzi.clear();
        this.treninzi.addAll(treninzi);
    }

    @Override
    public String toString() {
        return "SpecijalanDatum{" +
                "id=" + id +
                ", pocetakDatuma=" + pocetakDatuma +
                ", krajDatuma=" + krajDatuma +
                ", popust=" + popust +
                ", treninzi=" + treninzi +
                '}';
    }
}
