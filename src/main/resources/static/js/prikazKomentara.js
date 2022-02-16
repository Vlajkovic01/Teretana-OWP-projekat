var id = window.location.search.slice(1).split('&')[0].split('=')[1];

$(document).ready(function() {

    var tabela = $("table.komentari")

    function popuniKomentare() {

        var params = {
            id: id
        }

        $.get("komentari/prikaz", params, function(odgovor) {
            console.log(odgovor)

            if (odgovor.status == "ok") {
                var komentari = odgovor.komentari
                for (var k in komentari) {

                    var korisnik = komentari[k].autor.korisnickoIme
                    if (komentari[k].anoniman) {
                        korisnik = "Anoniman"
                    }
                    tabela.append(
                        '<tr>' +
                        '<td>' + komentari[k].datum + '</td>' +
                        '<td>' + korisnik + '</td>' +
                        '<td>' + komentari[k].ocena + '</td>' +
                        '<td>' + komentari[k].tekst + '</td>' +
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