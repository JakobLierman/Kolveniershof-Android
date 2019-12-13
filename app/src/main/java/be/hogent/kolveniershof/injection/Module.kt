package be.hogent.kolveniershof.injection

import be.hogent.kolveniershof.adapters.DateAdapter
import be.hogent.kolveniershof.api.KolvApi
import be.hogent.kolveniershof.repository.KolvRepository
import be.hogent.kolveniershof.util.Constants
import be.hogent.kolveniershof.viewmodels.DayViewModel
import be.hogent.kolveniershof.viewmodels.UserViewModel
import com.squareup.moshi.Moshi
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val viewModelModule = module {
    viewModel { UserViewModel(get()) }
    viewModel { DayViewModel(get()) }
}

val networkModule = module {
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        // Used for Retrofit/OkHttp debugging.
        return HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()
    }

    fun provideRetrofitInterface(okHttpClient: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder()
            .add(DateAdapter())
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    fun provideKolvApi(retrofit: Retrofit): KolvApi {
        return retrofit.create(KolvApi::class.java)
    }

    factory { provideHttpLoggingInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { provideKolvApi(get()) }
    single { provideRetrofitInterface(get()) }
}

val repositoryModule = module {
    fun provideKolvRepository(api: KolvApi/*, dao: KolvDao*/): KolvRepository {
        return KolvRepository(api/*, dao*/)
    }

    factory { provideKolvRepository(get()/*, get()*/) }
}