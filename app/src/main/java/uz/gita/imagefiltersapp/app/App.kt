package uz.gita.imagefiltersapp.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import uz.gita.imagefiltersapp.di.repositoryModule
import uz.gita.imagefiltersapp.di.viewModelModule

@Suppress("unused")
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(repositoryModule, viewModelModule)
        }
    }
}