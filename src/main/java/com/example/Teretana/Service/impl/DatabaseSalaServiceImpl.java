package com.example.Teretana.Service.impl;

import com.example.Teretana.DAO.SalaDAO;
import com.example.Teretana.Model.Sala;
import com.example.Teretana.Service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseSalaServiceImpl implements SalaService {

    @Autowired
    private SalaDAO salaDAO;

    @Override
    public List<Sala> findAll() {
        return salaDAO.findAll();
    }

    @Override
    public Sala findOne(Long id) {
        return salaDAO.findOne(id);
    }

    @Override
    public Sala findOne(String oznaka) {
        return salaDAO.findOne(oznaka);
    }

    @Override
    public List<Sala> find(String oznaka, String rastuce) {
        return salaDAO.find(oznaka, rastuce);
    }

    @Override
    public int save(Sala sala) {
        return salaDAO.save(sala);
    }

    @Override
    public int update(Sala sala) {
        return salaDAO.update(sala);
    }

    @Override
    public int delete(Long id) {
        return salaDAO.delete(id);
    }
}
