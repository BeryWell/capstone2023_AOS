package com.example.fitfume.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.example.fitfume.R
import com.example.fitfume.databinding.FragmentRecommendQ1Binding
import com.example.fitfume.viewmodel.GptViewModel

class RecommendQ1Fragment : Fragment() {
    lateinit var binding: FragmentRecommendQ1Binding
    private val viewModel: GptViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recommend_q1, container, false)
        binding.gptVm = viewModel

        binding.recommendManCl.setOnClickListener {
            viewModel.updateRecommendText("남성")
            (activity as RecommendActivity).replaceFragment(RecommendQ2Fragment())
        }

        binding.recommendTwoCl.setOnClickListener {
            viewModel.updateRecommendText("여성")
            (activity as RecommendActivity).replaceFragment(RecommendQ2Fragment())
        }

        return binding.root
    }

}