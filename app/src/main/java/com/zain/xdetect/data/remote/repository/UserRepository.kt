package com.zain.xdetect.data.remote.repository

import android.util.Log
import com.zain.xdetect.data.local.model.Result
import com.zain.xdetect.data.local.preference.UserPreference
import com.zain.xdetect.data.remote.api.ApiService
import com.zain.xdetect.data.remote.model.auth.DataUser
import com.zain.xdetect.data.remote.utils.reduceFileImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class UserRepository private constructor(
    private val userApiService: ApiService,
    private val userPreference: UserPreference
) {
    fun isLogin(): Flow<String?> = flow { emitAll(userPreference.getToken()) }

    fun login(email: String, password: String): Flow<Result<String>> = flow {
        emit(Result.Loading)
        try {
            Log.d("LOGIN", "login: $email $password")
            val response = userApiService.login(email, password)
            Log.d("LOGIN 2", "login 2: $response")
            val token = response.data?.uid
            Log.d("LOGIN 3", "login 3: $token")
            userPreference.saveToken(token!!)
            emit(Result.Success(response.msg))

        } catch (e: Exception) {
            Log.d("UserRepository", "loginnn: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun register(
        email: String,
        password: String,
        name: String,
        phone: String
    ): Flow<Result<String>> = flow {
        emit(Result.Loading)

        try {
            Log.d("register", "Register: $email pass $password name $name phone $phone")
            val response = userApiService.register(email, password, name, phone)
            emit(Result.Success(response.msg))
        } catch (e: Exception) {
            Log.d("UserRepository", "register: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }


    fun reset(email: String): Flow<Result<String>> = flow {
        emit(Result.Loading)
        try {
            val response = userApiService.reset(email)
            emit(Result.Success(response.msg))
        } catch (e: Exception) {
            Log.d("UserRepository", "reset: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun logout(): Flow<Result<String>> = flow {
        emit(Result.Loading)
        userPreference.logout()
        emit(Result.Success("success"))
    }

    fun getDataUser(
        token: String
    ): Flow<Result<DataUser>> = flow {
        emit(Result.Loading)
        try {
            val response = userApiService.getDataUser(token)
            emit(Result.Success(response.data!!))
        } catch (e: Exception) {
            Log.d("UserRepository", "getDataUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }

    }

    fun editEmailPassword(
        token: String,
        currentEmail: String,
        newEmail: String,
        currentPassword: String,
        newPassword: String
    ): Flow<Result<String>> = flow {
        emit(Result.Loading)
        try {
            val response = userApiService.editEmailPassword(
                uid = token,
                email = newEmail,
                password = newPassword,
                currentEmail = currentEmail,
                currentPassword = currentPassword
            )
            emit(Result.Success(response.message!!))

        } catch (e: Exception) {
            Log.d("UserRepository", "editEmailPassword: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun editName(
        uid: String,
        newName: String,
        currentName: String,
    ): Flow<Result<String>> = flow {
        emit(Result.Loading)
        try {
            val response = userApiService.editName(
                uid = uid,
                name = newName,
                currentName = currentName
            )
            emit(Result.Success(response.message!!))

        } catch (e: Exception) {
            Log.d("UserRepository", "editEmailPassword: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun editProfilePicture(
        uid: String, imageFile: File
    ): Flow<Result<String>> = flow {
        emit(Result.Loading)
        try {
            val reducedFile = reduceFileImage(imageFile)
            val requestImageFile = reducedFile.asRequestBody("image/jpeg".toMediaType())
            val imageMultipart: MultipartBody.Part =
                MultipartBody.Part.createFormData("image", imageFile.name, requestImageFile)

            val uidPart = uid.toRequestBody("text/plain".toMediaTypeOrNull())

            val response = userApiService.editProfilePicture(uid, imageMultipart, uidPart)
            emit(Result.Success(response.message!!))

        } catch (e: Exception) {
            Log.d("UserRepository", "editProfilePicture: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService, userPreference: UserPreference
        ): UserRepository = instance ?: synchronized(this) {
            instance ?: UserRepository(apiService, userPreference)
        }.also { instance = it }
    }
}