package uz.gita.imagefiltersapp.domain.repositories

import android.graphics.Bitmap
import android.net.Uri
import uz.gita.imagefiltersapp.data.ImageFilter

interface EditImageRepository {
    suspend fun prepareImagePreview(imageUri: Uri): Bitmap?
    suspend fun getImageFilters(image: Bitmap): List<ImageFilter>
    suspend fun saveFilteredImage(filteredImage: Bitmap): Uri?

}