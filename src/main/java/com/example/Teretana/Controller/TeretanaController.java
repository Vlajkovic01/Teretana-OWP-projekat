package com.example.Teretana.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Controller
@RequestMapping(value = "/Teretana")
public class TeretanaController {

    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping
    public ModelAndView index(@RequestParam() String lang,
                              HttpServletRequest request, HttpServletResponse response) {

        if (lang != null) {
            localeResolver.setLocale(request, response, Locale.forLanguageTag(lang));
        }

        return new ModelAndView("index");
    }
}
