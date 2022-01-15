package com.example.Teretana.DAO;

import com.example.Teretana.Model.TipTreninga;

import java.util.List;

public interface TipTreningaDAO {
    List<TipTreninga> findAll();
    TipTreninga findOne(Long id);
}
