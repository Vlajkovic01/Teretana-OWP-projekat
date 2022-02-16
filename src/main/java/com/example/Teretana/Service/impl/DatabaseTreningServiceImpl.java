package com.example.Teretana.Service.impl;

import com.example.Teretana.DAO.TreningDAO;
import com.example.Teretana.Model.Trening;
import com.example.Teretana.Service.TreningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class DatabaseTreningServiceImpl implements TreningService {

    @Autowired
    private TreningDAO treningDAO;

    @Override
    public Trening findOne(Long id) {
        return treningDAO.findOne(id);
    }

    @Override
    public Trening findOne(String naziv) {
        return treningDAO.findOne(naziv);
    }

    @Override
    public List<Trening> findAll() {
        return treningDAO.findAll();
    }

    @Override
    public List<Trening> find(String naziv, String treneri, Long tipTreningaId, Integer cenaOd,
                              Integer cenaDo, String vrstaTreninga, String nivoTreninga, String tipSortiranja, String rastuce) {
        return treningDAO.find(naziv, treneri, tipTreningaId, cenaOd, cenaDo, vrstaTreninga, nivoTreninga, tipSortiranja, rastuce);
    }

    @Override
    public String validacija(String naziv, String treneri, String kratakOpis, Integer cena, String vrsta,
                             String nivo, Integer trajanje, Double ocena) {
        String poruka = " Ispravi gresku: ";
        boolean postojiGreska = false;

        if (naziv.length() > 20 || naziv.length() < 3 ) {
            poruka += "-Naziv ne moze biti duzi od 20 i manji od 3 karaktera.\n";
            postojiGreska = true;
        }

        if (treneri.length() > 35 || treneri.length() < 3 ) {
            poruka += "-Treneri ne mogu biti duzi od 35 i manji od 3 karaktera.\n";
            postojiGreska = true;
        }

        if (kratakOpis.length() > 35 || kratakOpis.length() < 3 ) {
            poruka += "-Opis ne moze biti duzi od 35 i manji od 3 karaktera.\n";
            postojiGreska = true;
        }

        if (cena == null) {
            poruka += "-Morate uneti cenu.\n";
            postojiGreska = true;
        }
        else if (cena < 0) {
            poruka += "-Cena ne moze biti negativna vrednost.\n";
            postojiGreska = true;
        }

        if (vrsta.equals("0")) {
            poruka += "-Izaberite vrstu.\n";
            postojiGreska = true;
        }

        if (nivo.equals("0")) {
            poruka += "-Izaberite nivo.\n";
            postojiGreska = true;
        }

        if (trajanje == null) {
            poruka += "-Morate uneti trajanje.\n";
            postojiGreska = true;
        }
        else if (trajanje < 1) {
            poruka += "-Trajanje ne moze biti negativna vrednost.\n";
            postojiGreska = true;
        }

        if (ocena == null) {
            poruka += "-Morate uneti ocenu.\n";
            postojiGreska = true;
        }
        else if (ocena < 1 || ocena > 5) {
            poruka += "-Ocena moze biti u rasponu od 1 do 5.\n";
            postojiGreska = true;
        }

        if (postojiGreska) {
            return  poruka;
        }

        return null;
    }

    @Override
    public double izracunajProsecnuOcenu(Long idTreninga) {
        return treningDAO.izracunajProsecnuOcenu(idTreninga);
    }

    @Override
    public Trening save(Trening trening) {
        treningDAO.save(trening);
        return trening;
    }

    @Override
    public Trening update(Trening trening) {
        treningDAO.update(trening);
        return trening;
    }

    @Override
    public Trening delete(Long id) {
        Trening trening = treningDAO.findOne(id);
        treningDAO.delete(id);
        return trening;
    }

    @Override
    public Resource loadImage(String filename) {
        try {
            Path file = Paths.get("images/" + filename);

            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
