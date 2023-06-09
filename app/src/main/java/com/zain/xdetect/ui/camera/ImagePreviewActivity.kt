package com.zain.xdetect.ui.camera

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.zain.xdetect.R
import com.zain.xdetect.data.local.model.Result
import com.zain.xdetect.databinding.ActivityImagePreviewBinding
import com.zain.xdetect.ui.auth.AuthViewModel
import com.zain.xdetect.ui.auth.AuthViewModelFactory
import com.zain.xdetect.ui.detection.DetectionResultActivity
import com.zain.xdetect.ui.detection.DetectionViewModel
import com.zain.xdetect.ui.detection.DetectionViewModelFactory
import java.io.File

class ImagePreviewActivity : AppCompatActivity() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

    private var uid: String? = null

    private var isDetection = true
    private var imageFile: File? = null

    private lateinit var binding: ActivityImagePreviewBinding

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory.getInstance(dataStore)
    }

    private val detectionViewModel: DetectionViewModel by viewModels {
        DetectionViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagePreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel.isLogin().observe(this) { uid ->
            if (!uid.isNullOrEmpty()) {
                this.uid = uid
            }
        }

        getCondition()

        setToolBar()
        setPreviewImage()
        setAction()
    }

    private fun getCondition() {
        isDetection = intent.getBooleanExtra(IS_DETECTION, false)
    }

    private fun setAction() {
        binding.btnUpload.setOnClickListener {
            if (isDetection) {
                postDetection()
            } else {
                uploadProfilePicture()
            }
        }
    }

    private fun postDetection() {
        if (!uid.isNullOrEmpty())
            detectionViewModel.postDetectionDisease(uid!!, imageFile!!)
                .observe(this) { result ->
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            val dataResult = result.data
                            val intent = Intent(
                                this@ImagePreviewActivity,
                                DetectionResultActivity::class.java
                            )
                            intent.putExtra(
                                DetectionResultActivity.DETECTION_RESULT,
                                dataResult
                            )
                            startActivity(intent)
                            finish()
                        }
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this,
                                "Failed to detect disease",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                } else
            Toast.makeText(
                this,
                "Failed to obtain user authentication",
                Toast.LENGTH_SHORT
            ).show()
    }

    private fun uploadProfilePicture() {
        if (!uid.isNullOrEmpty())
            editProfilePicture(uid!!, imageFile!!)
        else
            Toast.makeText(
                this,
                "Failed to obtain user authentication",
                Toast.LENGTH_SHORT
            ).show()
    }

    private fun editProfilePicture(uid: String, imageFile: File) {
        authViewModel.editProfilePicture(uid, imageFile).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    Log.i("IMAGEPREVIEW", "OKAYYU BERHASIL ${result.data}")
                    binding.progressBar.visibility = View.GONE
                    AlertDialog.Builder(this).apply {
                        setTitle("Success!")
                        setMessage(result.data)
                        setPositiveButton("Lanjut") { _, _ ->
                            finish()
                        }
                        create()
                        show()
                    }
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "Failed to update profile picture",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setPreviewImage() {
        val imagePath = intent.getStringExtra(IMAGE_PATH)
        val imageUri = Uri.parse(imagePath)
        imageFile = imageUri.path?.let { File(it) }
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
        const val IMAGE_PATH = "image_absolute_path"
    }
}