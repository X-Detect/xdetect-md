package com.zain.xdetect.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zain.xdetect.data.remote.repository.UserRepository
import java.io.File

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun isLogin() = userRepository.isLogin().asLiveData()

    fun login(name: String, password: String) = userRepository.login(name, password).asLiveData()

    fun register(email: String, password: String, name: String, phone: String) =
        userRepository.register(email, password, name, phone).asLiveData()

    fun logout() = userRepository.logout().asLiveData()

    fun getDataUser(token:String) = userRepository.getDataUser(token).asLiveData()

    fun editEmailPassword(
        token: String,
        currentEmail: String,
        newEmail: String,
        currentPassword: String,
        newPassword: String
    ) = userRepository.editEmailPassword(token,currentEmail,newEmail,currentPassword,newPassword).asLiveData()

    fun editName(
        token: String,
        newName: String,
        currentName: String,
    ) = userRepository.editName(token,newName,currentName).asLiveData()

    fun editProfilePicture(
        uid: String, imageFile: File
    ) = userRepository.editProfilePicture(uid,imageFile).asLiveData()

}