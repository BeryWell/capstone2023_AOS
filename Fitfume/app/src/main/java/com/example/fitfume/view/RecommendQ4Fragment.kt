package com.example.fitfume.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.*
import androidx.lifecycle.Observer
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

        Log.d("lhj", "Q4: ${viewModel.recommendTextArr.value}")

        binding.recommendOneCl.setOnClickListener {
            viewModel.updateRecommendStateText("10대")
        }

        binding.recommendTwoCl.setOnClickListener {
            viewModel.updateRecommendStateText("20대")
        }

        binding.recommendThreeCl.setOnClickListener {
            viewModel.updateRecommendStateText("30대")
        }

        binding.recommendFourCl.setOnClickListener {
            viewModel.updateRecommendStateText("40대")
        }

        viewModel.recommendStateText.observe(requireActivity(), Observer {
            when(it){
                "10대" -> {
                    updateSelected(arrayListOf(true, false, false, false))
                }
                "20대" -> {
                    updateSelected(arrayListOf(false, true, false, false))
                }
                "30대" -> {
                    updateSelected(arrayListOf(false, false, true, false))
                }
                "40대" -> {
                    updateSelected(arrayListOf(false, false, false, true))
                }else -> {
                updateSelected(arrayListOf(false, false, false, false))
                }
            }
        })

        binding.recommendNextBtn.setOnClickListener {
            viewModel.updateRecommendTextArr(viewModel.recommendStateText.value!!)
            (activity as RecommendActivity).replaceFragment(RecommendEndFragment())
        }

        binding.recommendPreviewBtn.setOnClickListener {
            viewModel.deleteRecommendTextArr()
            (activity as RecommendActivity).replaceFragment(RecommendQ3Fragment())
        }

        return binding.root
    }

    private fun updateSelected(booleanArr: List<Boolean>) {
        binding.recommendOneCl.isSelected = booleanArr[0]
        binding.recommendTwoCl.isSelected = booleanArr[1]
        binding.recommendThreeCl.isSelected = booleanArr[2]
        binding.recommendFourCl.isSelected = booleanArr[3]
    }

}