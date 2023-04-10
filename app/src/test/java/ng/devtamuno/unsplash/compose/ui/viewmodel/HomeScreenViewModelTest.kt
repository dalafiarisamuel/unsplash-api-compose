package ng.devtamuno.unsplash.compose.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import io.kotest.assertions.withClue
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.testCoroutineScheduler
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import io.kotest.matchers.string.shouldNotBeEmpty
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import ng.devtamuno.unsplash.compose.data.dummy.RemoteData
import ng.devtamuno.unsplash.compose.data.mapper.PhotoCreatorMapper
import ng.devtamuno.unsplash.compose.data.mapper.PhotoMapper
import ng.devtamuno.unsplash.compose.data.mapper.PhotosUrlsMapper
import ng.devtamuno.unsplash.compose.data.repository.ImageRepository
import ng.devtamuno.unsplash.compose.ui.event.HomeScreenEvent

@OptIn(
    ExperimentalStdlibApi::class,
    ExperimentalKotest::class,
    ExperimentalCoroutinesApi::class
)
class HomeScreenViewModelTest : FeatureSpec({

    val dispatcher =
        StandardTestDispatcher(scheduler = TestCoroutineScheduler())

    val photoMapper = PhotoMapper(
        photosUrlsMapper = PhotosUrlsMapper(),
        photoCreatorMapper = PhotoCreatorMapper(),
    )

    val imageRepository: ImageRepository = mockk()
    val savedInstanceState: SavedStateHandle = mockk()

    val singlePhoto = photoMapper.mapToUI(RemoteData.remotePhoto)

    var viewModel: HomeScreenViewModel

    beforeTest {
        Dispatchers.setMain(dispatcher)

        every {
            savedInstanceState.get<String>(HomeScreenViewModel.CURRENT_QUERY)
        } returns "dog"

        coEvery {
            imageRepository.getImageSearchResult(
                query = "cat",
                page = 1,
                loadSize = 2
            )
        } returns RemoteData.remoteResponse
    }

    afterTest {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    feature("HomeScreenViewModel").config(coroutineTestScope = true) {

        scenario("When HomeScreenViewModel is initialised") {

            viewModel = HomeScreenViewModel(
                repository = imageRepository,
                photoMapper = photoMapper,
                state = savedInstanceState
            )


            withClue("View Model state.searchField should be empty") {
                viewModel.state.searchFieldValue.shouldBeEmpty()
            }

            dispatcher.testCoroutineScheduler.advanceTimeBy(1_100)

            withClue("View Model state.searchField should not be empty") {
                viewModel.state.searchFieldValue.shouldNotBeEmpty()
            }

            withClue("state.searchField value should be set to SavedStateHandle value") {
                viewModel.state.searchFieldValue.shouldBe("dog")
            }

            val photos = viewModel.photos.first()
            withClue("view model photos should no be null") {
                photos.shouldNotBeNull()
            }
        }

        scenario("When HomeScreenEvent.UpdateSearchField is dispatched") {

            viewModel = HomeScreenViewModel(
                repository = imageRepository,
                photoMapper = photoMapper,
                state = savedInstanceState
            )

            //when event is dispatched
            viewModel.dispatch(HomeScreenEvent.UpdateSearchField(searchTerm = "cat"))

            dispatcher.testCoroutineScheduler.advanceTimeBy(900)

            withClue("state.searchField should be updated to search term") {
                viewModel.state.searchFieldValue.shouldBe("cat")
            }

        }

        scenario("When HomeScreenEvent.SelectChip is dispatched") {

            viewModel = HomeScreenViewModel(
                repository = imageRepository,
                photoMapper = photoMapper,
                state = savedInstanceState
            )

            //when event is dispatched
            viewModel.dispatch(HomeScreenEvent.SelectChip(chipValue = "france"))

            dispatcher.testCoroutineScheduler.advanceTimeBy(900)

            withClue("state.searchField should be updated to search term") {
                viewModel.state.searchFieldValue.shouldBe("france")
            }

        }

        scenario("When HomeScreenEvent.SelectImage is dispatched") {

            viewModel = HomeScreenViewModel(
                repository = imageRepository,
                photoMapper = photoMapper,
                state = savedInstanceState
            )

            //when event is dispatched
            viewModel.dispatch(HomeScreenEvent.SelectImage(image = singlePhoto))

            dispatcher.testCoroutineScheduler.advanceTimeBy(900)

            withClue("state.selectedImage should be updated to selected image") {
                viewModel.state.selectedImage.shouldBe(singlePhoto)
            }

        }

        scenario("When HomeScreenEvent.OnImageClicked is dispatched") {

            viewModel = HomeScreenViewModel(
                repository = imageRepository,
                photoMapper = photoMapper,
                state = savedInstanceState
            )

            //when event is dispatched
            viewModel.dispatch(HomeScreenEvent.OnImageClicked(image = singlePhoto))

            dispatcher.testCoroutineScheduler.advanceTimeBy(900)

            withClue("state.selectedImage should be updated to selected image") {
                viewModel.state.selectedImage.shouldBe(singlePhoto)
            }

            withClue("state.isImagePreviewDialogVisible should be true") {
                viewModel.state.isImagePreviewDialogVisible.shouldBeTrue()
            }


        }

        scenario("When HomeScreenEvent.ImagePreviewDialog.Open is dispatched") {

            viewModel = HomeScreenViewModel(
                repository = imageRepository,
                photoMapper = photoMapper,
                state = savedInstanceState
            )

            withClue("state.isImagePreviewDialogVisible should be false") {
                viewModel.state.isImagePreviewDialogVisible.shouldBeFalse()
            }

            viewModel.dispatch(HomeScreenEvent.ImagePreviewDialog.Open)

            dispatcher.testCoroutineScheduler.advanceTimeBy(900)

            withClue("state.isImagePreviewDialogVisible should be true") {
                viewModel.state.isImagePreviewDialogVisible.shouldBeTrue()
            }

        }

        scenario("When HomeScreenEvent.ImagePreviewDialog.Dismiss is dispatched") {

            viewModel = HomeScreenViewModel(
                repository = imageRepository,
                photoMapper = photoMapper,
                state = savedInstanceState
            )

            viewModel.dispatch(HomeScreenEvent.ImagePreviewDialog.Open)

            dispatcher.testCoroutineScheduler.advanceTimeBy(900)

            withClue("state.isImagePreviewDialogVisible should be true") {
                viewModel.state.isImagePreviewDialogVisible.shouldBeTrue()
            }

            viewModel.dispatch(HomeScreenEvent.ImagePreviewDialog.Dismiss)

            dispatcher.testCoroutineScheduler.advanceTimeBy(900)

            withClue("state.isImagePreviewDialogVisible should be false") {
                viewModel.state.isImagePreviewDialogVisible.shouldBeFalse()
            }

        }

    }

})