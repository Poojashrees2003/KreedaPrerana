package com.example.kreedaprerana.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
// MAKE SURE THIS IS THE ONLY TYPOGRAPHY IMPORT
import androidx.compose.material3.Typography

@Composable
fun KreedaPreranaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = lightColorScheme(),
        typography = Typography(), // This now matches the expected Material3 type
        content = content
    )
}