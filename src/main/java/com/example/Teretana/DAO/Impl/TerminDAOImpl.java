package com.example.Teretana.DAO.Impl;

import com.example.Teretana.DAO.TerminDAO;
import com.example.Teretana.Model.*;
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
    private JdbcTemplate jdbcTemplate;

    private class TerminRowMapper implements RowMapper<Termin> {

        @Override
        public Termin mapRow(ResultSet rs, int rowNum) throws SQLException {
            int index = 1;
            Long terminId = rs.getLong(index++);
            Long salaId = rs.getLong(index++);
            Long treningId = rs.getLong(index++);
            LocalDateTime terminDatumOdrzavanja = rs.getTimestamp(index++).toLocalDateTime();

            Long idSale = rs.getLong(index++);
            String salaOznaka = rs.getString(index++);
            Integer salaKapacitet = rs.getInt(index++);
            Sala sala = new Sala(idSale, salaOznaka, salaKapacitet);

            Long idTreninga = rs.getLong(index++);
            String nazivTreninga = rs.getString(index++);
            String treneriTreninga = rs.getString(index++);
            String kratakOpisTreninga = rs.getString(index++);
            String urlSlikaTreninga = rs.getString(index++);
            int cena = rs.getInt(index++);
            VrstaTreninga vrstaTreninga = VrstaTreninga.valueOf(rs.getString(index++));
            NivoTreninga nivoTreninga = NivoTreninga.valueOf(rs.getString(index++));
            int trajanje = rs.getInt(index++);
            double ocena = rs.getDouble(index++);
            Trening trening = new Trening(idTreninga, nazivTreninga, treneriTreninga, kratakOpisTreninga,
                    urlSlikaTreninga, cena, vrstaTreninga, nivoTreninga, trajanje, ocena);


            Termin termin = new Termin(terminId, sala, trening, terminDatumOdrzavanja);
            return termin;
        }

    }

    @Override
    public List<Termin> findAll() {
        String sql =
                "select t.id, t.salaId, t.treningId, t.datumOdrzavanja, s.id, s.oznaka, s.kapacitet, tr.id, tr.naziv, tr.treneri, tr.kratakOpis, " +
                        "tr.urlSlika, tr.cena, tr.vrstaTreninga, tr.nivoTreninga, tr.trajanje, tr.ocena " +
                        "from termini t " +
                        "left join sale s on t.salaId = s.id " +
                        "left join treninzi tr on t.treningId = tr.id " +
                        "order by t.id";

        return jdbcTemplate.query(sql, new TerminRowMapper());
    }

    @Override
    public Termin findOne(Long id) {
        String sql =
                "select t.id, t.salaId, t.treningId, t.datumOdrzavanja, s.id, s.oznaka, s.kapacitet, tr.id, tr.naziv, tr.treneri, tr.kratakOpis, " +
                        "tr.urlSlika, tr.cena, tr.vrstaTreninga, tr.nivoTreninga, tr.trajanje, tr.ocena " +
                        "from termini t " +
                        "left join sale s on t.salaId = s.id " +
                        "left join treninzi tr on t.treningId = tr.id " +
                        "where t.id = ? " +
                        "order by t.id";

        return jdbcTemplate.queryForObject(sql, new TerminRowMapper(), id);
    }

    @Override
    public List<Termin> findByTreningId(Long id) {
        String sql =
                "select t.id, t.salaId, t.treningId, t.datumOdrzavanja, s.id, s.oznaka, s.kapacitet, tr.id, tr.naziv, tr.treneri, tr.kratakOpis, " +
                        "tr.urlSlika, tr.cena, tr.vrstaTreninga, tr.nivoTreninga, tr.trajanje, tr.ocena " +
                        "from termini t " +
                        "left join sale s on t.salaId = s.id " +
                        "left join treninzi tr on t.treningId = tr.id " +
                        "where t.treningId = ? " +
                        "order by t.id";

        return jdbcTemplate.query(sql, new TerminRowMapper(), id);
    }

    @Transactional
    @Override
    public int save(Termin termin) {
        String sql = "INSERT INTO termini (salaId, treningId, datumOdrzavanja) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, termin.getSala().getId(), termin.getTrening().getId(), termin.getDatumOdrzavanja());
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
