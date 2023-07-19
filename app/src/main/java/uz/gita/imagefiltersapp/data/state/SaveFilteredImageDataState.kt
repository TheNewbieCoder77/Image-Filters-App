package uz.gita.imagefiltersapp.data.state

import android.net.Uri

data class SaveFilteredImageDataState(
    val isLoading: Boolean,
    val uri: Uri?,
    val error: String?
)