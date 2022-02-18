package com.example.Teretana.DAO;

import com.example.Teretana.Model.ClanskaKartica;

import java.util.List;

public interface ClanskaKarticaDAO {
    List<ClanskaKartica> findAll();
    ClanskaKartica findOne(Long id);
    ClanskaKartica findbyKorisnik(Long idKorisnika);
    boolean imaKarticu(Long idKorisnika);
    int save(ClanskaKartica clanskaKartica);
    int update(ClanskaKartica clanskaKartica);
    int delete(Long id);
}
