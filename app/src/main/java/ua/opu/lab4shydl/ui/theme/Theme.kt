package ua.opu.lab4shydl.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF1565C0),
    secondary = Color(0xFF00897B),
    tertiary = Color(0xFFFF8F00)
)

/**
 * Єдина тема застосунку.
 *
 * Окремий файл теми потрібен, щоб усі екрани мали однаковий стиль.
 */
@Composable
fun TravelPlacesDemoTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        content = content
    )
}
