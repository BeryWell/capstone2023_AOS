package com.example.fitfume.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.fitfume.databinding.FragmentRecommendQ1Binding

class RecommendQ1Fragment : Fragment() {
    lateinit var binding: FragmentRecommendQ1Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecommendQ1Binding.inflate(inflater, container, false)

        binding.recommendManCl.setOnClickListener {
            val result = "남성"
            setFragmentResult("q1Key", bundleOf("bundleKey" to result))
            (activity as RecommendActivity).replaceFragment(RecommendQ2Fragment())
        }

        binding.recommendTwoCl.setOnClickListener {
            val result = "여성"
            setFragmentResult("q1Key", bundleOf("bundleKey" to result))
            (activity as RecommendActivity).replaceFragment(RecommendQ2Fragment())
        }

        return binding.root
    }

}