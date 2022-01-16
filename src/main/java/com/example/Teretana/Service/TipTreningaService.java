package com.example.Teretana.Service;

import com.example.Teretana.Model.TipTreninga;

import java.util.List;

public interface TipTreningaService {

    List<TipTreninga> findAll();
    List<TipTreninga> find(Long[] ids);
    TipTreninga findOne(Long id);
}
