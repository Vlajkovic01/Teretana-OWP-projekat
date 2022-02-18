package com.example.Teretana.Controller;

import com.example.Teretana.Model.SpecijalanDatum;
import com.example.Teretana.Model.Trening;
import com.example.Teretana.Service.SpecijalanDatumService;
import com.example.Teretana.Service.TreningService;
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
    private TreningService treningService;

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
        ModelAndView rezultat = new ModelAndView("specijalanDatum");
        rezultat.addObject("treninzi", treningService.findAll());
        return rezultat;
    }

    @PostMapping("/dodaj")
    public ModelAndView odobri(@RequestParam String datum, @RequestParam Integer popust,
                               @RequestParam(name="treningId", required=false) Long[] treningIds) {

        ModelAndView rezultat = new ModelAndView("specijalanDatum");

        String poruka = "";
        LocalDate pocetakDatuma = LocalDate.parse(datum);

        if (pocetakDatuma != null) {
            if (treningIds != null) {
                for (Long id : treningIds) {
                    if (specijalanDatumService.definisanZaTajDatum(pocetakDatuma, id)) {
                        Trening trening = treningService.findOne(id);
                        poruka += "-Vec ste definisali popust za taj datum za trening " + trening.getNaziv() + "-" + trening.getCena() + "\n";
                    }
                }
            } else {
                poruka += "-Izaberite treninge\n";
            }
        } else {
            poruka += "-Unesite datum.\n";
        }

        if (popust == null) {
            poruka += "-Unesite popust.\n";
        }

        if (poruka.equals("")) {
            SpecijalanDatum noviSpecijalanDatum = new SpecijalanDatum(pocetakDatuma.plusDays(1), pocetakDatuma.plusDays(2), popust);
            noviSpecijalanDatum.setTreninzi(treningService.findByIds(treningIds));
            specijalanDatumService.save(noviSpecijalanDatum);

            poruka += "-Uspesno dodat datum.";
        } else {
            // u slucaju greske popunice se polja
            rezultat.addObject("datum", pocetakDatuma);
            rezultat.addObject("popust", popust);
        }

        rezultat.addObject("greska", poruka);
        rezultat.addObject("treninzi", treningService.findAll());

        return rezultat;
    }

}
