package com.example.Teretana.Service.impl;

import com.example.Teretana.DAO.TipTreningaDAO;
import com.example.Teretana.Model.TipTreninga;
import com.example.Teretana.Service.TipTreningaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseTipTreningaServiceImpl implements TipTreningaService {

    @Autowired
    private TipTreningaDAO tipTreningaDAO;

    @Override
    public List<TipTreninga> ucitajTipoveTreninga() {
        return tipTreningaDAO.ucitajTipoveTreninga();
    }
}
