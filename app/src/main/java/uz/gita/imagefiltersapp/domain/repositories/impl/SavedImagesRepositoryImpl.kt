package uz.gita.imagefiltersapp.domain.repositories.impl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import uz.gita.imagefiltersapp.domain.repositories.SavedImagesRepository
import java.io.File

class SavedImagesRepositoryImpl(private val context: Context): SavedImagesRepository {

    override suspend fun loadSavedImages(): List<Pair<File, Bitmap>>? {
        val savedImages = ArrayList<Pair<File,Bitmap>>()
        val direction = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Saved Images")
        val data = direction.listFiles()
        data?.let {
            it.forEach {file ->
                savedImages.add(Pair(file, getPreviewBitmap(file)))
            }
            return savedImages
        } ?: return null
    }

    private fun getPreviewBitmap(file: File): Bitmap{
        val originalBitmap = BitmapFactory.decodeFile(file.absolutePath)
        val width = 150
        val height = ((originalBitmap.height * width) / originalBitmap.width)
        return Bitmap.createScaledBitmap(originalBitmap,width,height,false)
    }
}