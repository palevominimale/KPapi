package com.junopark.kpapi.app.ui.screens

import android.text.style.ClickableSpan
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Tab
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.placeholder.PlaceholderDefaults
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.junopark.kpapi.app.R
import com.junopark.kpapi.app.ui.theme.Typography
import com.junopark.kpapi.entities.films.FilmItemBig
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
@Preview
fun ListScreen(
    modifier: Modifier = Modifier,
    items: List<FilmItemBig> = emptyList(),
    onSelect: (Int) -> Unit = {},
    state: LazyListState = LazyListState(0)
) {
    val highlight = PlaceholderHighlight.shimmer(
        highlightColor = Color.White,
        animationSpec = PlaceholderDefaults.shimmerAnimationSpec
    )
    val filmList = mutableListOf<FilmItemBig>()
    val favsList = mutableListOf<FilmItemBig>()
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(0)
    val tabs = listOf(
        "Search" to Icons.Default.Search,
        "Favourites" to Icons.Default.FavoriteBorder
    )


    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            contentColor = Color.LightGray,
            backgroundColor = Color.White,
            indicator = {tabPositions ->
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .height(4.dp)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(color = Color.LightGray))
            }
        ) {
            tabs.forEachIndexed { index, item ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {scope.launch {pagerState.animateScrollToPage(index)}},
                    modifier = Modifier.height(40.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = item.second,
                            contentDescription = null,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        androidx.compose.material3.Text(text = item.first)
                    }

                }
            }
        }
        HorizontalPager(
            count = 2,
            userScrollEnabled = true,
            state = pagerState
        ) { page ->
        when(page) {
            0 -> {
                LazyColumn(
                    state = state,
                    userScrollEnabled = items.isNotEmpty(),
                    modifier = modifier
                        .fillMaxSize()
                ) {
                    if(items.isNotEmpty()) {
                        items.forEach {
                            item {
                                FilmItem(
                                    item = it,
                                    highlight = highlight,
                                    onSelect = { onSelect(it) }
                                )
                            }
                        }
                    } else {
                        repeat(5) {
                            item {
                                FilmItem(highlight = highlight, clickable = false)
                            }
                        }
                    }
                }
            }
            1 -> {
                LazyColumn(
                    state = state,
                    userScrollEnabled = items.isNotEmpty(),
                    modifier = modifier
                        .fillMaxSize()
                ) {
                    if(items.isNotEmpty()) {
                        items.forEach {
                            item {
                                FilmItem(
                                    item = it,
                                    highlight = highlight,
                                    onSelect = { onSelect(it) }
                                )
                            }
                        }
                    } else {
                        repeat(5) {
                            item {
                                FilmItem(highlight = highlight, clickable = false)
                            }
                        }
                    }
                }
            }
        }
        }



    }

}

@Composable
fun FilmItem(
    item: FilmItemBig? = null,
    highlight: PlaceholderHighlight,
    onSelect: (Int) -> Unit = {},
    clickable: Boolean = true
) {

    Surface(
        shape = RoundedCornerShape(20.dp),
        elevation = 5.dp,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(
                enabled = clickable
            ) {
                if (item?.filmId != null) {
                    onSelect(item.filmId!!)
                } else if (item?.kinopoiskId != null) {
                    onSelect(item.kinopoiskId!!)
                }
            }
            .placeholder(
                visible = item == null || item == FilmItemBig(),
                highlight = highlight,
                color = Color.LightGray,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            var phState by remember { mutableStateOf(true) }
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .data(item?.posterUrl)
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
                if(!item?.nameRu.isNullOrBlank()) {
                    Text(text = "${item?.nameRu} (${item?.year})", style = Typography.labelMedium)
                } else if(!item?.nameOriginal.isNullOrBlank()) {
                    Text(text = "${item?.nameOriginal} (${item?.year})", style = Typography.labelMedium)
                }
                if(!item?.nameEn.isNullOrBlank()) {
                    Text(text = item?.nameEn.toString(), style = Typography.labelSmall)
                }
                if(!item?.description.isNullOrBlank()) {
                    Text(
                        text = item?.description.toString(),
                        style = Typography.labelMedium,
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis
                    )
                } else if(!item?.shortDescription.isNullOrBlank()) {
                    Text(
                        text = item?.shortDescription.toString(),
                        style = Typography.labelMedium,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if(item?.genreItems?.isNotEmpty() == true) {
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
                if(item?.countries?.isNotEmpty() == true) {
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
                    if(!item?.filmLength.isNullOrBlank()) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_time_24),
                            contentDescription = null,
                            tint = Color.LightGray,
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .size(14.dp)
                        )
                        Text(text = item?.filmLength!!, style = Typography.labelMedium)
                    }

                    if(item?.rating != null) {
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