package com.example.fitfume.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
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
    private val viewModel: GptViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recommend_end, container, false)
        binding.gptVm = viewModel

        Log.d("lhj", "End: ${viewModel.recommendText.value}")

        binding.recommendEndBtn.setOnClickListener {
            viewModel.updateRecommendText("")
            (activity as RecommendActivity).replaceFragment(RecommendQ1Fragment())
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        sendChatGPT(viewModel.recommendText.value!!)

        viewModel.gptText.observe(requireActivity(), Observer {
            val strArr = it.split(" - ")
            binding.recommendEndPerfumeTv.text = strArr[0]
            binding.recommendEndPerfumeDetailTv.text = strArr[1]
            Log.d("lhj", "receive GPT: ${it}")
        })

    }

    override fun onPause() {
        super.onPause()
        viewModel.gptText.removeObservers(this)
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