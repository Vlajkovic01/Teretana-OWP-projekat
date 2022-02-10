package com.example.Teretana.DAO;

import com.example.Teretana.Model.Sala;

import java.util.List;

public interface SalaDAO {
    List<Sala> findAll();
    Sala findOne(Long id);
    List<Sala> find(String oznaka, String rastuce);
}
