package com.example.Teretana.Service;

import com.example.Teretana.Model.SpecijalanDatum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SpecijalanDatumService {
    List<SpecijalanDatum> findAll();
    SpecijalanDatum findOne(Long id);
    boolean definisanZaTajDatum(LocalDate datum, Long idTreninga);
    SpecijalanDatum nadjiPoDatumu(LocalDateTime datum, Long idTreninga);
    boolean imaPopusta(LocalDateTime datum);
    int save(SpecijalanDatum specijalanDatum);
    int update(SpecijalanDatum specijalanDatum);
    int delete(Long id);
}
