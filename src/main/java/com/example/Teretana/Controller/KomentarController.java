package com.example.Teretana.Controller;

import com.example.Teretana.Model.Komentar;
import com.example.Teretana.Service.KomentarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
}
