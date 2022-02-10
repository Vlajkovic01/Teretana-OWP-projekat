package com.example.Teretana.DAO;

import com.example.Teretana.Model.Sala;
import com.example.Teretana.Model.Trening;

import java.util.List;

public interface SalaDAO {
    List<Sala> findAll();
    Sala findOne(Long id);
    Sala findOne(String oznaka);
    List<Sala> find(String oznaka, String rastuce);
    int save(Sala sala);
    int update(Sala sala);
    int delete(Long id);
}
