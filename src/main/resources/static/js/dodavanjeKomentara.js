function popuniPrijavljenogKorisnika() {
    $.get("korisnici/prijavljeniKorisnik", function(odgovor) {

        if (odgovor.status == "ok") {

            var prijavljeniKorisnik = odgovor.prijavljeniKorisnik

            if (prijavljeniKorisnik == null) {
                window.location.href = 'korisnici/registracija';
            }

            if (prijavljeniKorisnik.uloga === "ADMINISTRATOR") {
                window.location.href = 'treninzi';
            }
        }
    })
}

var idTreninga = window.location.search.slice(1).split('&')[0].split('=')[1];

$(document).ready(function() {
    popuniPrijavljenogKorisnika()

    var ocenaInput = $("input[name=ocena]")
    var tekstInput = $("input[name=tekst]")
    var anonimanInput = $("input[name=anoniman]")

    var pasusGreska = $("p.greska")

    function dodajKomentar() {
        var ocena = ocenaInput.val()
        var tekst = tekstInput.val()
        var anoniman = anonimanInput.val()

        var params = {
            ocena: ocena,
            tekst: tekst,
            idTreninga: idTreninga,
            anoniman: anoniman
        }

        $.post("komentari/dodaj", params, function(odgovor) {

            if (odgovor.status == "ok" || odgovor.status == "odbijen") {
                window.location.href = 'treninzi';
            } else if (odgovor.status == "greska") {
                pasusGreska.text(odgovor.poruka)
            }
        })
    }

    $("input[type=submit]").click(function() {
        dodajKomentar()
        return false
    })
    $("body").show()
})