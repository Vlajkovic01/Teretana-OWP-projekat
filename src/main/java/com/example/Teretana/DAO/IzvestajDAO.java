package com.example.Teretana.DAO;

import com.example.Teretana.Model.Izvestaj;
import com.example.Teretana.Model.Trening;

import java.util.List;

public interface IzvestajDAO {
    List<Izvestaj> nadji(String tipSortiranja, String vremeOd, String vremeDo, String rastuce);
    int ukupnaCenaSvihTreninga();
    int ukupanBrojZakazanihTreninga();
}
