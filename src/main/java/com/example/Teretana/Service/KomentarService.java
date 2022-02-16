package com.example.Teretana.Service;

import com.example.Teretana.Model.Komentar;

import java.util.List;

public interface KomentarService {
    List<Komentar> findAll();
    Komentar findOne(Long id);
    List<Komentar> findByTreningId(Long idTreninga);
    List<Komentar> findByKorisnikId(Long idKorisnika);
    int save(Komentar komentar);
    int update(Komentar komentar);
    int delete(Long id);
}
