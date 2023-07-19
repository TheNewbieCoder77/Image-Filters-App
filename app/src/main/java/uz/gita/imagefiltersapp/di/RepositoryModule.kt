package uz.gita.imagefiltersapp.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import uz.gita.imagefiltersapp.domain.repositories.EditImageRepository
import uz.gita.imagefiltersapp.domain.repositories.SavedImagesRepository
import uz.gita.imagefiltersapp.domain.repositories.impl.EditImageRepositoryImpl
import uz.gita.imagefiltersapp.domain.repositories.impl.SavedImagesRepositoryImpl

val repositoryModule = module {
    factory<EditImageRepository> { EditImageRepositoryImpl(androidContext()) }
    factory<SavedImagesRepository> { SavedImagesRepositoryImpl(androidContext()) }
}