package com.zain.xdetect

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.zain.xdetect.databinding.ActivityMainBinding
import com.zain.xdetect.ui.MenuActivity
import com.zain.xdetect.ui.auth.AuthViewModel
import com.zain.xdetect.ui.auth.AuthViewModelFactory
import com.zain.xdetect.ui.onboard.OnboardingActivity
import java.util.*
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory.getInstance(dataStore)
    }

    private var isLogin = true
//    private var isLogin = false
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        authViewModel.isLogin().observe(this) {
            if (!it.isNullOrEmpty()) {
                isLogin = true
            }
        }

        Timer().schedule(timerTask {
            if (isLogin) {
                val moveToMenuActivity = Intent(this@MainActivity, MenuActivity::class.java)
                startActivity(moveToMenuActivity)
                finish()
            } else {
                val moveToOnboardingActivity =
                    Intent(this@MainActivity, OnboardingActivity::class.java)
                startActivity(moveToOnboardingActivity)
                finish()
            }
        }, timerSplashScreen)
    }

    companion object {
        const val timerSplashScreen = 3000L
    }
}