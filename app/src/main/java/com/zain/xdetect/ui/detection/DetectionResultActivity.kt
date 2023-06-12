package com.zain.xdetect.ui.detection

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.zain.xdetect.R
import com.zain.xdetect.data.remote.model.detection.DetectionResponse
import com.zain.xdetect.databinding.ActivityDetectionResultBinding
import com.zain.xdetect.ui.detection.adapter.ListPointsAdapter

class DetectionResultActivity : AppCompatActivity(), View.OnClickListener {

    private var isHistory = false
    private lateinit var binding: ActivityDetectionResultBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataResult()
        setToolbar()
    }

    private fun getDataResult() {
        isHistory = intent.getBooleanExtra(IS_HISTORY, false)
        val dataResultDetection =
            intent.getParcelableExtra<DetectionResponse>(DETECTION_RESULT) as DetectionResponse
        setDataResultView(dataResultDetection)

    }

    private fun setToolbar() {
        binding.toolbar.btnBackToolbar.setOnClickListener(this)
        binding.toolbar.tvToolbarTitle.text = if (!isHistory) "Detection Result" else "History"
    }

    private fun setDataResultView(dataResult: DetectionResponse) {
//        Log.i("OKAYY", dataResult.toString())
        val listSymptoms = dataResult.additionalInfo.symptoms
        val symptomsText = listSymptoms.joinToString(", ")

        val adapter = ListPointsAdapter(dataResult.additionalInfo.nextSteps)


        binding.apply {
            tvDescResult.text = dataResult.additionalInfo.description
            tvDescSymptoms.text = symptomsText

            rvStep.adapter = adapter
            val layoutManager = LinearLayoutManager(this@DetectionResultActivity)
            binding.rvStep.layoutManager = layoutManager

            tvDescAction.text = dataResult.recommendation.action
            tvDescMessage.text = dataResult.recommendation.message
            tvDescAccuracy.text = dataResult.maxLabel.percentage
            tvDetection.text = dataResult.maxLabel.label
            tvDate.text = dataResult.created
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_back_toolbar -> {
                finish()
            }
        }
    }

    companion object {
        const val DETECTION_RESULT = "detection_result"
        const val IS_HISTORY = "is_history"
    }
}