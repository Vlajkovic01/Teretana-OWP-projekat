package com.example.Teretana.DAO.Impl;

import com.example.Teretana.DAO.TipTreningaDAO;
import com.example.Teretana.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TipTreningaDAOImpl implements TipTreningaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class TipTreningaRowCallBackHandler implements RowCallbackHandler {

        private Map<Long, TipTreninga> tipovi = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;

            Long tipId = resultSet.getLong(index++);
            String ime = resultSet.getString(index++);
            String opis = resultSet.getString(index++);

            TipTreninga tipTreninga = tipovi.get(tipId);
            if (tipTreninga == null) {
                tipTreninga = new TipTreninga(tipId, ime, opis);
                tipovi.put(tipTreninga.getId(), tipTreninga);
            }

        }

        public List<TipTreninga> getTipovi() {
            return new ArrayList<>(tipovi.values());
        }

    }

    @Override
    public List<TipTreninga> findAll() {
        String sql = "select id, ime, opis from tipoviTreninga";

        TipTreningaRowCallBackHandler rowCallBackHandler = new TipTreningaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler);

        return rowCallBackHandler.getTipovi();
    }

    @Override
    public TipTreninga findOne(Long id) {
        String sql =
                "select * from tipoviTreninga where id = ?";

        TipTreningaRowCallBackHandler rowCallBackHandler = new TipTreningaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, id);

        try {
            return rowCallBackHandler.getTipovi().get(0);
        } catch (Exception ex) {
            return null;
        }
    }
}
