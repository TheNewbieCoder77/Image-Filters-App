package uz.gita.imagefiltersapp.presentation.ui.activities


import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import jp.co.cyberagent.android.gpuimage.GPUImage
import uz.gita.imagefiltersapp.databinding.ActivityEditImageBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.gita.imagefiltersapp.data.state.ImageFiltersDataState
import uz.gita.imagefiltersapp.data.state.ImagePreviewState
import uz.gita.imagefiltersapp.data.state.SaveFilteredImageDataState
import uz.gita.imagefiltersapp.presentation.ui.adapters.ImageFiltersAdapter
import uz.gita.imagefiltersapp.presentation.viewmodel.impl.EditImageViewModelImpl
import uz.gita.imagefiltersapp.utils.displayToast
import uz.gita.imagefiltersapp.utils.show

class EditImageActivity : AppCompatActivity() {
    companion object{
        const val KEY_FILTERED_IMAGE_URI = "filteredImageImage"
    }
    private lateinit var viewBinding: ActivityEditImageBinding
    private val viewModel: EditImageViewModelImpl by viewModel()
    private lateinit var adapter: ImageFiltersAdapter
    private lateinit var gpuImage: GPUImage

    //Image bitmaps
    private lateinit var originalBitmap: Bitmap
    private val filteredBitmap = MutableLiveData<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityEditImageBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setupObservers()
        prepareImagePreview()
        clickEvents()
    }

    private fun setupObservers(){
        viewModel.imagePreviewStateLiveData.observe(this,imagePreviewStateObserver)
        viewModel.imageFiltersDataStateLiveData.observe(this,imageFiltersObserver)
        filteredBitmap.observe(this, filteredBitmapObserver)
        viewModel.saveFilteredImageStateLiveData.observe(this,saveFilteredImageStateObserver)
    }

    private val imagePreviewStateObserver = Observer<ImagePreviewState>{ it ->
        val dataState = it ?: return@Observer
        viewBinding.imagePreviewProgressBar.visibility =
            if(dataState.isLoading) View.VISIBLE else View.GONE

        dataState.bitmap?.let { bitmap ->
            originalBitmap = bitmap
            filteredBitmap.value = bitmap
            gpuImage.setImage(originalBitmap)
            viewBinding.imgPreview.show()
            viewModel.loadImageFilters(originalBitmap)
        } ?: kotlin.run {
            dataState.error?.let {error ->
                displayToast(error)
            }
        }
    }

    private val imageFiltersObserver = Observer<ImageFiltersDataState>{
        val dataState = it ?: return@Observer
        viewBinding.imageFiltersProgressBar.visibility =
            if(dataState.isLoading) View.VISIBLE else View.GONE

        dataState.filtersList?.let { imageFilters->
            adapter = ImageFiltersAdapter(imageFilters)
            viewBinding.filtersRv.adapter = adapter
            setListenersInAdapter()
        } ?: kotlin.run {
            dataState.error?.let { error->
                displayToast(error)
            }
        }

    }

    private val filteredBitmapObserver = Observer<Bitmap> {
        viewBinding.imgPreview.setImageBitmap(it)
    }

    private fun prepareImagePreview(){
        gpuImage = GPUImage(applicationContext)
        intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URI)?.let { imgUri ->
            viewModel.prepareImagePreview(imgUri)
        }
    }

    private val saveFilteredImageStateObserver = Observer<SaveFilteredImageDataState>{
        val dataState = it ?:return@Observer
        if(dataState.isLoading){
            viewBinding.btnSave.visibility = View.GONE
            viewBinding.savingProgressBar.visibility = View.VISIBLE
        }else{
            viewBinding.savingProgressBar.visibility = View.GONE
            viewBinding.btnSave.visibility = View.VISIBLE
        }
        dataState.uri?.let {savedImageUri ->
            val intent = Intent(applicationContext,FilteredImageActivity::class.java)
            intent.putExtra(KEY_FILTERED_IMAGE_URI,savedImageUri)
            startActivity(intent)
        } ?: kotlin.run {
            dataState.error?.let { error ->
                displayToast(error)
            }
        }
    }

    private fun setListenersInAdapter(){
        adapter.setFilterSelectedListener {
            gpuImage.setFilter(it.filter)
            filteredBitmap.value = gpuImage.bitmapWithFilterApplied
        }
    }

    private fun clickEvents(){
        viewBinding.imgPreview.setOnLongClickListener{
            viewBinding.imgPreview.setImageBitmap(originalBitmap)
            return@setOnLongClickListener false
        }

        viewBinding.imgPreview.setOnClickListener{
            viewBinding.imgPreview.setImageBitmap(filteredBitmap.value)
        }

        viewBinding.btnSave.setOnClickListener {
            filteredBitmap.value?.let { filteredImage->
                viewModel.saveFilteredImage(filteredImage)
            }
        }

        viewBinding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}