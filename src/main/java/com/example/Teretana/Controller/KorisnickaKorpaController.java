package com.example.Teretana.Controller;

import com.example.Teretana.Model.Termin;
import com.example.Teretana.Service.TerminService;
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
import java.util.List;

@Controller
@RequestMapping("/korisnickaKorpa")
public class KorisnickaKorpaController implements ServletContextAware {

    @Autowired
    private TerminService terminService;

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

    @GetMapping("/pregled")
    public ModelAndView pregled(HttpSession session, HttpServletResponse response) throws IOException {
        return new ModelAndView("mojaKorpa");
    }

    @PostMapping("/izbaci")
    @SuppressWarnings("unchecked")
    public ModelAndView izbaci(@RequestParam Long id,
            HttpSession session, HttpServletResponse response) throws IOException {

        List<Termin> terminiUKorpi = (List<Termin>) session.getAttribute(TerminiController.IZABRANI_TERMINI_ZA_KORPU);

        terminiUKorpi.remove(terminService.findOne(id));
        
        return new ModelAndView("mojaKorpa");
    }
}
