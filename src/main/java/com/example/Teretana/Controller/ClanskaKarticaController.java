package com.example.Teretana.Controller;

import com.example.Teretana.Model.*;
import com.example.Teretana.Service.ClanskaKarticaService;
import com.example.Teretana.Service.ZahtevZaKarticuService;
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

@Controller
@RequestMapping("/clanskaKartica")
public class ClanskaKarticaController implements ServletContextAware {

    @Autowired
    private ZahtevZaKarticuService zahtevZaKarticuService;

    @Autowired
    private ClanskaKarticaService clanskaKarticaService;

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

    @GetMapping(value="/zahtev")
    public void edit(HttpSession session, HttpServletResponse response) throws IOException {

        Korisnik korisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
        ZahtevZaKarticu noviZahtev = new ZahtevZaKarticu(korisnik, LocalDateTime.now(), StatusKomentaraIZahtevaKartice.NA_CEKANJU);

        zahtevZaKarticuService.save(noviZahtev);

        response.sendRedirect(bURL + "treninzi");
    }

    @GetMapping(value="/pregledZahteva")
    public ModelAndView pregledZahteva(HttpSession session, HttpServletResponse response) throws IOException {

        ModelAndView rezultat = new ModelAndView("pregledZahteva");
        rezultat.addObject("zahtevi", zahtevZaKarticuService.nadjiNaCekanju());

        return rezultat;
    }

    @PostMapping(value="/odobri")
    public ModelAndView odobriZahtev(@RequestParam Long idZahteva,
                                     HttpSession session, HttpServletResponse response) throws IOException {
        ZahtevZaKarticu zahtevZaKarticu = zahtevZaKarticuService.findOne(idZahteva);

        zahtevZaKarticu.setStatus(StatusKomentaraIZahtevaKartice.ODOBREN);
        zahtevZaKarticuService.update(zahtevZaKarticu);

        ClanskaKartica clanskaKartica = new ClanskaKartica(zahtevZaKarticu.getKorisnik(),10);
        clanskaKarticaService.save(clanskaKartica);

        ModelAndView rezultat = new ModelAndView("pregledZahteva");
        rezultat.addObject("zahtevi", zahtevZaKarticuService.nadjiNaCekanju());
        rezultat.addObject("poruka", "Uspesno kreirana kartica");

        return rezultat;
    }

    @PostMapping(value="/odbijZahtev")
    public ModelAndView odbijZahtev(@RequestParam Long idZahteva,
                                    HttpSession session, HttpServletResponse response) throws IOException {
        ZahtevZaKarticu zahtevZaKarticu = zahtevZaKarticuService.findOne(idZahteva);

        zahtevZaKarticu.setStatus(StatusKomentaraIZahtevaKartice.NIJE_ODOBREN);
        zahtevZaKarticuService.update(zahtevZaKarticu);

        ModelAndView rezultat = new ModelAndView("pregledZahteva");
        rezultat.addObject("zahtevi", zahtevZaKarticuService.nadjiNaCekanju());
        rezultat.addObject("poruka", "Uspesno odbijena kartica");

        return rezultat;
    }
}
