package com.example.fitfume.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.fitfume.R
import com.example.fitfume.databinding.FragmentRecommendQ2Binding

class RecommendQ2Fragment : Fragment() {
    lateinit var binding: FragmentRecommendQ2Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecommendQ2Binding.inflate(inflater, container, false)
        var result = ""

        setFragmentResultListener("q1Key"){requestKey, bundle ->
            result = bundle.getString("bundleKey")!!
            Log.d("lhj", "Q2: ${result}")
        }

        binding.recommendOneCl.setOnClickListener {
            result = "${result},캐주얼"
            setFragmentResult("q2Key", bundleOf("bundleKey" to result))
            (activity as RecommendActivity).replaceFragment(RecommendQ3Fragment())
        }

        binding.recommendTwoCl.setOnClickListener {
            result = "${result},스트릿"
            setFragmentResult("q2Key", bundleOf("bundleKey" to result))
            (activity as RecommendActivity).replaceFragment(RecommendQ3Fragment())
        }


        binding.recommendThreeCl.setOnClickListener {
            result = "${result},미니멀"
            setFragmentResult("q2Key", bundleOf("bundleKey" to result))
            (activity as RecommendActivity).replaceFragment(RecommendQ3Fragment())
        }

        binding.recommendFourCl.setOnClickListener {
            result = "${result},정장"
            setFragmentResult("q2Key", bundleOf("bundleKey" to result))
            (activity as RecommendActivity).replaceFragment(RecommendQ3Fragment())
        }


        return binding.root
    }

}