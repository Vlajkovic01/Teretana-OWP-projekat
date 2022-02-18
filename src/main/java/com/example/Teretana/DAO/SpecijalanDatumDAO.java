package com.example.Teretana.DAO;

import com.example.Teretana.Model.SpecijalanDatum;

import java.util.List;

public interface SpecijalanDatumDAO {
    List<SpecijalanDatum> findAll();
    SpecijalanDatum findOne(Long id);
    int save(SpecijalanDatum specijalanDatum);
    int update(SpecijalanDatum specijalanDatum);
    int delete(Long id);
}
