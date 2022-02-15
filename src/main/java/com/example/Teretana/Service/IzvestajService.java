package com.example.Teretana.Service;

import com.example.Teretana.Model.Izvestaj;

import java.util.List;

public interface IzvestajService {
    List<Izvestaj> nadji(String tipSortiranja, String vremeOd, String vremeDo, String rastuce);
    int ukupnaCenaSvihTreninga();
    int ukupanBrojZakazanihTreninga();
}
