package uz.gita.imagefiltersapp.presentation.viewmodel.impl

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.imagefiltersapp.data.state.SavedImagesDataState
import uz.gita.imagefiltersapp.domain.repositories.SavedImagesRepository
import uz.gita.imagefiltersapp.presentation.viewmodel.SavedImagesViewModel
import uz.gita.imagefiltersapp.utils.Coroutines
import java.io.File

class SavedImagesViewModelImpl(private val savedImagesRepository: SavedImagesRepository) : ViewModel(), SavedImagesViewModel{
    override val savedImagesStateLiveData = MutableLiveData<SavedImagesDataState>()

    private fun emitSavedImagesUiState(isLoading: Boolean = false, savedImages: List<Pair<File, Bitmap>>? = null, error: String? = null){
        val dataState = SavedImagesDataState(isLoading, savedImages, error)
        savedImagesStateLiveData.postValue(dataState)
    }

    override fun loadSavedImages() {
         Coroutines.io {
             runCatching {
                 emitSavedImagesUiState(isLoading = true)
                 savedImagesRepository.loadSavedImages()
             }.onSuccess { savedImages ->
                 if(savedImages.isNullOrEmpty()){
                     emitSavedImagesUiState(error = "No image found")
                 }else{
                     emitSavedImagesUiState(savedImages = savedImages)
                 }
             }.onFailure {
                 emitSavedImagesUiState(error = it.message.toString())
             }
         }
    }

}