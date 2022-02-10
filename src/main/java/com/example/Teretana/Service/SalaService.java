package com.example.Teretana.Service;

import com.example.Teretana.Model.Sala;

import java.util.List;

public interface SalaService {
    List<Sala> findAll();
    Sala findOne(Long id);
    List<Sala> find(String oznaka, String rastuce);
}
