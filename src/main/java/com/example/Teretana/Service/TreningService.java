package com.example.Teretana.Service;

import com.example.Teretana.Model.Trening;

import java.util.List;

public interface TreningService {

    Trening findOne(Long id);
    List<Trening> findAll();
    Trening save(Trening trening);
    Trening update(Trening trening);
    Trening delete(Long id);
}
