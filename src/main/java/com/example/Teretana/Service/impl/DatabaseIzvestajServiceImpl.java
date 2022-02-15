package com.example.Teretana.Service.impl;

import com.example.Teretana.DAO.IzvestajDAO;
import com.example.Teretana.Model.Izvestaj;
import com.example.Teretana.Service.IzvestajService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseIzvestajServiceImpl implements IzvestajService {

    @Autowired
    private IzvestajDAO izvestajDAO;

    @Override
    public List<Izvestaj> nadji(String tipSortiranja, String vremeOd, String vremeDo, String rastuce) {
        return izvestajDAO.nadji(tipSortiranja, vremeOd, vremeDo, rastuce);
    }

    @Override
    public int ukupnaCenaSvihTreninga() {
        return izvestajDAO.ukupnaCenaSvihTreninga();
    }

    @Override
    public int ukupanBrojZakazanihTreninga() {
        return izvestajDAO.ukupanBrojZakazanihTreninga();
    }
}
