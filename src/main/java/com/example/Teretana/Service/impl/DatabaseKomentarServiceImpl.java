package com.example.Teretana.Service.impl;

import com.example.Teretana.DAO.KomentarDAO;
import com.example.Teretana.Model.Komentar;
import com.example.Teretana.Service.KomentarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseKomentarServiceImpl implements KomentarService {

    @Autowired
    private KomentarDAO komentarDAO;

    @Override
    public List<Komentar> findAll() {
        return komentarDAO.findAll();
    }

    @Override
    public Komentar findOne(Long id) {
        return komentarDAO.findOne(id);
    }

    @Override
    public List<Komentar> findByTreningId(Long idTreninga) {
        return komentarDAO.findByTreningId(idTreninga);
    }

    @Override
    public List<Komentar> findByKorisnikId(Long idKorisnika) {
        return komentarDAO.findByKorisnikId(idKorisnika);
    }

    @Override
    public int save(Komentar komentar) {
        return komentarDAO.save(komentar);
    }

    @Override
    public int update(Komentar komentar) {
        return komentarDAO.update(komentar);
    }

    @Override
    public int delete(Long id) {
        return komentarDAO.delete(id);
    }
}
