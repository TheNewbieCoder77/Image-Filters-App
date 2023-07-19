package uz.gita.imagefiltersapp.di



import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import uz.gita.imagefiltersapp.presentation.viewmodel.impl.EditImageViewModelImpl
import uz.gita.imagefiltersapp.presentation.viewmodel.impl.SavedImagesViewModelImpl

val viewModelModule = module {
    viewModel { EditImageViewModelImpl(editImageRepository = get()) }
    viewModel { SavedImagesViewModelImpl(savedImagesRepository = get()) }
}