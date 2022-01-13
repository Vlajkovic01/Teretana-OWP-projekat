package com.example.Teretana.Model;

public class Sala {

    private Long id;
    private String oznaka;
    private int kapacitet;

    public Sala() {

    }

    public Sala(String oznaka, int kapacitet) {
        this.oznaka = oznaka;
        this.kapacitet = kapacitet;
    }

    public Sala(Long id, String oznaka, int kapacitet) {
        this.id = id;
        this.oznaka = oznaka;
        this.kapacitet = kapacitet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOznaka() {
        return oznaka;
    }

    public void setOznaka(String oznaka) {
        this.oznaka = oznaka;
    }

    public int getKapacitet() {
        return kapacitet;
    }

    public void setKapacitet(int kapacitet) {
        this.kapacitet = kapacitet;
    }

    @Override
    public String toString() {
        return "Sala{" +
                "id=" + id +
                ", oznaka='" + oznaka + '\'' +
                ", kapacitet=" + kapacitet +
                '}';
    }
}
