package com.example.Teretana.Controller;

import com.example.Teretana.Model.Komentar;
import com.example.Teretana.Model.SpecijalanDatum;
import com.example.Teretana.Model.StatusKomentaraIZahtevaKartice;
import com.example.Teretana.Model.Trening;
import com.example.Teretana.Service.SpecijalanDatumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.time.LocalDate;

@Controller
@RequestMapping("/specijalanDatum")
public class SpecijalanDatumController implements ServletContextAware {

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

    @GetMapping
    @ResponseBody
    public ModelAndView index() {
        return new ModelAndView("specijalanDatum");
    }

    @PostMapping("/dodaj")
    public ModelAndView odobri(@RequestParam String datum, @RequestParam Integer popust) {
        ModelAndView rezultat = new ModelAndView("specijalanDatum");

        String poruka = "";
        LocalDate pocetakDatuma = LocalDate.parse(datum);

        if (pocetakDatuma != null) {

            if (specijalanDatumService.definisanZaTajDatum(pocetakDatuma)) {
                poruka += "-Vec ste definisali popust za taj datum.\n";
            }
        } else {
            poruka += "-Unesite datum.\n";
        }

        if (popust == null) {
            poruka += "-Unesite popust.\n";
        }

        if (poruka.equals("")) {
            SpecijalanDatum noviSpecijalanDatum = new SpecijalanDatum(pocetakDatuma, pocetakDatuma.plusDays(1), popust);
            specijalanDatumService.save(noviSpecijalanDatum);

            poruka += "-Uspesno dodat datum.";
        } else {
            // u slucaju greske popunice se polja
            rezultat.addObject("datum", pocetakDatuma);
            rezultat.addObject("popust", popust);
        }

        rezultat.addObject("greska", poruka);

        return rezultat;
    }

}
