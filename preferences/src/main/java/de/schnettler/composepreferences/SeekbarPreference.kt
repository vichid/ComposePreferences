package de.schnettler.composepreferences

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.unit.dp
import com.tfcporciuncula.flow.FlowSharedPreferences
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun SeekBarPreference(
    title: String,
    summary: String,
    singleLineTitle: Boolean,
    icon: VectorAsset,
    key: String,
    defaultValue: Float,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    valueRepresentation: (Float) -> String
) {
    val preferences = PreferenceAmbient.current
    var sliderValue by remember { mutableStateOf(preferences.getFloat(key, defaultValue).get()) }

    Preference(
        title = { Text(text = title, maxLines = if (singleLineTitle) 1 else Int.MAX_VALUE) },
        summary = {
            PreferenceSummary(summary, valueRepresentation, sliderValue, { sliderValue = it }, valueRange, steps,
                preferences, key)
        },
        icon = icon)
}

@Composable
private fun PreferenceSummary(summary: String, valueRepresentation: (Float) -> String, sliderValue: Float, onValueChanged:
(Float) -> Unit, valueRange: ClosedFloatingPointRange<Float>, steps: Int, preferences: FlowSharedPreferences, key: String) {
    Column {
        Text(text = summary)
        Row(verticalGravity = Alignment.CenterVertically) {
            Text(text = valueRepresentation(sliderValue))
            Spacer(modifier = Modifier.width(16.dp))
            Slider(
                value = sliderValue,
                onValueChange = { onValueChanged(it) },
                valueRange = valueRange,
                steps = steps,
                onValueChangeEnd = {
                    preferences.sharedPreferences.edit().putFloat(key, sliderValue).apply()
                }
            )
        }
    }
}