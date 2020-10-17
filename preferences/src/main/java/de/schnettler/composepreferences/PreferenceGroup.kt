package de.schnettler.composepreferences

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PreferenceGroup(title: String, enabled: Boolean = true, content: @Composable () -> Unit) {
    Column {
        Box(
            alignment = Alignment.CenterStart,
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 8.dp)
        ) {
            Text(
                text = title, fontSize = 14.sp, color = MaterialTheme.colors.secondary, fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Providers(AmbientPreferenceEnabled provides enabled) {
            content()
        }
    }
}