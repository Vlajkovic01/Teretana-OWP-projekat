package com.example.Teretana.DAO;

import com.example.Teretana.Model.Zelja;

import java.util.List;

public interface ZeljaDAO {
    List<Zelja> findAll();
    List<Zelja> findByKorisnikId(Long id);
    List<Zelja> findByTreningId(Long id);
    int save(Zelja zelja);
    int update(Zelja zelja);
    int delete(Long id);
}
