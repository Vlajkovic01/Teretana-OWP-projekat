package com.example.Teretana.Controller;

import com.example.Teretana.Model.Sala;
import com.example.Teretana.Model.TipTreninga;
import com.example.Teretana.Model.Trening;
import com.example.Teretana.Service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
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
}
