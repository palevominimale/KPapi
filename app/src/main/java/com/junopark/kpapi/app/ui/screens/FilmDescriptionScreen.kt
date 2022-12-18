package com.junopark.kpapi.app.ui.screens

import android.content.res.Resources.Theme
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface

import com.junopark.kpapi.entities.films.FilmItemBig
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.google.accompanist.placeholder.placeholder
import com.junopark.kpapi.app.R
import com.junopark.kpapi.app.ui.theme.Typography


@Composable
@Preview
fun FilmDescriptionScreen(
    modifier: Modifier = Modifier,
    item: FilmItemBig = FilmItemBig(),
    onBack: () -> Unit = {}
) {
    val genres = item.genreItems.map {
        it.genre ?: "no genre"
    }
    val country = item.countries.map {
        it.country ?: "no country"
    }
    BackHandler { onBack() }

    Surface(modifier = modifier.fillMaxWidth()) {

        Column(
            modifier = Modifier.padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
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
                    ),
                onState = {
                    if(it is AsyncImagePainter.State.Success) phState = false
                    if(it is AsyncImagePainter.State.Error) {
                        phState = false
                    }
                }
            )
            Text(
                text = item.nameRu.toString(),
                style = Typography.bodyLarge
            )
            Row {
                Text(
                    text = item.nameEn.toString(),
                    style = Typography.labelSmall
                )
                Text(text = ",")
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                Text(
                    text = item.year.toString(),
                    style = Typography.labelSmall)
            }
            Text(
                text = genres.joinToString(", "),
                style = Typography.labelSmall
            )
           Row {
               Text(
                   text = country.joinToString(",  "),
                   style = Typography.labelSmall)
               Text(
                   text = item.filmLength.toString() + " мин, ",
                   style = Typography.labelSmall)
               Text(
                   text = item.ratingAgeLimits.toString(),
                   style = Typography.labelSmall)
               
           }
            Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            Text(
                text = item.description.toString(),
                style = Typography.labelSmall,
                modifier = Modifier.padding(horizontal = 10.dp)
            )

        }
    }
}


