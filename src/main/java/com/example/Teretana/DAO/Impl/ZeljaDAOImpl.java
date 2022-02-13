package com.example.Teretana.DAO.Impl;

import com.example.Teretana.DAO.ZeljaDAO;
import com.example.Teretana.Model.Zelja;
import com.example.Teretana.Service.KorisnikService;
import com.example.Teretana.Service.TreningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ZeljaDAOImpl implements ZeljaDAO {

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private TreningService treningService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class ZeljaRowMapper implements RowMapper<Zelja> {

        @Override
        public Zelja mapRow(ResultSet rs, int rowNum) throws SQLException {
            int index = 1;
            Long zeljaId = rs.getLong(index++);
            Long korisnikId = rs.getLong(index++);
            Long treningId = rs.getLong(index++);

            Zelja zelja = new Zelja(zeljaId, korisnikService.findOne(korisnikId), treningService.findOne(treningId));

            return zelja;
        }

    }

    @Override
    public List<Zelja> findAll() {
        String sql ="select * from listaZelja";

        return jdbcTemplate.query(sql, new ZeljaRowMapper());
    }

    @Override
    public List<Zelja> findByKorisnikId(Long id) {
        String sql ="select * from listaZelja where korisnikId = ?";

        return jdbcTemplate.query(sql, new ZeljaRowMapper(), id);
    }

    @Override
    public List<Zelja> findByTreningId(Long id) {
        String sql ="select * from listaZelja where treningId = ?";

        return jdbcTemplate.query(sql, new ZeljaRowMapper(), id);
    }

    @Transactional
    @Override
    public int save(Zelja zelja) {
        String sql = "INSERT INTO listaZelja (korisnikId, treningId) VALUES (?, ?)";
        return jdbcTemplate.update(sql, zelja.getKorisnik().getId(),
                zelja.getTrening().getId());
    }

    @Transactional
    @Override
    public int update(Zelja zelja) {
        String sql = "UPDATE listaZelja SET korisnikId = ?, treningId = ? where id = ?";
        return jdbcTemplate.update(sql, zelja.getKorisnik().getId(),
                zelja.getTrening().getId(), zelja.getId());
    }

    @Transactional
    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM korisnickaKorpa WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
