package com.example.Teretana.DAO;

import com.example.Teretana.Model.NivoTreninga;
import com.example.Teretana.Model.TipTreninga;
import com.example.Teretana.Model.Trening;
import com.example.Teretana.Model.VrstaTreninga;

import java.util.List;

public interface TreningDAO {

    Trening findOne(Long id);
    List<Trening> findAll();
    List<Trening> find(String naziv, String treneri, TipTreninga tipTreninga,
                       int cenaOd, int cenaDo, VrstaTreninga vrstaTreninga, NivoTreninga nivoTreninga,
                       boolean rastuce, boolean opadajuce);
    int save(Trening trening);
    int update(Trening trening);
    int delete(Long id);
}
