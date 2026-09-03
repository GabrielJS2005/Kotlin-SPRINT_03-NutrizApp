package GabrielJS2005.com.github.sprint03_nutrizapp.ui.components

import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.BluePetroleum
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.BluePetroleumSoft
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.NunitoFontFamily
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.SurfaceWhite
import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.TextGrayLight
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class BottomNavItem(
    val route: String,
    val iconSelected: ImageVector,
    val iconUnselected: ImageVector,
    val label: String
) {
    object Home     : BottomNavItem("home",      Icons.Filled.Home,         Icons.Outlined.Home,         "Início")
    object Doacoes  : BottomNavItem("historico", Icons.Filled.Favorite,     Icons.Outlined.FavoriteBorder,"Doações")
    object Aprender : BottomNavItem("educativo", Icons.Filled.MenuBook,     Icons.Outlined.MenuBook,     "Aprender")
    object Suporte  : BottomNavItem("suporte",   Icons.Filled.ChatBubble,   Icons.Outlined.ChatBubbleOutline,"Suporte")
    object Perfil   : BottomNavItem("perfil",    Icons.Filled.Person,       Icons.Outlined.Person,       "Perfil")
}

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Doacoes,
        BottomNavItem.Aprender,
        BottomNavItem.Suporte,
        BottomNavItem.Perfil
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(
        containerColor = SurfaceWhite,
        tonalElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route

            NavigationBarItem(
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        // Pill indicator ao redor do ícone
                        Box(
                            modifier = Modifier
                                .width(48.dp)
                                .height(32.dp)
                                .clip(RoundedCornerShape(50))
                                .background(
                                    if (selected) BluePetroleumSoft else Color.Transparent
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (selected) item.iconSelected else item.iconUnselected,
                                contentDescription = item.label,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                },
                label = {
                    Text(
                        text = item.label,
                        fontFamily = NunitoFontFamily,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 10.sp
                    )
                },
                selected = selected,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            if (item.route == "home") {
                                popUpTo(0) { inclusive = true }
                            } else {
                                popUpTo("home")
                            }
                            launchSingleTop = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = BluePetroleum,
                    selectedTextColor   = BluePetroleum,
                    unselectedIconColor = TextGrayLight,
                    unselectedTextColor = TextGrayLight,
                    indicatorColor      = Color.Transparent  // Desabilitamos o indicador padrão (usamos nosso pill)
                )
            )
        }
    }
}