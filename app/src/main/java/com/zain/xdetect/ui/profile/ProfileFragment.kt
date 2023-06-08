package com.zain.xdetect.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.textfield.TextInputLayout
import com.zain.xdetect.R
import com.zain.xdetect.databinding.FragmentProfileBinding
import com.zain.xdetect.ui.auth.AuthViewModel
import com.zain.xdetect.ui.auth.AuthViewModelFactory
import com.zain.xdetect.data.local.model.Result
import com.zain.xdetect.data.remote.model.auth.DataUser
import com.zain.xdetect.ui.auth.RegisterActivity

class ProfileFragment : Fragment() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory.getInstance(requireContext().dataStore)
    }

    private lateinit var uid: String
    private lateinit var dataUser: DataUser

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var isEditing = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etPasswordLayout.endIconMode = TextInputLayout.END_ICON_NONE

        authViewModel.isLogin().observe(requireActivity()) { uid ->
            if (uid.isNullOrEmpty()) {
                Log.i("PROFILE", "SIUUUU GA GET DATA")
                navigateToSignup()
            } else {
                this.uid = uid.toString()
                Log.i("PROFILE", "SIUUUU GETDATA $uid")
                Log.i("PROFILE", "SIUUUU $isAdded")
                if (isAdded)
                    getDataUser(uid)
            }
        }

        setupAction()
    }

    private fun getDataUser(uid: String) {
        authViewModel.getDataUser(uid).observe(requireActivity()) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    dataUser = result.data
                    setDataUserView(dataUser)
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireActivity(),
                        "Failed to load user data",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }

    }

    private fun setDataUserView(dataUser: DataUser) {
        Log.i("SETDATAUSER", "aman ${dataUser.imgUrl}")
        val defaultImg = "https://i.pinimg.com/564x/8e/e3/51/8ee351b4914b264f69e748a9df1f2541.jpg"
        val shownImgUrl = dataUser.imgUrl ?: defaultImg
        binding.apply {
            etName.setText(dataUser.name)
            etEmail.setText(dataUser.email)
            Glide.with(requireActivity())
                .load(shownImgUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.imgPhoto)
        }
    }

    private fun navigateToSignup() {
        Toast.makeText(
            requireContext(),
            getString(R.string.logout),
            Toast.LENGTH_SHORT
        ).show()
        startActivity(Intent(requireActivity(), RegisterActivity::class.java))
        activity?.finish()
    }


    private fun setupAction() {
        binding.apply {
            btnLogout.setOnClickListener {
                logout()
            }

        }
    }

    private fun logout() {
        authViewModel.logout().observe(requireActivity()) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireActivity(),
                        "Gagal logout",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    companion object {

    }
}