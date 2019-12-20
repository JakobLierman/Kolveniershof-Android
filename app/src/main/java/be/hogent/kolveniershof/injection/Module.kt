package be.hogent.kolveniershof.injection

import android.app.Application
import android.content.Context
import android.net.Network
import androidx.room.Room
import be.hogent.kolveniershof.adapters.DateAdapter
import be.hogent.kolveniershof.api.KolvApi
import be.hogent.kolveniershof.database.DAO.*
import be.hogent.kolveniershof.database.KolveniershofDatabase
import be.hogent.kolveniershof.repository.*
import be.hogent.kolveniershof.util.Constants
import be.hogent.kolveniershof.viewmodels.DayViewModel
import be.hogent.kolveniershof.viewmodels.UserViewModel
import com.squareup.moshi.Moshi
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
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

val databaseModule = module {
    fun provideDatabase(application: Application): KolveniershofDatabase {

        val db =  Room.databaseBuilder(application, KolveniershofDatabase::class.java, "kolveniershofdb")
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()

        db.clearAllTables()
        return db

    }

    fun provideActivityDao(database: KolveniershofDatabase): ActivityDao {
        return database.ActivityDao()
    }

    fun provideActivityUnitDao(database: KolveniershofDatabase): ActivityUnitDao {
        return database.ActivityUnitDao()
    }

    fun provideActivityUnitUserJOINDao(database: KolveniershofDatabase): ActivityUnitUserJOINDao {
        return database.ActivityUnitUserJOINDao()
    }

    fun provideBusDao(database: KolveniershofDatabase): BusDao {
        return database.BusDao()
    }

    fun provideBusUnitDao(database: KolveniershofDatabase): BusUnitDao {
        return database.BusUnitDao()
    }

    fun provideBusUnitUserJOINDao(database: KolveniershofDatabase): BusUnitUserJOINDao {
        return database.BusUnitUserJOINDao()
    }

    fun provideLunchUnitDao(database: KolveniershofDatabase): LunchUnitDao {
        return database.LunchUnitDao()
    }

    fun provideUserDao(database: KolveniershofDatabase): UserDao {
        return database.UserDao()
    }

    fun provideWorkdayDao(database: KolveniershofDatabase): WorkdayDao {
        return database.WorkdayDao()
    }

    fun provideWorkdayUserJOINDao(database: KolveniershofDatabase): WorkdayUserJOINDao {
        return database.WorkdayUserJOINDao()
    }


    single { provideDatabase(androidApplication()) }
    single { provideActivityDao(get()) }
    single { provideActivityUnitDao(get()) }
    single { provideActivityUnitUserJOINDao(get()) }
    single { provideBusDao(get()) }
    single { provideBusUnitDao(get()) }
    single { provideBusUnitUserJOINDao(get()) }
    single { provideLunchUnitDao(get()) }
    single { provideUserDao(get()) }
    single { provideWorkdayDao(get()) }
    single { provideWorkdayUserJOINDao(get()) }
}

val repositoryModule = module {
    fun provideKolvRepository(api: KolvApi/*, dao: KolvDao*/): KolvRepository {
        return KolvRepository(api/*, dao*/)
    }
    fun provideActivityRepository(kolvApi: KolvApi, activityUnitDao: ActivityUnitDao, activityDao: ActivityDao, activityUnitUserJOINDao: ActivityUnitUserJOINDao, context: Context): ActivityRepository {
        return ActivityRepository(kolvApi, activityUnitDao, activityDao, activityUnitUserJOINDao, context)
    }
    fun provideUserRepository( kolvApi: KolvApi, userDao: UserDao, context: Context): UserRepository {
        return UserRepository(kolvApi, userDao, context)
    }
    fun provideBusRepository(kolvApi: KolvApi, busUnitDao: BusUnitDao, busDao: BusDao, userRepository: UserRepository, busUnitUserJOINDao: BusUnitUserJOINDao, context: Context): BusRepository {
        return BusRepository(kolvApi, busUnitDao, busDao, userRepository, busUnitUserJOINDao, context)
    }
    fun provideWorkdayRepository(api: KolvApi, workdayDao: WorkdayDao, workdayUserJOINDao: WorkdayUserJOINDao, busRepository: BusRepository, activityRepository: ActivityRepository, lunchUnitDao: LunchUnitDao, context: Context): WorkdayRepository {
        return WorkdayRepository(api, workdayDao, workdayUserJOINDao, busRepository, activityRepository, lunchUnitDao, context)
    }





    factory { provideKolvRepository(get()/*, get()*/) }
    factory { provideActivityRepository(get(), get(), get(), get(), androidContext()) }
    factory { provideUserRepository(get(), get(), androidContext()) }
    factory { provideBusRepository(get(), get(), get(), get(), get(), androidContext()) }
    factory { provideWorkdayRepository(get(), get(), get(), get(), get(), get(), androidContext()) }
}

