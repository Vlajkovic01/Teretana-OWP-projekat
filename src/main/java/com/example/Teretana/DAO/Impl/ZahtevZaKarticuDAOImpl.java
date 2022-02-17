package com.example.Teretana.DAO.Impl;

import com.example.Teretana.DAO.ZahtevZaKarticuDAO;
import com.example.Teretana.Model.StatusKomentaraIZahtevaKartice;
import com.example.Teretana.Model.ZahtevZaKarticu;
import com.example.Teretana.Service.KorisnikService;
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
public class ZahtevZaKarticuDAOImpl implements ZahtevZaKarticuDAO {

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class ZahtevZaKarticuRowMapper implements RowMapper<ZahtevZaKarticu> {

        @Override
        public ZahtevZaKarticu mapRow(ResultSet rs, int rowNum) throws SQLException {
            int index = 1;
            Long zahtevId = rs.getLong(index++);
            Long korisnikId = rs.getLong(index++);
            LocalDateTime podnosenjeZahteva = rs.getTimestamp(index++).toLocalDateTime();
            StatusKomentaraIZahtevaKartice status = StatusKomentaraIZahtevaKartice.valueOf(rs.getString(index++));


            ZahtevZaKarticu zahtevZaKarticu = new ZahtevZaKarticu(zahtevId, korisnikService.findOne(korisnikId),
                                                                podnosenjeZahteva, status);
            return zahtevZaKarticu;
        }

    }

    @Override
    public List<ZahtevZaKarticu> findAll() {
        String sql ="select * from zahteviZaKarticu";

        return jdbcTemplate.query(sql, new ZahtevZaKarticuRowMapper());
    }

    @Override
    public ZahtevZaKarticu findOne(Long id) {
        String sql ="select * from zahteviZaKarticu where id = ?";

        return jdbcTemplate.queryForObject(sql, new ZahtevZaKarticuRowMapper(), id);
    }

    @Override
    public ZahtevZaKarticu findbyKorisnikOdobren(Long idKorisnika) {
        String sql ="select * from zahteviZaKarticu where korisnikId = ? and statusZahteva = ?";

        return jdbcTemplate.queryForObject(sql, new ZahtevZaKarticuRowMapper(), idKorisnika, "ODOBREN");
    }

    @Transactional
    @Override
    public int save(ZahtevZaKarticu zahtevZaKarticu) {
        String sql = "INSERT INTO zahteviZaKarticu (korisnikId, podnosenjeZahteva, statusZahteva) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, zahtevZaKarticu.getKorisnik().getId(),
                zahtevZaKarticu.getPodnosenjeZahteva(), zahtevZaKarticu.getStatus());
    }

    @Transactional
    @Override
    public int update(ZahtevZaKarticu zahtevZaKarticu) {
        String sql = "UPDATE zahteviZaKarticu SET korisnikId = ?, podnosenjeZahteva = ?, statusZahteva = ? WHERE id = ?";
        return jdbcTemplate.update(sql, zahtevZaKarticu.getKorisnik().getId(),
                zahtevZaKarticu.getPodnosenjeZahteva(), zahtevZaKarticu.getStatus(), zahtevZaKarticu.getId());
    }

    @Transactional
    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM zahteviZaKarticu WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
