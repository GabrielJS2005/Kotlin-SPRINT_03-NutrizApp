package GabrielJS2005.com.github.sprint03_nutrizapp.ui.components

import GabrielJS2005.com.github.sprint03_nutrizapp.ui.theme.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NutrizButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    variant: NutrizButtonVariant = NutrizButtonVariant.PRIMARY,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    val shape = RoundedCornerShape(16.dp)

    when (variant) {
        NutrizButtonVariant.PRIMARY -> {
            Button(
                onClick = onClick,
                modifier = modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = enabled,
                shape = shape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = BluePetroleum,
                    contentColor = Color.White,
                    disabledContainerColor = InputBackground,
                    disabledContentColor = TextGrayLight
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 8.dp
                )
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                    Spacer(Modifier.width(8.dp))
                }
                Text(
                    text = text,
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
        NutrizButtonVariant.OUTLINE -> {
            OutlinedButton(
                onClick = onClick,
                modifier = modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = enabled,
                shape = shape,
                border = androidx.compose.foundation.BorderStroke(
                    width = 2.dp,
                    color = if (enabled) BluePetroleum else BorderColor
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = BluePetroleum,
                    disabledContentColor = TextGrayLight
                )
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                    Spacer(Modifier.width(8.dp))
                }
                Text(
                    text = text,
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
        NutrizButtonVariant.GHOST -> {
            TextButton(
                onClick = onClick,
                modifier = modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = enabled,
                shape = shape,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = BluePetroleum,
                    disabledContentColor = TextGrayLight
                )
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                    Spacer(Modifier.width(8.dp))
                }
                Text(
                    text = text,
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            }
        }
        NutrizButtonVariant.CORAL -> {
            Button(
                onClick = onClick,
                modifier = modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = enabled,
                shape = shape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CoralSoft,
                    contentColor = Color.White,
                    disabledContainerColor = InputBackground,
                    disabledContentColor = TextGrayLight
                )
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                    Spacer(Modifier.width(8.dp))
                }
                Text(
                    text = text,
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

enum class NutrizButtonVariant {
    PRIMARY, OUTLINE, GHOST, CORAL
}