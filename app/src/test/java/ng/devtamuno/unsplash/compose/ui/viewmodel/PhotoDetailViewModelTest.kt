package ng.devtamuno.unsplash.compose.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import io.kotest.assertions.withClue
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import ng.devtamuno.unsplash.compose.data.dummy.RemoteData
import ng.devtamuno.unsplash.compose.data.repository.ImageRepository
import ng.devtamuno.unsplash.compose.data.repository.Resource
import ng.devtamuno.unsplash.compose.file.FileDownloader
import ng.devtamuno.unsplash.compose.ui.navigation.ARG_PHOTO_ID

@OptIn(
    ExperimentalKotest::class, ExperimentalCoroutinesApi::class
)
class PhotoDetailViewModelTest : FeatureSpec({

    val dispatcher = StandardTestDispatcher(scheduler = TestCoroutineScheduler())

    val imageRepository: ImageRepository = mockk()
    val savedInstanceState: SavedStateHandle = mockk()
    val fileDownloader: FileDownloader = mockk()

    val singlePhoto = RemoteData.remotePhoto

    lateinit var viewModel: PhotoDetailViewModel

    beforeTest {
        Dispatchers.setMain(dispatcher)

        every {
            savedInstanceState.get<String>(ARG_PHOTO_ID)
        } returns "ldLty8YYYP4"

        coEvery {
            imageRepository.getPhoto("ldLty8YYYP4")
        } returns Resource.Success(singlePhoto)

        viewModel = PhotoDetailViewModel(imageRepository, fileDownloader, savedInstanceState)

    }


    afterTest {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    feature("PhotoDetailViewModel").config(coroutineTestScope = true) {

        scenario("When PhotoDetailViewModel is initialised") {

            withClue("photo id from savedInstanceState should not be empty") {

                viewModel.uiState.test {

                    withClue("first emission should be null") {
                        awaitItem().intentPhotoId.shouldBeNull()
                    }

                    withClue("second emission should not be null") {
                        awaitItem().intentPhotoId.shouldBe("ldLty8YYYP4")
                    }

                    cancelAndIgnoreRemainingEvents()
                }
            }
        }

        scenario("When getPhoto() method is triggered") {

            withClue("ui state isLoading property should mutate") {
                viewModel.uiState.test {

                    withClue("first emission should be true") {
                        awaitItem().isLoading.shouldBeTrue()
                    }

                    withClue("second emission should be true") {
                        awaitItem().isLoading.shouldBeTrue()
                    }

                    withClue("third emission should be false") {
                        awaitItem().isLoading.shouldBeFalse()
                    }

                    cancelAndIgnoreRemainingEvents()
                }

            }

        }

        scenario("When getPhoto() method returns a success state") {

            withClue("ui state photo property should mutate") {
                viewModel.uiState.test {

                    withClue("first emission should be null") {
                        awaitItem().photo.shouldBeNull()
                    }

                    withClue("second emission should be null") {
                        awaitItem().photo.shouldBeNull()
                    }

                    withClue("third emission should not be null") {
                        awaitItem().photo.also {
                            it.shouldNotBeNull()
                            it.shouldBe(singlePhoto)

                        }
                    }

                    cancelAndIgnoreRemainingEvents()
                }

            }

        }

        scenario("When getPhoto() method returns an error state") {

            withClue("Given getPhoto() returns error state") {
                coEvery {
                    imageRepository.getPhoto("ldLty8YYYP4")
                } returns Resource.Failure(Throwable("error message"))
            }

            withClue("ui state error property should mutate") {
                viewModel.uiState.test {

                    withClue("first emission should be null") {
                        awaitItem().error.shouldBeNull()
                    }

                    withClue("second emission should be null") {
                        awaitItem().error.shouldBeNull()
                    }

                    withClue("third emission should not be null") {
                        awaitItem().error.also {
                            it.shouldNotBeNull()
                            it.shouldHaveMessage("error message")

                        }
                    }

                    cancelAndIgnoreRemainingEvents()
                }

            }

        }

    }

})