package com.example.Teretana.DAO.Impl;

import com.example.Teretana.DAO.KorisnickaKorpaDAO;
import com.example.Teretana.Model.*;
import com.example.Teretana.Service.KorisnikService;
import com.example.Teretana.Service.TerminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class KorisnickaKorpaDAOImpl implements KorisnickaKorpaDAO {

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private TerminService terminService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class KorisnickaKorpaRowMapper implements RowMapper<KorisnickaKorpa> {

        @Override
        public KorisnickaKorpa mapRow(ResultSet rs, int rowNum) throws SQLException {
            int index = 1;
            Long korpaId = rs.getLong(index++);
            Long korisnikId = rs.getLong(index++);
            Long terminId = rs.getLong(index++);
            LocalDateTime datumRezervacije = rs.getTimestamp(index++).toLocalDateTime();


            KorisnickaKorpa korisnickaKorpa = new KorisnickaKorpa(korpaId, korisnikService.findOne(korisnikId),
                    terminService.findOne(terminId), datumRezervacije);
            return korisnickaKorpa;
        }

    }

    @Override
    public List<KorisnickaKorpa> findAll() {
        String sql ="select * from korisnickaKorpa";

        return jdbcTemplate.query(sql, new KorisnickaKorpaRowMapper());
    }

    @Override
    public List<KorisnickaKorpa> findByKorisnikId(Long id) {
        String sql ="select * from korisnickaKorpa where korisnikId = ?";

        return jdbcTemplate.query(sql, new KorisnickaKorpaRowMapper(), id);
    }

    @Override
    public List<KorisnickaKorpa> findByTerminId(Long id) {
        String sql ="select * from korisnickaKorpa where terminId = ?";

        return jdbcTemplate.query(sql, new KorisnickaKorpaRowMapper(), id);
    }

    @Override
    public boolean proveraKapaciteta(Long id, int kapacitetSale) {
        String sql = "select count(terminId) from korisnickaKorpa where terminId = ?";

        Integer uspeh = jdbcTemplate.queryForObject(sql, Integer.class, id);

        return uspeh == null || uspeh != kapacitetSale;
    }

    @Override
    public boolean proveraVremena(Long idKorisnika, LocalDateTime noviTerminPocetak, LocalDateTime noviTerminKraj) {
        String sql = "select count(*) " +
                "from korisnickaKorpa k left join termini t on k.terminId = t.id " +
                "where k.korisnikId = ? and ? between datumOdrzavanja and datumOdrzavanjaKraj " +
                "or ? between datumOdrzavanja and datumOdrzavanjaKraj " +
                "or (? < datumOdrzavanja and ? > datumOdrzavanjaKraj)";

        Integer uspeh = jdbcTemplate.queryForObject(sql, Integer.class, idKorisnika,
                noviTerminPocetak, noviTerminKraj, noviTerminPocetak, noviTerminKraj);

        return uspeh != null && uspeh > 0;
    }

    @Transactional
    @Override
    public int save(KorisnickaKorpa korisnickaKorpa) {
        String sql = "INSERT INTO korisnickaKorpa (korisnikId, terminId, datumRezervacije) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, korisnickaKorpa.getKorisnik().getId(),
                korisnickaKorpa.getTermin().getId(), korisnickaKorpa.getDatumRezervacije());
    }

    @Transactional
    @Override
    public int update(KorisnickaKorpa korisnickaKorpa) {
        String sql = "UPDATE korisnickaKorpa SET korisnikId = ?, terminId = ?, datumRezervacije = ? where id = ?";
        return jdbcTemplate.update(sql, korisnickaKorpa.getKorisnik().getId(),
                korisnickaKorpa.getTermin().getId(), korisnickaKorpa.getDatumRezervacije(), korisnickaKorpa.getId());
    }

    @Transactional
    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM korisnickaKorpa WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
