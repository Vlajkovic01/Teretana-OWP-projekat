package com.example.Teretana.DAO;

import com.example.Teretana.Model.Komentar;

import java.util.List;

public interface KomentarDAO {
    List<Komentar> findAll();
    List<Komentar> findByStatus(String status);
    Komentar findOne(Long id);
    List<Komentar> findByTreningId(Long idTreninga);
    List<Komentar> findByKorisnikId(Long idKorisnika);
    int save(Komentar komentar);
    int update(Komentar komentar);
    int delete(Long id);
}
