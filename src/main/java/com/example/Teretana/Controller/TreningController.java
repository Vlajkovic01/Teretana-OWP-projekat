package com.example.Teretana.Controller;

import com.example.Teretana.Model.*;
import com.example.Teretana.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping(value = "/treninzi")
public class TreningController implements ServletContextAware {

    @Autowired
    private TreningService treningService;

    @Autowired
    private TipTreningaService tipTreningaService;

    @Autowired
    private TerminService terminService;

    @Autowired
    private KorisnickaKorpaService korisnickaKorpaService;

    @Autowired
    private ClanskaKarticaService clanskaKarticaService;

    @Autowired
    private ZahtevZaKarticuService zahtevZaKarticuService;

    @Autowired
    private IzvestajService izvestajService;

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
    public ModelAndView index(@RequestParam(required=false) String naziv, @RequestParam(required=false) String treneri,
                              @RequestParam(required=false) Long tipId, @RequestParam(required=false) Integer cenaOd,
                              @RequestParam(required=false) Integer cenaDo, @RequestParam(required=false) String vrsta,
                              @RequestParam(required=false) String nivo, @RequestParam(required=false) String tipSortiranja,
                              @RequestParam(required = false) String rastuce, HttpSession session) {

        if (naziv != null && naziv.trim().equals("")) {
            naziv = null;
        }

        if (treneri != null && treneri.trim().equals("")) {
            treneri = null;
        }

        if (vrsta != null && vrsta.trim().equals("0")) {
            vrsta = null;
        }

        if (nivo != null && nivo.trim().equals("0")) {
            nivo = null;
        }

        if (tipSortiranja != null && tipSortiranja.trim().equals("0")) {
            tipSortiranja = null;
        }

        List<Trening> treninzi = treningService.find(naziv, treneri, tipId, cenaOd, cenaDo,
                vrsta, nivo, tipSortiranja, rastuce);
        List<TipTreninga> tipovi = tipTreningaService.findAll();

        Korisnik korisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);

        boolean zahtev = true;

        if (clanskaKarticaService.imaKarticu(korisnik.getId())) {
            zahtev = false;
        }

        if (zahtevZaKarticuService.poslaoZahtev(korisnik.getId())) {
            zahtev = false;
        }
        StringBuilder poruka = new StringBuilder("Danas su na popustu sledeci treninzi: \n");
        boolean popustPoruka = false;
        for (Trening trening : treninzi) {
            SpecijalanDatum specijalanDatum = specijalanDatumService.nadjiPoDatumu(LocalDateTime.now(), trening.getId());

            if (specijalanDatum != null) {
                poruka.append("-").append(trening.getNaziv()).append(" - Ocena: ").append(trening.getOcena()).append(
                        " -> Popust od ").append(specijalanDatum.getPopust()).append("% bice obracunat u korpi nakon dodavanja").append('\n');
                popustPoruka = true;
            }
        }

        ModelAndView rezultat = new ModelAndView("treninzi");
        rezultat.addObject("treninzi", treninzi);
        rezultat.addObject("tipovi", tipovi);
        rezultat.addObject("zahtev", zahtev);
        if (popustPoruka) {
            rezultat.addObject("poruka", poruka);
        }

        return rezultat;
    }

    @GetMapping(value="/details")
    @ResponseBody
    public ModelAndView details(@RequestParam Long id,
                                HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Trening trening = treningService.findOne(id);
        List<TipTreninga> tipovi = tipTreningaService.findAll();
        List<Termin> termini = terminService.findByTreningId(id);

        ModelAndView rezultat = new ModelAndView("trening");
        rezultat.addObject("trening", trening);
        rezultat.addObject("tipovi", tipovi);
        rezultat.addObject("termini", termini);

        return rezultat;
    }

    @PostMapping(value="/edit")
    public ModelAndView edit(@RequestParam Long id, @RequestParam String naziv, @RequestParam String treneri,
                     @RequestParam(name="tipId", required=false) Long[] tipIds,
                     @RequestParam String kratakOpis, @RequestParam(name="slikaFile") MultipartFile slikaFile,
                     @RequestParam Integer cena, @RequestParam String vrsta, @RequestParam String nivo,
                     @RequestParam Integer trajanje, @RequestParam Double ocena,
                     HttpSession session, HttpServletResponse response) throws IOException {

//      autentikacija, autorizacija
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
        if (prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
            response.sendRedirect(bURL + "treninzi");
        }

        Trening trening = treningService.findOne(id);
        if (trening == null) {
            response.sendRedirect(bURL + "treninzi");
        }

        String poruka = treningService.validacija(naziv, treneri, kratakOpis, cena, vrsta, nivo, trajanje, ocena);

        if (poruka != null) {
            List<TipTreninga> tipovi = tipTreningaService.findAll();

            ModelAndView rezultat = new ModelAndView("trening");
            rezultat.addObject("trening", trening);
            rezultat.addObject("tipovi", tipovi);
            rezultat.addObject("greska", poruka);

            return rezultat;
        }

        if (!slikaFile.isEmpty()) {
            byte[] bytes = slikaFile.getBytes();
            Path path = Paths.get("images/" + slikaFile.getOriginalFilename());
            Files.write(path, bytes);
            String slikaUrl = "images/" + slikaFile.getOriginalFilename();

            trening.setUrlSlika(slikaUrl);
        }

        trening.setNaziv(naziv);
        trening.setTreneri(treneri);
        trening.setKratakOpis(kratakOpis);
        trening.setCena(cena);
        trening.setVrstaTreninga(VrstaTreninga.valueOf(vrsta));
        trening.setNivoTreninga(NivoTreninga.valueOf(nivo));
        trening.setTrajanje(trajanje);
        trening.setOcena(ocena);
        trening.setTipTreninga(tipTreningaService.find(tipIds));

        treningService.update(trening);

        response.sendRedirect(bURL + "treninzi");
        return null;
    }

    @GetMapping(value="/create")
    public ModelAndView create(HttpSession session, HttpServletResponse response) throws IOException {
        // autentikacija, autorizacija
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
        if (prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
            response.sendRedirect(bURL + "treninzi");
            return null;
        }

        List<TipTreninga> tipovi = tipTreningaService.findAll();

        ModelAndView rezultat = new ModelAndView("dodavanjeTreninga");
        rezultat.addObject("tipovi", tipovi);

        return rezultat;
    }

    @PostMapping(value="/create")
    public ModelAndView create(@RequestParam String naziv, @RequestParam String treneri,
                             @RequestParam(name="tipId", required=false) Long[] tipIds,
                             @RequestParam String kratakOpis, @RequestParam(name="slikaFile") MultipartFile slikaFile,
                             @RequestParam Integer cena, @RequestParam String vrsta, @RequestParam String nivo,
                             @RequestParam Integer trajanje, @RequestParam Double ocena,
                             HttpSession session, HttpServletResponse response) throws IOException {

//      autentikacija, autorizacija
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
        if (prijavljeniKorisnik == null || !prijavljeniKorisnik.getUloga().equals(Uloga.ADMINISTRATOR)) {
            response.sendRedirect(bURL + "treninzi");
        }

        String poruka = treningService.validacija(naziv, treneri, kratakOpis, cena, vrsta, nivo, trajanje, ocena);
        if (tipIds == null) {
            poruka += "-Izaberi tip.\n";
        }

        if (slikaFile.isEmpty()) {
            poruka += "-Dodaj sliku.\n";
        }

        if (poruka != null) {
            List<TipTreninga> tipovi = tipTreningaService.findAll();

            ModelAndView rezultat = new ModelAndView("dodavanjeTreninga");
            rezultat.addObject("greska", poruka);
            rezultat.addObject("tipovi", tipovi);
            rezultat.addObject("naziv", naziv);
            rezultat.addObject("treneri", treneri);
            rezultat.addObject("kratakOpis", kratakOpis);
            rezultat.addObject("cena", cena);
            rezultat.addObject("trajanje", trajanje);
            rezultat.addObject("ocena", ocena);

            return rezultat;
        }


        byte[] bytes = slikaFile.getBytes();
        Path path = Paths.get("images/" + slikaFile.getOriginalFilename());
        Files.write(path, bytes);
        String slikaUrl = "images/" + slikaFile.getOriginalFilename();


        Trening noviTrening = new Trening(naziv, treneri,kratakOpis, slikaUrl, cena, VrstaTreninga.valueOf(vrsta), NivoTreninga.valueOf(nivo), trajanje, ocena);
        noviTrening.setTipTreninga(tipTreningaService.find(tipIds));
        treningService.save(noviTrening);

        response.sendRedirect(bURL + "treninzi");
        return null;
    }

    @GetMapping("/izvestaj")
    public ModelAndView izvestaj(@RequestParam(required=false) String tipSortiranja, @RequestParam(required=false) String vremeOd,
                                 @RequestParam(required=false) String vremeDo, @RequestParam(required = false) String rastuce) {


        if (tipSortiranja != null && tipSortiranja.trim().equals("0")) {
            tipSortiranja = null;
        }

        if (vremeOd == null) {
            vremeOd = LocalDateTime.MIN.toString();
        }

        if (vremeDo == null) {
            vremeDo = LocalDateTime.MAX.toString();
        }


        List<Izvestaj> treninzi = izvestajService.nadji(tipSortiranja, vremeOd, vremeDo, rastuce);

        ModelAndView rezultat = new ModelAndView("izvestaj");
        rezultat.addObject("treninzi", treninzi);
        rezultat.addObject("ukupnaCenaSvihTreninga", izvestajService.ukupnaCenaSvihTreninga());
        rezultat.addObject("ukupanBrojZakazanihTreninga", izvestajService.ukupanBrojZakazanihTreninga());

        return rezultat;
    }
}
