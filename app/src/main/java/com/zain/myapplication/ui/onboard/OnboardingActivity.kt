package com.zain.myapplication.ui.onboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import com.zain.myapplication.R
import com.zain.myapplication.data.local.model.Onboard
import com.zain.myapplication.databinding.ActivityOnboardingBinding
import com.zain.myapplication.ui.auth.LoginActivity
import com.zain.myapplication.ui.onboard.adapter.OnboardListAdapter
import com.zain.myapplication.ui.onboard.adapter.OnboardingPageChangeCallback

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var onboardListAdapter: OnboardListAdapter

    private lateinit var onboardingPageChangeCallback: OnboardingPageChangeCallback

    private var currentPosition = 0
    private val onboardList: List<Onboard> = listOf(
        Onboard(
            img = R.drawable.img_onboard,
            description = "Selamat datang di Aplikasi Penerjemah Hasil X-ray. Mari kita mulai interpretasi!"
        ),
        Onboard(
            img = R.drawable.img_onboard_2,
            description = "Kami akan membantumu memahami  lebih jelas hasil scanmu"
        ),
        Onboard(
            img = R.drawable.img_onboard_3,
            description = "Terjemahkan hasil X-ray dengan cepat dan akurat dengan X-DETECT"
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        onboardingPageChangeCallback = OnboardingPageChangeCallback(
            viewPager = binding.viewpagerOnboard,
            btnLeft = binding.btnLeftOnboard,
            btnRight = binding.btnRightOnboard,
            btnOnboard= binding.btnOnboard,
            pageSize = onboardList.size,
        )
        setOnboardList()
        setButtonVisibility()

        binding.apply {
            btnOnboard.setOnClickListener {
                val moveToSignInActivity =
                    Intent(this@OnboardingActivity, LoginActivity::class.java)
                startActivity(moveToSignInActivity, ActivityOptionsCompat.makeSceneTransitionAnimation(this@OnboardingActivity).toBundle())
            }
            btnRightOnboard.setOnClickListener {
                if (currentPosition < onboardList.size - 1)
                    currentPosition += 1
                setButtonVisibility()
                onboardingPageChangeCallback.setCurrentItem(currentPosition)
            }
            btnLeftOnboard.setOnClickListener {
                if (currentPosition > 0)
                    currentPosition -= 1
                setButtonVisibility()
                onboardingPageChangeCallback.setCurrentItem(currentPosition)

            }
        }
    }

    private fun setButtonVisibility() {
        binding.apply {
            if (currentPosition == onboardList.size - 1)
                btnRightOnboard.visibility = View.INVISIBLE
            else if (currentPosition == 0)
                btnLeftOnboard.visibility = View.INVISIBLE
            else {
                btnLeftOnboard.visibility = View.VISIBLE
                btnRightOnboard.visibility = View.VISIBLE
            }

            if(currentPosition == onboardList.size - 1)
                btnOnboard.visibility = View.VISIBLE
            else
                btnOnboard.visibility = View.INVISIBLE
        }

    }

    private fun setOnboardList() {
        onboardListAdapter = OnboardListAdapter(onboardList)
        binding.viewpagerOnboard.adapter = onboardListAdapter
        binding.viewpagerOnboard.registerOnPageChangeCallback(onboardingPageChangeCallback)
        currentPosition = onboardingPageChangeCallback.getSelectedPosition()
    }
}