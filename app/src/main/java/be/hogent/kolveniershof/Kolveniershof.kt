package be.hogent.kolveniershof

import android.app.Application
import be.hogent.kolveniershof.injection.appComponent
import net.danlew.android.joda.JodaTimeAndroid
import org.koin.android.ext.android.startKoin

class Kolveniershof : Application() {
    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
        startKoin(this, appComponent)
    }
}