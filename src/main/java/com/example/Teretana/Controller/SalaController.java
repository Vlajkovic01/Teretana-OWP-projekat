package com.example.Teretana.Controller;

import com.example.Teretana.Model.*;
import com.example.Teretana.Service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/sale")
public class SalaController implements ServletContextAware {

    @Autowired
    private SalaService salaService;

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
    public ModelAndView index(@RequestParam(required=false) String oznaka,
                              @RequestParam(required = false) String rastuce) {

        if (oznaka != null && oznaka.trim().equals("")) {
            oznaka = null;
        }

        List<Sala> sale = salaService.find(oznaka, rastuce);

        ModelAndView rezultat = new ModelAndView("sale");
        rezultat.addObject("sale", sale);

        return rezultat;
    }

    @GetMapping(value="/details")
    @ResponseBody
    public ModelAndView details(@RequestParam String oznaka,
                                HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Sala sala = salaService.findOne(oznaka);

        ModelAndView rezultat = new ModelAndView("sala");
        rezultat.addObject("sala", sala);

        return rezultat;
    }

    @PostMapping(value="/edit")
    public ModelAndView edit(@RequestParam String oznaka, @RequestParam Integer kapacitet,
                             HttpSession session, HttpServletResponse response) throws IOException {

//      autentikacija, autorizacija
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
        if (prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
            response.sendRedirect(bURL + "treninzi");
        }

        Sala sala = salaService.findOne(oznaka);
        if (sala == null) {
            response.sendRedirect(bURL + "sale");
        }

        if (kapacitet < 1) {
            ModelAndView rezultat = new ModelAndView("sala");
            rezultat.addObject("sala", sala);
            rezultat.addObject("greska", "Kapacitet ne moze biti manji od 1.");

            return rezultat;
        }

        sala.setKapacitet(kapacitet);
        salaService.update(sala);

        response.sendRedirect(bURL + "sale");
        return null;
    }

    @GetMapping(value="/create")
    public ModelAndView create(HttpSession session, HttpServletResponse response) throws IOException {
        // autentikacija, autorizacija
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
        if (prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
            response.sendRedirect(bURL + "treninzi");
            return null;
        }

        return new ModelAndView("dodavanjeSale");
    }

    @PostMapping(value="/create")
    public ModelAndView create(@RequestParam String oznaka, @RequestParam Integer kapacitet,
                             HttpSession session, HttpServletResponse response) throws IOException {

//      autentikacija, autorizacija
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
        if (prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
            response.sendRedirect(bURL + "treninzi");
        }

        String poruka = "";
        Sala validacijaOznake = salaService.findOne(oznaka);

        if (validacijaOznake != null) {
            poruka += "Postoji sala sa tom oznakom";
        }
        if (kapacitet < 1) {
            poruka += "Kapacitet ne moze biti manji od 1";
        }

        if (!poruka.equals("")) {
            ModelAndView rezultat = new ModelAndView("dodavanjeSale");
            rezultat.addObject("oznaka", oznaka);
            rezultat.addObject("kapacitet", kapacitet);
            rezultat.addObject("greska", poruka);

            return rezultat;
        }

        Sala novaSala = new Sala(oznaka, kapacitet);
        salaService.save(novaSala);

        response.sendRedirect(bURL + "sale");
        return null;
    }

    @PostMapping(value="/delete")
    public ModelAndView delete(@RequestParam Long id,
                       HttpSession session, HttpServletResponse response) throws IOException {
//      autentikacija, autorizacija
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
        if (prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
            response.sendRedirect(bURL + "treninzi");
        }

        // brisanje
        int brisanje = salaService.delete(id);

        if(brisanje == 1) {
            response.sendRedirect(bURL + "sale");
        } else {

            List<Sala> sale = salaService.findAll();

            ModelAndView rezultat = new ModelAndView("sale");
            rezultat.addObject("sale", sale);
            rezultat.addObject("greska", "Nije moguce izbrisati zauzetu salu.");

            return rezultat;
        }
        return null;
    }
}
