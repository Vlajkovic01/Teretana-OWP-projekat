package com.example.Teretana.Service;

import com.example.Teretana.Model.ClanskaKartica;

import java.util.List;

public interface ClanskaKarticaService {
    List<ClanskaKartica> findAll();
    ClanskaKartica findOne(Long id);
    ClanskaKartica findbyKorisnik(Long idKorisnika);
    int save(ClanskaKartica clanskaKartica);
    int update(ClanskaKartica clanskaKartica);
    int delete(Long id);
}
