package com.example.Teretana.Service.impl;

import com.example.Teretana.DAO.ClanskaKarticaDAO;
import com.example.Teretana.Model.ClanskaKartica;
import com.example.Teretana.Service.ClanskaKarticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DatabaseClanskaKarticaServiceImpl implements ClanskaKarticaService {

    @Autowired
    private ClanskaKarticaDAO clanskaKarticaDAO;


    @Override
    public List<ClanskaKartica> findAll() {
        return clanskaKarticaDAO.findAll();
    }

    @Override
    public ClanskaKartica findOne(Long id) {
        return clanskaKarticaDAO.findOne(id);
    }

    @Override
    public ClanskaKartica findbyKorisnik(Long idKorisnika) {
        return clanskaKarticaDAO.findbyKorisnik(idKorisnika);
    }

    @Override
    public int save(ClanskaKartica clanskaKartica) {
        return clanskaKarticaDAO.save(clanskaKartica);
    }

    @Override
    public int update(ClanskaKartica clanskaKartica) {
        return clanskaKarticaDAO.update(clanskaKartica);
    }

    @Override
    public int delete(Long id) {
        return clanskaKarticaDAO.delete(id);
    }
}
