package com.junopark.kpapi.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderDefaults
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.junopark.kpapi.app.R
import com.junopark.kpapi.app.ui.theme.Typography
import com.junopark.kpapi.entities.films.FilmItemBig

@Composable
@Preview
fun ListScreen(
    modifier: Modifier = Modifier,
    items: List<FilmItemBig> = listOf(FilmItemBig(),FilmItemBig(),FilmItemBig(),FilmItemBig(),FilmItemBig(),),
    onSelect: (Int, TYPE) -> Unit = { id, type ->
        Pair(id, type)
    }
) {
    val highlight = PlaceholderHighlight.shimmer(
        highlightColor = Color.White,
        animationSpec = PlaceholderDefaults.shimmerAnimationSpec
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items.forEach {
            item {
                FilmItem(
                    item = it,
                    highlight = highlight,
                    onSelect = { id, type ->
                        onSelect(id, type)
                    })
            }
        }
    }

}

enum class TYPE {
    KINOPOISK_ID,
    FILM_ID
}

@Composable
fun FilmItem(
    item: FilmItemBig,
    highlight: PlaceholderHighlight,
    onSelect: (Int, TYPE) -> Unit
) {

    Surface(
        shape = RoundedCornerShape(20.dp),
        elevation = 5.dp,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                if(item.filmId != null) {
                    onSelect(item.filmId!!, TYPE.FILM_ID)
                } else if(item.kinopoiskId != null) {
                    onSelect(item.kinopoiskId!!, TYPE.KINOPOISK_ID)
                }
            }
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            var phState by remember { mutableStateOf(true) }
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .data(item.posterUrl)
                    .error(R.drawable.ic_movies_24)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .width(100.dp)
                    .height(120.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .placeholder(
                        visible = phState,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(16.dp),
                        highlight = highlight
                    ),
                onState = {
                    if(it is AsyncImagePainter.State.Success) phState = false
                    if(it is AsyncImagePainter.State.Error) {
                        phState = false
                    }
                }
            )
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 8.dp)
            ) {
                if(!item.nameRu.isNullOrBlank()) {
                    Text(text = "${item.nameRu} (${item.year})", style = Typography.labelMedium)
                } else if(!item.nameOriginal.isNullOrBlank()) {
                    Text(text = "${item.nameOriginal} (${item.year})", style = Typography.labelMedium)
                }
                if(!item.nameEn.isNullOrBlank()) {
                    Text(text = item.nameEn.toString(), style = Typography.labelSmall)
                }
                if(!item.description.isNullOrBlank()) {
                    Text(
                        text = item.description.toString(),
                        style = Typography.labelMedium,
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis
                    )
                } else if(!item.shortDescription.isNullOrBlank()) {
                    Text(
                        text = item.shortDescription.toString(),
                        style = Typography.labelMedium,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if(item.genreItems.isNotEmpty()) {
                    val genres = item.genreItems.map {
                        it.genre ?: "no genre"
                    }
                    Text(
                        text = genres.joinToString(", "),
                        style = Typography.labelSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if(item.countries.isNotEmpty()) {
                    val countries = item.countries.map {
                        it.country ?: "no country"
                    }
                    Text(
                        text = countries.joinToString(", "),
                        style = Typography.labelSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row {
                    if(!item.filmLength.isNullOrBlank()) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_time_24),
                            contentDescription = null,
                            tint = Color.LightGray,
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .size(14.dp)
                        )
                        Text(text = item.filmLength!!, style = Typography.labelMedium)
                    }

                    if(item.rating != null) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color.Yellow,
                            modifier = Modifier
                                .padding(start = 16.dp, end = 4.dp)
                                .size(14.dp)
                        )
                        Text(text = item.rating.toString(), style = Typography.labelMedium)
                    }
                }
            }
        }
    }

}