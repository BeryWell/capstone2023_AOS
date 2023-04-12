package com.example.fitfume.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.fitfume.BuildConfig
import com.example.fitfume.databinding.ActivityMainBinding
import com.example.fitfume.model.GptContent
import com.example.fitfume.network.ApiInterface
import com.example.fitfume.network.data.request.GptRequest
import com.example.fitfume.viewmodel.GptViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: GptViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.gptVm = viewModel

        binding.sendBtn.setOnClickListener {
            val model = "gpt-3.5-turbo"
            val request = GptRequest(model, listOf(GptContent("user", "\"캐주얼 스타일 옷을 입는 40대 남성 직장인\" 향수 추천해줘. 근데 향수 이름은 한글로 말해줘. 그리고 기타 설명은 적지말고 \"브랜드 - 향수이름\" 으로 딱 하나만 알려줘.")))

            viewModel.getChatAnswer(request)
        }

        viewModel.gptText.observe(this, Observer {
            binding.sendTx.text = it
        })
    }
}