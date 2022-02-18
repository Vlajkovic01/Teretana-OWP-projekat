package com.example.Teretana.Controller;

import com.example.Teretana.Model.*;
import com.example.Teretana.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/termini")
public class TerminiController implements ServletContextAware {

    public static final String IZABRANI_TERMINI_ZA_KORPU = "izabraniTerminiZaKorpu";

    @Autowired
    private SalaService salaService;

    @Autowired
    private TreningService treningService;

    @Autowired
    private TerminService terminService;

    @Autowired
    private KorisnickaKorpaService korisnickaKorpaService;

    @Autowired
    private SpecijalanDatumService specijalanDatumService;

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

    @GetMapping(value="/create")
    public ModelAndView create(HttpSession session, HttpServletResponse response) throws IOException {
        // autentikacija, autorizacija
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
        if (prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
            response.sendRedirect(bURL + "treninzi");
            return null;
        }
        ModelAndView rezultat = new ModelAndView("dodavanjeTermina");
        rezultat.addObject("sale", salaService.findAll());
        rezultat.addObject("treninzi", treningService.findAll());

        return rezultat;
    }

    @PostMapping(value="/create")
    public ModelAndView create(@RequestParam String salaOznaka, @RequestParam Long treningId,
                               @RequestParam String datumOdrzavanja,
                               HttpSession session, HttpServletResponse response) throws IOException {

//      autentikacija, autorizacija
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
        if (prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
            response.sendRedirect(bURL + "treninzi");
        }

        String poruka = "";

        Sala sala = salaService.findOne(salaOznaka);
        if (sala == null) {
            poruka += "-Morate izabrati salu\n";
        }

        LocalDateTime noviTerminPocetak = LocalDateTime.parse(datumOdrzavanja);
        Trening trening = treningService.findOne(treningId);

        if (trening == null) {
            poruka += "-Morate izabrati trening\n";
        }

        if (sala != null && trening != null) {
            LocalDateTime noviTerminKraj = noviTerminPocetak.plusMinutes(trening.getTrajanje());

            boolean proveraDatuma = terminService.findByDateTime(sala.getId(), noviTerminPocetak, noviTerminKraj);

            if (proveraDatuma) {
                poruka += "-Sala je zauzeta u tom vremenu\n";
            }
        }

        if (!poruka.equals("")) {

            ModelAndView rezultat = new ModelAndView("dodavanjeTermina");
            rezultat.addObject("sale", salaService.findAll());
            rezultat.addObject("treninzi", treningService.findAll());
            rezultat.addObject("greska", poruka);

            return rezultat;
        }

        Termin termin = new Termin(sala, trening, noviTerminPocetak);
        terminService.save(termin);

        response.sendRedirect(bURL + "treninzi");
        return null;
    }

    @PostMapping(value="/korpa")
    @SuppressWarnings("unchecked")
    public ModelAndView korpa(@RequestParam Long idTermina, @RequestParam Long idTreninga,
                               HttpSession session, HttpServletResponse response) throws IOException {

//      autentikacija, autorizacija
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
        if (prijavljeniKorisnik == null) {
            response.sendRedirect(bURL + "treninzi");
        }

        Termin termin = terminService.findOne(idTermina);
        List<Termin> terminiZaKorpu = (List<Termin>) session.getAttribute(IZABRANI_TERMINI_ZA_KORPU);

        // provera da li je danas specijalan datum, ako jeste primenice popust
        SpecijalanDatum specijalanDatum = specijalanDatumService.nadjiPoDatumu(LocalDateTime.now());
        if (specijalanDatum != null) {
            termin.getTrening().setCena(termin.getTrening().getCena() - (termin.getTrening().getCena() / 100* specijalanDatum.getPopust()));
        }

        ModelAndView rezultat = new ModelAndView("trening");
        rezultat.addObject("trening", treningService.findOne(idTreninga));
        rezultat.addObject("termini", terminService.findByTreningId(idTreninga));

        if (korisnickaKorpaService.proveraKapaciteta(idTermina, termin.getSala().getKapacitet())) {
            if (!terminiZaKorpu.contains(termin)) {
                terminiZaKorpu.add(termin);
            }
        } else {
            rezultat.addObject("kapacitetGreska", "Kapacitet za taj termin je popunjen.");
        }
        return rezultat;
    }
}
