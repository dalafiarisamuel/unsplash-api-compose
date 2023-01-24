package ng.devtamuno.unsplash.compose.ui.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.request.ImageResult
import coil.transform.RoundedCornersTransformation
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
    isDataReturnedEmpty: ((Boolean) -> Unit)? = null,
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
            items(imageList.itemCount) { index ->
                with(imageList[index]) {
                    UnsplashImage(
                        this,
                        onImageClicked = onItemClicked,
                        onImageLongClicked = onItemLongClicked
                    )
                }
            }

            imageList.apply {
                isDataReturnedEmpty?.invoke(imageList.itemCount <= 0)
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            LoadingView(
                                modifier =
                                Modifier.wrapContentSize()
                            )
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        item { LoadingItem() }
                    }
                }
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
    data: Photo?,
    onImageClicked: (Photo?) -> Unit,
    onImageLongClicked: (Photo?) -> Unit
) {

    val imageColorParsed = (data?.color?.color ?: Color(0xFF212121))
    val imageColorParseComplementary = imageColorParsed.complementary()
    val isShowProgress by remember { mutableStateOf(MutableTransitionState(true)) }
    val imageRequestListener = remember {
        object : ImageRequest.Listener {
            override fun onSuccess(
                request: ImageRequest,
                metadata: ImageResult.Metadata
            ) {
                super.onSuccess(request, metadata)
                isShowProgress.targetState = false
            }

            override fun onStart(request: ImageRequest) {
                super.onStart(request)
                isShowProgress.targetState = true

            }
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth()
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
                painter = rememberImagePainter(data?.urls?.small) {
                    transformations(RoundedCornersTransformation(10f))
                    listener(imageRequestListener)
                },
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


/*
@ExperimentalFoundationApi
public fun <T : Any> LazyGridScope.items(
    lazyPagingItems: LazyPagingItems<T>,
    itemContent: @Composable LazyItemScope.(value: T?) -> Unit
) {
    items(lazyPagingItems.itemCount) { index ->
        itemContent(lazyPagingItems[index])
    }
}*/
