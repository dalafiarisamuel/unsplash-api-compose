package ng.devtamuno.unsplash.compose.ui.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import ng.devtamuno.unsplash.compose.R
import ng.devtamuno.unsplash.compose.data.model.ui.Photo
import ng.devtamuno.unsplash.compose.ui.theme.appWhite
import ng.devtamuno.unsplash.compose.ui.theme.color
import ng.devtamuno.unsplash.compose.ui.theme.complementary


@ExperimentalFoundationApi
@Composable
fun UnsplashImageList(
    modifier: Modifier,
    imageList: LazyPagingItems<Photo>,
    lazyListState: LazyGridState,
    nestedScrollConnection: NestedScrollConnection,
    onItemClicked: (Photo?) -> Unit,
    onItemLongClicked: (Photo?) -> Unit
) {

    val isListEmpty by remember { derivedStateOf { imageList.itemCount <= 0 } }

    if (imageList.loadState.refresh is LoadState.Loading) {
        LoadingView(
            modifier = Modifier.fillMaxSize()
        )
    } else {
        Crossfade(targetState = isListEmpty) {
            if (it) {
                EmptyListStateComponent(modifier = Modifier.fillMaxSize())
            } else {
                PhotosList(
                    modifier = modifier,
                    imageList = imageList,
                    lazyListState = lazyListState,
                    nestedScrollConnection = nestedScrollConnection,
                    onItemClicked = onItemClicked,
                    onItemLongClicked = onItemLongClicked,
                )
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
private fun PhotosList(
    modifier: Modifier,
    imageList: LazyPagingItems<Photo>,
    lazyListState: LazyGridState,
    nestedScrollConnection: NestedScrollConnection,
    onItemClicked: (Photo?) -> Unit,
    onItemLongClicked: (Photo?) -> Unit
) {
    LazyVerticalGrid(
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(top = 15.dp)
            .nestedScroll(nestedScrollConnection)
            .then(modifier),
        columns = GridCells.Fixed(2),
        content = {
            items(imageList) { photo ->
                UnsplashImage(
                    modifier = Modifier.animateItemPlacement(),
                    data = photo,
                    onImageClicked = onItemClicked,
                    onImageLongClicked = onItemLongClicked
                )
            }
        })
}

@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun EmptyListStateComponent(
    modifier: Modifier = Modifier,
    term: String = stringResource(id = R.string.searched_term_not_found)
) {

    Column(
        modifier = Modifier.then(modifier),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier
                .size(100.dp)
                .background(color = appWhite, shape = CircleShape),
            painter = painterResource(id = R.drawable.ic_image_search),
            contentScale = ContentScale.Inside,
            contentDescription = null
        )

        Text(
            text = term,
            color = appWhite,
            fontSize = 13.sp,
            fontStyle = FontStyle.Normal,
            modifier = Modifier.padding(top = 10.dp)
        )
    }


}

@ExperimentalFoundationApi
@Composable
private fun UnsplashImage(
    modifier: Modifier,
    data: Photo?,
    onImageClicked: (Photo?) -> Unit,
    onImageLongClicked: (Photo?) -> Unit
) {

    val imageColorParsed = (data?.color?.color ?: Color(0xFF212121))
    val imageColorParseComplementary = imageColorParsed.complementary()
    val isShowProgress by remember { mutableStateOf(MutableTransitionState(true)) }
    val painter = rememberAsyncImagePainter(data?.urls?.small)

    when (painter.state) {
        is AsyncImagePainter.State.Loading,
        is AsyncImagePainter.State.Empty -> { /*default state*/
        }
        is AsyncImagePainter.State.Error,
        is AsyncImagePainter.State.Success -> {
            isShowProgress.targetState = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {

        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = { onImageClicked(data) },
                    onLongClick = { onImageLongClicked(data) }
                )

        ) {
            Image(
                painter = painter,
                contentScale = ContentScale.Crop,
                contentDescription = data?.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.Center),
            visibleState = isShowProgress,
            enter = fadeIn(initialAlpha = 0.4f),
            exit = fadeOut(tween(durationMillis = 250))

        ) { CircularProgressIndicator(color = imageColorParseComplementary) }

    }

}


@ExperimentalFoundationApi
private fun <T : Any> LazyGridScope.items(
    lazyPagingItems: LazyPagingItems<T>,
    itemContent: @Composable LazyGridItemScope.(value: T?) -> Unit
) {
    items(lazyPagingItems.itemCount) { index ->
        itemContent(lazyPagingItems[index])
    }
}
