package com.zain.myapplication.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zain.myapplication.databinding.ActivityLoginBinding
import com.zain.myapplication.ui.MenuActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            tvBtnRegisterNow.setOnClickListener {
                val moveToRegisterActivity =
                    Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(moveToRegisterActivity)
            }
        }
        setInput()
    }

    private fun setInput() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            when {
                email.isEmpty() -> {
                    binding.etEmailLayout.error = "Masukkan email"
                }
                password.isEmpty() -> {
                    binding.etPasswordLayout.error = "Masukkan password"
                }
                else -> {
                    val moveToMainMenuActivity =
                        Intent(this@LoginActivity, MenuActivity::class.java)
                    startActivity(moveToMainMenuActivity)
                    finish()
                }
            }
        }
    }
}