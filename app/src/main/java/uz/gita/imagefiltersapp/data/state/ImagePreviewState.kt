package uz.gita.imagefiltersapp.data.state

import android.graphics.Bitmap

data class ImagePreviewState(
    val isLoading: Boolean,
    val bitmap: Bitmap?,
    val error: String?
)
