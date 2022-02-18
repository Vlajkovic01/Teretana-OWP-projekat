package com.example.Teretana.Service;

import com.example.Teretana.Model.SpecijalanDatum;

import java.util.List;

public interface SpecijalanDatumService {
    List<SpecijalanDatum> findAll();
    SpecijalanDatum findOne(Long id);
    int save(SpecijalanDatum specijalanDatum);
    int update(SpecijalanDatum specijalanDatum);
    int delete(Long id);
}
