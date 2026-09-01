package GabrielJS2005.com.github.sprint03_nutrizapp.model

enum class StatusDoacao(val descricao: String) {
    EM_ANALISE("Em Análise"),
    ELEGIVEL("Elegível"),
    AGENDADA("Coleta Agendada"),
    CONCLUIDA("Concluída")
}

data class Filho(
    val id: String,
    val nome: String,
    val dataNascimento: String
)

data class Doadora(
    val id: String,
    val nome: String,
    val cpf: String,
    val status: StatusDoacao,
    val litrosDoados: Float,
    val bebesAjudados: Int,
    val badges: List<Badge> = emptyList(),
    val dataNascimento: String = "",
    val cidade: String = "",
    val bairro: String = "",
    val telefone: String = "",
    val email: String = "",
    val possuiFilhos: Boolean = false,
    val filhos: List<Filho> = emptyList()
)

data class Doacao(
    val id: String,
    val data: String,
    val volumeMl: Int,
    val status: StatusDoacao,
    val pontoColeta: PontoColeta? = null
)

data class PontoColeta(
    val id: String,
    val nome: String,
    val endereco: String,
    val distanciaKm: Float = 0f
)

data class ConteudoEducativo(
    val id: String,
    val titulo: String,
    val descricaoBreve: String,
    val tempoLeituraMin: Int,
    val lido: Boolean = false
)

data class Badge(
    val id: String,
    val nome: String,
    val iconeResId: Int? = null,
    val emoji: String = "⭐"
)