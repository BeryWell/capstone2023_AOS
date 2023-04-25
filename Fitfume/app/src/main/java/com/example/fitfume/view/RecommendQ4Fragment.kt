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
import com.example.fitfume.databinding.FragmentRecommendQ4Binding
import com.example.fitfume.viewmodel.GptViewModel

class RecommendQ4Fragment : Fragment() {
    private lateinit var binding: FragmentRecommendQ4Binding
    private val viewModel: GptViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recommend_q4, container, false)
        binding.gptVm = viewModel

        var result = ""

//        setFragmentResultListener("q3Key"){requestKey, bundle ->
//            result = bundle.getString("bundleKey")!!
//            Log.d("lhj", "Q4: ${result}")
//        }

        Log.d("lhj", "Q4: ${viewModel.recommendText.value}")

        binding.recommendOneCl.setOnClickListener {
//            result = "${result},10대"
//            setFragmentResult("q4Key", bundleOf("bundleKey" to result))
            result = "${viewModel.recommendText.value},10대"
            viewModel.updateRecommendText(result)
            (activity as RecommendActivity).replaceFragment(RecommendEndFragment())
        }

        binding.recommendTwoCl.setOnClickListener {
//            result = "${result},20대"
//            setFragmentResult("q4Key", bundleOf("bundleKey" to result))
            result = "${viewModel.recommendText.value},20대"
            viewModel.updateRecommendText(result)
            (activity as RecommendActivity).replaceFragment(RecommendEndFragment())
        }

        binding.recommendThreeCl.setOnClickListener {
//            result = "${result},30대"
//            setFragmentResult("q4Key", bundleOf("bundleKey" to result))
            result = "${viewModel.recommendText.value},30대"
            viewModel.updateRecommendText(result)
            (activity as RecommendActivity).replaceFragment(RecommendEndFragment())
        }

        binding.recommendFourCl.setOnClickListener {
//            result = "${result},40대"
//            setFragmentResult("q4Key", bundleOf("bundleKey" to result))
            result = "${viewModel.recommendText.value},40대"
            viewModel.updateRecommendText(result)
            (activity as RecommendActivity).replaceFragment(RecommendEndFragment())

        }

        return binding.root
    }

}