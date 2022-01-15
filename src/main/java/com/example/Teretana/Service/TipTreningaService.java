package com.example.Teretana.Service;

import com.example.Teretana.Model.TipTreninga;

import java.util.List;

public interface TipTreningaService {

    List<TipTreninga> findAll();
    TipTreninga findOne(Long id);
}
