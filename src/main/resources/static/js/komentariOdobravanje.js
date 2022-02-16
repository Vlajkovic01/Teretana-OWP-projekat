function popuniPrijavljenogKorisnika() {
    $.get("korisnici/prijavljeniKorisnik", function(odgovor) {

        if (odgovor.status == "ok") {

            var prijavljeniKorisnik = odgovor.prijavljeniKorisnik

            if (prijavljeniKorisnik == null) {
                window.location.href = 'korisnici/registracija';
            }

            if (prijavljeniKorisnik.uloga === "CLAN") {
                window.location.href = 'treninzi';
            }
        }
    })
}

$(document).ready(function() {

    popuniPrijavljenogKorisnika()

    var tabela = $("table.content-table")

    function popuniKomentare() {


        $.get("komentari/zaOdobravanje", function(odgovor) {
            console.log(odgovor)

            if (odgovor.status == "ok") {
                var komentari = odgovor.komentari
                for (var k in komentari) {
                    tabela.append(
                        '<tr>' +
                        '<td>' + komentari[k].tekst + '</td>' +
                        '<td>' + komentari[k].ocena + '</td>' +
                        '<td>' + komentari[k].datum + '</td>' +
                        '<td>' + komentari[k].autor.korisnickoIme + '</td>' +
                        '<td>' + komentari[k].anoniman + '</td>' +
                        '<td>' + komentari[k].trening.naziv + '</td>' +
                        '<td>' +
                            '<form method="post" action="komentari/odobri"> ' +
                                '<input type="hidden" name="idKomentara" value="'+ komentari[k].id +'"/>' +
                                '<input type="submit" value="Odobri" />' +
                            '</form>' +
                        '</td>' +
                        '<td>' +
                            '<form method="post" action="komentari/izbrisi"> ' +
                                '<input type="hidden" name="idKomentara" value="'+ komentari[k].id +'"/>' +
                                '<input type="submit" value="Izbrisi" />' +
                            '</form>' +
                        '</td>' +
                        '</tr>'
                    )
                }
                tabela.show();
            }
        })
    }
    popuniKomentare()
    $("body").show()
})