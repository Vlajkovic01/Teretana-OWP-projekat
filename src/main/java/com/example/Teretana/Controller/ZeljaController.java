package com.example.Teretana.Controller;

import com.example.Teretana.Model.Korisnik;
import com.example.Teretana.Model.Zelja;
import com.example.Teretana.Service.TerminService;
import com.example.Teretana.Service.TreningService;
import com.example.Teretana.Service.ZeljaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping(value = "/listaZelja")
public class ZeljaController implements ServletContextAware {

    @Autowired
    private TreningService treningService;

    @Autowired
    private TerminService terminService;

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

    @PostMapping(value="/dodaj")
    public ModelAndView dodajUZeljene(@RequestParam Long idTreninga,
                                      HttpSession session, HttpServletResponse response) throws IOException {

        ModelAndView rezultat = new ModelAndView("trening");

//      autentikacija, autorizacija
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
        if (prijavljeniKorisnik == null) {
            response.sendRedirect(bURL + "treninzi");
        } else {

            if (zeljaService.postojiZelja(idTreninga, prijavljeniKorisnik.getId())) {
                rezultat.addObject("greska", "Trening se nalazi u listi zelja");
            } else {

                Zelja novaZelja = new Zelja(prijavljeniKorisnik, treningService.findOne(idTreninga));
                zeljaService.save(novaZelja);

                rezultat.addObject("greska", "Uspesno ste dodali trening u listu zelja");
            }
        }


        rezultat.addObject("trening", treningService.findOne(idTreninga));
        rezultat.addObject("termini", terminService.findByTreningId(idTreninga));

        return rezultat;
    }
}
