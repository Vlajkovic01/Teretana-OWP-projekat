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
                response.sendRedirect(bURL + "treninzi");
            } else {
                response.sendRedirect(bURL + "korisnici1");
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
        return "registracija"; // stranica za dodavanje knjige
    }

    @PostMapping(value = "/registracija")
    public void registracija(@RequestParam(required = true) String email, @RequestParam(required = true) String sifra,
                             @RequestParam(required = true) String ime, @RequestParam(required = true) String prezime,
                             HttpSession session, HttpServletResponse response) throws IOException {

        response.sendRedirect(bURL + "korisnici");
    }
}
