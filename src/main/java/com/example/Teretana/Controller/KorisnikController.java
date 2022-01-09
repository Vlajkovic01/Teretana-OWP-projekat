package com.example.Teretana.Controller;

import com.example.Teretana.Model.Korisnik;
import com.example.Teretana.Model.Uloga;
import com.example.Teretana.Service.KorisnikService;
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
import java.time.LocalDate;

@Controller
@RequestMapping(value = "/korisnici")
public class KorisnikController implements ServletContextAware {

    public static final String KORISNIK_KEY = "korisnik";

    @Autowired
    private KorisnikService korisnikService;

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

    @GetMapping(value = "/login")
    public void getLogin(@RequestParam(required = false) String korisnickoIme, @RequestParam(required = false) String lozinka,
                         HttpSession session, HttpServletResponse response) throws IOException {
        postLogin(korisnickoIme, lozinka, session, response);
    }

    @PostMapping(value = "/login")
    public ModelAndView postLogin(@RequestParam String korisnickoIme, @RequestParam String lozinka, HttpSession session,
                                   HttpServletResponse response) throws IOException {

        String greska = "";
        try {
            Korisnik korisnik = korisnikService.findOne(korisnickoIme, lozinka);


            if (korisnik == null) {
                greska = "Neispravni kredencijali.Pokusaj ponovo!";
                throw new Exception(greska);
            }
            else if (korisnik.isBlokiran()) {
                greska = "Nalog je blokiran.Kontaktirajte administratora.";
                throw new Exception(greska);
            }

            if (korisnik.getUloga().equals(Uloga.CLAN)) {
                response.sendRedirect(bURL + "nijeImplementiranoClan");
            } else {
                response.sendRedirect(bURL + "nijeImplementiranoAdmin");
            }

        } catch (Exception ex) {

            ModelAndView loginPage = new ModelAndView("index");
            loginPage.addObject("greska", greska);
            return loginPage;
        }

        return null;
    }

    @GetMapping(value="/registracija")
    public String create(HttpSession session, HttpServletResponse response){
        return "registracija"; // stranica za registraciju korisnika
    }

    @PostMapping(value="/registracija")
    public ModelAndView registracija(@RequestParam String korisnickoIme, @RequestParam String lozinka, @RequestParam String email,
                             @RequestParam String ime, @RequestParam String prezime, @RequestParam String datumRodjenja,
                             @RequestParam String adresa, @RequestParam String telefon,
                             HttpSession session, HttpServletResponse response) throws IOException {

        Korisnik kIme = korisnikService.findOne(korisnickoIme);
        Korisnik kEmail = korisnikService.findOneByEmail(email);

        String poruka = " Ispravi gresku: ";
        boolean postojiGreska = false;

        if (kIme != null) {
            poruka += "-Korisnik sa tim korisnickim imenom vec postoji.\n";
            postojiGreska = true;
        }

        if (kEmail != null) {
            poruka += "-Korisnik sa tim emailom vec postoji.\n";
            postojiGreska = true;
        }

        if (postojiGreska) {
            ModelAndView registracija = new ModelAndView("registracija");
            registracija.addObject("greska", poruka);
            registracija.addObject("korisnickoIme", korisnickoIme);
            registracija.addObject("lozinka", lozinka);
            registracija.addObject("email", email);
            registracija.addObject("ime", ime);
            registracija.addObject("prezime", prezime);
            registracija.addObject("datumRodjenja", datumRodjenja);
            registracija.addObject("adresa", adresa);
            registracija.addObject("telefon", telefon);

            return  registracija;
        }

        LocalDate datum = LocalDate.parse(datumRodjenja);

        return new ModelAndView("index");
    }
}
