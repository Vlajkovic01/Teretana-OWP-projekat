package com.example.Teretana.Controller;

import com.example.Teretana.Model.*;
import com.example.Teretana.Service.KomentarService;
import com.example.Teretana.Service.TreningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/komentari")
public class KomentarController implements ServletContextAware {

    @Autowired
    private KomentarService komentarService;

    @Autowired
    private TreningService treningService;

    @Autowired
    private ServletContext servletContext;
    private String bURL;

    @PostConstruct
    public void init() {
        bURL = servletContext.getContextPath()+"/";
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @GetMapping
    @ResponseBody
    public ModelAndView index() {
        return new ModelAndView("odobravanjeKomentara");
    }

    @GetMapping("/zaOdobravanje")
    @ResponseBody
    public Map<String, Object> zaOdobravanje() {
        // čitanje
        List<Komentar> komentari = komentarService.findByStatus("NA_CEKANJU");

        Map<String, Object> odgovor = new LinkedHashMap<>();
        odgovor.put("status", "ok");
        odgovor.put("komentari", komentari);
        return odgovor;
    }

    @PostMapping("/odobri")
    public ModelAndView odobri(@RequestParam Long idKomentara) {

        Komentar komentar = komentarService.findOne(idKomentara);
        Trening trening = treningService.findOne(komentar.getTrening().getId());
        komentar.setStatus(StatusKomentaraIZahtevaKartice.ODOBREN);

        komentarService.update(komentar);

        double prosecnaOcena = treningService.izracunajProsecnuOcenu(trening.getId());
        trening.setOcena(prosecnaOcena);
        treningService.update(trening);

        return new ModelAndView("odobravanjeKomentara");
    }

    @PostMapping("/izbrisi")
    public ModelAndView izbrisi(@RequestParam Long idKomentara) {

        Komentar komentar = komentarService.findOne(idKomentara);
        komentar.setStatus(StatusKomentaraIZahtevaKartice.NIJE_ODOBREN);

        komentarService.update(komentar);

        return new ModelAndView("odobravanjeKomentara");
    }

    @GetMapping("/prikaz")
    @ResponseBody
    public Map<String, Object> prikaz(@RequestParam Long id) {
        // čitanje
        List<Komentar> komentari = komentarService.findByTreningId(id);

        Map<String, Object> odgovor = new LinkedHashMap<>();
        odgovor.put("status", "ok");
        odgovor.put("komentari", komentari);
        return odgovor;
    }

    @GetMapping("/dodavanjeKomentara")
    @ResponseBody
    public ModelAndView dodavanje() {
        return new ModelAndView("dodavanjeKomentara");
    }

    @PostMapping("/dodaj")
    @ResponseBody
    public Map<String, Object> dodaj(@RequestParam Integer ocena, @RequestParam String tekst,
                                    @RequestParam Long idTreninga,@RequestParam(required = false) boolean anoniman,
                                     HttpSession session) {

        Korisnik korisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
        if (korisnik == null || !korisnik.getUloga().equals(Uloga.CLAN)) {
            Map<String, Object> odgovor = new LinkedHashMap<>();
            odgovor.put("status", "odbijen");
            return odgovor;
        }
        //Validacija
        String poruka = "";

        boolean vecKomentarisao = komentarService.vecKomentarisao(korisnik.getId(), idTreninga);

        if (vecKomentarisao) {
            poruka += "-Vec ste komentarisali jednom, ne mozete vise\n";
        }

        if (ocena != null) {
            if (ocena < 1 || ocena > 5) {
                poruka +="-Ocena mora biti u izmedju 1 i 5\n";
            }
        } else {
            poruka +="-Unesite ocenu\n";
        }

        if (tekst.length() < 3 || tekst.length() > 50) {
            poruka += "-Unesite tekst od 3 do 50 karaktera\n";
        }

        if (!poruka.equals("")) {

            Map<String, Object> odgovor = new LinkedHashMap<>();
            odgovor.put("status", "greska");
            odgovor.put("poruka", poruka);

            return odgovor;
        }

        Komentar komentar = new Komentar(tekst, ocena, LocalDate.now(), korisnik, treningService.findOne(idTreninga),
                                        StatusKomentaraIZahtevaKartice.NA_CEKANJU, anoniman);
        komentarService.save(komentar);

        Map<String, Object> odgovor = new LinkedHashMap<>();
        odgovor.put("status", "ok");
        return odgovor;
    }
}
