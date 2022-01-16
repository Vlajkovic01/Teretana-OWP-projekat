package com.example.Teretana.Service.impl;

import com.example.Teretana.DAO.TipTreningaDAO;
import com.example.Teretana.Model.TipTreninga;
import com.example.Teretana.Service.TipTreningaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseTipTreningaServiceImpl implements TipTreningaService {

    @Autowired
    private TipTreningaDAO tipTreningaDAO;

    @Override
    public List<TipTreninga> findAll() {
        return tipTreningaDAO.findAll();
    }

    @Override
    public List<TipTreninga> find(Long[] ids) {
        List<TipTreninga> rezultat = new ArrayList<>();
        for (Long id: ids) {
            TipTreninga tipTreninga = tipTreningaDAO.findOne(id);
            rezultat.add(tipTreninga);
        }
        return rezultat;
    }

    @Override
    public TipTreninga findOne(Long id) {
        return tipTreningaDAO.findOne(id);
    }

}
