package ng.devtamuno.unsplash.compose.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ng.devtamuno.unsplash.compose.R
import ng.devtamuno.unsplash.compose.ui.theme.appDark
import ng.devtamuno.unsplash.compose.ui.theme.appWhite


@Preview(name = "Chip Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Chip Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Chip(
    chip: ChipData = ChipData(
        stringResource(id = R.string.current_events_emoji),
        stringResource(id = R.string.current_events)
    ),
    isSelected: Boolean = true,
    onSelectionChanged: (String) -> Unit = {},
) {

    Surface(
        modifier = Modifier.padding(4.dp),
        elevation = 3.dp,
        shape = RoundedCornerShape(50),
        color = if (isSelected) appWhite else appDark
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = {
                    if (!it) {
                        onSelectionChanged("")
                        return@toggleable
                    }
                    onSelectionChanged(chip.chipText)
                }
            )
        ) {
            Text(
                text = chip.emoji + " " + chip.chipText,
                style = MaterialTheme.typography.caption,
                color = if (isSelected) appDark else appWhite,
                modifier = Modifier.padding(8.dp)
            )
        }
    }

}

@Preview
@Composable
fun ChipGroup(
    modifier: Modifier = Modifier,
    itemList: List<ChipData> = listOf(
        ChipData(
            stringResource(id = R.string.current_events_emoji),
            stringResource(id = R.string.current_events)
        ),
        ChipData(
            stringResource(id = R.string.nature_emoji),
            stringResource(id = R.string.nature)
        )
    ),
    selectedText: String? = null,
    onSelectedChanged: (String) -> Unit = {},
) {
    Column(
        modifier = modifier
            .background(color = Color.Transparent)
    ) {
        LazyRow {
            items(itemList) { chip ->
                Chip(
                    chip = chip,
                    isSelected = selectedText?.equals(chip.chipText, ignoreCase = true) ?: false,
                    onSelectionChanged = {
                        onSelectedChanged(it)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun ChipComponent(
    modifier: Modifier = Modifier,
    selectedText: String? = null,
    textValueChange: ((String) -> Unit)? = null
) {

    ChipGroup(
        modifier = modifier,
        selectedText = selectedText,
        itemList = listOf(
            ChipData(
                stringResource(id = R.string.current_events_emoji),
                stringResource(id = R.string.current_events)
            ),
            ChipData(
                stringResource(id = R.string.nigeria_emoji),
                stringResource(id = R.string.nigeria)
            ),
            ChipData(
                stringResource(id = R.string.nature_emoji),
                stringResource(id = R.string.nature)
            ),
            ChipData(
                stringResource(id = R.string.fashion_emoji),
                stringResource(id = R.string.fashion)
            ),
            ChipData(
                stringResource(id = R.string.people_emoji),
                stringResource(id = R.string.people)
            ),
            ChipData(
                stringResource(id = R.string.technology_emoji),
                stringResource(id = R.string.technology)
            ),
            ChipData(
                stringResource(id = R.string.film_emoji),
                stringResource(id = R.string.film)
            ),
            ChipData(
                stringResource(id = R.string.travel_emoji),
                stringResource(id = R.string.travel)
            ),
            ChipData(
                stringResource(id = R.string.history_emoji),
                stringResource(id = R.string.history)
            ),
            ChipData(
                stringResource(id = R.string.animals_emoji),
                stringResource(id = R.string.animals)
            ),
            ChipData(
                stringResource(id = R.string.food_and_drink_emoji),
                stringResource(id = R.string.food_and_drink)
            ),
            ChipData(
                stringResource(id = R.string.spirituality_emoji),
                stringResource(id = R.string.spirituality)
            ),
            ChipData(
                stringResource(id = R.string.architecture_emoji),
                stringResource(id = R.string.architecture)
            ),
            ChipData(
                stringResource(id = R.string.business_and_work_emoji),
                stringResource(id = R.string.business_and_work)
            ),
            ChipData(
                stringResource(id = R.string.interior_emoji),
                stringResource(id = R.string.interior)
            ),
            ChipData(
                stringResource(id = R.string.experimental_emoji),
                stringResource(id = R.string.experimental)
            ),
            ChipData(
                stringResource(id = R.string.textures_and_patterns_emoji),
                stringResource(id = R.string.textures_and_patterns)
            ),
        )
    ) {
        textValueChange?.invoke(it)
    }

}

data class ChipData(val emoji: String, val chipText: String)
