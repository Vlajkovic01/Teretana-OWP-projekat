package com.example.Teretana.DAO.Impl;

import com.example.Teretana.DAO.TerminDAO;
import com.example.Teretana.Model.*;
import com.example.Teretana.Service.SalaService;
import com.example.Teretana.Service.TreningService;
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
public class TerminDAOImpl implements TerminDAO {

    @Autowired
    private SalaService salaService;

    @Autowired
    private TreningService treningService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class TerminRowMapper implements RowMapper<Termin> {

        @Override
        public Termin mapRow(ResultSet rs, int rowNum) throws SQLException {
            int index = 1;
            Long terminId = rs.getLong(index++);
            Long salaId = rs.getLong(index++);
            Long treningId = rs.getLong(index++);
            LocalDateTime terminDatumOdrzavanja = rs.getTimestamp(index++).toLocalDateTime();
            LocalDateTime terminDatumOdrzavanjaKraj = rs.getTimestamp(index++).toLocalDateTime();


            Termin termin = new Termin(terminId, salaService.findOne(salaId),
                    treningService.findOne(treningId), terminDatumOdrzavanja);
            return termin;
        }

    }

    @Override
    public List<Termin> findAll() {
        String sql =
                "select * from termini";

        return jdbcTemplate.query(sql, new TerminRowMapper());
    }

    @Override
    public Termin findOne(Long id) {
        String sql =
                "select * from termini where id = ?";

        return jdbcTemplate.queryForObject(sql, new TerminRowMapper(), id);
    }

    @Override
    public List<Termin> findByTreningId(Long id) {
        String sql =
                "select * from termini where treningId = ?";

        return jdbcTemplate.query(sql, new TerminRowMapper(), id);
    }

    @Override
    public boolean findByDateTime(Long idSale,LocalDateTime noviTerminPocetak, LocalDateTime noviTerminKraj) {
        String sql = "select count(*) " +
                "from termini t " +
                "where t.salaId = ? and ? between datumOdrzavanja and datumOdrzavanjaKraj " +
                "or ? between datumOdrzavanja and datumOdrzavanjaKraj " +
                "or (? < datumOdrzavanja and ? > datumOdrzavanjaKraj)";

        Integer uspeh = jdbcTemplate.queryForObject(sql, Integer.class, idSale,
                noviTerminPocetak, noviTerminKraj, noviTerminPocetak, noviTerminKraj);

        return uspeh != null && uspeh > 0;
    }


    @Transactional
    @Override
    public int save(Termin termin) {
        String sql = "INSERT INTO termini (salaId, treningId, datumOdrzavanja, datumOdrzavanjaKraj) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, termin.getSala().getId(),
                termin.getTrening().getId(), termin.getDatumOdrzavanja(),
                termin.getDatumOdrzavanja().plusMinutes(termin.getTrening().getTrajanje()));
    }

    @Transactional
    @Override
    public int update(Termin termin) {
        String sql = "UPDATE termini SET salaId = ?, treningId = ?, datumOdrzavanja = ? WHERE id = ?";
        return jdbcTemplate.update(sql, termin.getSala().getId(), termin.getTrening().getId(), termin.getDatumOdrzavanja(), termin.getId());
    }

    @Transactional
    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM termini WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
