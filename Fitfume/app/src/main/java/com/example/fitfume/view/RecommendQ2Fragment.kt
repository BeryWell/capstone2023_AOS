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
import com.example.fitfume.databinding.FragmentRecommendQ2Binding
import com.example.fitfume.viewmodel.GptViewModel

class RecommendQ2Fragment : Fragment() {
    lateinit var binding: FragmentRecommendQ2Binding
    private val viewModel: GptViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_recommend_q2, container, false)
        binding.gptVm = viewModel


        Log.d("lhj", "Q2: ${viewModel.recommendTextArr.value}")

        binding.recommendOneCl.setOnClickListener {
//            viewModel.updateRecommendTextArr(result)
//            (activity as RecommendActivity).replaceFragment(RecommendQ3Fragment())
            viewModel.updateRecommendStateText("캐주얼")
        }

        binding.recommendTwoCl.setOnClickListener {
//            result = "${viewModel.recommendTextArr.value},스트릿"
//            viewModel.updateRecommendTextArr(result)
//            (activity as RecommendActivity).replaceFragment(RecommendQ3Fragment())
            viewModel.updateRecommendStateText("스트릿")
        }


        binding.recommendThreeCl.setOnClickListener {
//            result = "${viewModel.recommendTextArr.value},미니멀"
//            viewModel.updateRecommendTextArr(result)
//            (activity as RecommendActivity).replaceFragment(RecommendQ3Fragment())
            viewModel.updateRecommendStateText("미니멀")
        }

        binding.recommendFourCl.setOnClickListener {
//            result = "${viewModel.recommendTextArr.value},정장"
//            viewModel.updateRecommendTextArr(result)
//            (activity as RecommendActivity).replaceFragment(RecommendQ3Fragment())
            viewModel.updateRecommendStateText("정장")
        }

        viewModel.recommendStateText.observe(requireActivity(), Observer {
            when (it) {
                "캐주얼" -> {
                    updateSelected(arrayListOf(true, false, false, false))
                }
                "스트릿" -> {
                    updateSelected(arrayListOf(false, true, false, false))
                }
                "미니멀" -> {
                    updateSelected(arrayListOf(false, false, true, false))
                }
                "정장" -> {
                    updateSelected(arrayListOf(false, false, false, true))
                }
                else -> {
                    updateSelected(arrayListOf(false, false, false, false))
                }
            }
        })

        binding.recommendNextBtn.setOnClickListener {
            viewModel.updateRecommendTextArr(viewModel.recommendStateText.value!!)
            (activity as RecommendActivity).replaceFragment(RecommendQ3Fragment())
        }


        binding.recommendPreviewBtn.setOnClickListener {
            viewModel.deleteRecommendTextArr()
            (activity as RecommendActivity).replaceFragment(RecommendQ1Fragment())
        }

        return binding.root
    }

    private fun updateSelected(booleanArr: List<Boolean>) {
        binding.recommendOneCl.isSelected = booleanArr[0]
        binding.recommendTwoCl.isSelected = booleanArr[1]
        binding.recommendThreeCl.isSelected = booleanArr[2]
        binding.recommendFourCl.isSelected = booleanArr[3]
    }

    override fun onPause() {
        super.onPause()
        viewModel.recommendStateText.removeObservers(this)
    }

}