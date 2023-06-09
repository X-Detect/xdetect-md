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
import androidx.core.app.ActivityOptionsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.zain.xdetect.databinding.ActivityRegisterBinding
import com.zain.xdetect.data.local.model.Result
import com.zain.xdetect.ui.MenuActivity

class RegisterActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory.getInstance(dataStore)
    }

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupAction()
//        playAnimation()
    }

    private fun setupAction() {

        binding.tvBtnLoginNow.setOnClickListener {
            val moveToLoginActivity = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(
                moveToLoginActivity,
                ActivityOptionsCompat.makeSceneTransitionAnimation(this@RegisterActivity).toBundle()
            )
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val phone = binding.etPhone.text.toString()
            when {
                name.isEmpty() -> {
                    binding.etUsernameLayout.error = "Masukkan nama"
                }
                email.isEmpty() -> {
                    binding.etEmailLayout.error = "Masukkan email"
                }
                password.isEmpty() -> {
                    binding.etPasswordLayout.error = "Masukkan password"
                }
                phone.isEmpty() -> {
                    binding.etPhoneLayout.error = "Masukkan nomor HP"
                }
                Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length >= 8 -> {
                    binding.apply {
                        etUsername.onEditorAction(EditorInfo.IME_ACTION_DONE)
                        etEmail.onEditorAction(EditorInfo.IME_ACTION_DONE)
                        etPassword.onEditorAction(EditorInfo.IME_ACTION_DONE)
                    }
                    authViewModel.register(email, password, name, phone).observe(this) { result ->
                        when (result) {
                            is Result.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is Result.Success -> {
                                binding.progressBar.visibility = View.GONE
                                AlertDialog.Builder(this).apply {
                                    setTitle("Yey!")
                                    setMessage("Berhasil Register, silakan Login")
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
                                    this@RegisterActivity, "Email sudah terdaftar", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

                else -> {
                    val moveToMainMenuActivity =
                        Intent(this@RegisterActivity, MenuActivity::class.java)
                    startActivity(
                        moveToMainMenuActivity,
                        ActivityOptionsCompat.makeSceneTransitionAnimation(this@RegisterActivity)
                            .toBundle()
                    )
                    finish()
                }
            }
        }
    }

//    private fun playAnimation() {
//        val logo =
//            ObjectAnimator.ofFloat(binding.imgLogoHorizontal, View.ALPHA, 1f).setDuration(500)
//        val title = ObjectAnimator.ofFloat(binding.tvTitle, View.ALPHA, 1f).setDuration(500)
//        val name = ObjectAnimator.ofFloat(binding.tvName, View.ALPHA, 1f).setDuration(500)
//        val etName = ObjectAnimator.ofFloat(binding.etNameLayout, View.ALPHA, 1f).setDuration(500)
//        val email = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(500)
//        val etEmail = ObjectAnimator.ofFloat(binding.etEmailLayout, View.ALPHA, 1f).setDuration(500)
//        val password = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(500)
//        val etPassword =
//            ObjectAnimator.ofFloat(binding.etPasswordLayout, View.ALPHA, 1f).setDuration(500)
//        val loginNow = ObjectAnimator.ofFloat(binding.btnTvLogin, View.ALPHA, 1f).setDuration(500)
//        val btnSignup = ObjectAnimator.ofFloat(binding.btnSignup, View.ALPHA, 1f).setDuration(500)
//
//        AnimatorSet().apply {
//            playTogether(
//                logo,
//                title,
//                name,
//                etName,
//                email,
//                etEmail,
//                password,
//                etPassword,
//                loginNow,
//                btnSignup
//            )
//            startDelay = 500
//        }.start()
//    }
}