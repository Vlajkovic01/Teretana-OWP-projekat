package com.example.Teretana.Controller;

import com.example.Teretana.Model.Korisnik;
import com.example.Teretana.Model.StatusKomentaraIZahtevaKartice;
import com.example.Teretana.Model.Uloga;
import com.example.Teretana.Model.ZahtevZaKarticu;
import com.example.Teretana.Service.ZahtevZaKarticuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;

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
}
