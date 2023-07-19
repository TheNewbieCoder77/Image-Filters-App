package uz.gita.imagefiltersapp.presentation.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import uz.gita.imagefiltersapp.R
import uz.gita.imagefiltersapp.data.ImageFilter
import uz.gita.imagefiltersapp.databinding.ItemFilterBinding

class ImageFiltersAdapter(private val imageFilters: List<ImageFilter>) : RecyclerView.Adapter<ImageFiltersAdapter.ImageFiltersViewHolder>() {

    private var imageFilterListener: ((ImageFilter)-> Unit)? = null
    private var selectedFilterPosition = 0
    private var prevSelectedFilterPosition = 0

    fun setFilterSelectedListener(f: (ImageFilter)-> Unit){
        imageFilterListener = f
    }

    inner class ImageFiltersViewHolder(val binding: ItemFilterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageFiltersViewHolder {
        val binding = ItemFilterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageFiltersViewHolder(binding)
    }

    override fun getItemCount() = imageFilters.size

    override fun onBindViewHolder(holder: ImageFiltersViewHolder, @SuppressLint("RecyclerView") position: Int) {
        with(holder){
            binding.imageFilterPreview.setImageBitmap(imageFilters[position].filterPreview)
            binding.txtFilterName.text = imageFilters[position].name
            binding.root.setOnClickListener {
                if(selectedFilterPosition != position){
                    imageFilterListener?.invoke(imageFilters[position])
                    prevSelectedFilterPosition = selectedFilterPosition
                    selectedFilterPosition = position
                    with(this@ImageFiltersAdapter){
                        notifyItemChanged(prevSelectedFilterPosition,Unit)
                        notifyItemChanged(selectedFilterPosition,Unit)
                    }
                }

            }
            binding.txtFilterName.setTextColor(ContextCompat.getColor(
                binding.txtFilterName.context,
                if(selectedFilterPosition == position) R.color.primaryDark
                else R.color.primaryText
            ))
        }
    }
}