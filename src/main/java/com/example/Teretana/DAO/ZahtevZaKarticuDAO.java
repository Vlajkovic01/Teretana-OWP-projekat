package com.example.Teretana.DAO;

import com.example.Teretana.Model.ZahtevZaKarticu;

import java.util.List;

public interface ZahtevZaKarticuDAO {
    List<ZahtevZaKarticu> findAll();
    ZahtevZaKarticu findOne(Long id);
    List<ZahtevZaKarticu> nadjiNaCekanju();
    ZahtevZaKarticu findbyKorisnikOdobren(Long idKorisnika);
    boolean poslaoZahtev(Long idKorisnika);
    int save(ZahtevZaKarticu zahtevZaKarticu);
    int update(ZahtevZaKarticu zahtevZaKarticu);
    int delete(Long id);
}
