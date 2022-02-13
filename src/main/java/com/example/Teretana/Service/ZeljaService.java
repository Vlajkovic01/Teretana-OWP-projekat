package com.example.Teretana.Service;

import com.example.Teretana.Model.Zelja;

import java.util.List;

public interface ZeljaService {
    List<Zelja> findAll();
    List<Zelja> findByKorisnikId(Long id);
    List<Zelja> findByTreningId(Long id);
    boolean postojiZelja(Long idTreninga, Long idKorisnika);
    int save(Zelja zelja);
    int update(Zelja zelja);
    int delete(Long id);
}
