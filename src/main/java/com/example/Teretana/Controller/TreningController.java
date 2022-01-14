package com.example.Teretana.Controller;

import com.example.Teretana.Model.Trening;
import com.example.Teretana.Service.TreningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.List;

@Controller
@RequestMapping(value = "/treninzi")
public class TreningController implements ServletContextAware {

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
    public ModelAndView index() {
        List<Trening> treninzi = treningService.findAll();

        ModelAndView rezultat = new ModelAndView("treninzi");
        rezultat.addObject("treninzi", treninzi);

        return rezultat;
    }
}
