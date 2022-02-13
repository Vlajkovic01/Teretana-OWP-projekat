package com.example.Teretana.Service.impl;

import com.example.Teretana.DAO.ZeljaDAO;
import com.example.Teretana.Model.Zelja;
import com.example.Teretana.Service.ZeljaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseZeljaServiceImpl implements ZeljaService {

    @Autowired
    private ZeljaDAO zeljaDAO;

    @Override
    public List<Zelja> findAll() {
        return zeljaDAO.findAll();
    }

    @Override
    public List<Zelja> findByKorisnikId(Long id) {
        return zeljaDAO.findByKorisnikId(id);
    }

    @Override
    public List<Zelja> findByTreningId(Long id) {
        return zeljaDAO.findByTreningId(id);
    }

    @Override
    public int save(Zelja zelja) {
        return zeljaDAO.save(zelja);
    }

    @Override
    public int update(Zelja zelja) {
        return zeljaDAO.update(zelja);
    }

    @Override
    public int delete(Long id) {
        return zeljaDAO.delete(id);
    }
}
