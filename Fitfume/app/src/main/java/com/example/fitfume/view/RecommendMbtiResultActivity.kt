package com.example.fitfume.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.fitfume.R
import com.example.fitfume.databinding.ActivityRecommendMbtiResultBinding
import com.example.fitfume.model.GptContent
import com.example.fitfume.network.data.request.GptRequest
import com.example.fitfume.viewmodel.GptViewModel

class RecommendMbtiResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendMbtiResultBinding
    private val viewModel: GptViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRecommendMbtiResultBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.mbtiResultVm = viewModel


        val mbti = intent.getStringExtra("mbti")
        sendChatGPT(mbti!!)

        viewModel.gptText.observe(this, Observer {
            val strArr = it.split(" - ")

            binding.recommendEndPerfumeTv.text = strArr[0]
            binding.recommendEndPerfumeDetailTv.text = strArr[1]
        })


        binding.recommendEndBtn.setOnClickListener {
            val intent = Intent(this@RecommendMbtiResultActivity, HomeActivity::class.java)
            startActivity(intent)
        }

    }

    fun sendChatGPT(text: String) {
        val model = "gpt-3.5-turbo"
        val request = GptRequest(
            model,
            listOf(
                GptContent(
                    "user",
                    "\"MBTI가 ${text}인 사람\" 향수 추천해줘. 근데 향수 이름은 한글로 말해줘. 그리고 기타 설명은 적지말고 \"브랜드 - 향수이름\" 으로 딱 하나만 알려줘."
                )
            )
        )

        viewModel.getChatAnswer(request)
    }
}