package be.hogent.kolveniershof.injection

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import be.hogent.kolveniershof.adapters.DateAdapter
import be.hogent.kolveniershof.api.KolvApi
import be.hogent.kolveniershof.repository.KolvRepository
import be.hogent.kolveniershof.util.Constants
import be.hogent.kolveniershof.viewmodels.DayViewModel
import be.hogent.kolveniershof.viewmodels.UserViewModel
import com.squareup.moshi.Moshi
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
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
    fun provideOkHttpClient(context: Context): OkHttpClient {
        fun hasNetwork(context: Context): Boolean? {
            var isConnected: Boolean? = false // Initial Value
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnected)
                isConnected = true
            return isConnected
        }

        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(context.cacheDir, cacheSize)

        return OkHttpClient.Builder()
            // Specify the cache we created earlier.
            .cache(myCache)
            // Add an Interceptor to the OkHttpClient.
            .addInterceptor { chain ->

                // Get the request from the chain.
                var request = chain.request()

                /*
                *  Leveraging the advantage of using Kotlin,
                *  we initialize the request and change its header depending on whether
                *  the device is connected to Internet or not.
                */
                request = if (hasNetwork(context)!!)
                /*
                *  If there is Internet, get the cache that was stored 5 seconds ago.
                *  If the cache is older than 5 seconds, then discard it,
                *  and indicate an error in fetching the response.
                *  The 'max-age' attribute is responsible for this behavior.
                */
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                /*
                *  If there is no Internet, get the cache that was stored 7 days ago.
                *  If the cache is older than 7 days, then discard it,
                *  and indicate an error in fetching the response.
                *  The 'max-stale' attribute is responsible for this behavior.
                *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
                */
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                // End of if-else statement

                // Add the modified request to the chain.
                chain.proceed(request)
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