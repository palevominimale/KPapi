package com.junopark.kpapi.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
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
fun SingleItemScreen(
    modifier: Modifier = Modifier,
    item: FilmItemBig = FilmItemBig()
) {

    val highlight = PlaceholderHighlight.shimmer(
        highlightColor = Color.White,
        animationSpec = PlaceholderDefaults.shimmerAnimationSpec
    )

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            var phState by remember { mutableStateOf(true) }
            AsyncImage(
               model = ImageRequest.Builder(LocalContext.current)
                   .memoryCachePolicy(CachePolicy.ENABLED)
                   .diskCachePolicy(CachePolicy.ENABLED)
                   .data(item.posterUrlPreview)
                   .error(R.drawable.ic_movies_24)
                   .crossfade(true)
                   .build(),
               contentDescription = null,
               contentScale = ContentScale.Crop,
               alignment = Alignment.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .height(240.dp)
                    .width(160.dp)
                    .clip(RoundedCornerShape(10.dp))
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp, top = 8.dp, bottom = 8.dp)
            ) {
                Text(text = "${item.nameRu} (${item.year})", style = Typography.labelMedium)
                Text(text = "${item.nameEn}", style = Typography.labelSmall,
                        modifier = Modifier.padding(bottom = 8.dp))
                Text(text = "Рейтинг IMDB: ${item.ratingImdb}", style = Typography.labelMedium)
                Text(text = "Рейтинг Кинопоиска: ${item.ratingKinopoisk}", style = Typography.labelMedium)
                Text(text = "${item.shortDescription}", style = Typography.labelMedium)
            }
        }
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            val genres = item.genreItems.joinToString(", ")
            val countries = item.countries.joinToString(", ")
        }
    }

}