package com.example.Teretana.Service;

import com.example.Teretana.Model.Trening;

import java.util.List;

public interface TreningService {

    Trening findOne(Long id);
    List<Trening> findAll();
    List<Trening> find(String naziv, String treneri, Long tipTreningaId,
                       Integer cenaOd, Integer cenaDo, String vrstaTreninga, String nivoTreninga,
                       String sortiranje);
    Trening save(Trening trening);
    Trening update(Trening trening);
    Trening delete(Long id);
}
