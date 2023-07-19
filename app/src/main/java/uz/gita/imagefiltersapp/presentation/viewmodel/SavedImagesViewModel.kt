package uz.gita.imagefiltersapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.imagefiltersapp.data.state.SavedImagesDataState

interface SavedImagesViewModel {
    val savedImagesStateLiveData: LiveData<SavedImagesDataState>

    fun loadSavedImages()
}