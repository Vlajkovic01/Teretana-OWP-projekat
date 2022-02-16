$(document).ready(function() {

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
                        '<td>' + komentari[k].trening.naziv + '</td>' +
                        '<td><button>Odobri</button></td>' +
                        '<td><button>Izbrisi</button></td>' +
                        '</tr>'
                    )
                }
                tabela.show();
            }
        })
    }
    popuniKomentare()
    $("body").show() // prikazi stanicu nakon sto se preuzmu podaci
})