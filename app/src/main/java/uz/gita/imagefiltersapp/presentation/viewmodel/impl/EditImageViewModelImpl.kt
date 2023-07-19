package uz.gita.imagefiltersapp.presentation.viewmodel.impl

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.imagefiltersapp.data.ImageFilter
import uz.gita.imagefiltersapp.data.state.ImageFiltersDataState
import uz.gita.imagefiltersapp.data.state.ImagePreviewState
import uz.gita.imagefiltersapp.data.state.SaveFilteredImageDataState
import uz.gita.imagefiltersapp.domain.repositories.EditImageRepository
import uz.gita.imagefiltersapp.presentation.viewmodel.EditImageViewModel
import uz.gita.imagefiltersapp.utils.Coroutines

class EditImageViewModelImpl(private val editImageRepository: EditImageRepository) : ViewModel(), EditImageViewModel {
    override val imagePreviewStateLiveData = MutableLiveData<ImagePreviewState>()
    override val imageFiltersDataStateLiveData = MutableLiveData<ImageFiltersDataState>()
    override val saveFilteredImageStateLiveData = MutableLiveData<SaveFilteredImageDataState>()
    //region:: Prepare Image preview
    private fun emitImagePreviewUiState(isLoading: Boolean = false, bitmap: Bitmap? = null,error: String? = null) {
        val dataState = ImagePreviewState(isLoading, bitmap, error)
        imagePreviewStateLiveData.postValue(dataState)
    }

    override fun prepareImagePreview(imageUri: Uri) {
        Coroutines.io {
            runCatching {
                emitImagePreviewUiState(isLoading = true)
                editImageRepository.prepareImagePreview(imageUri)
            }.onSuccess { bitmap ->
                if (bitmap != null) {
                    emitImagePreviewUiState(bitmap = bitmap)
                } else {
                    emitImagePreviewUiState(error = "Unable to prepare image preview")
                }
            }.onFailure {
                emitImagePreviewUiState(error = it.message.toString())
            }
        }
    }

    //endregion

    //region:: Load ImageFilters
    private fun emitImageFiltersUiState(isLoading: Boolean = false, imageFilters: List<ImageFilter>? = null, error: String? = null) {
        val dataState = ImageFiltersDataState(isLoading, imageFilters, error)
        imageFiltersDataStateLiveData.postValue(dataState)
    }

    private fun getImagePreview(originalImage: Bitmap): Bitmap {
        return runCatching {
            val previewWidth = 150
            val previewHeight = originalImage.height * previewWidth / originalImage.width
            Bitmap.createScaledBitmap(originalImage, previewWidth, previewHeight, false)
        }.getOrDefault(originalImage)
    }

    override fun loadImageFilters(originalImage: Bitmap) {
        Coroutines.io {
            runCatching {
                emitImageFiltersUiState(isLoading = true)
                editImageRepository.getImageFilters(getImagePreview(originalImage))
            }.onSuccess { imageFilters ->
                emitImageFiltersUiState(imageFilters = imageFilters)
            }.onFailure {
                emitImageFiltersUiState(error = it.message.toString())
            }
        }
    }


    //endregion

    //region:: Save Filtered Image
    private fun emitSaveFilteredImageUiState(isLoading: Boolean = false, uri: Uri? = null,error: String? = null){
        val dataState = SaveFilteredImageDataState(isLoading,uri,error)
        saveFilteredImageStateLiveData.postValue(dataState)
    }

    override fun saveFilteredImage(filteredImage: Bitmap) {
        Coroutines.io {
            runCatching {
                emitSaveFilteredImageUiState(isLoading = true)
                editImageRepository.saveFilteredImage(filteredImage)
            }.onSuccess {savedImageUri ->
                emitSaveFilteredImageUiState(uri = savedImageUri)
            }.onFailure {
                emitSaveFilteredImageUiState(error = it.message.toString())
            }
        }
    }

    //endregion

}