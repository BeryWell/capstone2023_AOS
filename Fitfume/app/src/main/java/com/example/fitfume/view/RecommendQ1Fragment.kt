package com.example.fitfume.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_recommend_q1, container, false)
        binding.gptVm = viewModel

        binding.recommendManCl.setOnClickListener {
            viewModel.updateRecommendStateText("남성")
        }

        binding.recommendTwoCl.setOnClickListener {
            viewModel.updateRecommendStateText("여성")
        }

        viewModel.recommendStateText.observe(requireActivity(), Observer {
            when (it) {
                "남성" -> {
                    binding.recommendManCl.isSelected = true
                    binding.recommendTwoCl.isSelected = false
                }
                "여성" -> {
                    binding.recommendManCl.isSelected = false
                    binding.recommendTwoCl.isSelected = true
                }
                else -> {
                    binding.recommendManCl.isSelected = false
                    binding.recommendTwoCl.isSelected = false
                }
            } 
        })

        binding.recommendNextBtn.setOnClickListener {
            viewModel.updateRecommendTextArr(viewModel.recommendStateText.value!!)
            (activity as RecommendActivity).replaceFragment(RecommendQ2Fragment())
        }

        binding.recommendPreviewBtn.setOnClickListener {
            val intent = Intent(requireActivity(), HomeActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()

        viewModel.recommendStateText.removeObservers(this)
    }

}