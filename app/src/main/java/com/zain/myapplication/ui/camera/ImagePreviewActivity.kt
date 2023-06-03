package com.zain.myapplication.ui.camera

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import com.zain.myapplication.R
import com.zain.myapplication.databinding.ActivityImagePreviewBinding
//import com.zain.myapplication.databinding.ActivityImagePreviewBinding
//import com.zain.myapplication.ui.auth.AuthViewModel
//import com.zain.myapplication.ui.auth.AuthViewModelFactory
import com.zain.myapplication.ui.detection.DetectionResultActivity
//import com.zain.myapplication.ui.profile.ProfileViewModel
import java.io.File

class ImagePreviewActivity : AppCompatActivity() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

    private var userId: String? = null

    private var isDetection = true
    private var isSkinDisease = true
    private var imageFile: File? = null

    private lateinit var binding: ActivityImagePreviewBinding
//    private val profileViewModel: ProfileViewModel by viewModels()

//    private val authViewModel: AuthViewModel by viewModels {
//        AuthViewModelFactory.getInstance(dataStore)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagePreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        profileViewModel.isLoading.observe(this) {
//            showLoading(it)
//        }
//
//        profileViewModel.isSuccess.observe(this) { isSuccess ->
//            showPopUp(isSuccess)
//        }
//
//        authViewModel.isLogin().observe(this) { userId ->
//            if (!userId.isNullOrEmpty()) {
//                this.userId = userId
//            }
//        }

        getCondition()

        setToolBar()
        setPreviewImage()
        setAction()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


    private fun showPopUp(success: Boolean) {
        if (success)
            AlertDialog.Builder(this).apply {
                setTitle("Berhasil!")
                setMessage("Update Profile Berhasil")
                setPositiveButton("Lanjut") { _, _ ->
                    finish()
                }
                create()
                show()
            }
    }

    private fun getCondition() {
        isDetection = intent.getBooleanExtra(IS_DETECTION, true)
        isSkinDisease = intent.getBooleanExtra(IS_SKIN_DISEASE, true)
    }

    private fun setAction() {
        binding.btnUpload.setOnClickListener {
            Log.i("okay", "okay $isDetection $isSkinDisease")
            if (isDetection) {
                val intent = Intent(this@ImagePreviewActivity, DetectionResultActivity::class.java)
                startActivity(intent)
                finish()
            } else {
//                uploadProfilePicture()

            }

        }
    }

//    private fun uploadProfilePicture() {
//        if (!userId.isNullOrEmpty())
//            profileViewModel.editProfilePicture(userId!!,imageFile = imageFile!!)
//    }

    private fun setPreviewImage() {
        val imagePath = intent.getStringExtra(IMAGE_PATH)
        val imageUri = Uri.parse(imagePath)
        imageFile = File(imageUri.path)
        binding.ivPreviewImage.setImageURI(imageUri)

    }

    private fun setToolBar() {
        binding.apply {
            setSupportActionBar(toolbar.toolbar)
            toolbar.tvToolbarTitle.text = getString(R.string.preview)
            toolbar.btnBackToolbar.setOnClickListener {
                finish()
            }
        }
    }

    companion object {
        const val IS_DETECTION = "is_detection"
        const val IS_SKIN_DISEASE = "is_skin_disease"
        const val IMAGE_PATH = "image_absolute_path"
    }
}