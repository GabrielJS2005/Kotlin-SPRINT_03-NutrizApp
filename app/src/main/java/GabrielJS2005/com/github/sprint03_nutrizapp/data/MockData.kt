package GabrielJS2005.com.github.sprint03_nutrizapp.data

import GabrielJS2005.com.github.sprint03_nutrizapp.model.*

object MockData {
    val badges = listOf(
        Badge("1", "Primeira Doação", emoji = "🍼"),
        Badge("2", "Super Mãe", emoji = "🦸‍♀️"),
        Badge("3", "1 Litro Doador", emoji = "💧")
    )

    val filhosMock = listOf(
        Filho("f1", "Pedro Henrique Silva", "14/03/2022"),
        Filho("f2", "Ana Clara Silva", "07/09/2024")
    )

    val doadoraMock = Doadora(
        id = "d1",
        nome = "Maria Silva",
        cpf = "***.***.***-12",
        status = StatusDoacao.ELEGIVEL,
        litrosDoados = 2.3f,
        bebesAjudados = 3,
        badges = badges.take(2),
        dataNascimento = "22/04/1992",
        cidade = "São Paulo",
        bairro = "Pinheiros",
        telefone = "(11) 98765-4321",
        email = "maria.silva@email.com",
        possuiFilhos = true,
        filhos = filhosMock
    )

    val pontosColeta = listOf(
        PontoColeta("p1", "Hospital das Clínicas", "Av. Dr. Enéas Carvalho de Aguiar, 255", 2.5f),
        PontoColeta("p2", "UBS Vila Prudente", "R. Bonfim, 135", 5.1f),
        PontoColeta("p3", "Hospital Santana", "Av. Tucuruvi, 1010", 8.4f)
    )

    val historicoDoacoes = listOf(
        Doacao("do1", "10/07/2026", 180, StatusDoacao.CONCLUIDA, pontosColeta[0]),
        Doacao("do2", "22/06/2026", 210, StatusDoacao.CONCLUIDA, pontosColeta[1]),
        Doacao("do3", "05/08/2026", 150, StatusDoacao.AGENDADA, pontosColeta[0])
    )

    val conteudosEducativos = listOf(
        ConteudoEducativo(
            "c1",
            "Como ordenhar o leite",
            "Dicas práticas para uma ordenha manual indolor e eficiente.",
            3
        ),
        ConteudoEducativo("c2", "Armazenamento seguro", "Saiba a temperatura e os frascos ideais para guardar o leite.", 5),
        ConteudoEducativo("c3", "Mitos e Verdades", "Desmistificando a produção de leite e a alimentação da mãe.", 4, lido = true)
    )
}