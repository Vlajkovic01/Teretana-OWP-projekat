package com.example.Teretana.listeners;

import com.example.Teretana.Controller.TerminiController;
import com.example.Teretana.Model.Termin;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;

@Component
public class InitHttpSessionListener implements HttpSessionListener {
    /** kod koji se izvrsava po kreiranju sesije */
    public void sessionCreated(HttpSessionEvent event) {
        System.out.println("Inicijalizacija sesisje HttpSessionListener...");

        // pri kreiranju sesije inicijalizujemo je ili radimo neke dodatne aktivnosti
        HttpSession session  = event.getSession();
        System.out.println("Session id korisnika je "+ session.getId());

        session.setAttribute(TerminiController.IZABRANI_TERMINI_ZA_KORPU, new ArrayList<Termin>());

        System.out.println("Uspeh HttpSessionListener!");
    }

    /** kod koji se izvrsava po brisanju sesije */
    public void sessionDestroyed(HttpSessionEvent arg0) {
        System.out.println("Brisanje sesisje HttpSessionListener...");

        System.out.println("Uspeh HttpSessionListener!");
    }
}
