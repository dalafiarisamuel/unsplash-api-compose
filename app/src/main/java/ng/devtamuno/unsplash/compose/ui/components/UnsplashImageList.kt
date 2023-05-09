package ng.devtamuno.unsplash.compose.ui.components

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.devtamuno.composeblurhash.ext.rememberBlurHashPainter
import kotlinx.coroutines.flow.Flow
import ng.devtamuno.unsplash.compose.R
import ng.devtamuno.unsplash.compose.data.model.ui.Photo
import ng.devtamuno.unsplash.compose.ui.theme.appWhite


@ExperimentalFoundationApi
@Composable
fun UnsplashImageList(
    modifier: Modifier,
    imageList: Flow<PagingData<Photo>>,
    lazyGridState: LazyStaggeredGridState,
    nestedScrollConnection: NestedScrollConnection,
    onItemClicked: (Photo?) -> Unit,
    onItemLongClicked: (Photo?) -> Unit,
) {
    val list = imageList.collectAsLazyPagingItems()
    val isListEmpty by remember { derivedStateOf { list.itemCount <= 0 } }
    if (list.loadState.refresh is LoadState.Loading) {
        LoadingView(modifier = Modifier.fillMaxSize())
    } else {
        Crossfade(targetState = isListEmpty) {
            if (it) {
                EmptyListStateComponent(modifier = Modifier.fillMaxSize())
            } else {
                PhotosList(
                    modifier = modifier,
                    imageList = list,
                    lazyGridState = lazyGridState,
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
    lazyGridState: LazyStaggeredGridState,
    nestedScrollConnection: NestedScrollConnection,
    onItemClicked: (Photo?) -> Unit,
    onItemLongClicked: (Photo?) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        state = lazyGridState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(top = 15.dp)
            .nestedScroll(nestedScrollConnection)
            .then(modifier),
        columns = StaggeredGridCells.Fixed(2)
    ) {
        lazyItems(imageList) { photo ->
            UnsplashImageStaggered(
                modifier = Modifier,
                data = photo,
                onImageClicked = onItemClicked,
                onImageLongClicked = onItemLongClicked
            )
        }
    }
}

@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun EmptyListStateComponent(
    modifier: Modifier = Modifier,
    term: String = stringResource(id = R.string.searched_term_not_found),
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
private fun UnsplashImageStaggered(
    modifier: Modifier,
    data: Photo?,
    onImageClicked: (Photo?) -> Unit,
    onImageLongClicked: (Photo?) -> Unit,
) {

    val aspectRatio: Float by remember {
        derivedStateOf { (data?.width?.toFloat() ?: 1.0F) / (data?.height?.toFloat() ?: 1.0F) }
    }
    val rememberBlurHash = rememberBlurHashPainter(
        blurString = data?.blurHash ?: "",
        width = data?.width ?: 1,
        height = data?.height ?: 1
    )

    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onImageClicked(data) },
                onLongClick = { onImageLongClicked(data) },
            )
            .then(modifier)

    ) {
        AsyncImage(
            model = data?.urls?.small,
            contentDescription = data?.description,
            placeholder = rememberBlurHash,
            error = rememberBlurHash,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .aspectRatio(aspectRatio)
                .fillMaxWidth()
                .defaultMinSize(minHeight = 200.dp)
        )
    }

}

@ExperimentalFoundationApi
private fun <T : Any> LazyStaggeredGridScope.lazyItems(
    lazyPagingItems: LazyPagingItems<T>,
    itemContent: @Composable LazyStaggeredGridItemScope.(value: T?) -> Unit,
) {
    items(lazyPagingItems.itemCount) { index ->
        itemContent(lazyPagingItems[index])
    }
}
