package com.zain.xdetect.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zain.xdetect.data.remote.repository.UserRepository

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun isLogin() = userRepository.isLogin().asLiveData()

    fun login(name: String, password: String) = userRepository.login(name, password).asLiveData()

    fun register(email: String, password: String, name: String, phone: String) =
        userRepository.register(email, password, name, phone).asLiveData()

    fun logout() = userRepository.logout().asLiveData()
}