package uz.gita.imagefiltersapp.presentation.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import uz.gita.imagefiltersapp.data.state.ImageFiltersDataState
import uz.gita.imagefiltersapp.data.state.ImagePreviewState
import uz.gita.imagefiltersapp.data.state.SaveFilteredImageDataState

interface EditImageViewModel {
    val imagePreviewStateLiveData: LiveData<ImagePreviewState>
    val imageFiltersDataStateLiveData: LiveData<ImageFiltersDataState>
    val saveFilteredImageStateLiveData: LiveData<SaveFilteredImageDataState>


    fun prepareImagePreview(imageUri: Uri)
    fun loadImageFilters(originalImage: Bitmap)
    fun saveFilteredImage(filteredImage: Bitmap)

}