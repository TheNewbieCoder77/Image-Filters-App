package uz.gita.imagefiltersapp.presentation.ui.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.imagefiltersapp.R
import uz.gita.imagefiltersapp.databinding.ItemSavedImageBinding
import java.io.File

class SavedImagesAdapter : ListAdapter<Pair<File,Bitmap>, SavedImagesAdapter.SavedImagesListViewHolder>(SavedImageDiffUtil) {
    private var onSavedImageClickedListener: ((file: File) -> Unit)? = null

    fun setOnSavedImageClickedListener(f: (file: File) -> Unit){
        onSavedImageClickedListener = f
    }

    object SavedImageDiffUtil : DiffUtil.ItemCallback<Pair<File,Bitmap>>(){
        override fun areItemsTheSame(oldItem: Pair<File,Bitmap>, newItem: Pair<File,Bitmap>) =
            false

        override fun areContentsTheSame(oldItem: Pair<File,Bitmap>, newItem: Pair<File,Bitmap>) =
            oldItem == newItem

    }


    inner class SavedImagesListViewHolder(private val binding: ItemSavedImageBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(){
            val data = getItem(adapterPosition)
            binding.savedImg.setImageBitmap(data.second)
            binding.savedImg.setOnClickListener {
                onSavedImageClickedListener?.invoke(data.first)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedImagesListViewHolder =
        SavedImagesListViewHolder(ItemSavedImageBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.item_saved_image,parent,false)))

    override fun onBindViewHolder(holder: SavedImagesListViewHolder, position: Int) =
        holder.bind()
}