package com.example.Teretana.DAO;

import com.example.Teretana.Model.Trening;

import java.util.List;

public interface TreningDAO {

    Trening findOne(Long id);
    List<Trening> findAll();
    List<Trening> find(String naziv, String treneri, Long tipTreningaId,
                       Integer cenaOd, Integer cenaDo, String vrstaTreninga, String nivoTreninga,
                       String sortiranje);
    int save(Trening trening);
    int update(Trening trening);
    int delete(Long id);
}
