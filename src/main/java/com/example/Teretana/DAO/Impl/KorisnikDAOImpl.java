package com.example.Teretana.DAO.Impl;

import com.example.Teretana.DAO.KorisnikDAO;
import com.example.Teretana.Model.Korisnik;
import com.example.Teretana.Model.Uloga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
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
public class KorisnikDAOImpl implements KorisnikDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class KorisnikRowCallBackHandler implements RowCallbackHandler {

        private Map<Long, Korisnik> korisnici = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            Long id = resultSet.getLong(index++);
            String korisnickoIme = resultSet.getString(index++);
            String lozinka = resultSet.getString(index++);
            String email = resultSet.getString(index++);
            String ime = resultSet.getString(index++);
            String prezime = resultSet.getString(index++);
            LocalDate datumRodjenja = resultSet.getTimestamp(index++).toLocalDateTime().toLocalDate();
            String adresa = resultSet.getString(index++);
            String telefon = resultSet.getString(index++);
            LocalDateTime datumIVremeRegistracije = resultSet.getTimestamp(index++).toLocalDateTime();
            Uloga uloga = Uloga.valueOf(resultSet.getString(index++));
            boolean blokiran = resultSet.getBoolean(index++);

            Korisnik korisnik = korisnici.get(id);
            if (korisnik == null) {
                korisnik = new Korisnik(id, korisnickoIme, lozinka, email, ime, prezime, datumRodjenja, adresa, telefon, datumIVremeRegistracije, uloga, blokiran);
                korisnici.put(korisnik.getId(), korisnik); // dodavanje u kolekciju
            }
        }

        public List<Korisnik> getKorisnici() {
            return new ArrayList<>(korisnici.values());
        }
    }

    @Override
    public Korisnik findOne(Long id) {
        String sql =
                "select * from korisnici where id = ?";

        KorisnikRowCallBackHandler rowCallBackHandler = new KorisnikRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, id);

        try {
            return rowCallBackHandler.getKorisnici().get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Korisnik findOne(String korisnickoIme) {
        String sql =
                "select * from korisnici where korisnickoIme = ?";

        KorisnikRowCallBackHandler rowCallBackHandler = new KorisnikRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, korisnickoIme);

        try {
            return rowCallBackHandler.getKorisnici().get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Korisnik findOne(String korisnickoIme, String lozinka) {
        String sql =
                "select * from korisnici where korisnickoIme = ? and lozinka = ?";

        KorisnikRowCallBackHandler rowCallBackHandler = new KorisnikRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, korisnickoIme, lozinka);

        try {
            return rowCallBackHandler.getKorisnici().get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Korisnik findOneByEmail(String email) {
        String sql =
                "select * from korisnici where email = ?";

        KorisnikRowCallBackHandler rowCallBackHandler = new KorisnikRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler, email);

        try {
            return rowCallBackHandler.getKorisnici().get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Korisnik> findAll() {
        String sql =
                "select * from korisnici";

        KorisnikRowCallBackHandler rowCallBackHandler = new KorisnikRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallBackHandler);

        return rowCallBackHandler.getKorisnici();
    }

    @Transactional
    @Override
    public int save(Korisnik korisnik) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "INSERT INTO korisnici (korisnickoIme, lozinka, email, ime, prezime, datumRodjenja, " +
                        "adresa, telefon, datumIVremeRegistracije, uloga, blokiran) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setString(index++, korisnik.getKorisnickoIme());
                preparedStatement.setString(index++, korisnik.getLozinka());
                preparedStatement.setString(index++, korisnik.getEmail());
                preparedStatement.setString(index++, korisnik.getIme());
                preparedStatement.setString(index++, korisnik.getPrezime());
                preparedStatement.setTimestamp(index++, Timestamp.valueOf(korisnik.getDatumRodjenja().atStartOfDay()));
                preparedStatement.setString(index++, korisnik.getAdresa());
                preparedStatement.setString(index++, korisnik.getTelefon());
                preparedStatement.setTimestamp(index++, Timestamp.valueOf(korisnik.getDatumIVremeRegistracije()));
                preparedStatement.setString(index++, korisnik.getUloga().toString());
                preparedStatement.setBoolean(index++, korisnik.isBlokiran());

                return preparedStatement;
            }
        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
        return uspeh?1:0;
    }

    @Transactional
    @Override
    public int update(Korisnik korisnik) {
        String sql = "UPDATE korisnici SET korisnickoIme = ?, lozinka = ?, email = ?, ime = ?, prezime = ?, datumRodjenja = ?, adresa = ?, " +
                "telefon = ?, datumIVremeRegistracije = ?, uloga = ?, blokiran = ? WHERE id = ?";
        boolean uspeh = jdbcTemplate.update(sql, korisnik.getKorisnickoIme(), korisnik.getLozinka(), korisnik.getEmail(), korisnik.getIme(),
                korisnik.getPrezime(), korisnik.getDatumRodjenja(), korisnik.getAdresa(), korisnik.getTelefon(),
                korisnik.getDatumIVremeRegistracije(), korisnik.getUloga().toString(), korisnik.isBlokiran(), korisnik.getId()) == 1;

        return uspeh?1:0;
    }

    @Transactional
    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM korisnici WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
