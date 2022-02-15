package com.example.Teretana.DAO.Impl;

import com.example.Teretana.DAO.IzvestajDAO;
import com.example.Teretana.Model.Izvestaj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IzvestajDAOImpl implements IzvestajDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class IzvestajRowMapper implements RowMapper<Izvestaj> {

        @Override
        public Izvestaj mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int index = 1;

            Long treningId = resultSet.getLong(index++);
            String nazivTreninga = resultSet.getString(index++);
            String treneri = resultSet.getString(index++);
            int brojTreninga = resultSet.getInt(index++);
            int ukupnaCena = resultSet.getInt(index++);

            Izvestaj izvestaj = new Izvestaj(treningId, nazivTreninga, treneri, brojTreninga, ukupnaCena);
            return izvestaj;
        }
    }


    @Override
    public List<Izvestaj> nadji(String tipSortiranja, String vremeOd, String vremeDo, String rastuce) {
        ArrayList<Object> listaArgumenata = new ArrayList<Object>();
        listaArgumenata.add(vremeOd);
        listaArgumenata.add(vremeDo);

        String sql = "select tr.id, tr.naziv, tr.treneri, " +
                "count(tr.id) as brojTreninga, sum(tr.cena) as ukupnaCena " +
                "from korisnickaKorpa k left join termini t on k.terminId = t.id left join treninzi tr on t.treningId = tr.id " +
                "where t.datumOdrzavanja and t.datumOdrzavanjaKraj between ? and ? " +
                "group by tr.id ";


        if(tipSortiranja != null) {
            if (tipSortiranja.equals("1")) {
                sql = sql + " order by brojTreninga";
            }
            else if(tipSortiranja.equals("2")){
                sql = sql + " order by ukupnaCena";
            }
        }

        if (rastuce != null && tipSortiranja != null) {
            if (rastuce.equals("1")) {
                sql = sql + " asc";
            } else {
                sql = sql + " desc";
            }
        }
        else if (rastuce != null) {
            if (rastuce.equals("1")) {
                sql = sql + " order by brojTreninga asc";
            } else {
                sql = sql + " order by brojTreninga desc";
            }
        }

        System.out.println(sql);

        return jdbcTemplate.query(sql, listaArgumenata.toArray(), new IzvestajRowMapper());
    }

    @Override
    public int ukupnaCenaSvihTreninga() {
        String sql = "select sum(tr.cena) as ukupnaCena " +
                "from korisnickaKorpa k left join termini t on k.terminId = t.id left join treninzi tr on t.treningId = tr.id ";

        Integer broj = jdbcTemplate.queryForObject(sql, Integer.class);

        return broj;
    }

    @Override
    public int ukupanBrojZakazanihTreninga() {
        String sql = "select count(*) as ukupanBroj " +
                "from korisnickaKorpa k left join termini t on k.terminId = t.id left join treninzi tr on t.treningId = tr.id ";

        Integer broj = jdbcTemplate.queryForObject(sql, Integer.class);

        return broj;
    }
}
