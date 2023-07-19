package uz.gita.imagefiltersapp.presentation.ui.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.gita.imagefiltersapp.data.state.SavedImagesDataState
import uz.gita.imagefiltersapp.databinding.ActivitySavedImagesBinding
import uz.gita.imagefiltersapp.presentation.ui.adapters.SavedImagesAdapter
import uz.gita.imagefiltersapp.presentation.viewmodel.SavedImagesViewModel
import uz.gita.imagefiltersapp.presentation.viewmodel.impl.SavedImagesViewModelImpl
import uz.gita.imagefiltersapp.utils.displayToast
import uz.gita.imagefiltersapp.utils.show


class SavedImagesActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySavedImagesBinding
    private val viewModel: SavedImagesViewModelImpl by viewModel()
    private lateinit var adapter: SavedImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySavedImagesBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setupObservers()
        clickEvents()
        viewModel.loadSavedImages()
    }


    private fun setupObservers(){
        viewModel.savedImagesStateLiveData.observe(this,savedImagesStateObserver)
    }

    private val savedImagesStateObserver = Observer<SavedImagesDataState>{
        val dataState = it ?: return@Observer
        viewBinding.savedImagesProgressBar.visibility =
            if(dataState.isLoading) View.VISIBLE else View.GONE

        dataState.savedImages?.let { savedImages->
            setupRecyclerView()
            adapter.submitList(savedImages)
        } ?: kotlin.run {
            dataState.error?.let { error ->
                displayToast(error)
            }
        }
    }

    private fun setupRecyclerView(){
        adapter = SavedImagesAdapter()
        viewBinding.savedImagesRecyclerView.show()
        viewBinding.savedImagesRecyclerView.adapter = adapter

        adapter.setOnSavedImageClickedListener { file ->
            val fileUri: Uri = FileProvider.getUriForFile(applicationContext, "${packageName}.provider",file)
            val intent = Intent(applicationContext, FilteredImageActivity::class.java)
            intent.putExtra(EditImageActivity.KEY_FILTERED_IMAGE_URI,fileUri)
            startActivity(intent)
        }
    }


    private fun clickEvents() {
        viewBinding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}