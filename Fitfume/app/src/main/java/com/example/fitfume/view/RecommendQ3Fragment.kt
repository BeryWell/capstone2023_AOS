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

        Log.d("lhj", "Q3: ${viewModel.recommendTextArr.value}")

        binding.recommendOneCl.setOnClickListener {
            viewModel.updateRecommendStateText("내향적")
        }

        binding.recommendTwoCl.setOnClickListener {
            viewModel.updateRecommendStateText("외향적")
        }

        viewModel.recommendStateText.observe(requireActivity(), Observer {
            when(it){
                "내향적" -> {
                    updateSelected(arrayListOf(true, false))
                }
                "외향적" -> {
                    updateSelected(arrayListOf(false, true))
                }else -> {
                updateSelected(arrayListOf(false, false))
                }

            }
        })

        binding.recommendNextBtn.setOnClickListener {
            viewModel.updateRecommendTextArr(viewModel.recommendStateText.value!!)
            (activity as RecommendActivity).replaceFragment(RecommendQ4Fragment())
        }

        binding.recommendPreviewBtn.setOnClickListener {
            viewModel.deleteRecommendTextArr()
            (activity as RecommendActivity).replaceFragment(RecommendQ2Fragment())
        }

        return binding.root
    }

    private fun updateSelected(booleanArr: List<Boolean>) {
        binding.recommendOneCl.isSelected = booleanArr[0]
        binding.recommendTwoCl.isSelected = booleanArr[1]
    }

    override fun onPause() {
        super.onPause()

        viewModel.recommendStateText.removeObservers(this)
    }

}