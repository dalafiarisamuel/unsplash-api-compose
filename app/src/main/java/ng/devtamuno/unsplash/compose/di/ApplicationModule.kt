package ng.devtamuno.unsplash.compose.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ng.devtamuno.unsplash.compose.env.Env
import ng.devtamuno.unsplash.compose.file.FileDownloader
import ng.devtamuno.unsplash.compose.file.FileDownloaderImpl
import ng.devtamuno.unsplash.compose.networking.ApiInterface
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import ng.devtamuno.unsplash.compose.data.repository.ImageRepository
import ng.devtamuno.unsplash.compose.data.repository.ImageRepositoryImpl

@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {

    @[Provides Singleton]
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }

    @[Provides Singleton]
    fun providesOkhttpClient(): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
            .followRedirects(true)
            .followSslRedirects(true)
            .cache(null)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)

        okHttpBuilder.addInterceptor { chain: Interceptor.Chain ->
            val request = chain.request()
            val newRequest = request.newBuilder()
                .header("Authorization", "Client-ID ${Env.API_KEY}")
            chain.proceed(newRequest.build())
        }
        return okHttpBuilder.build()
    }

    @[Provides Singleton]
    fun providesApiInterface(retrofit: Retrofit): ApiInterface =
        retrofit.create(ApiInterface::class.java)

    @[Provides Singleton]
    fun providesImageRepository(api: ApiInterface): ImageRepository =
        ImageRepositoryImpl(api)

    @[Provides Singleton]
    fun providesLocalFileDownloader(
        @ApplicationContext context: Context
    ): FileDownloader = FileDownloaderImpl(context)


}