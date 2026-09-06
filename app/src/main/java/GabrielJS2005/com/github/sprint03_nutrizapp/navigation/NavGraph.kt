package GabrielJS2005.com.github.sprint03_nutrizapp.navigation

import GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.agendamentos.AgendamentoScreen
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.cadastro.CadastroScreen
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.detalhes.DetalhesAgendamentoScreen
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.documentos.DocumentosScreen
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.documentos.MeusDocumentosScreen
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.educativo.ConteudoEducativoScreen
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.historico.HistoricoScreen
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.login.LoginScreen
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.notificacoes.NotificacoesScreen
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.onboarding.OnboardingScreen
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.perfil.DadosPessoaisScreen
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.perfil.PerfilScreen
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.splash.SplashScreen
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.suporte.SuporteScreen
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.triagem.TriagemScreen
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.screens.home.HomeScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(navController = navController)
        }
        composable("onboarding") {
            OnboardingScreen(navController = navController)
        }
        composable("login") {
            LoginScreen(navController = navController)
        }

        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("historico") {
            HistoricoScreen(navController = navController)
        }
        composable("educativo") {
            ConteudoEducativoScreen(navController = navController)
        }
        composable("suporte") {
            SuporteScreen(navController = navController)
        }
        composable("perfil") {
            PerfilScreen(navController = navController)
        }
        composable("dados_pessoais") {
            DadosPessoaisScreen(navController = navController)
        }

        composable("cadastro") {
            CadastroScreen(navController = navController)
        }
        composable("triagem") {
            TriagemScreen(navController = navController)
        }

        composable("documentos") {
            DocumentosScreen(navController = navController, fromTriagem = false)
        }
        composable("documentos_triagem") {
            DocumentosScreen(navController = navController, fromTriagem = true)
        }
        composable("meus_documentos") {
            MeusDocumentosScreen(navController = navController)
        }

        composable("agendamento") {
            AgendamentoScreen(navController = navController)
        }

        composable("notificacoes") {
            NotificacoesScreen(navController = navController)
        }

        composable("detalhes_agendamento/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DetalhesAgendamentoScreen(navController = navController, agendamentoId = id)
        }
    }
}
