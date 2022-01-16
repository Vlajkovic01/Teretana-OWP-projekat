package com.example.Teretana.Service;

import com.example.Teretana.Model.Trening;

import java.util.List;

public interface TreningService {

    Trening findOne(Long id);
    Trening findOne(String naziv);
    List<Trening> findAll();
    List<Trening> find(String naziv, String treneri, Long tipTreningaId,
                       Integer cenaOd, Integer cenaDo, String vrstaTreninga, String nivoTreninga,
                       String tipSortiranja, String rastuce);
    String validacija(String naziv, String treneri, String kratakOpis, Integer cena,
                      Integer trajanje, Double ocena);
    Trening save(Trening trening);
    Trening update(Trening trening);
    Trening delete(Long id);
}
