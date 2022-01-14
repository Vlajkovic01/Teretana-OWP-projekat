package com.example.Teretana.DAO.Impl;

import com.example.Teretana.DAO.TreningDAO;
import com.example.Teretana.Model.NivoTreninga;
import com.example.Teretana.Model.TipTreninga;
import com.example.Teretana.Model.Trening;
import com.example.Teretana.Model.VrstaTreninga;
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
public class TreningDAOImpl implements TreningDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class TreningTipRowCallBackHandler implements RowCallbackHandler {

        private Map<Long, Trening> treninzi = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;

            Long treningId = resultSet.getLong(index++);
            String naziv = resultSet.getString(index++);
            String treneri = resultSet.getString(index++);
            String kratakOpis = resultSet.getString(index++);
            String urlSlika = resultSet.getString(index++);
            int cena = resultSet.getInt(index++);
            VrstaTreninga vrstaTreninga = VrstaTreninga.valueOf(resultSet.getString(index++));
            NivoTreninga nivoTreninga = NivoTreninga.valueOf(resultSet.getString(index++));
            int trajanje = resultSet.getInt(index++);
            double ocena = resultSet.getDouble(index++);

            Trening trening = treninzi.get(treningId);
            if (trening == null) {
                trening = new Trening(treningId, naziv, treneri, kratakOpis, urlSlika, cena, vrstaTreninga, nivoTreninga, trajanje, ocena);
                treninzi.put(trening.getId(), trening);
            }

            Long tipId = resultSet.getLong(index++);
            String tipNaziv = resultSet.getString(index++);
            String tipOpis = resultSet.getString(index++);

            TipTreninga tipTreninga = new TipTreninga(tipId, tipNaziv, tipOpis);
            trening.getTipTreninga().add(tipTreninga);
        }

        public List<Trening> getTreninzi() {
            return new ArrayList<>(treninzi.values());
        }

    }

    @Override
    public Trening findOne(Long id) {
        String sql = "select t.id, t.naziv, t.treneri, t.kratakOpis, t.urlSlika, t.cena, t.vrstaTreninga, t.nivoTreninga, t.trajanje, t.ocena, ttr.id, ttr.ime, ttr.opis " +
                "from treninzi t left join treninziTipovi tp on tp.treningId = t.id left join tipoviTreninga ttr on tp.tipId = ttr.id " +
                "where t.id = ? " +
                "order by t.id";

        TreningTipRowCallBackHandler rowCallBackHandler = new TreningTipRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, id);

        try {
            return rowCallBackHandler.getTreninzi().get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Trening> findAll() {
        String sql = "select t.id, t.naziv, t.treneri, t.kratakOpis, t.urlSlika, t.cena, t.vrstaTreninga, t.nivoTreninga, t.trajanje, t.ocena, ttr.id, ttr.ime, ttr.opis " +
                "from treninzi t left join treninziTipovi tp on tp.treningId = t.id left join tipoviTreninga ttr on tp.tipId = ttr.id " +
                "order by t.id";

        TreningTipRowCallBackHandler rowCallBackHandler = new TreningTipRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler);

        return rowCallBackHandler.getTreninzi();
    }

    @Override
    public int save(Trening trening) {
        return 0;
    }

    @Override
    public int update(Trening trening) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }
}
