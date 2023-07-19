package uz.gita.imagefiltersapp.presentation.ui.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.gita.imagefiltersapp.R
import uz.gita.imagefiltersapp.databinding.ActivityFilteredImageBinding

class FilteredImageActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityFilteredImageBinding
    private lateinit var fileUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFilteredImageBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        displayFilteredImage()
        clickEvents()
    }

    private fun displayFilteredImage(){
        intent.getParcelableExtra<Uri>(EditImageActivity.KEY_FILTERED_IMAGE_URI)?.let {imageUri->
            fileUri = imageUri
            viewBinding.imgFilteredImage.setImageURI(imageUri)
        }
    }

    private fun clickEvents(){
        viewBinding.btnShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_STREAM,fileUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.type = "image/*"
            startActivity(intent)
        }

        viewBinding.btnDone.setOnClickListener{
            finish()
        }
    }
}