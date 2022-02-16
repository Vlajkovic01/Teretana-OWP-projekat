package com.example.Teretana.DAO.Impl;

import com.example.Teretana.DAO.KomentarDAO;
import com.example.Teretana.Model.Komentar;
import com.example.Teretana.Model.StatusKomentara;
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
import java.time.LocalDate;
import java.util.List;

@Repository
public class KomentarDAOImpl implements KomentarDAO {

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private TreningService treningService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class KomentarRowMapper implements RowMapper<Komentar> {

        @Override
        public Komentar mapRow(ResultSet rs, int rowNum) throws SQLException {
            int index = 1;
            Long komentarId = rs.getLong(index++);
            String tekst = rs.getString(index++);
            int ocena = rs.getInt(index++);
            LocalDate datum = rs.getTimestamp(index++).toLocalDateTime().toLocalDate();
            Long korisnikId = rs.getLong(index++);
            Long treningId = rs.getLong(index++);
            StatusKomentara status = StatusKomentara.valueOf(rs.getString(index++));
            boolean anoniman = rs.getBoolean(index++);

            Komentar komentar = new Komentar(komentarId, tekst, ocena, datum, korisnikService.findOne(korisnikId),
                    treningService.findOne(treningId), status, anoniman);

            return komentar;
        }

    }

    @Override
    public List<Komentar> findAll() {
        String sql ="select * from komentari";

        return jdbcTemplate.query(sql, new KomentarRowMapper());
    }

    @Override
    public List<Komentar> findByStatus(String status) {
        String sql ="select * from komentari where statusKomentara = ?";

        return jdbcTemplate.query(sql, new KomentarRowMapper(), status);
    }

    @Override
    public Komentar findOne(Long id) {
        String sql ="select * from komentari where id = ?";

        return jdbcTemplate.queryForObject(sql, new KomentarRowMapper(), id);
    }

    @Override
    public List<Komentar> findByTreningId(Long idTreninga) {
        String sql ="select * from komentari where treningId = ?";

        return jdbcTemplate.query(sql, new KomentarRowMapper(), idTreninga);
    }

    @Override
    public List<Komentar> findByKorisnikId(Long idKorisnika) {
        String sql ="select * from komentari where korisnikId = ?";

        return jdbcTemplate.query(sql, new KomentarRowMapper(), idKorisnika);
    }

    @Transactional
    @Override
    public int save(Komentar komentar) {
        String sql = "INSERT INTO komentari (tekst, ocena, datum, korisnikId, treningId, statusKomentara, anoniman) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, komentar.getTekst(), komentar.getOcena(), komentar.getDatum(),
                komentar.getAutor().getId(), komentar.getTrening().getId(), komentar.getStatus().toString(), komentar.isAnoniman());
    }

    @Transactional
    @Override
    public int update(Komentar komentar) {
        String sql = "UPDATE komentari SET tekst = ?, ocena = ?, datum = ?, korisnikId = ?, " +
                "treningId = ?, statusKomentara = ?, anoniman = ? where id = ?";
        return jdbcTemplate.update(sql, komentar.getTekst(), komentar.getOcena(), komentar.getDatum(),
                komentar.getAutor().getId(), komentar.getTrening().getId(), komentar.getStatus().toString(),
                komentar.isAnoniman(), komentar.getId());
    }

    @Transactional
    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM komentari WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
