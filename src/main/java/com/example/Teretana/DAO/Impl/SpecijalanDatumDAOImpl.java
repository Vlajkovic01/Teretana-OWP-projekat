package com.example.Teretana.DAO.Impl;

import com.example.Teretana.DAO.SpecijalanDatumDAO;
import com.example.Teretana.Model.*;
import com.example.Teretana.Service.TreningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SpecijalanDatumDAOImpl implements SpecijalanDatumDAO {

    @Autowired
    private TreningService treningService;

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

    private class SpecijalanDatumRowCallBackHandler implements RowCallbackHandler {

        private Map<Long, SpecijalanDatum> datumi = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;

            Long id = resultSet.getLong(index++);
            LocalDate pocetakDatuma = resultSet.getTimestamp(index++).toLocalDateTime().toLocalDate();
            LocalDate krajDatuma = resultSet.getTimestamp(index++).toLocalDateTime().toLocalDate();
            int popust = resultSet.getInt(index++);

            SpecijalanDatum specijalanDatum = datumi.get(id);
            if (specijalanDatum == null) {
                specijalanDatum = new SpecijalanDatum(id, pocetakDatuma, krajDatuma, popust);
                datumi.put(specijalanDatum.getId(), specijalanDatum);
            }

            Long datumId = resultSet.getLong(index++);
            Long treningId = resultSet.getLong(index++);

            specijalanDatum.getTreninzi().add(treningService.findOne(treningId));
        }

        public List<SpecijalanDatum> getSpecijalniDatumi() {
            return new ArrayList<>(datumi.values());
        }

    }

    @Override
    public List<SpecijalanDatum> findAll() {
        String sql ="select * from specijalniDatumi s left join datumiTreninzi d on s.id = d.datumId";

        SpecijalanDatumRowCallBackHandler rowCallBackHandler = new SpecijalanDatumRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler);

        return rowCallBackHandler.getSpecijalniDatumi();
    }

    @Override
    public SpecijalanDatum findOne(Long id) {
        String sql ="select * from specijalniDatumi where id = ?";

        return jdbcTemplate.queryForObject(sql, new SpecijalanDatumRowMapper(), id);
    }

    @Override
    public boolean definisanZaTajDatum(LocalDate datum, Long idTreninga) {
        String sql = "select count(*) " +
                "from specijalniDatumi s left join datumiTreninzi d on s.id = d.datumId " +
                "where pocetakDatuma = ? and d.treningId = ?";

        Integer broj = jdbcTemplate.queryForObject(sql, Integer.class, datum, idTreninga);

        return broj != null && broj > 0;
    }

    @Override
    public SpecijalanDatum nadjiPoDatumu(LocalDateTime datum, Long idTreninga) {
        String sql ="select * " +
                "from specijalniDatumi s left join datumiTreninzi d on s.id = d.datumId " +
                "where ? between pocetakDatuma and krajDatuma and d.treningId = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new SpecijalanDatumRowMapper(), datum, idTreninga);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public boolean imaPopusta(LocalDateTime datum) {
        String sql = "select count(*) " +
                "from specijalniDatumi " +
                "where ? between pocetakDatuma and krajDatuma";

        Integer broj = jdbcTemplate.queryForObject(sql, Integer.class, datum);

        return broj != null && broj > 0;
    }

    @Override
    public int save(SpecijalanDatum specijalanDatum) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "insert into specijalniDatumi(pocetakDatuma, krajDatuma, popust) " +
                        "values (?, ?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setTimestamp(index++, Timestamp.valueOf(specijalanDatum.getPocetakDatuma().atStartOfDay()));
                preparedStatement.setTimestamp(index++, Timestamp.valueOf(specijalanDatum.getKrajDatuma().atStartOfDay()));
                preparedStatement.setInt(index++, specijalanDatum.getPopust());

                return preparedStatement;
            }

        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
        if (uspeh) {
            String sql = "insert into datumiTreninzi (datumId, treningId) VALUES (?, ?)";
            for (Trening itTrening: specijalanDatum.getTreninzi()) {
                uspeh = uspeh && jdbcTemplate.update(sql, keyHolder.getKey(), itTrening.getId()) == 1;
            }
        }
        return uspeh?1:0;
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
