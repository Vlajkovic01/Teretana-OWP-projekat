package com.example.Teretana.DAO.Impl;

import com.example.Teretana.DAO.ClanskaKarticaDAO;
import com.example.Teretana.Model.ClanskaKartica;
import com.example.Teretana.Service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClanskaKarticaDAOImpl implements ClanskaKarticaDAO {

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class ClanskaKarticaRowMapper implements RowMapper<ClanskaKartica> {

        @Override
        public ClanskaKartica mapRow(ResultSet rs, int rowNum) throws SQLException {
            int index = 1;
            Long karticaId = rs.getLong(index++);
            Long korisnikId = rs.getLong(index++);
            int brojBodova = rs.getInt(index++);

            ClanskaKartica clanskaKartica = new ClanskaKartica(karticaId, korisnikService.findOne(korisnikId), brojBodova);
            return clanskaKartica;
        }

    }

    @Override
    public List<ClanskaKartica> findAll() {
        String sql ="select * from clanskeKartice";

        return jdbcTemplate.query(sql, new ClanskaKarticaRowMapper());
    }

    @Override
    public ClanskaKartica findOne(Long id) {
        String sql ="select * from clanskeKartice where id = ?";

        return jdbcTemplate.queryForObject(sql, new ClanskaKarticaRowMapper(), id);
    }

    @Override
    public ClanskaKartica findbyKorisnik(Long idKorisnika) {
        String sql ="select * from clanskeKartice where korisnikId = ?";

        return jdbcTemplate.queryForObject(sql, new ClanskaKarticaRowMapper(), idKorisnika);
    }

    @Override
    public boolean imaKarticu(Long idKorisnika) {
        String sql = "select count(*) " +
                "from clanskeKartice " +
                "where korisnikId = ?";

        Integer broj = jdbcTemplate.queryForObject(sql, Integer.class, idKorisnika);

        return broj != null && broj > 0;
    }

    @Transactional
    @Override
    public int save(ClanskaKartica clanskaKartica) {
        String sql = "INSERT INTO clanskeKartice (korisnikId, brojBodova) VALUES (?, ?)";
        return jdbcTemplate.update(sql, clanskaKartica.getKorisnik().getId(), clanskaKartica.getBrojBodova());
    }

    @Transactional
    @Override
    public int update(ClanskaKartica clanskaKartica) {
        String sql = "UPDATE clanskeKartice SET korisnikId = ?, brojBodova = ? WHERE id = ?";
        return jdbcTemplate.update(sql, clanskaKartica.getKorisnik().getId(),
                clanskaKartica.getBrojBodova(), clanskaKartica.getId());
    }

    @Transactional
    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM clanskeKartice WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
