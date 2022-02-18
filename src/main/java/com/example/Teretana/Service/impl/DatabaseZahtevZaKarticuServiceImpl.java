package com.example.Teretana.Service.impl;

import com.example.Teretana.DAO.ZahtevZaKarticuDAO;
import com.example.Teretana.Model.ZahtevZaKarticu;
import com.example.Teretana.Service.ZahtevZaKarticuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseZahtevZaKarticuServiceImpl implements ZahtevZaKarticuService {

    @Autowired
    private ZahtevZaKarticuDAO zahtevZaKarticuDAO;

    @Override
    public List<ZahtevZaKarticu> findAll() {
        return zahtevZaKarticuDAO.findAll();
    }

    @Override
    public ZahtevZaKarticu findOne(Long id) {
        return zahtevZaKarticuDAO.findOne(id);
    }

    @Override
    public List<ZahtevZaKarticu> nadjiNaCekanju() {
        return zahtevZaKarticuDAO.nadjiNaCekanju();
    }

    @Override
    public ZahtevZaKarticu findbyKorisnikOdobren(Long idKorisnika) {
        return zahtevZaKarticuDAO.findbyKorisnikOdobren(idKorisnika);
    }

    @Override
    public boolean poslaoZahtev(Long idKorisnika) {
        return zahtevZaKarticuDAO.poslaoZahtev(idKorisnika);
    }

    @Override
    public int save(ZahtevZaKarticu zahtevZaKarticu) {
        return zahtevZaKarticuDAO.save(zahtevZaKarticu);
    }

    @Override
    public int update(ZahtevZaKarticu zahtevZaKarticu) {
        return zahtevZaKarticuDAO.update(zahtevZaKarticu);
    }

    @Override
    public int delete(Long id) {
        return zahtevZaKarticuDAO.delete(id);
    }
}
