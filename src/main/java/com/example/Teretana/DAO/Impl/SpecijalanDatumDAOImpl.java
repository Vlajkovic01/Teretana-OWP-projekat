package com.example.Teretana.DAO.Impl;

import com.example.Teretana.DAO.SpecijalanDatumDAO;
import com.example.Teretana.Model.ClanskaKartica;
import com.example.Teretana.Model.SpecijalanDatum;
import com.example.Teretana.Service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class SpecijalanDatumDAOImpl implements SpecijalanDatumDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class SpecijalanDatumRowMapper implements RowMapper<SpecijalanDatum> {

        @Override
        public SpecijalanDatum mapRow(ResultSet rs, int rowNum) throws SQLException {
            int index = 1;
            Long datumId = rs.getLong(index++);
            LocalDate pocetakDatuma = rs.getTimestamp(index++).toLocalDateTime().toLocalDate();
            LocalDate krajDatuma = rs.getTimestamp(index++).toLocalDateTime().toLocalDate();
            int popust = rs.getInt(index++);

            SpecijalanDatum specijalanDatum = new SpecijalanDatum(datumId, pocetakDatuma, krajDatuma, popust);
            return specijalanDatum;
        }

    }

    @Override
    public List<SpecijalanDatum> findAll() {
        String sql ="select * from specijalniDatumi";

        return jdbcTemplate.query(sql, new SpecijalanDatumRowMapper());
    }

    @Override
    public SpecijalanDatum findOne(Long id) {
        String sql ="select * from specijalniDatumi where id = ?";

        return jdbcTemplate.queryForObject(sql, new SpecijalanDatumRowMapper(), id);
    }

    @Override
    public boolean nadjiPoDatumu(LocalDate datum) {
        String sql = "select count(*) " +
                "from specijalniDatumi " +
                "where pocetakDatuma = ?";

        Integer broj = jdbcTemplate.queryForObject(sql, Integer.class, datum);

        return broj != null && broj > 0;
    }

    @Override
    public int save(SpecijalanDatum specijalanDatum) {
        String sql = "INSERT INTO specijalniDatumi (pocetakDatuma, krajDatuma, popust) VALUES (?, ?, ?)";

        return jdbcTemplate.update(sql, specijalanDatum.getPocetakDatuma(), specijalanDatum.getKrajDatuma(),
                specijalanDatum.getPopust());
    }

    @Override
    public int update(SpecijalanDatum specijalanDatum) {
        String sql = "UPDATE specijalniDatumi SET pocetakDatuma = ?, krajDatuma = ?, popust = ? WHERE id = ?";
        return jdbcTemplate.update(sql, specijalanDatum.getPocetakDatuma(), specijalanDatum.getKrajDatuma(),
                specijalanDatum.getPopust(), specijalanDatum.getId());
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM specijalniDatumi WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
