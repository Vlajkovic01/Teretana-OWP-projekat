package com.example.Teretana.Controller;

import com.example.Teretana.Model.KorisnickaKorpa;
import com.example.Teretana.Model.Korisnik;
import com.example.Teretana.Model.Termin;
import com.example.Teretana.Service.KorisnickaKorpaService;
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
    public ModelAndView pregled(HttpSession session, HttpServletResponse response) throws IOException {
        return new ModelAndView("mojaKorpa");
    }

    @PostMapping("/izbaci")
    @SuppressWarnings("unchecked")
    public ModelAndView izbaci(@RequestParam Long id,
            HttpSession session, HttpServletResponse response) throws IOException {

        List<Termin> terminiUKorpi = (List<Termin>) session.getAttribute(TerminiController.IZABRANI_TERMINI_ZA_KORPU);

        terminiUKorpi.remove(terminService.findOne(id));

        return new ModelAndView("mojaKorpa");
    }

    @PostMapping("/zakazi")
    @SuppressWarnings("unchecked")
    public ModelAndView zakazi(@RequestParam Long id,
                               HttpSession session, HttpServletResponse response) throws IOException {

        Termin termin = terminService.findOne(id);
        Korisnik korisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);

        boolean proveraVremena = korisnickaKorpaService.proveraVremena(korisnik.getId(), termin.getDatumOdrzavanja(),
                                        termin.getDatumOdrzavanja().plusMinutes(termin.getTrening().getTrajanje()));

        ModelAndView rezultat = new ModelAndView("mojaKorpa");

        if (proveraVremena) {
            rezultat.addObject("greska", "Imate vec zakazan termin u tom periodu");
        } else {
            KorisnickaKorpa novaRezervacija = new KorisnickaKorpa(korisnik,termin, LocalDateTime.now());
            korisnickaKorpaService.save(novaRezervacija);

            List<Termin> terminiUKorpi = (List<Termin>) session.getAttribute(TerminiController.IZABRANI_TERMINI_ZA_KORPU);
            terminiUKorpi.remove(termin);

            rezultat.addObject("greska", "Uspesno ste zakazali termin.");
        }

        return rezultat;
    }
}
