package uz.gita.imagefiltersapp.data.state

import uz.gita.imagefiltersapp.data.ImageFilter

data class ImageFiltersDataState(
    val isLoading: Boolean,
    val filtersList: List<ImageFilter>?,
    val error: String?
)
