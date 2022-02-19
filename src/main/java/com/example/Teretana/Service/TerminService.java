package com.example.Teretana.Service;

import com.example.Teretana.Model.Termin;

import java.time.LocalDateTime;
import java.util.List;

public interface TerminService {
    List<Termin> findAll();
    Termin findOne(Long id);
    List<Termin> findByTreningId(Long id, LocalDateTime trenutnoVreme);
    boolean findByDateTime(Long idSale, LocalDateTime noviTerminPocetak, LocalDateTime noviTerminKraj);
    int save(Termin termin);
    int update(Termin termin);
    int delete(Long id);
}
