package com.example.Teretana.DAO.Impl;

import com.example.Teretana.DAO.TipTreningaDAO;
import com.example.Teretana.DAO.TreningDAO;
import com.example.Teretana.Model.NivoTreninga;
import com.example.Teretana.Model.TipTreninga;
import com.example.Teretana.Model.Trening;
import com.example.Teretana.Model.VrstaTreninga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class TreningDAOImpl implements TreningDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TipTreningaDAO tipTreningaDAO;

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
    public List<Trening> find(String naziv, String treneri, Long tipTreningaId, Integer cenaOd, Integer cenaDo, String vrstaTreninga, String nivoTreninga, String sortiranje) {

        ArrayList<Object> listaArgumenata = new ArrayList<Object>();

        String sql = "select t.id, t.naziv, t.treneri, t.kratakOpis, t.urlSlika, t.cena, " +
                "t.vrstaTreninga, t.nivoTreninga, t.trajanje, t.ocena from treninzi t ";

        StringBuffer whereSql = new StringBuffer(" where ");
        boolean imaArgumenata = false;


        if(naziv!=null) {
            naziv = "%" + naziv + "%";
            if(imaArgumenata)
                whereSql.append(" and ");
            whereSql.append("t.naziv like ?");
            imaArgumenata = true;
            listaArgumenata.add(naziv);
        }

        if(treneri!=null) {
            treneri = "%" + treneri + "%";
            if(imaArgumenata)
                whereSql.append(" and ");
            whereSql.append("t.treneri like ?");
            imaArgumenata = true;
            listaArgumenata.add(treneri);
        }

        if(cenaOd!=null) {
            if(imaArgumenata)
                whereSql.append(" and ");
            whereSql.append("t.cena >= ?");
            imaArgumenata = true;
            listaArgumenata.add(cenaOd);
        }

        if(cenaDo!=null) {
            if(imaArgumenata)
                whereSql.append(" and ");
            whereSql.append("t.cena <= ?");
            imaArgumenata = true;
            listaArgumenata.add(cenaDo);
        }

        if(vrstaTreninga!=null) {
            vrstaTreninga = "%" + vrstaTreninga + "%";
            if(imaArgumenata)
                whereSql.append(" and ");
            whereSql.append("t.vrstaTreninga like ?");
            imaArgumenata = true;
            listaArgumenata.add(vrstaTreninga);
        }

        if(nivoTreninga!=null) {
            nivoTreninga = "%" + nivoTreninga + "%";
            if(imaArgumenata)
                whereSql.append(" and ");
            whereSql.append("t.nivoTreninga like ?");
            imaArgumenata = true;
            listaArgumenata.add(nivoTreninga);
        }

        if (imaArgumenata) {
            sql = sql + whereSql.toString();
        }

        if(sortiranje != null) {
            if (sortiranje.equals("1")) {
                sql = sql + " order by t.ocena asc";
            }
            else {
                sql = sql + " order by t.ocena desc";
            }

        }
        System.out.println(sql);
        System.out.println(listaArgumenata);

        List<Trening> treninzi = jdbcTemplate.query(sql, listaArgumenata.toArray(), new TreningRowMapper());

        for (Trening trening : treninzi) {
            trening.setTipTreninga(findTreningTip(trening.getId(), null));
        }

        if(tipTreningaId!=null)
            for (Iterator iterator = treninzi.iterator(); iterator.hasNext();) {
                Trening trening = (Trening) iterator.next();
                boolean zaBrisanje = true;
                for (TipTreninga tip : trening.getTipTreninga()) {
                    if(tip.getId() == tipTreningaId) {
                        zaBrisanje = false;
                        break;
                    }
                }
                if(zaBrisanje)
                    iterator.remove();
            }

        return treninzi;
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

    private class TreningRowMapper implements RowMapper<Trening> {

        @Override
        public Trening mapRow(ResultSet resultSet, int rowNum) throws SQLException {
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

            Trening trening = new Trening(treningId, naziv, treneri, kratakOpis, urlSlika, cena, vrstaTreninga, nivoTreninga, trajanje, ocena);
            return trening;
        }
    }

    private class TreningTipRowMapper implements RowMapper<Long []> {

        @Override
        public Long [] mapRow(ResultSet rs, int rowNum) throws SQLException {
            int index = 1;
            Long treningId = rs.getLong(index++);
            Long tipId = rs.getLong(index++);

            Long [] treningTip = {treningId, tipId};
            return treningTip;
        }
    }

    private List<TipTreninga> findTreningTip(Long treningId, Long tipId) {

        List<TipTreninga> tipoviTreninga = new ArrayList<TipTreninga>();

        ArrayList<Object> listaArgumenata = new ArrayList<Object>();

        String sql =
                "select tp.treningId, tp.tipId from treninziTipovi tp ";

        StringBuffer whereSql = new StringBuffer(" where ");
        boolean imaArgumenata = false;

        if(treningId!=null) {
            if(imaArgumenata)
                whereSql.append(" and ");
            whereSql.append("tp.treningId = ?");
            imaArgumenata = true;
            listaArgumenata.add(treningId);
        }

        if(tipId!=null) {
            if(imaArgumenata)
                whereSql.append(" and ");
            whereSql.append("tp.tipId = ?");
            imaArgumenata = true;
            listaArgumenata.add(tipId);
        }

        if(imaArgumenata)
            sql=sql + whereSql.toString()+" order by tp.treningId";
        else
            sql=sql + " order by tp.treningId";


        List<Long[]> treningTipovi = jdbcTemplate.query(sql, listaArgumenata.toArray(), new TreningTipRowMapper());

        for (Long[] tp : treningTipovi) {
            tipoviTreninga.add(tipTreningaDAO.findOne(tp[1]));
        }
        return tipoviTreninga;
    }
}
