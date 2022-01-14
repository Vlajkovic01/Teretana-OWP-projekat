package com.example.Teretana.Service.impl;

import com.example.Teretana.DAO.TreningDAO;
import com.example.Teretana.Model.Trening;
import com.example.Teretana.Service.TreningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseTreningServiceImpl implements TreningService {

    @Autowired
    private TreningDAO treningDAO;

    @Override
    public Trening findOne(Long id) {
        return treningDAO.findOne(id);
    }

    @Override
    public List<Trening> findAll() {
        return treningDAO.findAll();
    }

    @Override
    public List<Trening> find(String naziv, String treneri, Long tipTreningaId, Integer cenaOd, Integer cenaDo, String vrstaTreninga, String nivoTreninga, String sortiranje) {
        return treningDAO.find(naziv, treneri, tipTreningaId, cenaOd, cenaDo, vrstaTreninga, nivoTreninga, sortiranje);
    }

    @Override
    public Trening save(Trening trening) {
        treningDAO.save(trening);
        return trening;
    }

    @Override
    public Trening update(Trening trening) {
        treningDAO.update(trening);
        return trening;
    }

    @Override
    public Trening delete(Long id) {
        Trening trening = treningDAO.findOne(id);
        treningDAO.delete(id);
        return trening;
    }
}
