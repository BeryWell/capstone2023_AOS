package com.example.fitfume.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.fitfume.R
import com.example.fitfume.databinding.FragmentRecommendEndBinding
import com.example.fitfume.model.GptContent
import com.example.fitfume.network.data.request.GptRequest
import com.example.fitfume.viewmodel.GptViewModel

class RecommendEndFragment : Fragment() {
    private lateinit var binding: FragmentRecommendEndBinding
    private val viewModel: GptViewModel by viewModels()
    var result = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecommendEndBinding.inflate(inflater, container, false)
        binding.gptVm = viewModel

        setFragmentResultListener("q4Key") { requestKey, bundle ->
            result = bundle.getString("bundleKey")!!
            Log.d("lhj", "onCreateView: ${result}")
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        sendChatGPT(result)


        viewModel.gptText.observe(requireActivity(), Observer {
            binding.recommendEndPerfumeTv.text = it
            Log.d("lhj", "receive GPT: ${it}")
        })

    }

    fun sendChatGPT(str: String) {
        val strArr = str.split(",").map { it.toString() }

        val model = "gpt-3.5-turbo"
        val request = GptRequest(
            model,
            listOf(
                GptContent(
                    "user",
                    "\"${strArr[1]} 스타일 옷을 입는 ${strArr[3]} ${strArr[0]} ${strArr[2]}인 사람\" 향수 추천해줘. 근데 향수 이름은 한글로 말해줘. 그리고 기타 설명은 적지말고 \"브랜드 - 향수이름\" 으로 딱 하나만 알려줘."
                )
            )
        )

        viewModel.getChatAnswer(request)

    }
}