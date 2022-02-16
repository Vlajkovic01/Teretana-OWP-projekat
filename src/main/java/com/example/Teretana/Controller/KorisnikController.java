package com.example.Teretana.Controller;

import com.example.Teretana.Model.Korisnik;
import com.example.Teretana.Model.TipTreninga;
import com.example.Teretana.Model.Trening;
import com.example.Teretana.Model.Uloga;
import com.example.Teretana.Service.KorisnickaKorpaService;
import com.example.Teretana.Service.KorisnikService;
import com.example.Teretana.Service.ZeljaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/korisnici")
public class KorisnikController implements ServletContextAware {

    public static final String KORISNIK_KEY = "prijavljenKorisnik";

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private KorisnickaKorpaService korisnickaKorpaService;

    @Autowired
    private ZeljaService zeljaService;

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
    public ModelAndView index(@RequestParam(required=false) String korisnickoIme,
                              @RequestParam(required=false) String uloga,
                              @RequestParam(required=false) String tipSortiranja,
                              @RequestParam(required = false) String rastuce,
                              HttpSession session, HttpServletResponse response) throws IOException {
        // autentikacija, autorzacija
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
        if (prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
            response.sendRedirect(bURL);
            return null;
        }

        if (korisnickoIme != null && korisnickoIme.trim().equals("")) {
            korisnickoIme = null;
        }

        if (uloga != null && uloga.trim().equals("0")) {
            uloga = null;
        }

        if (tipSortiranja != null && tipSortiranja.trim().equals("0")) {
            tipSortiranja = null;
        }


        List<Korisnik> korisnici = korisnikService.find(korisnickoIme, uloga, tipSortiranja, rastuce);

        ModelAndView rezultat = new ModelAndView("korisnici");
        rezultat.addObject("korisnici", korisnici);

        return rezultat;
    }

    @GetMapping(value="/details")
    public ModelAndView details(@RequestParam String korisnickoIme,
                                HttpSession session, HttpServletResponse response) throws IOException {
        // autentikacija, autorzacija
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
        if (prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
            response.sendRedirect(bURL);
            return null;
        }

        // validacija
        Korisnik korisnik = korisnikService.findOne(korisnickoIme);
        if (korisnik == null) {
            response.sendRedirect(bURL + "korisnici");
            return null;
        }

        // prosleđivanje
        ModelAndView rezultat = new ModelAndView("korisnik");
        rezultat.addObject("korisnik", korisnik);
        rezultat.addObject("rezervacije", korisnickaKorpaService.findByKorisnikId(korisnik.getId()));
        rezultat.addObject("ukupnaCenaRezervacija", korisnickaKorpaService.ukupnaCenaRezervacija(korisnik.getId()));

        return rezultat;
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

            session.setAttribute(KorisnikController.KORISNIK_KEY, korisnik);

            response.sendRedirect(bURL + "treninzi");

        } catch (Exception ex) {

            ModelAndView loginPage = new ModelAndView("index");
            loginPage.addObject("greska", greska);
            return loginPage;
        }

        return null;
    }

    @GetMapping(value="/logout")
    public void logout(HttpSession session, HttpServletResponse response) throws IOException {
        // odjava
        session.invalidate();

        response.sendRedirect(bURL);
    }

    @GetMapping(value="/registracija")
    public String create(HttpSession session, HttpServletResponse response){
        return "registracija"; // stranica za registraciju korisnika
    }

    @PostMapping(value="/registracija")
    public ModelAndView registracija(@RequestParam(required = true) String korisnickoIme, @RequestParam String lozinka, @RequestParam String lozinkaPotvrda, @RequestParam String email,
                             @RequestParam String ime, @RequestParam String prezime, @RequestParam String datumRodjenja,
                             @RequestParam String adresa, @RequestParam String telefon,
                             HttpSession session, HttpServletResponse response) throws IOException {

        String poruka = korisnikService.validacija(korisnickoIme, lozinka, lozinkaPotvrda, email, ime, prezime, datumRodjenja, adresa, telefon);

        if (poruka != null) {
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
        Korisnik noviKorisnik = new Korisnik(korisnickoIme, lozinka, email, ime, prezime, datum, adresa, telefon, LocalDateTime.now(), Uloga.CLAN, false);

        korisnikService.save(noviKorisnik);

        ModelAndView login = new ModelAndView("index");
        login.addObject("greska","Uspesno ste se registrovali. Prijavite se!");

        return login;
    }

    @GetMapping(value="/mojProfil")
    public ModelAndView mojProfil(HttpSession session, HttpServletResponse response){

        Korisnik prijavljenKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);

        ModelAndView mojProfil = new ModelAndView("mojProfil");
        mojProfil.addObject("prijavljenKorisnik", prijavljenKorisnik);
        mojProfil.addObject("rezervacije", korisnickaKorpaService.findByKorisnikId(prijavljenKorisnik.getId()));
        mojProfil.addObject("ukupnaCenaRezervacija", korisnickaKorpaService.ukupnaCenaRezervacija(prijavljenKorisnik.getId()));
        mojProfil.addObject("listaZelja", zeljaService.findByKorisnikId(prijavljenKorisnik.getId()));

        return mojProfil;
    }

    @PostMapping(value="/mojProfilEdit")
    public ModelAndView mojProfilEdit(@RequestParam String korisnickoIme, @RequestParam String novaLozinka, @RequestParam String lozinkaPotvrda, @RequestParam String email,
                                      @RequestParam String ime, @RequestParam String prezime, @RequestParam String datumRodjenja,
                                      @RequestParam String adresa, @RequestParam String telefon,
                                      HttpSession session, HttpServletResponse response) throws IOException {

        Korisnik prijavljenKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);

        if (novaLozinka.equals("") && lozinkaPotvrda.equals("")) {
            novaLozinka = null;
        }

        if (prijavljenKorisnik.getEmail().equals(email)) {
            email = null;
        }

        if (prijavljenKorisnik.getKorisnickoIme().equals(korisnickoIme)) {
            korisnickoIme = null;
        }

        String poruka = korisnikService.validacija(korisnickoIme, novaLozinka, lozinkaPotvrda,email, ime, prezime, datumRodjenja, adresa, telefon);

        if (poruka != null) {
            ModelAndView mojProfil = new ModelAndView("mojProfil");
            mojProfil.addObject("prijavljenKorisnik", prijavljenKorisnik);
            mojProfil.addObject("greska", poruka);
            mojProfil.addObject("email", email);
            mojProfil.addObject("ime", ime);
            mojProfil.addObject("prezime", prezime);
            mojProfil.addObject("datumRodjenja", datumRodjenja);
            mojProfil.addObject("adresa", adresa);
            mojProfil.addObject("telefon", telefon);

            return  mojProfil;
        }

        if (novaLozinka != null) {
            prijavljenKorisnik.setLozinka(novaLozinka);
        }

        if (email != null) {
            prijavljenKorisnik.setEmail(email);
        }

        if (korisnickoIme != null) {
            prijavljenKorisnik.setKorisnickoIme(korisnickoIme);
        }

        prijavljenKorisnik.setIme(ime);
        prijavljenKorisnik.setPrezime(prezime);
        prijavljenKorisnik.setDatumRodjenja(LocalDate.parse(datumRodjenja));
        prijavljenKorisnik.setAdresa(adresa);
        prijavljenKorisnik.setTelefon(telefon);

        korisnikService.update(prijavljenKorisnik);

        response.sendRedirect(bURL + "treninzi");
        return null;
    }

    @PostMapping(value="/edit")
    public void edit(@RequestParam String korisnickoIme, @RequestParam(required = false) boolean blokiran,
                             @RequestParam String uloga,
                             HttpServletResponse response) throws IOException {

        Korisnik korisnik = korisnikService.findOne(korisnickoIme);

        if (blokiran && uloga.equals("ADMINISTRATOR")) {
            blokiran = false;
        }

        korisnik.setBlokiran(blokiran);
        korisnik.setUloga(Uloga.valueOf(uloga));

        korisnikService.update(korisnik);

        response.sendRedirect(bURL + "korisnici");
    }

    @GetMapping(value="/prijavljeniKorisnik")
    @ResponseBody
    public Map<String, Object> prijavljeniKorisnik(HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);

        Map<String, Object> odgovor = new LinkedHashMap<>();
        odgovor.put("status", "ok");
        odgovor.put("prijavljeniKorisnik", prijavljeniKorisnik);
        return odgovor;
    }
}
