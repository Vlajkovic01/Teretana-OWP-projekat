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
}
