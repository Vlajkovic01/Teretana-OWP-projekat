package com.example.Teretana.Controller;

import com.example.Teretana.Model.*;
import com.example.Teretana.Service.ClanskaKarticaService;
import com.example.Teretana.Service.KorisnickaKorpaService;
import com.example.Teretana.Service.SpecijalanDatumService;
import com.example.Teretana.Service.TerminService;
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
@RequestMapping("/korisnickaKorpa")
public class KorisnickaKorpaController implements ServletContextAware {

    @Autowired
    private TerminService terminService;

    @Autowired
    private KorisnickaKorpaService korisnickaKorpaService;

    @Autowired
    private ClanskaKarticaService clanskaKarticaService;

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

    @GetMapping("/pregled")
    @SuppressWarnings("unchecked")
    public ModelAndView pregled(HttpSession session, HttpServletResponse response) throws IOException {

        ModelAndView rezultat = new ModelAndView("mojaKorpa");
        List<Termin> terminiUKorpi = (List<Termin>) session.getAttribute(TerminiController.IZABRANI_TERMINI_ZA_KORPU);
        Korisnik korisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);

        int ukupnaCena = 0;

        for (Termin termin : terminiUKorpi) {
            ukupnaCena += termin.getTrening().getCena();
        }

        int brojNovihBodova = ukupnaCena / 500;

        boolean specijalanDatum = specijalanDatumService.imaPopusta(LocalDateTime.now());
        boolean imaKarticu = clanskaKarticaService.imaKarticu(korisnik.getId());

        rezultat.addObject("ukupnaCena", ukupnaCena);
        rezultat.addObject("brojNovihBodova", brojNovihBodova);
        rezultat.addObject("specijalanDatum", specijalanDatum);
        rezultat.addObject("imaKarticu", imaKarticu);
        if (specijalanDatum) {
            rezultat.addObject("greska", "Danas je specijalan datum pa se bodovi nece racunati.\n");
        }

        return rezultat;
    }

    @PostMapping("/izbaci")
    @SuppressWarnings("unchecked")
    public void izbaci(@RequestParam Long id,
            HttpSession session, HttpServletResponse response) throws IOException {

        List<Termin> terminiUKorpi = (List<Termin>) session.getAttribute(TerminiController.IZABRANI_TERMINI_ZA_KORPU);

        terminiUKorpi.remove(terminService.findOne(id));

        response.sendRedirect(bURL + "korisnickaKorpa/pregled");
    }

    @PostMapping("/zakazi")
    @SuppressWarnings("unchecked")
    public ModelAndView zakazi(@RequestParam(required = false) Integer brojBodova,
                               HttpSession session, HttpServletResponse response) throws IOException {

        List<Termin> terminiUKorpi = (List<Termin>) session.getAttribute(TerminiController.IZABRANI_TERMINI_ZA_KORPU);
        Korisnik korisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
        ClanskaKartica clanskaKartica = clanskaKarticaService.findbyKorisnik(korisnik.getId());

        StringBuilder poruka = new StringBuilder("");
        ModelAndView rezultat = new ModelAndView("mojaKorpa");

        boolean specijalanDatum = specijalanDatumService.imaPopusta(LocalDateTime.now());
        boolean imaKarticu = clanskaKarticaService.imaKarticu(korisnik.getId());
        rezultat.addObject("specijalanDatum", specijalanDatum);
        rezultat.addObject("imaKarticu", imaKarticu);

        //validacija
        for(Termin termin : terminiUKorpi) {
            boolean proveraVremena = korisnickaKorpaService.proveraVremena(korisnik.getId(), termin.getDatumOdrzavanja().minusHours(1),
                    termin.getDatumOdrzavanja().plusMinutes(termin.getTrening().getTrajanje()).minusHours(1));
            if (proveraVremena) {
                poruka.append("-Imate vec zakazan termin u tom periodu za ").append(termin.getDatumOdrzavanja().minusHours(1)).append('\n');
            }
        }

        //da bi se ponovo prikazala ukupna ocena i novih bodova i nakon izbacene greske
        int ukupnaCena = 0;
        for (Termin termin : terminiUKorpi) {
            ukupnaCena += termin.getTrening().getCena();
        }
        int brojNovihBodova = ukupnaCena / 500;

        if (terminiUKorpi.isEmpty()) {
            poruka.append("-Korpa je prazna\n");
        }

        if (clanskaKartica != null && brojBodova != null) {
            if ((clanskaKartica.getBrojBodova() - brojBodova) < 0) {
                poruka.append("-Nemate toliko bodova, preostalo vam je ").append(clanskaKartica.getBrojBodova());
            }
        }

        if (poruka.length() > 0) {

            rezultat.addObject("ukupnaCena", ukupnaCena);
            rezultat.addObject("brojNovihBodova", brojNovihBodova);
            rezultat.addObject("greska", poruka);

            return rezultat;
        } else {

            for (Termin termin : terminiUKorpi) {
                KorisnickaKorpa novaRezervacija = new KorisnickaKorpa(korisnik,termin, LocalDateTime.now());
                korisnickaKorpaService.save(novaRezervacija);

            }


            // ako je uneo neki broj bodova koje hoce da iskoristi
            if (brojBodova != null) {
                if (clanskaKartica != null) {
                    clanskaKartica.setBrojBodova(clanskaKartica.getBrojBodova() - brojBodova);
                    clanskaKarticaService.update(clanskaKartica);
                }
            }
            if (clanskaKartica != null) {
                if (!specijalanDatum) {
                    clanskaKartica.setBrojBodova(clanskaKartica.getBrojBodova()+brojNovihBodova);
                    clanskaKarticaService.update(clanskaKartica);
                }
            }

            terminiUKorpi.clear();

            rezultat.addObject("greska", "Uspesno ste zakazali termine.");
        }

        return rezultat;
    }

    @GetMapping(value="/details")
    public ModelAndView details(@RequestParam Long id,
                                HttpSession session, HttpServletResponse response) throws IOException {
        // autentikacija, autorzacija
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
        if (prijavljeniKorisnik == null) {
            response.sendRedirect(bURL);
            return null;
        }

        // prosle??ivanje
        ModelAndView rezultat = new ModelAndView("rezervacija");
        rezultat.addObject("rezervacija", korisnickaKorpaService.findOne(id));

        return rezultat;
    }

    @PostMapping("/otkazi")
    @SuppressWarnings("unchecked")
    public ModelAndView otkaziTermin(@RequestParam Long idRezervacije,
                               HttpSession session, HttpServletResponse response) throws IOException {

        KorisnickaKorpa rezervacija = korisnickaKorpaService.findOne(idRezervacije);

        ModelAndView rezultat = new ModelAndView("rezervacija");

        if (LocalDateTime.now().isAfter(rezervacija.getTermin().getDatumOdrzavanja().minusHours(24))) {

            rezultat.addObject("greska", "Ne mozete otkazati termin jer je preostalo manje od 24h do istog.");
            rezultat.addObject("rezervacija", rezervacija);

            return rezultat;
        } else {

            korisnickaKorpaService.delete(idRezervacije);

            response.sendRedirect(bURL + "korisnici/mojProfil");
        }
        return null;
    }

    @PostMapping(value="/primeniBodove")
    @SuppressWarnings("unchecked")
    public ModelAndView primeniBodove(@RequestParam Long brojBodova,
                                HttpSession session, HttpServletResponse response) throws IOException {
        System.out.println(brojBodova);
        ModelAndView rezultat = new ModelAndView("mojaKorpa");
        List<Termin> terminiUKorpi = (List<Termin>) session.getAttribute(TerminiController.IZABRANI_TERMINI_ZA_KORPU);
        Korisnik korisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);

        int ukupnaCena = 0;

        for (Termin termin : terminiUKorpi) {
            ukupnaCena += termin.getTrening().getCena();
        }

        int brojNovihBodova = ukupnaCena / 500;

        double cenaSaPopustom = ukupnaCena - (ukupnaCena * (brojBodova * 0.05));

        boolean specijalanDatum = specijalanDatumService.imaPopusta(LocalDateTime.now());
        boolean imaKarticu = clanskaKarticaService.imaKarticu(korisnik.getId());

        rezultat.addObject("ukupnaCena", ukupnaCena);
        rezultat.addObject("brojNovihBodova", brojNovihBodova);
        rezultat.addObject("cenaSaPopustom", cenaSaPopustom);
        rezultat.addObject("brojIskoriscenihBodova", brojBodova);
        rezultat.addObject("specijalanDatum", specijalanDatum);
        rezultat.addObject("imaKarticu", imaKarticu);
        if (specijalanDatum) {
            rezultat.addObject("greska", "Danas je specijalan datum pa se bodovi nece racunati.\n");
        }

        return rezultat;
    }
}
