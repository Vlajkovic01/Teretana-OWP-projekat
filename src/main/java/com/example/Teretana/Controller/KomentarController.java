package com.example.Teretana.Controller;

import com.example.Teretana.Model.Komentar;
import com.example.Teretana.Model.StatusKomentara;
import com.example.Teretana.Model.Trening;
import com.example.Teretana.Service.KomentarService;
import com.example.Teretana.Service.TreningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/komentari")
public class KomentarController implements ServletContextAware {

    @Autowired
    private KomentarService komentarService;

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
        return new ModelAndView("odobravanjeKomentara");
    }

    @GetMapping("/zaOdobravanje")
    @ResponseBody
    public Map<String, Object> zaOdobravanje() {
        // čitanje
        List<Komentar> komentari = komentarService.findByStatus("NA_CEKANJU");

        Map<String, Object> odgovor = new LinkedHashMap<>();
        odgovor.put("status", "ok");
        odgovor.put("komentari", komentari);
        return odgovor;
    }

    @PostMapping("/odobri")
    public ModelAndView dodaj(@RequestParam Long idKomentara) {

        Komentar komentar = komentarService.findOne(idKomentara);
        Trening trening = treningService.findOne(komentar.getTrening().getId());
        komentar.setStatus(StatusKomentara.ODOBREN);

        komentarService.update(komentar);

        double prosecnaOcena = treningService.izracunajProsecnuOcenu(trening.getId());
        trening.setOcena(prosecnaOcena);
        treningService.update(trening);

        return new ModelAndView("odobravanjeKomentara");
    }

    @PostMapping("/izbrisi")
    public ModelAndView izbrisi(@RequestParam Long idKomentara) {

        Komentar komentar = komentarService.findOne(idKomentara);
        komentar.setStatus(StatusKomentara.NIJE_ODOBREN);

        komentarService.update(komentar);

        return new ModelAndView("odobravanjeKomentara");
    }

    @GetMapping("/prikaz")
    @ResponseBody
    public Map<String, Object> zaOdobravanje(@RequestParam Long id) {
        // čitanje
        List<Komentar> komentari = komentarService.findByTreningId(id);

        Map<String, Object> odgovor = new LinkedHashMap<>();
        odgovor.put("status", "ok");
        odgovor.put("komentari", komentari);
        return odgovor;
    }
}
