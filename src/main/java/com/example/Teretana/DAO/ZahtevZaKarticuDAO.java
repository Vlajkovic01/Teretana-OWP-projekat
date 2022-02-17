package com.example.Teretana.DAO;

import com.example.Teretana.Model.ZahtevZaKarticu;

import java.util.List;

public interface ZahtevZaKarticuDAO {
    List<ZahtevZaKarticu> findAll();
    ZahtevZaKarticu findOne(Long id);
    ZahtevZaKarticu findbyKorisnikOdobren(Long idKorisnika);
    int save(ZahtevZaKarticu zahtevZaKarticu);
    int update(ZahtevZaKarticu zahtevZaKarticu);
    int delete(Long id);
}
