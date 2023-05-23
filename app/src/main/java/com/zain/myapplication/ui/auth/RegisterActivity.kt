package com.zain.myapplication.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zain.myapplication.R
import com.zain.myapplication.databinding.ActivityRegisterBinding
import com.zain.myapplication.ui.MenuActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setInput()
    }

    private fun setInput() {
        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString()
            val phone = binding.etPhone.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            when {
                name.isEmpty() -> {
                    binding.etNameLayout.error = "Masukkan nama"
                }
                phone.isEmpty() -> {
                    binding.etPhoneLayout.error = "Masukkan nomor handphone"
                }
                email.isEmpty() -> {
                    binding.etEmailLayout.error = "Masukkan email"
                }
                password.isEmpty() -> {
                    binding.etPasswordLayout.error = "Masukkan password"
                }
                else -> {
                    val moveToMainMenuActivity =
                        Intent(this@RegisterActivity, MenuActivity::class.java)
                    startActivity(moveToMainMenuActivity)
                    finish()
                }
            }
        }
    }
}