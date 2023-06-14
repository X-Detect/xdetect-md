package com.zain.xdetect.ui.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.zain.xdetect.data.local.model.Result
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.zain.xdetect.R
import com.zain.xdetect.databinding.ActivityResetPasswordBinding

class ResetPasswordActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory.getInstance(dataStore)
    }

    private lateinit var binding: ActivityResetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupAction()
//        playAnimation()
    }

    private fun setupAction() {
        binding.btnReset.setOnClickListener {
            val email = binding.etEmail.text.toString()
            when {
                email.isEmpty() -> {
                    binding.etEmailLayout.error = "Masukkan email"
                }
                Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    binding.etEmail.onEditorAction(EditorInfo.IME_ACTION_DONE)
                    authViewModel.reset(email).observe(this) { result ->
                        when (result) {
                            is Result.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is Result.Success -> {
                                binding.progressBar.visibility = View.GONE
                                AlertDialog.Builder(this).apply {
                                    setMessage(getString(R.string.msg_success_reset))
                                    setPositiveButton("Lanjut") { _, _ ->
                                        val intent = Intent(context, LoginActivity::class.java)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                        finish()
                                    }
                                    create()
                                    show()
                                }
                            }
                            is Result.Error -> {
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(
                                    this@ResetPasswordActivity,
                                    "Email tidak ditemukan ${result.error}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

                else -> {
                    Toast.makeText(
                        this@ResetPasswordActivity,
                        "Email invalid",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

//    private fun playAnimation() {
//        val tvEmail = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(500)
//        val etEmail = ObjectAnimator.ofFloat(binding.etEmailLayout, View.ALPHA, 1f).setDuration(500)
//        val btnReset = ObjectAnimator.ofFloat(binding.btnReset, View.ALPHA, 1f).setDuration(500)
//        AnimatorSet().apply {
//            playTogether(
//                tvEmail,
//                etEmail,
//                btnReset
//            )
//            startDelay = 500
//        }.start()
//    }
}