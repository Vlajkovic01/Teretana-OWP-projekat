package com.example.Teretana.DAO;

import com.example.Teretana.Model.Termin;

import java.util.List;

public interface TerminDAO {
    List<Termin> findAll();
    Termin findOne(Long id);
    List<Termin> findByTreningId(Long id);
    int save(Termin termin);
    int update(Termin termin);
    int delete(Long id);
}
