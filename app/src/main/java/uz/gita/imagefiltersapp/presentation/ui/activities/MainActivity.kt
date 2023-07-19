package uz.gita.imagefiltersapp.presentation.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import uz.gita.imagefiltersapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object{
        private const val REQUEST_CODE_PICK_IMAGE = 1
        const val KEY_IMAGE_URI = "imageUri"
    }
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        clickEvents()
    }

    private fun clickEvents(){
        viewBinding.btnEditNewImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
        }

        viewBinding.btnSavedImages.setOnClickListener {
            val intent = Intent(applicationContext,SavedImagesActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK){
            data?.data?.let { imgUri ->
                val intent = Intent(this@MainActivity, EditImageActivity::class.java)
                intent.putExtra(KEY_IMAGE_URI,imgUri)
                startActivity(intent)
            }
        }
    }
}