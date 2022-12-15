package com.junopark.kpapi.app.ui.screens.bottomsheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.junopark.kpapi.app.R
import com.junopark.kpapi.app.ui.theme.Typography

@Composable
@Preview
fun FilterPad(
    modifier: Modifier = Modifier,
    switchFilter: () -> Unit = {}
) {
    Surface(
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        color = Color.White,
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
    ) {
        Column() {
            SearchBar(
                switchFilter = { switchFilter() }
            )
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                DropdownList(
                    items = listOf("1","2","3","4"),
                    label = stringResource(id = R.string.search_genres),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                DropdownList(
                    items = listOf("1","2","3","4"),
                    label = stringResource(id = R.string.search_countries)
                )
            }
        }
    }
}

@Composable
private fun SearchBar(
    switchFilter: () -> Unit = {},
    searchUpdate: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {

    val focusManager = LocalFocusManager.current

    Surface(
        color = Color.White,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomSearchField(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .shadow(
                        elevation = 5.dp,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(20.dp)
                    ),
                leadingIcon = {
                    Icon(
                        modifier = Modifier.padding(end = 8.dp),
                        painter = rememberVectorPainter(Icons.Outlined.Search),
                        contentDescription = stringResource(id = R.string.search_text),
                        tint = Color.LightGray,
                    )
                },
                style = Typography.labelMedium,
                textUpdate = {
                    searchUpdate(it)
                    focusManager.clearFocus(true)
                }
            )
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clip(CircleShape)
                    .clickable { switchFilter() }
            )
        }
    }
}

@Composable
private fun CustomSearchField(
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = stringResource(id = R.string.search_text),
    style: TextStyle = MaterialTheme.typography.labelMedium,
    textUpdate: (text: String) -> Unit = {}
) {

    var text by remember { mutableStateOf("") }

    BasicTextField(
        modifier = modifier,
        value = text,
        onValueChange = {
            text = it
        },
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        textStyle = style,
        decorationBox = { innerTextField ->
            Row(
                modifier.padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) leadingIcon()
                Box(Modifier.weight(1f)) {
                    if (text.isEmpty()) Text(
                        placeholderText,
                        style = style
                    )
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
        keyboardActions = KeyboardActions(
            onGo = {
                textUpdate(text)
            }
        )
    )
}

@Composable
private fun DropdownList(
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Light),
    items: List<String>,
    onSelect: (String) -> Unit = {},
    text: String? = null,
    label: String = ""
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(text ?: "") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon =
        if (expanded) Icons.Filled.KeyboardArrowUp
        else Icons.Filled.KeyboardArrowDown

    Column() {
        Text(
            text = label,
            style = Typography.labelMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = Color.White,
            border = BorderStroke(1.dp, Color.LightGray),
            modifier = modifier
        ) {
            Column(
                modifier = Modifier.padding(4.dp)
            ) {
                BasicTextField(modifier = Modifier
                    .onGloballyPositioned {
                        textFieldSize = it.size.toSize()
                    },
                    readOnly = false,
                    value = selectedText,
                    onValueChange = {
                        selectedText = it
                        onSelect(it)
                    },
                    singleLine = true,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    textStyle = style,
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier.padding(start = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(Modifier.weight(1f)) {
                                if (selectedText.isEmpty()) {
                                    Text(
                                        text = text ?: items[0],
                                        style = style.copy(Color.LightGray)
                                    )
                                }
                                innerTextField()
                            }
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                modifier = Modifier
                                    .clickable { expanded = !expanded }
                            )
                        }
                    }
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                        .background(color = Color.Unspecified, shape = RoundedCornerShape(20.dp))
                ) {
                    items.forEach { label ->
                        DropdownMenuItem(text = { Text(text = label) },
                            onClick = {
                                selectedText = label
                                expanded = false
                                onSelect(label)
                            }
                        )
                    }
                }
            }
        }
    }
}