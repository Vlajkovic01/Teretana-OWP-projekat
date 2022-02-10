package com.example.Teretana.Service;

import com.example.Teretana.Model.Sala;

import java.util.List;

public interface SalaService {
    List<Sala> findAll();
    Sala findOne(Long id);
    Sala findOne(String oznaka);
    List<Sala> find(String oznaka, String rastuce);
    int save(Sala sala);
    int update(Sala sala);
    int delete(Long id);
}
