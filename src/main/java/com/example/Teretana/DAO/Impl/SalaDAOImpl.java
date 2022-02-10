package com.example.Teretana.DAO.Impl;

import com.example.Teretana.DAO.SalaDAO;
import com.example.Teretana.Model.Korisnik;
import com.example.Teretana.Model.Sala;
import com.example.Teretana.Model.Uloga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SalaDAOImpl implements SalaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class SalaRowCallBackHandler implements RowCallbackHandler {

        private Map<Long, Sala> sale = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;

            Long salaId = resultSet.getLong(index++);
            String oznaka = resultSet.getString(index++);
            int kapacitet = resultSet.getInt(index++);

            Sala sala = sale.get(salaId);
            if (sala == null) {
                sala = new Sala(salaId, oznaka, kapacitet);
                sale.put(sala.getId(), sala);
            }

        }

        public List<Sala> getSale() {
            return new ArrayList<>(sale.values());
        }

    }

    private class SalaRowMapper implements RowMapper<Sala> {

        @Override
        public Sala mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int index = 1;
            Long salaId = resultSet.getLong(index++);
            String oznaka = resultSet.getString(index++);
            int kapacitet = resultSet.getInt(index++);

            Sala sala = new Sala(salaId, oznaka, kapacitet);
            return sala;
        }

    }

    @Override
    public List<Sala> findAll() {
        String sql = "select id, oznaka, kapacitet from sale";

        SalaRowCallBackHandler rowCallBackHandler = new SalaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler);

        return rowCallBackHandler.getSale();
    }

    @Override
    public Sala findOne(Long id) {
        String sql =
                "select * from sale where id = ?";

        SalaRowCallBackHandler rowCallBackHandler = new SalaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, id);

        try {
            return rowCallBackHandler.getSale().get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Sala findOne(String oznaka) {
        String sql =
                "select * from sale where oznaka = ?";

        SalaRowCallBackHandler rowCallBackHandler = new SalaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, oznaka);

        try {
            return rowCallBackHandler.getSale().get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Sala> find(String oznaka, String rastuce) {

        ArrayList<Object> listaArgumenata = new ArrayList<Object>();

        String sql = "select id, oznaka, kapacitet from sale";

        StringBuffer whereSql = new StringBuffer(" where ");
        boolean imaArgumenata = false;

        if(oznaka!=null) {
            oznaka = "%" + oznaka + "%";
            if(imaArgumenata)
                whereSql.append(" and ");
            whereSql.append("oznaka like ?");
            imaArgumenata = true;
            listaArgumenata.add(oznaka);
        }

        if (imaArgumenata) {
            sql = sql + whereSql.toString();
        }

        if (rastuce != null) {
            if (rastuce.equals("1")) {
                sql = sql + " order by oznaka asc";
            } else {
                sql = sql + " order by oznaka desc";
            }
        }

        System.out.println(sql);

        return jdbcTemplate.query(sql, listaArgumenata.toArray(), new SalaRowMapper());
    }

    @Transactional
    @Override
    public int save(Sala sala) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "INSERT INTO sale (oznaka, kapacitet) VALUES (?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setString(index++, sala.getOznaka());
                preparedStatement.setInt(index++, sala.getKapacitet());

                return preparedStatement;
            }
        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
        return uspeh?1:0;
    }

    @Transactional
    @Override
    public int update(Sala sala) {
        String sql = "UPDATE sale SET kapacitet = ? WHERE id = ?";
        boolean uspeh = jdbcTemplate.update(sql, sala.getKapacitet(), sala.getId()) == 1;

        return uspeh?1:0;
    }

    @Transactional
    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM sale WHERE id = ? AND id NOT IN " +
                "(SELECT salaId FROM termini)";
        boolean uspeh = jdbcTemplate.update(sql, id) == 1;

        return uspeh?1:0;
    }
}
