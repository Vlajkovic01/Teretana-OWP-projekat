package com.example.Teretana.Service.impl;

import com.example.Teretana.DAO.SpecijalanDatumDAO;
import com.example.Teretana.Model.SpecijalanDatum;
import com.example.Teretana.Service.SpecijalanDatumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DatabaseSpecijalanDatumServiceImpl implements SpecijalanDatumService {

    @Autowired
    private SpecijalanDatumDAO specijalanDatumDAO;

    @Override
    public List<SpecijalanDatum> findAll() {
        return specijalanDatumDAO.findAll();
    }

    @Override
    public SpecijalanDatum findOne(Long id) {
        return specijalanDatumDAO.findOne(id);
    }

    @Override
    public boolean definisanZaTajDatum(LocalDate datum) {
        return specijalanDatumDAO.definisanZaTajDatum(datum);
    }

    @Override
    public SpecijalanDatum nadjiPoDatumu(LocalDateTime datum) {
        return specijalanDatumDAO.nadjiPoDatumu(datum);
    }

    @Override
    public boolean imaPopusta(LocalDateTime datum) {
        return specijalanDatumDAO.imaPopusta(datum);
    }

    @Override
    public int save(SpecijalanDatum specijalanDatum) {
        return specijalanDatumDAO.save(specijalanDatum);
    }

    @Override
    public int update(SpecijalanDatum specijalanDatum) {
        return specijalanDatumDAO.update(specijalanDatum);
    }

    @Override
    public int delete(Long id) {
        return specijalanDatumDAO.delete(id);
    }
}
