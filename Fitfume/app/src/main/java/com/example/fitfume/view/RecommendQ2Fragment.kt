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
import com.example.fitfume.databinding.FragmentRecommendQ2Binding
import com.example.fitfume.viewmodel.GptViewModel

class RecommendQ2Fragment : Fragment() {
    lateinit var binding: FragmentRecommendQ2Binding
    private val viewModel: GptViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recommend_q2, container, false)
        binding.gptVm = viewModel
        var result: String


        Log.d("lhj", "Q2: ${viewModel.recommendText.value}")

        binding.recommendOneCl.setOnClickListener {
            result = "${viewModel.recommendText.value},캐주얼"
            viewModel.updateRecommendText(result)
            (activity as RecommendActivity).replaceFragment(RecommendQ3Fragment())
        }

        binding.recommendTwoCl.setOnClickListener {
            result = "${viewModel.recommendText.value},스트릿"
            viewModel.updateRecommendText(result)
            (activity as RecommendActivity).replaceFragment(RecommendQ3Fragment())
        }


        binding.recommendThreeCl.setOnClickListener {
            result = "${viewModel.recommendText.value},미니멀"
            viewModel.updateRecommendText(result)
            (activity as RecommendActivity).replaceFragment(RecommendQ3Fragment())
        }

        binding.recommendFourCl.setOnClickListener {
            result = "${viewModel.recommendText.value},정장"
            viewModel.updateRecommendText(result)
            (activity as RecommendActivity).replaceFragment(RecommendQ3Fragment())
        }


        return binding.root
    }

}