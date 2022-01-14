package com.example.Teretana.DAO;

import com.example.Teretana.Model.TipTreninga;

import java.util.List;

public interface TipTreningaDAO {
    List<TipTreninga> ucitajTipoveTreninga();
    TipTreninga findOne(Long id);
}
