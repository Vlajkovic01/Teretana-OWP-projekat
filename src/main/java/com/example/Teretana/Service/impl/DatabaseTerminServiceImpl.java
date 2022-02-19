package com.example.Teretana.Service.impl;

import com.example.Teretana.DAO.TerminDAO;
import com.example.Teretana.Model.Termin;
import com.example.Teretana.Service.TerminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DatabaseTerminServiceImpl implements TerminService {

    @Autowired
    private TerminDAO terminDAO;

    @Override
    public List<Termin> findAll() {
        return terminDAO.findAll();
    }

    @Override
    public Termin findOne(Long id) {
        return terminDAO.findOne(id);
    }

    @Override
    public List<Termin> findByTreningId(Long id, LocalDateTime trenutnoVreme) {
        return terminDAO.findByTreningId(id, trenutnoVreme);
    }

    @Override
    public boolean findByDateTime(Long idSale, LocalDateTime noviTerminPocetak, LocalDateTime noviTerminKraj) {
        return terminDAO.findByDateTime(idSale, noviTerminPocetak, noviTerminKraj);
    }

    @Override
    public int save(Termin termin) {
        return terminDAO.save(termin);
    }

    @Override
    public int update(Termin termin) {
        return terminDAO.update(termin);
    }

    @Override
    public int delete(Long id) {
        return terminDAO.delete(id);
    }
}
