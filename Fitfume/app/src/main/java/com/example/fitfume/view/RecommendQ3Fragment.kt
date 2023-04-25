package com.example.fitfume.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.*
import com.example.fitfume.R
import com.example.fitfume.databinding.FragmentRecommendQ3Binding
import com.example.fitfume.viewmodel.GptViewModel

class RecommendQ3Fragment : Fragment() {
    private lateinit var binding: FragmentRecommendQ3Binding
    private val viewModel: GptViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recommend_q3, container, false)
        binding.gptVm = viewModel

        var result = ""

//        setFragmentResultListener("q2Key"){requestKey, bundle ->
//            result = bundle.getString("bundleKey")!!
//            Log.d("lhj", "Q3: ${result}")
//        }

        Log.d("lhj", "Q3: ${viewModel.recommendText.value}")

        binding.recommendOneCl.setOnClickListener {
//            result = "${result},내향적"
//            setFragmentResult("q3Key", bundleOf("bundleKey" to result))
            result = "${viewModel.recommendText.value},내향적"
            viewModel.updateRecommendText(result)
            (activity as RecommendActivity).replaceFragment(RecommendQ4Fragment())
        }

        binding.recommendTwoCl.setOnClickListener {
//            result = "${result},외향적"
//            setFragmentResult("q3Key", bundleOf("bundleKey" to result))
            result = "${viewModel.recommendText.value},외향적"
            viewModel.updateRecommendText(result)
            (activity as RecommendActivity).replaceFragment(RecommendQ4Fragment())
        }

        return binding.root
    }

}