package com.zain.xdetect.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.material.snackbar.Snackbar
import com.zain.xdetect.data.local.model.Result
import com.zain.xdetect.databinding.ActivityLoginBinding
import com.zain.xdetect.ui.MenuActivity

class LoginActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory.getInstance(dataStore)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupAction()
//        playAnimation()
    }

    private fun setupAction() {
        binding.tvBtnRegisterNow.setOnClickListener {
            val moveToSignUpActivity = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(
                moveToSignUpActivity,
                ActivityOptionsCompat.makeSceneTransitionAnimation(this@LoginActivity).toBundle()
            )
        }
        binding.btnLogin.setOnClickListener {
            val name = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            when {
                name.isEmpty() -> {
                    binding.etUsernameLayout.error = "Masukkan username"
                }
                password.isEmpty() -> {
                    binding.etPasswordLayout.error = "Masukkan password"
                }
                password.length >= 8 -> {
                    binding.apply {
                        etUsername.onEditorAction(EditorInfo.IME_ACTION_DONE)
                        etPassword.onEditorAction(EditorInfo.IME_ACTION_DONE)
                    }
                    authViewModel.login(name, password).observe(this) { result ->
                        when (result) {
                            is Result.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is Result.Success -> {
                                binding.progressBar.visibility = View.GONE
                                val moveToMenuActivity =
                                    Intent(this@LoginActivity, MenuActivity::class.java)
                                startActivity(moveToMenuActivity)
                            }
                            is Result.Error -> {
                                binding.progressBar.visibility = View.GONE
                                Snackbar.make(binding.root, result.error, Snackbar.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }

                else -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Invalid username or password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

//    private fun playAnimation() {
//        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
//            duration = 6000
//            repeatCount = ObjectAnimator.INFINITE
//            repeatMode = ObjectAnimator.REVERSE
//        }.start()

//        val logo =
//            ObjectAnimator.ofFloat(binding.imgLogoHorizontal, View.ALPHA, 1f).setDuration(500)
//        val title = ObjectAnimator.ofFloat(binding.tvTitle, View.ALPHA, 1f).setDuration(500)
//        val email = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(500)
//        val etEmail = ObjectAnimator.ofFloat(binding.etEmailLayout, View.ALPHA, 1f).setDuration(500)
//        val password = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(500)
//        val etPassword =
//            ObjectAnimator.ofFloat(binding.etPasswordLayout, View.ALPHA, 1f).setDuration(500)
//        val signupNow = ObjectAnimator.ofFloat(binding.btnTvSignup, View.ALPHA, 1f).setDuration(500)
//        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)

//        val tgtname = AnimatorSet().apply {
//            playTogether(nameTextView, nameEditTextLayout)
//        }
//        val tgtemail = AnimatorSet().apply {
//            playTogether(emailTextView, emailEditTextLayout)
//        }
//        val tgtpass = AnimatorSet().apply {
//            playTogether(passwordTextView, passwordEditTextLayout)
//        }

//        AnimatorSet().apply {
//            playTogether(
//                logo,
//                title,
//                email,
//                etEmail,
//                password,
//                etPassword,
//                signupNow,
//                btnLogin
//            )
//            startDelay = 500
//        }.start()
//    }
}